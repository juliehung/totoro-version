import React, { useState } from 'react';
import { connect } from 'react-redux';
import styled from 'styled-components';
import { Helmet } from 'react-helmet-async';
import ShiftCalendar from './ShiftCalendar';
import DefaultShift from './DefaultShift';

//#region
const Container = styled.div`
  height: 100vh;
  width: 100%;
  display: flex;
  padding: 1%;
  margin: 0 auto;
  position: relative;
`;
//#endregion

function ShiftPage() {
  const [popoverVisible, setPopoverVisible] = useState(false);

  const onClick = e => {
    if (e.target) {
      const child = e.target.querySelector(':scope>.fc-event-container');
      if (child) {
        return;
      }
    }
    setPopoverVisible(false);
  };

  return (
    <Container onClick={onClick}>
      <Helmet>
        <title>排班</title>
      </Helmet>
      <ShiftCalendar popoverVisible={popoverVisible} setPopoverVisible={setPopoverVisible} />
      <DefaultShift />
    </Container>
  );
}

const mapStateToProps = state => ({});

const mapDispatchToProps = {};

export default connect(mapStateToProps, mapDispatchToProps)(ShiftPage);
