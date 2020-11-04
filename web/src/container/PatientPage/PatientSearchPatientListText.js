import React from 'react';
import styled from 'styled-components';

//#region
const ColorSpan = styled.span`
  color: #3266ff;
  font-weight: bold;
`;
//#endregion

function PatientSearchPatientListText(props) {
  const { keyword = '', text = '', isBirth = false } = props;
  const re = new RegExp(isBirth ? `(${keyword.split('').join('/*')})` : `(${keyword})`);
  const split = text.split(re);
  const array = split.map((s, i) => (i % 2 === 1 ? <ColorSpan key={i}>{s}</ColorSpan> : <span key={i}>{s}</span>));

  return (
    <div>
      <span>{array}</span>
    </div>
  );
}

export default PatientSearchPatientListText;
