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
import TwoArrowLeft from '../../images/arrow-left.svg';
import TwoArrowRight from '../../images/arrow-right.svg';
import ArrowLeft from '../../images/two-arrow-left.svg';
import ArrowRight from '../../images/two-arrow-right.svg';
import { parseDisplayRange } from './utils/parseDisplayRange';

//#region
const Container = styled.div`
  display: flex;
  flex-direction: column;
  width: 85%;
  @media (max-width: 800px) {
    width: 100%;
    height: 600px;
  }
`;

const Header = styled.div`
  flex-shrink: 0;
  height: 76px;
  display: flex;
  align-items: center;
  justify-content: space-between;
  padding: 0 1%;
  @media (max-width: 850px) {
    justify-content: flex-end;
  }
`;

const TodayContainer = styled.div`
  display: flex;
  align-items: center;
  font-size: 13px;
  display: flex;
  color: #8f9bb3;
  & > div {
    font-size: 10px;
    padding: 5px 10px;
    border: solid 1px #8f9bb3;
    border-radius: 34px;
    margin-left: 7px;
    cursor: pointer;
  }
  @media (max-width: 850px) {
    display: none;
  }
`;

const TitleContainer = styled.div`
  font-size: 18px;
  font-weight: bold;
  display: flex;
  align-items: center;
  color: #222b45;
  & > img {
    width: 24px;
    height: 24px;
    cursor: pointer;
  }
  & > :nth-child(1),
  & > :nth-child(2),
  & > :nth-child(3) {
    margin-right: 16px;
  }

  @media (max-width: 1180px) {
    display: none;
  }
`;

const ViewContainer = styled.div`
  font-size: 12px;
  display: flex;
  height: 32px;
  color: #3366ff;
  font-weight: bold;
  cursor: pointer;
`;

const ViewItem = styled.div`
  display: flex;
  justify-content: center;
  align-items: center;
  border: 1px solid #3266ff;
  width: 60px;
  &:first-child {
    border-top-left-radius: 25px;
    border-bottom-left-radius: 25px;
  }
  &:last-child {
    border-top-right-radius: 25px;
    border-bottom-right-radius: 25px;
  }

  background: ${props => (props.selected ? '#3366ff' : '#fff')};
  color: ${props => (props.selected ? '#fff' : '#3366ff')};
`;

const CalendarContainer = styled.div`
  flex: 1;
  overflow: scroll;
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

  .fc-unthemed thead,
  .fc-unthemed tbody {
    /* border-color: red !important; */
  }

  .fc-axis.fc-time {
    color: #8f9bb3;
  }

  .fc-day-header.fc-widget-header {
    & > a {
      color: #222b45;
      font-weight: 600;
    }
  }

  .fc-widget-header,
  .fc-widget-content {
    border: 1px solid #d7e3f1;
  }

  .fc-bgevent {
    color: rgba(0, 0, 0, 0.1);
    background-image: repeating-linear-gradient(
      45deg,
      currentColor 0,
      currentColor 1px,
      transparent 0,
      transparent 50%
    );
    background-size: 20px 20px;
  }
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

  onDayClick = () => {
    this.changeView('resourceTimeGridDay');
  };

  onWeekClick = () => {
    this.changeView('timeGridWeek');
  };

  onMonthClick = () => {
    this.changeView('dayGridMonth');
  };

  onListClick = () => {
    this.changeView('listWeek');
  };

  changeView = view => {
    let calendarApi = this.calendarComponentRef.current.getApi();
    calendarApi.changeView(view);
  };

  viewSkeletonRender = info => {
    this.setState({ viewType: info.view.type });
  };

  navLinkDayClick = date => {
    this.props.changeCalDate(moment(date));
    let calendarApi = this.calendarComponentRef.current.getApi();
    calendarApi.changeView('resourceTimeGridDay');
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
          .map(d => ({ id: d.id, title: d.name, avatar: d.avatar }));

    return (
      <Container>
        <Header>
          <TodayContainer>
            <span>{moment().format('LLLL')}</span>
            <div onClick={this.todayClick}>
              <span>今日</span>
            </div>
          </TodayContainer>
          <TitleContainer className="fc-center">
            <img src={TwoArrowLeft} alt="two-arrow-left" onClick={this.prevMonthClick} />
            <img src={ArrowLeft} alt="arrow-left" onClick={this.prevClick} />
            <span>{parseDisplayRange(this.props.calendarRange)}</span>
            <img src={ArrowRight} alt="arrow-right" onClick={this.nextClick} />
            <img src={TwoArrowRight} alt="two-arrow-right" onClick={this.nextMonthClick} />
          </TitleContainer>
          <ViewContainer>
            <ViewItem onClick={this.onDayClick} selected={this.state.viewType === 'resourceTimeGridDay'}>
              <span>天</span>
            </ViewItem>
            <ViewItem onClick={this.onWeekClick} selected={this.state.viewType === 'timeGridWeek'}>
              <span>周</span>
            </ViewItem>
            <ViewItem onClick={this.onMonthClick} selected={this.state.viewType === 'dayGridMonth'}>
              <span>月</span>
            </ViewItem>
            <ViewItem onClick={this.onListClick} selected={this.state.viewType === 'listWeek'}>
              <span>周列表</span>
            </ViewItem>
          </ViewContainer>
        </Header>
        <CalendarContainer>
          <FullCalendar
            ref={this.calendarComponentRef}
            height="parent"
            resources={resource}
            events={event}
            plugins={[
              interactionPlugin,
              timeGridPlugin,
              dayGridPlugin,
              resourceTimeGridPlugin,
              listPlugin,
              momentPlugin,
            ]}
            header={false}
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
            navLinkDayClick={this.navLinkDayClick}
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
            viewSkeletonRender={this.viewSkeletonRender}
          />
        </CalendarContainer>
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
  getShift,
};

export default connect(mapStateToProps, mapDispatchToProps)(AppCalendar);
