import React, { useState, Fragment } from 'react';
import { SingleDatePicker } from 'react-dates';
import 'react-dates/initialize';
import 'react-dates/lib/css/_datepicker.css';
import { renderMonthElement } from './utils';
import styled, { createGlobalStyle } from 'styled-components';
import { LeftOutlined, RightOutlined } from '@ant-design/icons';

//#region
export const GlobalStyle = createGlobalStyle`
  .CalendarDay__selected,
  .CalendarDay__selected:active,
  .CalendarDay__selected:hover {
    background: #3266ff !important;
    border: 1px transparent solid !important;
    color: #fff;
  }
  .CalendarDay__today {
	  font-weight: bold;
  }
`;

const SingleDatePickerContainer = styled.div`
  & .DateInput_input {
    font-size: ${props => (props.size === 'large' ? '18px' : props.size === 'small' ? '14px' : '16px')} !important;
    padding-top: ${props => (props.size === 'large' ? '9px' : props.size === 'small' ? '1px' : '2px')} !important;
    padding-bottom: ${props => (props.size === 'large' ? '9px' : props.size === 'small' ? '1px' : '2px')} !important;
    border: 1px solid transparent !important;
    color: rgba(0, 0, 0, 0.65);
  }
  & .DateInput_input__focused {
    border: 1px solid #5c8aff !important;
  }
  & .DateInput_input::placeholder {
    color: #a1a1a1;
    font-size: ${props => (props.size === 'large' ? '18px' : props.size === 'small' ? '14px' : '16px')} !important;
    font-weight: normal;
  }
  & .DateInput.DateInput_1 {
    width: ${props => (props.size === 'large' ? '200px' : '150px')} !important;
  }
`;

const NavButton = styled.div`
  background-color: #fff;
  color: #484848;
  padding: 3px;
  position: absolute;
  top: 21px;
`;

const NavButtonR = styled(NavButton)`
  right: 22px;
`;
const NavButtonL = styled(NavButton)`
  left: 22px;
`;

//#endregion

function DatePicker(props) {
  const { date, onDateChange, readOnly, placeholder, size, disabled, upperYearLimit, lowerYearLimit } = props;
  const [focused, setFocused] = useState(false);

  return (
    <Fragment>
      <GlobalStyle />
      <SingleDatePickerContainer size={size}>
        <SingleDatePicker
          numberOfMonths={1}
          onDateChange={onDateChange}
          onFocusChange={({ focused }) => setFocused(focused)}
          focused={focused}
          date={date}
          hideKeyboardShortcutsPanel
          // withPortal
          withFullScreenPortal={window.innerWidth < 500}
          renderMonthElement={({ month, onMonthSelect, onYearSelect }) =>
            renderMonthElement(month, onMonthSelect, onYearSelect, upperYearLimit, lowerYearLimit)
          }
          enableOutsideDays
          isOutsideRange={() => false}
          readOnly={readOnly}
          placeholder={placeholder ? placeholder : '請選擇日期'}
          disabled={disabled}
          displayFormat={() => {
            if (date) {
              const year = date.year() - 1911;
              return `${year}年${date.format('MMMDo')}`;
            }
          }}
          navPrev={
            <NavButtonL>
              <LeftOutlined />
            </NavButtonL>
          }
          navNext={
            <NavButtonR>
              <RightOutlined />
            </NavButtonR>
          }
        />
      </SingleDatePickerContainer>
    </Fragment>
  );
}

export default DatePicker;
