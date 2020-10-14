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
import { message } from 'antd';
import { onLeavePage } from './actions';
import { changeXrayModalVisible } from '../Home/actions';

export const appointmentPage = 'Appointment page';

const XRAY_GREETING_MESSAGE = 'XRAY_GREETING_MESSAGE';

//#region
export const GlobalStyle = createGlobalStyle`
  .ant-popover-inner {
    border-radius: 8px !important;
  }
  .ant-popover-content {
    box-shadow: 0 4px 25px 0 rgba(0, 0, 0, 0.1);
  }
  .fc-list-item{
    &:hover{
      &>.fc-widget-content{
        background-color:#f7f9fc!important;
      }
    }

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
const defaultView = 'resourceTimeGridDay';

function AppointmentPage(props) {
  const { xrayServerState, xrayServerError, onLeavePage, xrayOnRequest, changeXrayModalVisible } = props;
  const [viewType, setViewType] = useState(defaultView);

  useEffect(() => {
    GApageView();
  }, []);

  useEffect(() => {
    if (xrayOnRequest) {
      message.loading({ content: '載入中...', key: XRAY_GREETING_MESSAGE, duration: 10 });
    } else {
      if (xrayServerState && !xrayServerError) {
        message.success({ content: '開啟 xray 軟體中...', key: XRAY_GREETING_MESSAGE });
      } else if (!xrayServerState && xrayServerError) {
        message.destroy();
        changeXrayModalVisible(true);
      }
    }

    return () => {
      onLeavePage();
    };
  }, [xrayServerState, xrayServerError, onLeavePage, xrayOnRequest, changeXrayModalVisible]);

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

const mapStateToProps = ({ homePageReducer }) => ({
  xrayServerState: homePageReducer.xray.serverState,
  xrayServerError: homePageReducer.xray.serverError,
  xrayOnRequest: homePageReducer.xray.onRequest,
});

const mapDispatchToProps = { onLeavePage, changeXrayModalVisible };

export default connect(mapStateToProps, mapDispatchToProps)(AppointmentPage);
