import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import styled from 'styled-components';
import { Helmet } from 'react-helmet-async';
import ShiftCalendar from './ShiftCalendar';
import DefaultShift from './DefaultShift';
import { GApageView } from '../../ga';
import './index.css';

//#region
const Container = styled.div`
  height: 100%;
  display: flex;
  position: relative;
  padding: 10px;
`;
//#endregion

function ShiftPage() {
  useEffect(() => {
    GApageView();
  }, []);
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
