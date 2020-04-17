import React, { useEffect, useCallback } from 'react';
import styled from 'styled-components';
import { connect } from 'react-redux';
import {
  getDefaultShift,
  changeDefaultShiftName,
  changeDefaultShiftRange,
  createDefaultShift,
  deleteDefaultShift,
} from './actions';
import { Card, Button, TimePicker, Input, Popover, message } from 'antd';
import { DeleteTwoTone } from '@ant-design/icons';
import { Draggable } from '@fullcalendar/interaction';
import { convertRangeToRangePickerValue } from './utils/convertRangeToRangePickerValue';
import { orderDefaultShiftByStartTime } from './utils/orderDefaultShiftByStartTime';
const { RangePicker } = TimePicker;

//#region
const Container = styled.div`
  width: 300px;
  margin: 10px;
  display: flex;
  flex-direction: column;
  box-shadow: 0px 0px 15px 0px rgba(0, 0, 0, 0.05);

  & > :first-child {
    display: flex;
    align-items: baseline;
    & > :first-child {
      margin-right: 10px;
    }
  }

  @media (max-width: 960px) {
    display: none;
  }
`;

const ShiftsContainer = styled.div`
  display: flex;
  flex-wrap: wrap;
  overflow-y: scroll;
`;

const CardContent = styled.span`
  display: flex;
  flex-direction: row;
  justify-content: space-between;
  font-size: 14px;
  color: rgba(0, 0, 0, 0.45);
  & > div:first-child {
    display: flex;
    flex-direction: column;
    & > :first-child {
      color: rgba(0, 0, 0, 0.85);
      font-size: 16px;
      font-weight: bold;
      width: 150px;
      overflow: hidden;
      white-space: nowrap;
      text-overflow: ellipsis;
    }
  }
  & > div:not(:first-child) {
    display: flex;
    align-items: center;
  }
`;

const IconContainer = styled.div`
  opacity: 0;
  transition: all ease-in-out 400ms;
  margin-left: 15px;
  ${CardContent}:hover & {
    opacity: 1;
  }
`;

const PopoverContainer = styled.div`
  width: 200px;
  display: flex;
  flex-direction: column;
  & > * {
    margin: 5px 0 !important;
  }
`;

const ButtonContainer = styled.div`
  align-self: flex-end;
`;

//#endregion

const gridStyle = {
  cursor: 'pointer',
  width: '100%',
  margin: '10px 5px',
  borderRadius: '8px',
  padding: '9px 15px',
};

function DefaultShift(props) {
  const simulateMouseClick = element => {
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

  const clickTitle = useCallback(() => {
    const title = document.querySelector('h2');
    if (title) {
      simulateMouseClick(title);
    }
  }, []);

  const { getDefaultShift, createSuccess } = props;
  useEffect(() => {
    getDefaultShift();
  }, [getDefaultShift]);

  useEffect(() => {
    clickTitle();
    if (createSuccess) {
      getDefaultShift();
      message.success('新增成功');
    }
  }, [createSuccess, getDefaultShift, clickTitle]);

  useEffect(() => {
    let draggableEl = document.getElementById('external-events');
    new Draggable(draggableEl, {
      itemSelector: '.external-event',
      eventData: function (eventEl) {
        const title = eventEl.dataset.title;
        return { title, create: false };
      },
    });
  }, []);

  return (
    <Container>
      <div>
        <h2>班別</h2>
        <Popover
          trigger="click"
          content={
            <PopoverContainer>
              <Input
                placeholder={'班別名稱'}
                onChange={e => {
                  props.changeDefaultShiftName(e.target.value);
                }}
                value={props.newShift.name}
              />
              <RangePicker
                format={'HH:mm'}
                allowClear={false}
                placeholder={['開始時間', '結束時間']}
                onChange={range => {
                  props.changeDefaultShiftRange(range);
                }}
                value={convertRangeToRangePickerValue(props.newShift.range)}
              />
              <ButtonContainer>
                <Button
                  onClick={props.createDefaultShift}
                  disabled={!props.newShift.name || !props.newShift.range.start || !props.newShift.range.end}
                >
                  確認
                </Button>
              </ButtonContainer>
            </PopoverContainer>
          }
          placement="bottomLeft"
        >
          <Button>新增</Button>
        </Popover>
      </div>
      <ShiftsContainer id={'external-events'}>
        {props.defaultShift.map(s => (
          <Card.Grid
            style={gridStyle}
            key={s.origin.id}
            className={'external-event'}
            data-start={s.origin.range.start}
            data-end={s.origin.range.end}
            data-title={s.origin.name}
          >
            <CardContent>
              <div>
                <span>{s.origin.name}</span>
                <span>
                  {s.origin.range.start} ~ {s.origin.range.end}
                </span>
              </div>
              <IconContainer>
                <DeleteTwoTone
                  twoToneColor="red"
                  style={{ fontSize: '20px' }}
                  onClick={() => {
                    props.deleteDefaultShift(s.origin.id);
                  }}
                />
              </IconContainer>
            </CardContent>
          </Card.Grid>
        ))}
      </ShiftsContainer>
    </Container>
  );
}

const mapStateToProps = ({ shiftPageReducer }) => ({
  defaultShift: orderDefaultShiftByStartTime(shiftPageReducer.defaultShift.shift),
  createSuccess: shiftPageReducer.defaultShift.createSuccess,
  newShift: shiftPageReducer.defaultShift.newShift,
});

const mapDispatchToProps = {
  getDefaultShift,
  changeDefaultShiftName,
  changeDefaultShiftRange,
  createDefaultShift,
  deleteDefaultShift,
};

export default connect(mapStateToProps, mapDispatchToProps)(DefaultShift);
