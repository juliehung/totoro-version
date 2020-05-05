import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import styled from 'styled-components';
import AppCalendar from './AppCalendar';
import AppRight from './AppRight';
import PrintModal from './PrintModal';
import ConfirmDropModal from './ConfirmDropModal';
import CreatAppModal from './CreateAppModal';
import EditAppModal from './EditAppModal';
import CreateCalendarEventModal from './CreateCalendarEventModal';
import EditCalendarEventModal from './EditCalendarEventModal';
import { Helmet } from 'react-helmet-async';
import { GApageView } from '../../ga';

export const appointmentPage = 'Appointment page';

//#region
const Container = styled.div`
  height: 100%;
  display: flex;
  flex-wrap: no-wrap;
  @media (max-width: 800px) {
    flex-direction: column;
  }
  & *::-webkit-scrollbar {
    display: none;
  }

  * {
    scrollbar-width: none;
  }
`;
//#endregion

function AppointmentPage() {
  useEffect(() => {
    GApageView();
  }, []);

  return (
    <Container>
      <Helmet>
        <title>預約</title>
      </Helmet>
      <AppCalendar />
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

const mapStateToProps = () => ({});

// const mapDispatchToProps = {};

export default connect(mapStateToProps)(AppointmentPage);
