import React, { useEffect, useState } from 'react';
import FullCalendar from '@fullcalendar/react';
import '@fullcalendar/core/main.css';
import interactionPlugin from '@fullcalendar/interaction';
import resourceTimelinePlugin from '@fullcalendar/resource-timeline';
import zhTW from '@fullcalendar/core/locales/zh-tw';
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
import handleShiftEvtTitle from './utils/handleShiftEvtTitle';
import { handleResourceRender } from './utils/handleResourceRender';
import { changeDate, getShift, createShift, editShift, shiftDrop, changeResourceColor } from './actions';
import ShiftPopover from './ShiftPopover';
import { handleEventDrop } from './utils/handleEventDrop';
import { message } from 'antd';
import moment from 'moment';

//#region
const Container = styled.div`
  /* height: 100%; */
  width: 100%;
  padding: 20px;
  flex-grow: 1;
  .fc-license-message {
    display: none;
  }
`;
//#endregion

function ShiftCalendar(props) {
  const {
    range,
    getShift,
    setPopoverVisible,
    createShiftSuccess,
    editShiftSuccess,
    shiftDrop,
    changeColorSuccess,
  } = props;

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

  useEffect(() => {
    if (changeColorSuccess) {
      message.success('更新成功');
    }
  }, [changeColorSuccess]);

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
    handleResourceRender(info, { colorClick: handleResourceColorClick });
  };

  const handleResourceColorClick = (id, color) => {
    props.changeResourceColor(id, color);
  };

  const events = handleShiftEvtTitle(props.event, props.defaultShift);

  return (
    <Container>
      <FullCalendar
        locales={zhTW}
        locale="zh-tw"
        height="auto"
        resources={props.resource}
        resourceRender={resourceRender}
        slotWidth={60}
        events={events}
        selectable
        plugins={[interactionPlugin, resourceTimelinePlugin]}
        defaultView={'resourceTimelineMonth'}
        views={{
          resourceTimelineFourDays: {
            type: 'resourceTimeline',
            duration: { days: 4 },
          },
        }}
        editable={true}
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
      />
      <ShiftPopover
        setVisible={setPopoverVisible}
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
  defaultShift: shiftPageReducer.defaultShift.shift,
  event: convertShiftToEvent(shiftPageReducer.shift.shift),
  createShiftSuccess: shiftPageReducer.shift.createShiftSuccess,
  editShiftSuccess: shiftPageReducer.shift.editShiftSuccess,
  changeColorSuccess: shiftPageReducer.resourceColor.changeColorSuccess,
});

const mapDispatchToProps = { changeDate, getShift, createShift, editShift, shiftDrop, changeResourceColor };

export default connect(mapStateToProps, mapDispatchToProps)(ShiftCalendar);
