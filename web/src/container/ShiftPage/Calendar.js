import React, { useRef, useEffect, useState } from 'react';
import { connect } from 'react-redux';
import FullCalendar from '@fullcalendar/react';
import interactionPlugin from '@fullcalendar/interaction';
import resourceTimelinePlugin from '@fullcalendar/resource-timeline';
import zhTW from '@fullcalendar/core/locales/zh-tw';
import '@fullcalendar/core/main.css';
import '@fullcalendar/timeline/main.css';
import '@fullcalendar/resource-timeline/main.css';
import extractDoctorsFromUser from '../../utils/extractDoctorsFromUser';
import convertShiftToEvent from './utils/convertShiftToEvent';
import handlePopoverPosition from './utils/handlePopoverPosition';
import handleShiftEvtTitle from './utils/handleShiftEvtTitle';
import { handleResourceRender } from './utils/handleResourceRender';
import {
  changeDate,
  getShift,
  editShift,
  shiftDrop,
  changeResourceColor,
  deleteShift,
  copyShift,
  changeCopyModalVisible,
  changeSelectAllDoctor,
} from './actions';
import { handleEventDrop } from './utils/handleEventDrop';
import { handleEventRender } from './utils/handleEventRender';
import moment from 'moment';
import styled, { createGlobalStyle } from 'styled-components';
import ArrowRight from '../../images/two-arrow-right.svg';
import ArrowLeft from '../../images/two-arrow-left.svg';
import { CSSTransition } from 'react-transition-group';
import './Calendar.css';
import { usePrevious } from '../../utils/hooks/usePrevious';
import { weekOfMonth } from './utils/weekOfMonth';
import CopyModal from './CopyModal';
import { addAllDay } from './utils/addAllDay';

//#region
const GlobalStyle = createGlobalStyle`
  .fc-no-scrollbars {
    overflow: hidden !important;
  }
  .fc-resource-area .fc-scroller {
    overflow: hidden !important;
  }
  .fc-title.fc-sticky {
    width: 100%;
  }

  .fc-view-container * {
    scrollbar-width: none;
  }

  .fc-view-container *::-webkit-scrollbar {
    display: none;
  }
`;

const Container = styled.div`
  display: flex;
  flex-direction: column;
  height: 100%;

  .fc-widget-header,
  .fc-widget-content {
    border-color: #d7e3f1;
  }
`;

const Header = styled.div`
  display: flex;
  justify-content: space-around;
  align-items: center;
  padding: 25px 0;
  position: relative;
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
  & > :nth-child(2) {
    margin-right: 16px;
  }
`;

const SavedIndicator = styled.span`
  position: absolute;
  right: 3%;
  font-weight: 600;
  font-size: 12px;
  color: #8992a3;
  user-select: none;
`;

const CalendarContainer = styled.div`
  flex-grow: 1;
  flex-shrink: 1;
`;

const CopyAllShiftContainer = styled.div`
  position: absolute;
  left: 3%;
  font-weight: 600;
  font-size: 14px;
  color: #8992a3;
  user-select: none;
  cursor: pointer;
`;

//#endregion

function Calendar(props) {
  const {
    shiftDrop,
    setClickInfo,
    setPopoverVisible,
    deleteShift,
    popoverSize,
    createShiftSuccess,
    deleteSuccess,
    editShiftSuccess,
    changeColorSuccess,
    range,
    copySuccess,
    getShift,
    changeCopyModalVisible,
  } = props;
  const calendarRef = useRef(null);

  const [showSaved, setShowSaved] = useState(false);

  const prevCreateShiftSuccess = usePrevious(createShiftSuccess);
  const prevDeleteSuccess = usePrevious(deleteSuccess);
  const prevEditShiftSuccess = usePrevious(editShiftSuccess);
  const prevChangeColorSuccess = usePrevious(changeColorSuccess);
  const prevCopySuccess = usePrevious(copySuccess);

  useEffect(() => {
    if (
      (createShiftSuccess && !prevCreateShiftSuccess) ||
      (editShiftSuccess && !prevEditShiftSuccess) ||
      (deleteSuccess && !prevDeleteSuccess) ||
      (changeColorSuccess && !prevChangeColorSuccess) ||
      (copySuccess && !prevCopySuccess)
    ) {
      setShowSaved(true);
      requestAnimationFrame(() => {
        setShowSaved(false);
      });
    }
  }, [
    createShiftSuccess,
    deleteSuccess,
    editShiftSuccess,
    changeColorSuccess,
    copySuccess,
    prevCreateShiftSuccess,
    prevDeleteSuccess,
    prevEditShiftSuccess,
    prevChangeColorSuccess,
    prevCopySuccess,
    setShowSaved,
  ]);

  useEffect(() => {
    if (copySuccess) {
      getShift(range);
      changeCopyModalVisible(false);
    }
  }, [copySuccess, range, getShift, changeCopyModalVisible]);

  const datesRender = ({ view }) => {
    const start = view.activeStart;
    const end = view.activeEnd;
    props.changeDate({ start, end });
  };

  const dateClick = dateClickInfo => {
    const hightlight = document.querySelector('.fc-highlight');
    if (hightlight) {
      const highlightRec = hightlight.getBoundingClientRect();
      const right = highlightRec.right;
      const top = highlightRec.top;
      const left = highlightRec.left;
      const { resource, date } = dateClickInfo;
      const id = Object.keys(props.resourceColor).find(id => id === resource.id);
      let color;
      if (id) {
        color = props.resourceColor[id];
      }
      setClickInfo({
        position: handlePopoverPosition({ right, left, top }, popoverSize),
        date,
        resourceId: resource.id,
        color,
      });
      setPopoverVisible(true);
    }
  };

  const eventDrop = eventDropInfo => {
    props.editShift(handleEventDrop(eventDropInfo));
  };

  const eventResize = eventResizeInfo => {
    props.editShift(handleEventDrop(eventResizeInfo));
  };

  const drop = info => {
    const resourceId = info.resource.id;
    const date = info.date;
    const timeRange = {
      start: moment(info.draggedEl.dataset.start, 'HH:mm'),
      end: moment(info.draggedEl.dataset.end, 'HH:mm'),
    };
    shiftDrop({ resourceId, date, timeRange });
  };

  const resourceRender = info => {
    handleResourceRender(info, {
      copyShiftClick: handleCopyShiftClick,
      colorClick: handleResourceColorClick,
      resourceColor: props.resourceColor,
    });
  };

  const handleResourceColorClick = (id, color) => {
    props.changeResourceColor(id, color);
  };

  const handleCopyShiftClick = doctor => {
    changeCopyModalVisible(true);
    props.copyShift(doctor, range);
  };

  const handleCopyAllShiftClick = () => {
    changeCopyModalVisible(true);
    props.changeSelectAllDoctor(true);
    props.copyShift(null, range);
  };

  const eventRender = info => {
    handleEventRender(info, { deleteShift });
  };

  const prevClick = () => {
    const calendarApi = calendarRef.current.getApi();
    if (calendarApi) {
      calendarApi.prev();
    }
  };

  const nextClick = () => {
    const calendarApi = calendarRef.current.getApi();
    if (calendarApi) {
      calendarApi.next();
    }
  };

  return (
    <Container>
      <GlobalStyle />
      <CopyModal />
      <Header>
        <CSSTransition className="savedTransition" timeout={3000} in={showSaved} unmountOnExit>
          <SavedIndicator>已儲存!</SavedIndicator>
        </CSSTransition>
        <CopyAllShiftContainer onClick={handleCopyAllShiftClick}>複製全醫師班表</CopyAllShiftContainer>
        <TitleContainer className="fc-center">
          <img src={ArrowLeft} alt="arrow-left" onClick={prevClick} />
          <span>{`${moment(range.start).format('YYYY 第 w 週, MMM')} 第 ${weekOfMonth(moment(range.start))} 週`}</span>
          <img src={ArrowRight} alt="arrow-right" onClick={nextClick} />
        </TitleContainer>
      </Header>
      <CalendarContainer>
        <FullCalendar
          ref={calendarRef}
          header={false}
          locales={zhTW}
          locale="zh-tw"
          height="parent"
          resources={props.resource}
          resourceRender={resourceRender}
          events={props.event}
          eventRender={eventRender}
          plugins={[interactionPlugin, resourceTimelinePlugin]}
          defaultView={'resourceTimelineWeek'}
          editable={true}
          eventDurationEditable={false}
          resourceLabelText={'Doctor'}
          resourceAreaWidth={'250px'}
          datesRender={datesRender}
          dateClick={dateClick}
          eventDrop={eventDrop}
          eventResize={eventResize}
          drop={drop}
          slotLabelFormat={{ day: 'numeric', weekday: 'short', month: 'numeric' }}
          slotDuration="24:00:00"
          displayEventTime={false}
          selectable
          selectLongPressDelay={500}
          scrollTime="00:00:00"
        />
      </CalendarContainer>
    </Container>
  );
}

const mapStateToProps = ({ homePageReducer, shiftPageReducer }) => ({
  resource: extractDoctorsFromUser(homePageReducer.user.users.filter(u => u.activated)).map(d => ({
    id: d.id,
    title: d.name,
    avatar: d.avatar,
    doctor: d,
  })),
  range: shiftPageReducer.shift.range,
  event: handleShiftEvtTitle(
    convertShiftToEvent(shiftPageReducer.shift.shift, shiftPageReducer.resourceColor.color),
    shiftPageReducer.defaultShift.shift,
  ).map(addAllDay),
  resourceColor: shiftPageReducer.resourceColor.color,
  createShiftSuccess: shiftPageReducer.shift.createShiftSuccess,
  deleteSuccess: shiftPageReducer.shift.deleteSuccess,
  editShiftSuccess: shiftPageReducer.shift.editShiftSuccess,
  changeColorSuccess: shiftPageReducer.resourceColor.changeColorSuccess,
  copySuccess: shiftPageReducer.copy.success,
});

const mapDispatchToProps = {
  changeDate,
  getShift,
  editShift,
  shiftDrop,
  changeResourceColor,
  deleteShift,
  copyShift,
  changeCopyModalVisible,
  changeSelectAllDoctor,
};

export default connect(
  mapStateToProps,
  mapDispatchToProps,
)(
  React.memo(Calendar, (prevProps, nextProps) => {
    return prevProps.popoverVisible && !nextProps.popoverVisible;
  }),
);
