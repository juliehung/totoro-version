import React from 'react';
import { connect } from 'react-redux';
import styled from 'styled-components';
import { Helmet } from 'react-helmet-async';
import ShiftCalendar from './ShiftCalendar';

//#region
const Container = styled.div`
  height: 100vh;
`;
//#endregion

function ShiftPage({ getUsersStart, changeSelectedDoctors, account }) {
  return (
    <Container>
      <Helmet>
        <title>預約</title>
      </Helmet>
      <ShiftCalendar />
    </Container>
  );
}

const mapStateToProps = state => ({});

const mapDispatchToProps = {};

export default connect(mapStateToProps, mapDispatchToProps)(ShiftPage);
