import React, { useState, useEffect } from 'react';
import styled from 'styled-components';
import { Radio, Checkbox, InputNumber, Button, TimePicker } from 'antd';
import { CloseOutlined } from '@ant-design/icons';
import moment from 'moment';

const { RangePicker } = TimePicker;

//#region
const Container = styled.div`
  font-size: 14px;
  max-width: 370px;
  background: #fff;
  position: fixed;
  border-radius: 10px;
  padding: 0 10px 10px;
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
      font-size: 20px;
      color: rgba(0, 0, 0, 0.85);
    }
  }
  border-top: 10px solid ${props => (props.color ? props.color : '#1890ff')};
  box-shadow: 0 4px 25px 0 rgba(0, 0, 0, 0.1);
`;

const BoldSpan = styled.div`
  font-size: 16px;
  font-weight: bold;
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

const radioStyle = {
  display: 'block',
};

//#endregion

function ShiftPopover(props) {
  const { visible, setVisible, color } = props;

  const [radioValue, setRadioValue] = useState(1);
  const [selectedShift, setSelectedShift] = useState([]);
  const [customRange, setCustomRange] = useState([undefined, undefined]);
  const [week, setWeek] = useState(0);
  const [buttonDisabled, setButtonDisabled] = useState(true);

  const onPopoverClick = e => {
    e.stopPropagation();
  };

  const onConfirm = () => {
    props.onConfirm({ radioValue, selectedShift, customRange, week, date: props.date, userId: props.resourceId });
  };

  useEffect(() => {
    if (radioValue === 1) {
      setButtonDisabled(selectedShift.length === 0);
    } else if (radioValue === 2) {
      setButtonDisabled(customRange[0] === undefined && customRange[1] === undefined);
    }
  }, [radioValue, selectedShift, customRange, buttonDisabled]);

  useEffect(() => {
    if (!visible) {
      setRadioValue(1);
      setSelectedShift([]);
      setCustomRange([undefined, undefined]);
      setWeek(0);
    }
  }, [visible, setRadioValue, setSelectedShift, setCustomRange, setWeek]);

  return (
    <Container
      className="shift-popover"
      visible={visible}
      position={props.position}
      onClick={onPopoverClick}
      color={color}
    >
      <div>
        <span>新增</span>
        <CloseOutlined
          onClick={() => {
            setVisible(false);
          }}
        />
      </div>
      <Radio.Group
        defaultValue={radioValue}
        onChange={e => {
          setRadioValue(e.target.value);
        }}
      >
        <Radio style={radioStyle} value={1}>
          範本班別
        </Radio>
        <Checkbox.Group
          style={{ maxHeight: '200px', overflowY: 'scroll' }}
          options={props.defaultShift.map(s => ({
            label: `${s.origin.name} {${s.origin.range.start} ~ ${s.origin.range.end})`,
            value: s.origin.id,
          }))}
          value={selectedShift}
          disabled={radioValue !== 1}
          onChange={e => {
            setSelectedShift(e);
          }}
        />
        <Radio style={radioStyle} value={2}>
          <span>自訂時間</span>
          <StyledRangePicker
            size="small"
            format={'HH:mm'}
            disabled={radioValue !== 2}
            value={customRange}
            onChange={setCustomRange}
            allowClear={false}
          />
        </Radio>
      </Radio.Group>
      <BoldSpan>
        每
        <StyleInputNumber size="small" value={week} min={0} max={5} onChange={setWeek} />
        周重複至 {props.date ? moment(props.date).format('M') : ''} 月底
      </BoldSpan>
      <ButtonContainer>
        <Button onClick={onConfirm} type="primary" disabled={buttonDisabled}>
          儲存
        </Button>
      </ButtonContainer>
    </Container>
  );
}

export default ShiftPopover;
