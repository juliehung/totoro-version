import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import styled from 'styled-components';
import AppCalendar from './AppCalendar';
import AppRight from './AppRight';
import PrintModal from './PrintModal';
import ConfirmDropModal from './ConfirmDropModal';
import CreatAppModal from './CreateAppModal';
import EditAppModal from './EditAppModal';
import { getUsersStart } from './actions';
import CreateCalendarEventModal from './CreateCalendarEventModal';
import EditCalendarEventModal from './EditCalendarEventModal';
import { Helmet } from 'react-helmet-async';

const Container = styled.div`
  height: 100vh;
  display: flex;
  flex-wrap: no-wrap;
`;
const CalendarContainer = styled.div`
  width: 85%;
`;

function AppointmentPage({ getUsersStart }) {
  useEffect(() => {
    getUsersStart();
  }, [getUsersStart]);

  return (
    <Container>
      <Helmet><title>預約</title></Helmet>
      <CalendarContainer>
        <AppCalendar />
      </CalendarContainer>
      <AppRight />
      <PrintModal />
      <ConfirmDropModal />
      <CreatAppModal />
      <EditAppModal />
      <CreateCalendarEventModal />
      <EditCalendarEventModal />
    </Container>
  );
}

const mapStateToProps = state => ({});

const mapDispatchToProps = { getUsersStart };

export default connect(mapStateToProps, mapDispatchToProps)(AppointmentPage);
