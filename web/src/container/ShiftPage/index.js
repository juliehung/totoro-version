import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import styled from 'styled-components';
import { Helmet } from 'react-helmet-async';
import ShiftCalendar from './ShiftCalendar';
import DefaultShift from './DefaultShift';
import GAHelper from '../../ga';
import './index.css';
import { leavePage } from './actions';

//#region
const Container = styled.div`
  height: 100%;
  display: flex;
  position: relative;
`;
//#endregion

function ShiftPage(props) {
  const { leavePage } = props;

  useEffect(() => {
    GAHelper.pageView();
    return () => {
      leavePage();
    };
  }, [leavePage]);
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

const mapDispatchToProps = { leavePage };

export default connect(null, mapDispatchToProps)(ShiftPage);
