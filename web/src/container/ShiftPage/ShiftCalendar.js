import React, { useEffect, useState } from 'react';
import FullCalendar from '@fullcalendar/react';
import '@fullcalendar/core/main.css';
import interactionPlugin from '@fullcalendar/interaction';
import resourceTimelinePlugin from '@fullcalendar/resource-timeline';
import '@fullcalendar/daygrid/main.css';
import '@fullcalendar/timegrid/main.css';
import '@fullcalendar/list/main.css';
import { connect } from 'react-redux';
// import zhTW from '@fullcalendar/core/locales/zh-tw';
import styled from 'styled-components';
import '@fullcalendar/timeline/main.css';
import '@fullcalendar/resource-timeline/main.css';
import extractDoctorsFromUser from '../../utils/extractDoctorsFromUser';
import convertShiftToEvent from './utils/convertShiftToEvent';
import handlePopoverPosition from './utils/handlePopoverPosition';
// import { handleResourceRender } from './utils/handleResourceRender';
import { changeDate, getShift, createShift, editShift } from './actions';
import ShiftPopover from './ShiftPopover';
import { handleEventDrop } from './utils/handleEventDrop';
import { message } from 'antd';

//#region
const Container = styled.div`
  /* height: 100%; */
  width: 100%;
  border: 1px solid #070707;
  border-radius: 10px;
  padding: 20px;
  flex-grow: 1;
  .fc-license-message {
    display: none;
  }
`;

//#endregion

function ShiftCalendar(props) {
  const { range, getShift, setPopoverVisible, createShiftSuccess, editShiftSuccess } = props;
  const [clickInfo, setClickInfo] = useState({ x: undefined, y: undefined });

  useEffect(() => {
    const msg = document.querySelector('.fc-license-message');
    if (msg) {
      msg.parentNode.removeChild(msg);
    }
  });

  useEffect(() => {
    if (range.start && range.end) {
      getShift(range);
    }
  }, [range, getShift]);

  useEffect(() => {
    if (createShiftSuccess) {
      setPopoverVisible(false);
      message.success('新增成功');
    }
  }, [createShiftSuccess, setPopoverVisible]);

  useEffect(() => {
    if (editShiftSuccess) {
      message.success('更新成功');
    }
  }, [editShiftSuccess]);

  const datesRender = ({ view }) => {
    const start = view.activeStart;
    const end = view.activeEnd;
    props.changeDate({ start, end });
  };

  const dateClick = dateClickInfo => {
    const { jsEvent, resource, date } = dateClickInfo;
    setClickInfo({ position: handlePopoverPosition(jsEvent), date, resourceId: resource.id });
    setPopoverVisible(true);
  };

  const eventDrop = eventDropInfo => {
    props.editShift(handleEventDrop(eventDropInfo));
  };

  const eventResize = eventResizeInfo => {
    props.editShift(handleEventDrop(eventResizeInfo));
  };

  return (
    <Container>
      <FullCalendar
        height="auto"
        resources={props.resource}
        // resourceRender={handleResourceRender}
        slotWidth={60}
        events={props.event}
        selectable
        plugins={[interactionPlugin, resourceTimelinePlugin]}
        defaultView={'resourceTimelineDay'}
        views={{
          resourceTimelineFourDays: {
            type: 'resourceTimeline',
            duration: { days: 4 },
          },
        }}
        editable={true}
        resourceLabelText={'Doctor'}
        header={{
          left: 'prev,next',
          center: 'title',
          right: 'resourceTimelineDay,resourceTimelineWeek,resourceTimelineMonth',
        }}
        resourceAreaWidth={'20%'}
        datesRender={datesRender}
        dateClick={dateClick}
        eventDrop={eventDrop}
        eventResize={eventResize}
      />
      <ShiftPopover
        visible={props.popoverVisible}
        position={clickInfo.position}
        date={clickInfo.date}
        resourceId={clickInfo.resourceId}
        className="shift-popover"
        defaultShift={props.defaultShift}
        onConfirm={props.createShift}
      />
    </Container>
  );
}

const mapStateToProps = ({ homePageReducer, shiftPageReducer }) => ({
  resource: extractDoctorsFromUser(homePageReducer.user.users).map(d => ({ id: d.id, title: d.name })),
  range: shiftPageReducer.shift.range,
  defaultShift: shiftPageReducer.shift.defaultShift,
  event: convertShiftToEvent(shiftPageReducer.shift.shift),
  createShiftSuccess: shiftPageReducer.shift.createShiftSuccess,
  editShiftSuccess: shiftPageReducer.shift.editShiftSuccess,
});

const mapDispatchToProps = { changeDate, getShift, createShift, editShift };

export default connect(mapStateToProps, mapDispatchToProps)(ShiftCalendar);
