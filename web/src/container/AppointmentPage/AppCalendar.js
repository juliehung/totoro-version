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
  popoverRestoreApp,
  changeSelectedDoctors,
  getShift,
  changeCalendarRange,
} from './actions';
import { openXray } from '../Home/actions';
import zhTW from '@fullcalendar/core/locales/zh-tw';
import styled from 'styled-components';
import { handleEventRender } from './utils/handleEventRender';
import { handleResourceRender } from './utils/handleResourceRender';
import { convertSettingsToClinicOffEvent } from './utils/convertSettingsToClinicOffEvent';
import { message, Spin, Popover, Button } from 'antd';
import { calFirstDay } from './reducers/calendar';
import extractDoctorsFromUser from '../../utils/extractDoctorsFromUser';
import { convertShitToBackgroundEvent } from './utils/convertShitToBackgroundEvent';
import { reverseEvents, generateRangeEvent } from './utils/reverseEvents';
import { GAevent } from '../../ga';
import { appointmentPage } from './';
import TwoArrowLeft from '../../images/arrow-left.svg';
import TwoArrowRight from '../../images/arrow-right.svg';
import ArrowLeft from '../../images/two-arrow-left.svg';
import ArrowRight from '../../images/two-arrow-right.svg';
import EyeFill from '../../images/eye-fill.svg';
import EyeFillWhite from '../../images/eye-fill-white.svg';
import EyeOffFill from '../../images/eye-off-fill.svg';
import { parseDisplayRange } from './utils/parseDisplayRange';
import TimeDisplay from './TimeDisplay';
import MqttHelper from '../../utils/mqtt';
import { RedoOutlined, StopOutlined, CheckCircleOutlined } from '@ant-design/icons';
import { handleResources } from './utils/handleResources';

//#region
const Container = styled.div`
  display: flex;
  flex-direction: column;
  width: 85%;
  @media (max-width: 800px) {
    width: 100%;
    min-height: 600px;
  }
`;

const Header = styled.div`
  flex-shrink: 0;
  height: 76px;
  display: flex;
  align-items: center;
  justify-content: space-between;
  padding: 0 1%;
  background: #fff;
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
  font-weight: bold;
  & > div {
    font-size: 10px;
    padding: 5px 10px;
    border: solid 1px #8f9bb3;
    border-radius: 34px;
    margin-left: 7px;
    cursor: pointer;
    background-color: rgba(143, 155, 179, 0.08);
    transition: all 300ms ease-in-out;
    &:hover {
      background-color: rgba(143, 155, 179, 0.4);
    }
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

  @media (max-width: 1250px) {
    display: none;
  }
`;

const HeaderRight = styled.div`
  display: flex;
  align-items: center;
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

const DoctorControlContainer = styled.div`
  width: 40px;
  height: 40px;
  background-color: #edf1f7;
  margin-left: 11px;
  border-radius: 50%;
  cursor: pointer;
  display: flex;
  justify-content: center;
  align-items: center;
  user-select: none;
`;

const DoctorControl = styled.div`
  width: 300px;
  max-height: 500px;

  & > :first-child {
    height: 32px;
    display: flex;
    justify-content: space-between;
    align-items: center;
    font-weight: 600;
    font-size: 13px;
    color: #222b45;
    & > :first-child {
      font-size: 18px;
    }
  }

  & > :nth-child(2) {
    margin-top: 14px;
    display: flex;
    flex-wrap: wrap;
  }
`;

const DoctorControlItem = styled.div`
  width: 45%;
  font-weight: bold;
  font-size: 12px;
  margin: 4px 2.5%;
  padding: 8px 12px;
  border-radius: 34px;
  display: flex;
  align-items: center;
  user-select: none;
  cursor: pointer;
  transition: all ease 300ms;
  color: ${props => (props.selected ? '#fff' : '#8f9bb3')};
  background-color: ${props => (props.selected ? '#008F72' : 'rgba(143, 155, 179, 0.08)')};
  border: ${props => (props.selected ? ' 1px solid #008F72' : '1px solid #8f9bb3')};
  & > img {
    color: red;
    width: 16px;
    margin-right: 6px;
  }
  .on {
    display: ${props => (props.selected ? 'block' : 'none')};
  }
  .off {
    display: ${props => (props.selected ? 'none' : 'block')};
  }
  & > span {
    width: 100%;
    white-space: nowrap;
    overflow: hidden;
    text-overflow: ellipsis;
  }
`;

const CalendarContainer = styled.div`
  position: relative;
  flex: 1;
  overflow: scroll;
  .eventCard {
    font-weight: 500;
    letter-spacing: 2px;
    padding: ${props => (props.fullScreen ? '0 5px' : '5px')};
    font-size: 12px !important;
    font-weight: normal !important;
    & b {
      font-weight: normal !important;
    }
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
    height: ${props => {
      return props.fullScreen ? props.slotHeight + 'px' : '2em';
    }};
    border-bottom: 0;
    font-size: ${props => (props.fullScreen ? '10px !important' : '')};
  }

  .fc-highlight {
    background-color: #3266ff;
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
    border-color: #d7e3f1;
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
    background-size: 10px 10px;
  }

  .fc-day.fc-widget-content {
    ${props =>
      props.noResourceAndShiftOpen
        ? `
            color: rgba(0, 0, 0, 0.1);
            background-image: repeating-linear-gradient(45deg,currentColor 0,currentColor 1px,transparent 0,transparent 50%);
            background-size: 10px 10px;
            background-color: rgba(143, 155, 179, 0.08);
          `
        : ''}
  }
`;

const SpinContainer = styled.div`
  width: 100%;
  height: 100%;
  background-color: rgba(0, 0, 0, 0.08);
  position: absolute;
  z-index: 200;
  display: flex;
  justify-content: center;
  align-items: center;
`;
//#endregion

class AppCalendar extends React.Component {
  calendarComponentRef = React.createRef();
  calendarContainerRef = React.createRef();
  state = { pauseShift: false };

  componentDidMount() {
    const calendarApi = this.calendarComponentRef.current.getApi();
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
        this.getAllEvent();
      }
    });

    this.setState({ pauseShift: false });
  }

  debounceFetch;
  generalSettingEvents = [];

  componentDidUpdate(prevProps) {
    if (prevProps.calendarDate.format('YYYY-MM-DD') !== this.props.calendarDate.format('YYYY-MM-DD')) {
      const calendarApi = this.calendarComponentRef.current.getApi();
      calendarApi.gotoDate(this.props.calendarDate.format('YYYY-MM-DD'));
    }

    if (
      !this.props.calendarRange.start.isSame(prevProps.calendarRange.start) ||
      !this.props.calendarRange.end.isSame(prevProps.calendarRange.end)
    ) {
      clearTimeout(this.debounceFetch);
      this.debounceFetch = setTimeout(() => {
        this.getAllEvent();
      }, 100);
    }

    if (
      prevProps.generalSetting !== this.props.generalSetting ||
      prevProps.calendarRange !== this.props.calendarRange
    ) {
      this.generalSettingEvents = convertSettingsToClinicOffEvent(this.props.generalSetting, this.props.calendarRange);
    }

    if (prevProps.cancelApp !== this.props.cancelApp) {
      this.clickTitle();
      message.success('取消預約成功');
    }

    if (prevProps.restoreApp !== this.props.restoreApp) {
      this.clickTitle();
      message.success('恢復預約成功');
    }

    //select self on mobile
    const id = this.props.account ? this.props.account.id : undefined;
    if (this.props.phone && id) {
      if (this.props.doctors.length !== 0 && this.props.selectedDoctors.length === 0) {
        this.props.changeSelectedDoctors([id]);
      }
    }
    if (this.calendarComponentRef.current) {
      const calendarApi = this.calendarComponentRef.current.getApi();
      calendarApi.updateSize();
    }

    if (prevProps.shiftOpen !== this.props.shiftOpen && this.props.shiftOpen) {
      this.setState({ pauseShift: false });
    }

    // before geting xRayVendors, events have been rendered, so we need rerender here
    if (prevProps.xRayVendors !== this.props.xRayVendors) {
      const calendarApi = this.calendarComponentRef.current.getApi();
      calendarApi.rerenderEvents();
    }
  }

  getAllEvent() {
    this.props.getCalendarEvent();
    this.props.getShift();
    this.props.getAppointments();
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
      const calendarApi = this.calendarComponentRef.current.getApi();
      const fullcalendarDate = moment(calendarApi.getDate());
      if (this.props.calendarDate.format('YYYY-MM-DD') !== fullcalendarDate.format('YYYY-MM-DD')) {
        this.props.changeCalDate(moment(fullcalendarDate));
      }
    }

    const start = moment(info.view.activeStart);
    const end = moment(info.view.activeEnd);
    this.props.changeCalendarRange(start, end);

    // listen fullcalendar scroll event
    if (this.scrollListener) {
      this.scrollListener.removeEventListener('scroll', this.clickTitle);
    }
    this.scrollListener = document.querySelector('.fc-scroller');
    this.scrollListener.addEventListener('scroll', this.clickTitle);
  };

  nextClick = () => {
    const calendarApi = this.calendarComponentRef.current.getApi();
    const currentViewType = calendarApi.state.viewType;
    if (currentViewType === 'timeGridWeek') {
      const nextDay = this.props.calendarDate.clone().add(1, 'd').format('YYYY-MM-DD');
      calendarApi.gotoDate(nextDay);
      this.props.changeCalFirstDay(this.props.firstDay + 1);
      return;
    }
    calendarApi.next();
  };

  prevClick = () => {
    const calendarApi = this.calendarComponentRef.current.getApi();
    const currentViewType = calendarApi.state.viewType;
    if (currentViewType === 'timeGridWeek') {
      const prevDay = this.props.calendarDate.clone().add(-1, 'd').format('YYYY-MM-DD');
      calendarApi.gotoDate(prevDay);
      this.props.changeCalFirstDay(this.props.firstDay - 1);
      return;
    }
    calendarApi.prev();
  };

  nextMonthClick = () => {
    const calendarApi = this.calendarComponentRef.current.getApi();
    const currentViewType = calendarApi.state.viewType;
    if (currentViewType === 'dayGridMonth') {
      calendarApi.nextYear();
      return;
    }
    this.props.changeCalDate(moment(calendarApi.getDate()).add(1, 'month'));
  };

  prevMonthClick = () => {
    const calendarApi = this.calendarComponentRef.current.getApi();
    const currentViewType = calendarApi.state.viewType;
    if (currentViewType === 'dayGridMonth') {
      calendarApi.prevYear();
      return;
    }
    this.props.changeCalDate(moment(calendarApi.getDate()).add(-1, 'month'));
  };

  todayClick = () => {
    const calendarApi = this.calendarComponentRef.current.getApi();
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
      handleEventRender(
        info,
        {
          edit: this.handleAppointmentDblClick,
          cancel: this.handlePopoverCancelApp,
          xray: this.handleXrayClick,
          restore: this.handlePopoverRestoreApp,
        },
        { xRayVendors: this.props.xRayVendors },
      );
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

  handlePopoverRestoreApp = apptData => {
    this.props.popoverRestoreApp(apptData);
  };

  handleXrayClick = data => {
    this.props.openXray(data);
  };

  eventEditStart = () => {
    if (this.scrollListener) {
      this.scrollListener.removeEventListener('scroll', this.clickTitle);
    }
  };

  eventEditStop = () => {
    this.clickTitle();
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
    const calendarApi = this.calendarComponentRef.current.getApi();
    calendarApi.changeView(view);
  };

  viewSkeletonRender = info => {
    this.props.setViewType(info.view.type);
  };

  navLinkDayClick = date => {
    this.props.changeCalDate(moment(date));
    const calendarApi = this.calendarComponentRef.current.getApi();
    calendarApi.changeView('resourceTimeGridDay');
  };

  handleDoctorChange = id => {
    const selectedDoctors =
      this.props.shiftOpen && !this.state.pauseShift
        ? handleResources(this.props.doctors, this.props.backgroundEvent).map(d => d.id)
        : this.props.selectedDoctors;

    if (selectedDoctors.includes(id)) {
      this.props.changeSelectedDoctors(selectedDoctors.filter(s => s !== id));
    } else {
      this.props.changeSelectedDoctors([...selectedDoctors, id]);
    }
    this.setState({ pauseShift: true });
  };

  handleRemoveAllDoctors = () => {
    this.props.changeSelectedDoctors([]);
    this.setState({ pauseShift: true });
  };

  handleSelectAllDoctor = () => {
    const doctors = this.props.doctors.filter(d => d.activated).map(d => d.id);
    this.props.changeSelectedDoctors(doctors);
    this.setState({ pauseShift: true });
  };

  handleResetSelectedDoctor = () => {
    const selectedDoctors = handleResources(this.props.doctors, this.props.backgroundEvent);
    this.props.changeSelectedDoctors(selectedDoctors.map(d => d.id));
    this.setState({ pauseShift: false });
  };

  render() {
    const shiftOpen = this.props.shiftOpen;
    const doctorsFromShift = shiftOpen ? handleResources(this.props.doctors, this.props.backgroundEvent) : [];
    let shiftEvents = reverseEvents(this.props.backgroundEvent, this.props.viewType, this.props.calendarRange);
    if (this.state.pauseShift && this.props.viewType === 'resourceTimeGridDay') {
      const shiftDoctorsId = [...new Set(shiftEvents.map(s => s.resourceId))];
      this.props.selectedDoctors.forEach(d => {
        if (!shiftDoctorsId.find(id => id === d)) {
          shiftEvents = [...shiftEvents, generateRangeEvent(this.props.calendarRange, d)];
        }
      });
    }
    const event = [
      ...(shiftOpen && !this.state.pauseShift
        ? this.props.appointments.filter(a => doctorsFromShift.map(d => d.id).includes(a.appointment.doctor.user.id))
        : this.props.appointments.filter(a => this.props.selectedDoctors.includes(a.appointment.doctor.user.id))),
      ...this.props.calendarEvents,
      ...(shiftOpen ? shiftEvents : this.generalSettingEvents),
    ];

    const resource =
      shiftOpen && !this.state.pauseShift
        ? doctorsFromShift
        : this.props.doctors
            .filter(d => d.activated)
            .filter(d => this.props.selectedDoctors.includes(d.id))
            .map(d => ({ id: d.id, title: d.name, avatar: d.avatar }));

    const doctorControl = (
      <DoctorControl>
        <div>
          <span>檢視</span>
          {this.props.shiftOpen && this.state.pauseShift && (
            <Button icon={<RedoOutlined />} shape={'round'} onClick={this.handleResetSelectedDoctor}>
              重設為班表醫師
            </Button>
          )}
          {resource.length !== 0 ? (
            <Button icon={<StopOutlined />} shape={'round'} onClick={this.handleRemoveAllDoctors}>
              全不選
            </Button>
          ) : (
            <Button icon={<CheckCircleOutlined />} shape={'round'} onClick={this.handleSelectAllDoctor}>
              全選
            </Button>
          )}
        </div>
        <div>
          {this.props.doctors
            .filter(d => d.activated)
            .map(d => {
              const selected =
                shiftOpen && !this.state.pauseShift
                  ? doctorsFromShift.map(d => d.id).includes(d.id)
                  : this.props.selectedDoctors.includes(d.id);
              return (
                <DoctorControlItem
                  key={d.id}
                  selected={selected}
                  onClick={() => {
                    this.handleDoctorChange(d.id);
                  }}
                >
                  <img className="off" src={EyeOffFill} alt="icon" />
                  <img className="on" src={EyeFillWhite} alt="icon" />
                  <span>{`${d.name}(${this.props.doctorAppCount[d.id] ?? 0})`}</span>
                </DoctorControlItem>
              );
            })}
        </div>
      </DoctorControl>
    );

    let slotHeight;
    if (this.calendarContainerRef.current) {
      slotHeight = this.calendarContainerRef.current.clientHeight / 36;
    }
    return (
      <Container>
        <Header>
          <TodayContainer>
            <TimeDisplay />
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
          <HeaderRight>
            <ViewContainer>
              <ViewItem onClick={this.onDayClick} selected={this.props.viewType === 'resourceTimeGridDay'}>
                <span>天</span>
              </ViewItem>
              <ViewItem onClick={this.onWeekClick} selected={this.props.viewType === 'timeGridWeek'}>
                <span>周</span>
              </ViewItem>
              <ViewItem onClick={this.onMonthClick} selected={this.props.viewType === 'dayGridMonth'}>
                <span>月</span>
              </ViewItem>
              <ViewItem onClick={this.onListClick} selected={this.props.viewType === 'listWeek'}>
                <span>周列表</span>
              </ViewItem>
            </ViewContainer>
            <Popover placement="bottomLeft" trigger="hover" content={doctorControl}>
              <DoctorControlContainer>
                <img src={EyeFill} alt={'select doctor'} />
              </DoctorControlContainer>
            </Popover>
          </HeaderRight>
        </Header>
        <CalendarContainer
          noResourceAndShiftOpen={!resource.length && shiftOpen && this.props.viewType === 'resourceTimeGridDay'}
          ref={this.calendarContainerRef}
          slotHeight={slotHeight}
          slotDuration={this.props.slotDuration}
          fullScreen={this.props.calendarFullScreen}
        >
          {this.props.loading && (
            <SpinContainer>
              <Spin spinning={this.props.loading} />
            </SpinContainer>
          )}
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
            slotLabelInterval={{ hours: 1 }}
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
            defaultView={this.props.defaultView}
            viewSkeletonRender={this.viewSkeletonRender}
            listDayAltFormat={'MMMDo'}
          />
        </CalendarContainer>
      </Container>
    );
  }
}

const mapStateToProps = ({ homePageReducer, appointmentPageReducer, settingPageReducer }) => ({
  calendarDate: appointmentPageReducer.calendar.calendarDate,
  appointments: appointmentPageReducer.calendar.appointments,
  firstDay: appointmentPageReducer.calendar.calendarFirstDay,
  selectedDoctors: appointmentPageReducer.calendar.selectedDoctors,
  doctors: extractDoctorsFromUser(homePageReducer.user.users),
  doctorAppCount: appointmentPageReducer.calendar.doctorAppCount,
  calendarEvents: appointmentPageReducer.calendar.calendarEvents,
  slotDuration: appointmentPageReducer.calendar.slotDuration,
  calendarFullScreen: appointmentPageReducer.calendar.calendarFullScreen,
  xRayVendors: settingPageReducer.configurations.config.xRayVendors,
  generalSetting: homePageReducer.settings.settings?.preferences?.generalSetting,
  calendarRange: appointmentPageReducer.calendar.range,
  cancelApp: appointmentPageReducer.calendar.cancelApp,
  restoreApp: appointmentPageReducer.calendar.restoreApp,
  account: homePageReducer.account.data,
  backgroundEvent: convertShitToBackgroundEvent(appointmentPageReducer.shift.shift),
  getShiftSuccess: appointmentPageReducer.shift.getShiftSuccess,
  loading:
    !appointmentPageReducer.shift.getShiftSuccess ||
    !appointmentPageReducer.calendar.getSuccess ||
    !homePageReducer.user.getSuccess,
  shiftOpen: appointmentPageReducer.shift.shiftOpen,
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
  popoverRestoreApp,
  changeSelectedDoctors,
  getShift,
  openXray,
  changeCalendarRange,
};

export default connect(mapStateToProps, mapDispatchToProps)(AppCalendar);
