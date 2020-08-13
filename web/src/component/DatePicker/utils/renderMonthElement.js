import React from 'react';
import moment from 'moment';
import styled from 'styled-components';

//#region
const Container = styled.div`
  display: flex;
  justify-content: center;
  align-items: center;
`;

const StyledSelect = styled.select`
  border: 0px solid #ccc;
`;

const StyledOption = styled.option``;

//#endregion

function renderMonthElement(month, onMonthSelect, onYearSelect, upperYearLimit, lowerYearLimit) {
  return (
    <Container>
      <div>
        <StyledSelect value={month.year()} onChange={e => onYearSelect(month, e.target.value)}>
          {returnYears(upperYearLimit, lowerYearLimit)}
        </StyledSelect>
      </div>
      <div>
        <StyledSelect
          value={month.month()}
          onChange={e => {
            onMonthSelect(month, e.target.value);
          }}
        >
          {moment.months().map((label, value) => {
            return (
              <option key={value} value={value}>
                {label}
              </option>
            );
          })}
        </StyledSelect>
      </div>
    </Container>
  );
}

function returnYears(upperYearLimit = 15, lowerYearLimit = 15) {
  const years = [];
  for (let i = moment().year() - lowerYearLimit; i <= moment().year() + upperYearLimit; i++) {
    years.push(
      <StyledOption key={i} value={i}>
        {i - 1911}年
      </StyledOption>,
    );
  }
  return years;
}

export default renderMonthElement;
