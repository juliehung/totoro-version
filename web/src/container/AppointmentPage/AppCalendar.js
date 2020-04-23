import React from 'react';
import FullCalendar from '@fullcalendar/react';
import dayGridPlugin from '@fullcalendar/daygrid';
import timeGridPlugin from '@fullcalendar/timegrid';
import listPlugin from '@fullcalendar/list';
import momentPlugin from '@fullcalendar/moment';
import resourceTimeGridPlugin from '@fullcalendar/resource-timegrid';
import interactionPlugin from '@fullcalendar/interaction';
import moment from 'moment';
import { connect } from 'react-redux';
import '@fullcalendar/core/main.css';
import '@fullcalendar/daygrid/main.css';
import '@fullcalendar/timegrid/main.css';
import '@fullcalendar/list/main.css';
import {
  changeCalDate,
  changeCalFirstDay,
  getAppointments,
  changeConfirmModalVisible,
  insertPendingInfo,
  changeCreateAppExpectedArrivalDate,
  changeCreateAppExpectedArrivalTime,
  changeCreateAppModalVisible,
  changeCreateAppDoctor,
  changeEditAppModalVisible,
  insertAppToEditAppModal,
  getCalendarEvent,
  insertCalEvtToEditCalEvtModal,
  changeEditCalModalVisible,
  deleteAppointment,
  deleteCalEvt,
  popoverCancelApp,
  changeSelectedDoctors,
  sendSms,
  getShift,
} from './actions';
import zhTW from '@fullcalendar/core/locales/zh-tw';
import styled from 'styled-components';
import { handleEventRender } from './utils/handleEventRender';
import { handleResourceRender } from './utils/handleResourceRender';
import { convertSettingsToClinicOffEvent } from './utils/convertSettingsToClinicOffEvent';
import { message } from 'antd';
import MqttHelper from '../../utils/mqtt';
import { calFirstDay } from './reducers/calendar';
import MobileDetect from 'mobile-detect';
import extractDoctorsFromUser from '../../utils/extractDoctorsFromUser';
import { convertShitToBackgroundEvent } from './utils/convertShitToBackgroundEvent';
import { reverseEvents } from './utils/reverseEvents';
import { handleResources } from './utils/handleResources';
import { GAevent } from '../../ga';
import { appointmentPage } from './';

//#region
const Container = styled.div`
  height: 100%;
  .fc-toolbar.fc-header-toolbar {
    background: #f3f8ff;
    padding: 17px 23px 17px 38px;
    margin-bottom: 0;
    border-left: 1px solid rgb(221, 221, 221);
    border-right: 1px solid rgb(221, 221, 221);
  }

  .fc-button.fc-button-primary {
    background-color: #fff !important;
    color: rgba(0, 0, 0, 0.65) !important;
    border-color: #d9d9d9 !important;
  }

  @media (max-width: 800px) {
    .fc-button.fc-button-primary {
      display: none !important;
    }
  }

  .eventCard {
    font-weight: 500;
    letter-spacing: 2px;
    padding: 5px;
    font-size: 14px;
  }

  .fc-license-message {
    display: none;
  }
  .fc-unthemed .fc-today.fc-day-header {
    background: rgb(252, 248, 227) !important;
  }

  .fc-unthemed .fc-day.fc-today {
    background: white !important;
  }

  .fc-axis {
    width: 40px !important;
  }

  .fc-body.fc-widget-content > .fc-event-container {
    max-height: 40vh !important;
    overflow: scroll !important;
  }

  .fc-time-grid .fc-slats td {
    height: 3em;
    border-bottom: 0;
  }
`;

const StyledFullCalendar = styled(FullCalendar)`
  font-size: 400px !important;
`;
//#endregion

const md = new MobileDetect(window.navigator.userAgent);
const phone = md.phone();
const defaultView = phone ? 'resourceTimeGridDay' : 'timeGridWeek';

class AppCalendar extends React.Component {
  state = { viewType: defaultView };
  calendarComponentRef = React.createRef();

  componentDidMount() {
    let calendarApi = this.calendarComponentRef.current.getApi();
    calendarApi.gotoDate(this.props.calendarDate.format('YYYY-MM-DD'));

    // XD just delete the message
    const msg = document.querySelector('.fc-license-message');
    if (msg) {
      msg.parentNode.removeChild(msg);
    }

    // mqtt
    MqttHelper.subscribeAppointment(AppCalendar.name, message => {
      let messageObj;
      try {
        messageObj = JSON.parse(message);
      } catch (e) {
        return;
      }

      const { start, end } = this.props.calendarRange;
      const expectedArrivalTime = moment(messageObj.expectedArrivalTime);
      if (expectedArrivalTime.isBetween(start, end)) {
        // TODO: find a better way to update appointments
      }
    });
  }

  generalSetting = [];
  componentDidUpdate(prevProps) {
    if (prevProps.calendarDate.format('YYYY-MM-DD') !== this.props.calendarDate.format('YYYY-MM-DD')) {
      let calendarApi = this.calendarComponentRef.current.getApi();
      calendarApi.gotoDate(this.props.calendarDate.format('YYYY-MM-DD'));
    }

    if (
      prevProps.generalSetting !== this.props.generalSetting ||
      prevProps.calendarRange !== this.props.calendarRange
    ) {
      this.generalSetting = convertSettingsToClinicOffEvent(this.props.generalSetting, this.props.calendarRange);
    }

    if (prevProps.cancelApp !== this.props.cancelApp) {
      this.clickTitle();
      message.success('取消/恢復預約成功');
    }

    //select self on mobile
    const id = this.props.account ? this.props.account.id : undefined;
    if (phone && id) {
      if (this.props.doctors.length !== 0 && this.props.selectedDoctors.length === 0) {
        this.props.changeSelectedDoctors([id.toString()]);
      }
    }
    if (this.calendarComponentRef.current) {
      let calendarApi = this.calendarComponentRef.current.getApi();
      calendarApi.updateSize();
    }
  }

  componentWillUnmount() {
    MqttHelper.unsubscribeAppointment(AppCalendar.name);
  }

  simulateMouseClick = element => {
    const mouseClickEvents = ['mousedown', 'click', 'mouseup'];
    mouseClickEvents.forEach(mouseEventType =>
      element.dispatchEvent(
        new MouseEvent(mouseEventType, {
          view: window,
          bubbles: true,
          cancelable: true,
          buttons: 1,
        }),
      ),
    );
  };

  clickTitle = () => {
    const title = document.querySelector('.fc-center');
    if (title) {
      this.simulateMouseClick(title);
    }
  };

  scrollListener = undefined;
  handleDatesRender = info => {
    // reset first day when leave timeGridWeek
    this.setState({ viewType: info.view.type });
    if (info.view.type !== 'timeGridWeek') {
      this.props.changeCalFirstDay(calFirstDay);
    }

    if (this.calendarComponentRef.current) {
      let calendarApi = this.calendarComponentRef.current.getApi();
      const fullcalendarDate = moment(calendarApi.getDate());
      if (this.props.calendarDate.format('YYYY-MM-DD') !== fullcalendarDate.format('YYYY-MM-DD')) {
        this.props.changeCalDate(moment(fullcalendarDate));
      }
    }
    const start = moment(info.view.activeStart);
    const end = moment(info.view.activeEnd);
    this.props.getAppointments(start, end);
    this.props.getCalendarEvent(start, end);
    this.props.getShift(start, end);

    // listen fullcalendar scroll event
    if (this.scrollListener) {
      this.scrollListener.removeEventListener('scroll', this.clickTitle);
    }
    this.scrollListener = document.querySelector('.fc-scroller');
    this.scrollListener.addEventListener('scroll', this.clickTitle);
  };

  nextClick = () => {
    let calendarApi = this.calendarComponentRef.current.getApi();
    const currentViewType = calendarApi.state.viewType;
    if (currentViewType === 'timeGridWeek') {
      this.props.changeCalFirstDay(this.props.firstDay + 1);
      this.props.changeCalDate(moment(calendarApi.getDate()).add(1, 'day'));
      return;
    }
    calendarApi.next();
  };

  prevClick = () => {
    let calendarApi = this.calendarComponentRef.current.getApi();
    const currentViewType = calendarApi.state.viewType;
    if (currentViewType === 'timeGridWeek') {
      this.props.changeCalFirstDay(this.props.firstDay - 1);
      this.props.changeCalDate(moment(calendarApi.getDate()).add(-1, 'day'));
      return;
    }
    calendarApi.prev();
  };

  nextMonthClick = () => {
    let calendarApi = this.calendarComponentRef.current.getApi();
    const currentViewType = calendarApi.state.viewType;
    if (currentViewType === 'dayGridMonth') {
      calendarApi.nextYear();
      return;
    }
    this.props.changeCalDate(moment(calendarApi.getDate()).add(1, 'month'));
  };

  prevMonthClick = () => {
    let calendarApi = this.calendarComponentRef.current.getApi();
    const currentViewType = calendarApi.state.viewType;
    if (currentViewType === 'dayGridMonth') {
      calendarApi.prevYear();
      return;
    }
    this.props.changeCalDate(moment(calendarApi.getDate()).add(-1, 'month'));
  };

  todayClick = () => {
    let calendarApi = this.calendarComponentRef.current.getApi();
    calendarApi.gotoDate(moment().format('YYYY-MM-DD'));
  };

  handleEventDrop = info => {
    if (info.event.extendedProps.eventType === 'appointment') {
      this.props.changeConfirmModalVisible(true);
      this.props.insertPendingInfo(info);
    }
  };

  handleEventResize = info => {
    if (info.event.extendedProps.eventType === 'appointment') {
      this.props.changeConfirmModalVisible(true);
      this.props.insertPendingInfo(info);
    }
  };

  clicks = 0;
  clearClicks = undefined;
  handleDateClick = info => {
    this.clicks++;
    if (this.clicks === 2) {
      const date = moment(info.date);
      if (info.resource) {
        const doctor = parseInt(info.resource.id);
        this.handleDblClick(date, doctor);
      } else {
        this.handleDblClick(date);
      }
    }
    this.clearClicks = setTimeout(() => {
      this.clicks = 0;
    }, 300);
  };

  handleDblClick = (date, doctor) => {
    this.props.changeCreateAppModalVisible(true);
    this.props.changeCreateAppExpectedArrivalDate(date);
    this.props.changeCreateAppExpectedArrivalTime(date);
    if (doctor) {
      this.props.changeCreateAppDoctor(doctor);
    }
    GAevent(appointmentPage, 'DblClick calendar to create appt');
  };

  eventRender = info => {
    if (info.event.extendedProps.eventType === 'appointment') {
      handleEventRender(info, {
        edit: this.handleAppointmentDblClick,
        cancel: this.handlePopoverCancelApp,
        send: this.handleSendSms,
      });
    } else if (info.event.extendedProps.eventType === 'doctorDayOff') {
      handleEventRender(info, { edit: this.handleCalEvtDblClick });
    }
  };

  handleAppointmentDblClick = app => {
    this.props.changeEditAppModalVisible(true);
    this.props.insertAppToEditAppModal(app);
    GAevent(appointmentPage, 'DblClick appt to edit');
  };

  handleCalEvtDblClick = calEvt => {
    this.props.changeEditCalModalVisible(true);
    this.props.insertCalEvtToEditCalEvtModal(calEvt);
    GAevent(appointmentPage, 'DblClick calendar event to edit');
  };

  handleSendSms = app => {
    this.props.sendSms(app);
  };

  handlePopoverCancelApp = apptData => {
    this.props.popoverCancelApp(apptData);
  };

  eventEditStart = () => {
    if (this.scrollListener) {
      this.scrollListener.removeEventListener('scroll', this.clickTitle);
    }
  };

  eventEditStop = () => {
    this.scrollListener = document.querySelector('.fc-scroller');
    this.scrollListener.addEventListener('scroll', this.clickTitle);
  };

  eventAllow = dropInfo => {
    if (dropInfo.allDay) {
      return false;
    }
    return true;
  };

  render() {
    const shiftOpen = this.props.generalSetting && this.props.generalSetting.showShift;

    const event = [
      ...(shiftOpen
        ? this.props.appointments
        : this.props.appointments.filter(a => {
            if (this.props.selectedAllDoctors) {
              return a;
            }
            return this.props.selectedDoctors.includes(a.appointment.doctor.user.id.toString());
          })),
      ...this.props.calendarEvents.filter(() => this.props.showCalEvt),
      ...(shiftOpen
        ? reverseEvents(this.props.backgroundEvent, this.state.viewType, this.props.calendarRange)
        : this.generalSetting),
    ];

    const resource = shiftOpen
      ? handleResources(this.props.doctors, this.props.backgroundEvent, this.props.getShiftSuccess)
      : this.props.doctors
          .filter(d => d.activated)
          .filter(d => {
            if (this.props.selectedAllDoctors) {
              return d;
            } else {
              return this.props.selectedDoctors.includes(d.id.toString());
            }
          })
          .map(d => ({ id: d.id, title: d.name }));

    return (
      <Container>
        <StyledFullCalendar
          ref={this.calendarComponentRef}
          height="parent"
          resources={resource}
          events={event}
          plugins={[interactionPlugin, timeGridPlugin, dayGridPlugin, resourceTimeGridPlugin, listPlugin, momentPlugin]}
          header={{
            left: 'customPrevMonthButton,customPrevButton,customNextButton,customNextMonthButton, customToday',
            center: 'title',
            right: 'resourceTimeGridDay,timeGridWeek,dayGridMonth,listWeek',
          }}
          customButtons={{
            customNextButton: {
              click: this.nextClick,
              icon: 'chevron-right',
            },
            customPrevButton: {
              click: this.prevClick,
              icon: 'chevron-left',
            },
            customPrevMonthButton: {
              click: this.prevMonthClick,
              icon: 'chevrons-left',
            },
            customNextMonthButton: {
              click: this.nextMonthClick,
              icon: 'chevrons-right',
            },
            customToday: {
              click: this.todayClick,
              text: '今日',
            },
          }}
          buttonText={{
            listWeek: '時間表',
          }}
          titleRangeSeparator=" ~ "
          titleFormat={{ year: 'numeric', month: 'numeric', day: 'numeric' }}
          eventTimeFormat={{
            hour: '2-digit',
            minute: '2-digit',
            hour12: false,
          }}
          slotLabelFormat={{
            hour: '2-digit',
            minute: '2-digit',
            hour12: false,
          }}
          minTime="08:00:00"
          scrollTime="08:30:00"
          slotLabelInterval={{ hours: 0.5 }}
          slotDuration={`00:${this.props.slotDuration}:00`}
          locales={zhTW}
          locale="zh-tw"
          datesRender={this.handleDatesRender}
          dateClick={this.handleDateClick}
          slotWidth={2}
          firstDay={this.props.firstDay}
          eventRender={this.eventRender}
          resourceRender={handleResourceRender}
          eventDrop={this.handleEventDrop}
          eventResize={this.handleEventResize}
          navLinks
          editable
          droppable
          nowIndicator
          eventLimit
          selectable
          eventDragStart={this.eventEditStart}
          eventDragStop={this.eventEditStop}
          eventResizeStart={this.eventEditStart}
          eventResizeStop={this.eventEditStop}
          timeGridEventMinHeight={15}
          eventAllow={this.eventAllow}
          defaultView={defaultView}
        />
      </Container>
    );
  }
}

const mapStateToProps = ({ homePageReducer, appointmentPageReducer }) => ({
  calendarDate: appointmentPageReducer.calendar.calendarDate,
  appointments: appointmentPageReducer.calendar.appointments,
  firstDay: appointmentPageReducer.calendar.calendarFirstDay,
  selectedDoctors: appointmentPageReducer.calendar.selectedDoctors,
  selectedAllDoctors: appointmentPageReducer.calendar.selectedAllDoctors,
  doctors: extractDoctorsFromUser(homePageReducer.user.users),
  calendarEvents: appointmentPageReducer.calendar.calendarEvents,
  slotDuration: appointmentPageReducer.calendar.slotDuration,
  showCalEvt: appointmentPageReducer.calendar.showCalEvt,
  generalSetting: homePageReducer.settings.generalSetting,
  calendarRange: appointmentPageReducer.calendar.range,
  cancelApp: appointmentPageReducer.calendar.cancelApp,
  account: homePageReducer.account.data,
  backgroundEvent: convertShitToBackgroundEvent(appointmentPageReducer.shift.shift),
  getShiftSuccess: appointmentPageReducer.shift.getShiftSuccess,
});

const mapDispatchToProps = {
  changeCalDate,
  changeCalFirstDay,
  getAppointments,
  changeConfirmModalVisible,
  insertPendingInfo,
  changeCreateAppModalVisible,
  changeCreateAppExpectedArrivalDate,
  changeCreateAppExpectedArrivalTime,
  changeCreateAppDoctor,
  changeEditAppModalVisible,
  insertAppToEditAppModal,
  getCalendarEvent,
  insertCalEvtToEditCalEvtModal,
  changeEditCalModalVisible,
  deleteAppointment,
  deleteCalEvt,
  popoverCancelApp,
  changeSelectedDoctors,
  sendSms,
  getShift,
};

export default connect(mapStateToProps, mapDispatchToProps)(AppCalendar);
