import React, { useEffect } from 'react';
import styled from 'styled-components';
import { connect } from 'react-redux';
import {
  getDefaultShift,
  createDefaultShiftTemplate,
  changeDefaultShiftName,
  changeDefaultShiftRange,
  createDefaultShift,
  deleteDefaultShift,
} from './actions';
import { Card, Button, TimePicker, Input } from 'antd';
import { DeleteTwoTone, SaveOutlined } from '@ant-design/icons';
import { Draggable } from '@fullcalendar/interaction';
import { convertRangeToRangePickerValue } from './utils/convertRangeToRangePickerValue';
import { orderDefaultShiftByStartTime } from './utils/orderDefaultShiftByStartTime';
const { RangePicker } = TimePicker;

//#region
const Container = styled.div`
  width: 300px;
  height: 100%;
  padding: 10px;
  display: flex;
  flex-direction: column;
  box-shadow: 0px 0px 15px 0px rgba(0, 0, 0, 0.05);
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
    }
  }
  & > div:not(:first-child) {
    display: flex;
    align-items: center;
  }
`;

const ButtonContainer = styled.div`
  opacity: 0;
  transition: all ease-in-out 400ms;
  margin-left: 15px;
  ${CardContent}:hover & {
    opacity: 1;
  }
`;

//#endregion

const gridStyle = {
  cursor: 'pointer',
  width: '100%',
  margin: '16px 0 0',
  borderRadius: '8px',
  padding: '9px 15px',
};

function DefaultShift(props) {
  const { getDefaultShift, createDefaultShiftTemplate, createSuccess } = props;
  useEffect(() => {
    getDefaultShift();
  }, [getDefaultShift]);

  useEffect(() => {
    if (createSuccess) {
      getDefaultShift();
    }
  }, [createSuccess, getDefaultShift]);

  useEffect(() => {
    let draggableEl = document.getElementById('external-events');
    new Draggable(draggableEl, {
      itemSelector: '.external-event',
      eventData: function(eventEl) {
        const title = eventEl.dataset.title;
        return { title, create: false };
      },
    });
  }, []);

  return (
    <Container>
      <Button type="primary" size="large" onClick={createDefaultShiftTemplate}>
        新增班別
      </Button>
      <ShiftsContainer id={'external-events'}>
        {props.defaultShift.map(s => {
          if (s.isNew) {
            return (
              <Card.Grid style={gridStyle} key={s.origin.id}>
                <CardContent>
                  <div>
                    <Input
                      onFocus={() => {
                        console.log(123);
                      }}
                      defaultValue={s.origin.name}
                      onChange={e => {
                        props.changeDefaultShiftName(s.origin.id, e.target.value);
                      }}
                    />
                    <RangePicker
                      format={'HH:mm'}
                      allowClear={false}
                      defaultValue={convertRangeToRangePickerValue(s.origin.range)}
                      onChange={range => {
                        props.changeDefaultShiftRange(s.origin.id, range);
                      }}
                    />
                  </div>
                  <ButtonContainer>
                    <SaveOutlined onClick={props.createDefaultShift} style={{ fontSize: '20px', color: '#1890ff' }} />
                  </ButtonContainer>
                </CardContent>
              </Card.Grid>
            );
          } else {
            return (
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
                  <ButtonContainer>
                    <DeleteTwoTone
                      twoToneColor="red"
                      style={{ fontSize: '20px' }}
                      onClick={() => {
                        props.deleteDefaultShift(s.origin.id);
                      }}
                    />
                  </ButtonContainer>
                </CardContent>
              </Card.Grid>
            );
          }
        })}
      </ShiftsContainer>
    </Container>
  );
}

const mapStateToProps = ({ shiftPageReducer }) => ({
  defaultShift: orderDefaultShiftByStartTime(shiftPageReducer.defaultShift.shift),
  createSuccess: shiftPageReducer.defaultShift.createSuccess,
});

const mapDispatchToProps = {
  getDefaultShift,
  createDefaultShiftTemplate,
  changeDefaultShiftName,
  changeDefaultShiftRange,
  createDefaultShift,
  deleteDefaultShift,
};

export default connect(mapStateToProps, mapDispatchToProps)(DefaultShift);
