import React, { useState, useEffect } from 'react';
import styled from 'styled-components';
import { Checkbox, InputNumber, Button, TimePicker } from 'antd';
import { CloseOutlined } from '@ant-design/icons';
import clockIcon from '../../images/Icon-clock.svg';
import repeatIcon from '../../images/Icon-repeat.svg';
import boardIcon from '../../images/Icon-board.svg';
import moment from 'moment';

const { RangePicker } = TimePicker;

//#region
const Container = styled.div`
  font-size: 14px;
  max-width: 370px;
  background: #fff;
  position: fixed;
  border-radius: 10px;
  padding: 0 30px 10px;
  z-index: 400;
  top: ${props => (props.position ? props.position.y : 0)}px;
  left: ${props => (props.position ? props.position.x : 0)}px;
  display: ${props => (props.visible ? 'flex' : 'none')};
  flex-direction: column;
  transition: all ease-in-out 200ms;
  & > * {
    margin: 6px 0;
  }
  & > div:first-child {
    display: flex;
    justify-content: space-between;
    align-items: center;
    & > span {
      font-size: 22px;
      font-weight: 600;
      color: rgba(0, 0, 0, 0.85);
    }
  }
  border-top: 10px solid ${props => (props.color ? props.color : '#1890ff')};
  box-shadow: 0 4px 25px 0 rgba(0, 0, 0, 0.1);
`;

const StyleInputNumber = styled(InputNumber)`
  width: 50px !important;
  margin: 0 1em !important;
`;

const StyledRangePicker = styled(RangePicker)`
  margin: 0.5em 1em !important;
`;

const ButtonContainer = styled.div`
  align-self: flex-end;
`;

const ItemContainer = styled.div`
  font-size: 14px;
  font-weight: 600;
  display: flex;
  flex-direction: column;
  & > :first-child {
    display: flex;
    align-items: baseline;
    & > :first-child {
      width: 30px;
      & > img {
        width: 20px;
        height: 20px;
      }
    }
  }
  & > :nth-child(2) {
    margin-left: 30px !important;
    margin-top: 5px !important;
    margin-bottom: 10px !important;
  }
`;

const HeightDiv = styled.div`
  max-height: 200px;
  overflow-y: scroll;
`;

//#endregion

function ShiftPopover(props) {
  const { visible, setVisible, color } = props;

  const [selectedShift, setSelectedShift] = useState([]);
  const [customRange, setCustomRange] = useState([undefined, undefined]);
  const [week, setWeek] = useState(0);
  const [buttonDisabled, setButtonDisabled] = useState(true);

  const onPopoverClick = e => {
    e.stopPropagation();
  };

  const onConfirm = () => {
    props.onConfirm({ selectedShift, customRange, week, date: props.date, userId: props.resourceId });
  };

  useEffect(() => {
    setButtonDisabled(selectedShift.length === 0 && (!customRange[0] || !customRange[1]));
  }, [selectedShift, customRange, buttonDisabled]);

  useEffect(() => {
    if (!visible) {
      setSelectedShift([]);
      setCustomRange([undefined, undefined]);
      setWeek(0);
    }
  }, [visible, setSelectedShift, setCustomRange, setWeek]);

  useEffect(() => {
    window.addEventListener('mousedown', e => {
      const child = e.target.querySelector(':scope>.fc-event-container');
      if (child) {
        return;
      }
      setVisible(false);
    });
  }, [visible, setVisible]);

  const onMouseDown = e => {
    e.stopPropagation();
  };

  return (
    <Container
      visible={visible}
      position={props.position}
      onClick={onPopoverClick}
      color={color}
      onMouseDown={onMouseDown}
    >
      <div>
        <span>新增看診時間</span>
        <CloseOutlined
          onClick={() => {
            setVisible(false);
          }}
        />
      </div>
      <ItemContainer>
        <span>
          <span>
            <img src={boardIcon} alt="icon" />
          </span>
          <span>班表</span>
        </span>
        <HeightDiv>
          <Checkbox.Group
            options={props.defaultShift.map(s => ({
              label: `${s.origin.name} {${s.origin.range.start} ~ ${s.origin.range.end})`,
              value: s.origin.id,
            }))}
            value={selectedShift}
            onChange={setSelectedShift}
          />
        </HeightDiv>
      </ItemContainer>
      <ItemContainer>
        <span>
          <span>
            <img src={clockIcon} alt="icon" />
          </span>
          自訂時間
        </span>
        <StyledRangePicker
          size="small"
          format={'HH:mm'}
          value={customRange}
          onChange={e => {
            if (!e) {
              setCustomRange([undefined, undefined]);
              return;
            }
            setCustomRange(e);
          }}
        />
      </ItemContainer>
      <ItemContainer>
        <span>
          <span>
            <img src={repeatIcon} alt="icon" />
          </span>
          複製
        </span>
        <span>
          每
          <StyleInputNumber size="small" value={week} min={0} max={5} onChange={setWeek} />
          周重複至 {props.date ? moment(props.date).format('M') : ''} 月底
        </span>
      </ItemContainer>
      <ButtonContainer>
        <Button onClick={onConfirm} type="primary" disabled={buttonDisabled}>
          儲存
        </Button>
      </ButtonContainer>
    </Container>
  );
}

export default ShiftPopover;
