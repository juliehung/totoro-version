import React, { useEffect, useState } from 'react';
import { connect } from 'react-redux';
import styled, { createGlobalStyle } from 'styled-components';
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
import MobileDetect from 'mobile-detect';

export const appointmentPage = 'Appointment page';

//#region
export const GlobalStyle = createGlobalStyle`
  .ant-popover-inner {
    border-radius: 8px !important;
  }
  .ant-popover-content {
    box-shadow: 0 4px 25px 0 rgba(0, 0, 0, 0.1);
  }
`;

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

const md = new MobileDetect(window.navigator.userAgent);
const phone = md.phone();
const defaultView = phone ? 'resourceTimeGridDay' : 'timeGridWeek';

function AppointmentPage() {
  const [viewType, setViewType] = useState(defaultView);

  useEffect(() => {
    GApageView();
  }, []);

  return (
    <Container>
      <Helmet>
        <title>預約</title>
      </Helmet>
      <GlobalStyle />
      <AppCalendar viewType={viewType} setViewType={setViewType} defaultView={defaultView} phone={phone} />
      <AppRight viewType={viewType} />
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
