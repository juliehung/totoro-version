import React from 'react';
import FullCalendar from '@fullcalendar/react';
import '@fullcalendar/core/main.css';
import interactionPlugin from '@fullcalendar/interaction';
import resourceTimelinePlugin from '@fullcalendar/resource-timeline';
import zhTW from '@fullcalendar/core/locales/zh-tw';
import '@fullcalendar/daygrid/main.css';
import '@fullcalendar/timegrid/main.css';
import '@fullcalendar/list/main.css';
import { connect } from 'react-redux';
import '@fullcalendar/timeline/main.css';
import '@fullcalendar/resource-timeline/main.css';
import extractDoctorsFromUser from '../../utils/extractDoctorsFromUser';
import convertShiftToEvent from './utils/convertShiftToEvent';
import handlePopoverPosition from './utils/handlePopoverPosition';
import handleShiftEvtTitle from './utils/handleShiftEvtTitle';
import { handleResourceRender } from './utils/handleResourceRender';
import { changeDate, getShift, editShift, shiftDrop, changeResourceColor, deleteShift } from './actions';
import { handleEventDrop } from './utils/handleEventDrop';
import { handleEventRender } from './utils/handleEventRender';
import moment from 'moment';

function Calendar(props) {
  const { shiftDrop, setClickInfo, setPopoverVisible, deleteShift, popoverSize } = props;

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
    handleResourceRender(info, { colorClick: handleResourceColorClick, resourceColor: props.resourceColor });
  };

  const handleResourceColorClick = (id, color) => {
    props.changeResourceColor(id, color);
  };

  const events = handleShiftEvtTitle(props.event, props.defaultShift);

  const eventRender = info => {
    handleEventRender(info, { deleteShift });
  };

  return (
    <FullCalendar
      locales={zhTW}
      locale="zh-tw"
      height="auto"
      resources={props.resource}
      resourceRender={resourceRender}
      slotWidth={150}
      events={events}
      eventRender={eventRender}
      plugins={[interactionPlugin, resourceTimelinePlugin]}
      defaultView={'resourceTimelineMonth'}
      editable={true}
      eventDurationEditable={false}
      resourceLabelText={'Doctor'}
      header={{
        left: 'today prev',
        center: 'title',
        right: 'next',
      }}
      resourceAreaWidth={'20%'}
      datesRender={datesRender}
      dateClick={dateClick}
      eventDrop={eventDrop}
      eventResize={eventResize}
      drop={drop}
      slotLabelFormat={{ day: 'numeric', weekday: 'short' }}
      displayEventTime={false}
      selectable
    />
  );
}

const mapStateToProps = ({ homePageReducer, shiftPageReducer }) => ({
  resource: extractDoctorsFromUser(homePageReducer.user.users).map(d => ({ id: d.id, title: d.name })),
  range: shiftPageReducer.shift.range,
  defaultShift: shiftPageReducer.defaultShift.shift,
  event: convertShiftToEvent(shiftPageReducer.shift.shift, shiftPageReducer.resourceColor.color),
  resourceColor: shiftPageReducer.resourceColor.color,
});

const mapDispatchToProps = {
  changeDate,
  getShift,
  editShift,
  shiftDrop,
  changeResourceColor,
  deleteShift,
};

export default connect(
  mapStateToProps,
  mapDispatchToProps,
)(
  React.memo(Calendar, (prevProps, nextProps) => {
    return prevProps.popoverVisible && !nextProps.popoverVisible;
  }),
);
