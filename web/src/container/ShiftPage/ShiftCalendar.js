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
// import { handleResourceRender } from './utils/handleResourceRender';
import { changeDate, getShift } from './actions';
import { Radio, Checkbox, InputNumber } from 'antd';

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

const Popover = styled.div`
  width: 300px;
  background: #facfac;
  position: absolute;
  border-radius: 10px;
  padding: 10px;
  z-index: 400;
  top: ${props => (props.position ? props.position.y : 0)}px;
  left: ${props => (props.position ? props.position.x : 0)}px;
  display: ${props => (props.visible ? 'visible' : 'none')};
  display: ${props => (props.visible ? 'flex' : 'none')};
  flex-direction: column;
  transition: all ease-in-out 200ms;
`;

const CheckboxConatainer = styled.div`
  display: flex;
  flex-direction: column;
  margin-left: 2em;
  & > * {
    margin-left: 0 !important;
  }
`;

const BoldSpan = styled.div`
  font-size: 14px;
  font-weight: bold;
`;

const StyleInputNumber = styled(InputNumber)`
  width: 50px !important;
  margin: 0 1em !important;
`;

const radioStyle = {
  display: 'block',
  height: '30px',
  lineHeight: '30px',
};

//#endregion

function ShiftCalendar(props) {
  const { range, getShift, setPopoverVisible } = props;
  const [clickInfo, setClickInfo] = useState({ x: undefined, y: undefined });

  useEffect(() => {
    if (clickInfo.x && clickInfo.y) {
      // console.log(clickInfo);
    }
  }, [clickInfo]);

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

  const datesRender = ({ view }) => {
    const start = view.activeStart;
    const end = view.activeEnd;
    props.changeDate({ start, end });
  };

  const onPopoverClick = e => {
    e.stopPropagation();
  };

  const dateClick = dateClickInfo => {
    const { jsEvent } = dateClickInfo;
    setClickInfo({ x: jsEvent.clientX, y: jsEvent.clientY });
    setPopoverVisible(true);
  };
  return (
    <Container>
      <FullCalendar
        height="auto"
        resources={props.doctors.map((d, i) => {
          return { id: i + 1, title: d.name };
        })}
        // resourceRender={handleResourceRender}
        slotWidth={60}
        events={[]}
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
      />
      <Popover visible={props.popoverVisible} position={clickInfo} className="shift-popover" onClick={onPopoverClick}>
        <span>班別選擇</span>
        <Radio.Group
          onChange={e => {
            console.log(e.target.value);
          }}
        >
          <Radio style={radioStyle} value={1}>
            範本班別
          </Radio>
          <CheckboxConatainer>
            {props.defaultShift.map(s => (
              <Checkbox key={s.name}>
                {s.name} ({s.range.start} ~ {s.range.end})
              </Checkbox>
            ))}
          </CheckboxConatainer>
          <Radio style={radioStyle} value={2}>
            <span>自訂時間</span>
          </Radio>
          <BoldSpan>
            每<StyleInputNumber size="small" defaultValue={1} min={0} max={10}></StyleInputNumber>周重複至3月底
          </BoldSpan>
        </Radio.Group>
      </Popover>
    </Container>
  );
}

const mapStateToProps = ({ homePageReducer, shiftPageReducer }) => ({
  doctors: extractDoctorsFromUser(homePageReducer.user.users),
  range: shiftPageReducer.shift.range,
  defaultShift: shiftPageReducer.shift.defaultShift,
});

const mapDispatchToProps = { changeDate, getShift };

export default connect(mapStateToProps, mapDispatchToProps)(ShiftCalendar);
