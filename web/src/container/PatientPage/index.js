import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import styled from 'styled-components';
import { useParams } from 'react-router-dom';
import { useCookies } from 'react-cookie';
import { Helmet } from 'react-helmet-async';
import { message } from 'antd';
import { changeDrawerVisible } from './actions';
import PatientDetail from './PatientDetail';
import EmptyPage from './EmptyPage';
import PatientSearchDrawer from './PatientSearchDrawer';
import { changeXrayModalVisible, restoreXrayState } from '../Home/actions';
import { onLeavePage } from './actions';
import GAHelper from '../../ga';

//#region
const Container = styled.div`
  background-color: #f8fafb;
  height: 100%;
  position: relative;
`;
//#endregion

const XRAY_GREETING_MESSAGE = 'XRAY_GREETING_MESSAGE';

function PatientPage(props) {
  const { id } = useParams();
  const [cookies] = useCookies();
  const { patient_center_pid } = cookies;
  const {
    changeDrawerVisible,
    xrayOnRequest,
    xrayServerState,
    xrayServerError,
    changeXrayModalVisible,
    onLeavePage,
    restoreXrayState,
  } = props;

  useEffect(() => {
    let url;
    if (!id && patient_center_pid) {
      url = `${window.location.origin}${window.location.pathname}#/patient/${patient_center_pid}`;
    } else {
      return;
    }
    window.location.replace(url);
  }, [id, changeDrawerVisible, patient_center_pid]);

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
      restoreXrayState();
    };
  }, [xrayServerState, xrayServerError, xrayOnRequest, changeXrayModalVisible, restoreXrayState]);

  useEffect(() => {
    return () => {
      onLeavePage();
    };
  }, [onLeavePage]);

  useEffect(() => {
    GAHelper.pageView();
  }, []);

  return (
    <Container>
      <Helmet>
        <title>病患中心</title>
      </Helmet>
      <PatientSearchDrawer />
      {id ? <PatientDetail id={id} /> : <EmptyPage />}
    </Container>
  );
}

const mapStateToProps = ({ homePageReducer }) => ({
  xrayOnRequest: homePageReducer.xray.onRequest,
  xrayServerState: homePageReducer.xray.serverState,
  xrayServerError: homePageReducer.xray.serverError,
});

const mapDispatchToProps = { changeDrawerVisible, onLeavePage, changeXrayModalVisible, restoreXrayState };

export default connect(mapStateToProps, mapDispatchToProps)(PatientPage);
