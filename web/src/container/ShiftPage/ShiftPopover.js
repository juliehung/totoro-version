import React, { useState, useEffect } from 'react';
import styled from 'styled-components';
import { Radio, Checkbox, InputNumber, Button, TimePicker } from 'antd';

const { RangePicker } = TimePicker;

//#region
const Conatainer = styled.div`
  font-size: 14px;
  max-width: 370px;
  background: #f0f0f0;
  position: fixed;
  border-radius: 10px;
  padding: 10px;
  z-index: 400;
  top: ${props => (props.position ? props.position.y : 0)}px;
  left: ${props => (props.position ? props.position.x : 0)}px;
  display: ${props => (props.visible ? 'flex' : 'none')};
  flex-direction: column;
  transition: all ease-in-out 200ms;
  & > * {
    margin: 6px 0;
  }
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

const radioStyle = {
  display: 'block',
};

//#endregion

function ShiftPopover(props) {
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
  return (
    <Conatainer className="shift-popover" visible={props.visible} position={props.position} onClick={onPopoverClick}>
      <span>班別選擇</span>
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
        周重複至3月底
      </BoldSpan>
      <Button onClick={onConfirm} type="primary" disabled={buttonDisabled}>
        確認
      </Button>
    </Conatainer>
  );
}

export default ShiftPopover;
