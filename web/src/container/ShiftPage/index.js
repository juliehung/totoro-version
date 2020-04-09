import React from 'react';
import { connect } from 'react-redux';
import styled from 'styled-components';
import { Helmet } from 'react-helmet-async';
import ShiftCalendar from './ShiftCalendar';
import DefaultShift from './DefaultShift';
import './index.css';

//#region
const Container = styled.div`
  height: 100vh;
  width: 100%;
  display: flex;
  padding: 1%;
  margin: 0 auto;
  position: relative;
  overflow-y: scroll;
`;
//#endregion

function ShiftPage() {
  return (
    <Container>
      <Helmet>
        <title>排班</title>
      </Helmet>
      <ShiftCalendar />
      <DefaultShift />
    </Container>
  );
}

const mapDispatchToProps = {};

export default connect(null, mapDispatchToProps)(ShiftPage);
