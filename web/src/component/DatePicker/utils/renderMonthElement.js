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

function renderMonthElement({ month, onMonthSelect, onYearSelect }) {
  return (
    <Container>
      <div>
        <StyledSelect value={month.year()} onChange={e => onYearSelect(month, e.target.value)}>
          {returnYears()}
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

function returnYears() {
  const years = [];
  for (let i = moment().year() - 100; i <= moment().year() + 50; i++) {
    years.push(
      <StyledOption key={i} value={i}>
        {i - 1911}年
      </StyledOption>,
    );
  }
  return years;
}

export default renderMonthElement;
