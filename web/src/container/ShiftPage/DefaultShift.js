import React from 'react';
import styled from 'styled-components';
import { connect } from 'react-redux';

//#region
const Container = styled.div`
  height: 20vh;
  border: 1px solid #070707;
  border-radius: 10px;
  padding: 10px;
  display: flex;
  flex-direction: column;
  & > div {
    display: flex;
    margin: 0 5px;
    & > div {
      display: flex;
      flex-direction: column;
    }
  }
`;

const ShiftCard = styled.div`
  margin: 0 5px;
  padding: 10px;
  border: 1px solid #070707;
  border-radius: 10px;
`;
//#endregion

function DefaultShift(props) {
  return (
    <Container>
      <h2>班別</h2>
      <div>
        {props.defaultShift.map(s => (
          <ShiftCard key={s.name}>
            <span>{s.name}</span>
            <span>
              {s.range.start} ~ {s.range.end}
            </span>
          </ShiftCard>
        ))}
        <div></div>
      </div>
    </Container>
  );
}

const mapStateToProps = ({ shiftPageReducer }) => ({
  defaultShift: shiftPageReducer.shift.defaultShift,
});

// const mapDispatchToProps = {};

export default connect(mapStateToProps)(DefaultShift);