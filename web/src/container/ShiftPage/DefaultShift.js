import React, { useEffect } from 'react';
import styled from 'styled-components';
import { connect } from 'react-redux';
import {
  getDefaultShift,
  createDefaultShiftTemplate,
  changeDeafultShiftName,
  changeDefaultShiftRange,
} from './actions';
import { Card, Button, TimePicker, Input, Tooltip } from 'antd';
import { Draggable } from '@fullcalendar/interaction';
import { EditOutlined, DeleteOutlined, SaveOutlined } from '@ant-design/icons';
import { convertRangeToRangePickerValue } from './utils/convertRangeToRangePickerValue';

const { RangePicker } = TimePicker;

//#region
const Container = styled.div`
  min-height: 20vh;
  border: 1px solid #070707;
  border-radius: 10px;
  padding: 10px;
  display: flex;
  flex-direction: column;
  & > div {
    margin: 0px 5px 15px;
  }
`;

const ShiftCard = styled(Card)`
  margin: 5px 5px !important;
`;

const TitleContainer = styled.div`
  display: flex;
  font-size: 20px;
  font-weight: bold;
  align-items: baseline;
  & > :not(:first-child) {
    margin-left: 10px;
  }
`;

const ShiftsContainer = styled.div`
  display: flex;
  flex-wrap: wrap;
`;

const ShiftContainer = styled.div`
  display: flex;
  flex-direction: column;
  & > * {
    margin: 5px 0 !important;
  }
`;
//#endregion

function DefaultShift(props) {
  const { getDefaultShift, createDefaultShiftTemplate } = props;
  useEffect(() => {
    getDefaultShift();
  }, [getDefaultShift]);

  useEffect(() => {
    let draggableEl = document.getElementById('external-events');
    new Draggable(draggableEl, {
      itemSelector: '.fc-event',
      eventData: function(eventEl) {
        let title = eventEl.getAttribute('title');
        let id = eventEl.getAttribute('data');
        return {
          title: title,
          id: id,
        };
      },
    });
  }, []);

  return (
    <Container>
      <TitleContainer>
        <span>班別</span>
        <Button onClick={createDefaultShiftTemplate}>新增</Button>
      </TitleContainer>
      <ShiftsContainer id={'external-events'}>
        {props.defaultShift.map(s => {
          if (s.isNew) {
            return (
              <ShiftCard
                bodyStyle={{ padding: '15px', width: '300px', height: '120px' }}
                key={s.origin.id}
                style={{ color: 'black' }}
                actions={[
                  <Tooltip placement="bottom" title={'儲存'}>
                    <SaveOutlined />
                  </Tooltip>,
                ]}
              >
                <ShiftContainer>
                  <Input
                    defaultValue={s.origin.name}
                    onChange={e => {
                      props.changeDeafultShiftName(s.origin.id, e.target.value);
                    }}
                  ></Input>
                  <RangePicker
                    format={'HH:mm'}
                    allowClear={false}
                    defaultValue={convertRangeToRangePickerValue(s.origin.range)}
                    onChange={range => {
                      props.changeDefaultShiftRange(s.origin.id, range);
                    }}
                  />
                </ShiftContainer>
              </ShiftCard>
            );
          } else if (s.isEditing) {
            return (
              <ShiftCard
                bodyStyle={{
                  padding: '15px',
                  width: '300px',
                  height: '120px',
                }}
                key={s.origin.id}
                style={{ color: 'black' }}
                actions={[
                  <Tooltip placement="bottom" title={'刪除'}>
                    <DeleteOutlined />
                  </Tooltip>,
                  <Tooltip placement="bottom" title={'儲存'}>
                    <SaveOutlined />
                  </Tooltip>,
                ]}
              >
                <ShiftContainer>
                  <Input defaultValue={s.origin.name}></Input>
                  <RangePicker format={'HH:mm'} allowClear={false} />
                </ShiftContainer>
              </ShiftCard>
            );
          } else {
            return (
              <ShiftCard
                bodyStyle={{ padding: '15px', width: '300px', height: '120px' }}
                key={s.origin.id}
                className={'fc-event'}
                style={{ color: 'black' }}
                actions={[
                  <Tooltip placement="bottom" title={'編輯'}>
                    <EditOutlined />
                  </Tooltip>,
                  <Tooltip placement="bottom" title={'刪除'}>
                    <DeleteOutlined />
                  </Tooltip>,
                ]}
              >
                <ShiftContainer>
                  <span>{s.origin.name}</span>
                  <span>
                    {s.origin.range.start} ~ {s.origin.range.end}
                  </span>
                </ShiftContainer>
              </ShiftCard>
            );
          }
        })}
        <div></div>
      </ShiftsContainer>
    </Container>
  );
}

const mapStateToProps = ({ shiftPageReducer }) => ({
  defaultShift: shiftPageReducer.defaultShift.shift,
});

const mapDispatchToProps = {
  getDefaultShift,
  createDefaultShiftTemplate,
  changeDeafultShiftName,
  changeDefaultShiftRange,
};

export default connect(mapStateToProps, mapDispatchToProps)(DefaultShift);
