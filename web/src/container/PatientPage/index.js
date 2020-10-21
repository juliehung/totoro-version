import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import styled from 'styled-components';
import { useParams } from 'react-router-dom';
import { useCookies } from 'react-cookie';
import { changeDrawerVisible } from './actions';
import PatientDetail from './PatientDetail';
import EmptyPage from './EmptyPage';
import PatientSearchDrawer from './PatientSearchDrawer';
import { Helmet } from 'react-helmet-async';
import { changeXrayModalVisible } from '../Home/actions';
import { onLeavePage } from './actions';
import { message } from 'antd';

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
    createAppointmentSuccess,
  } = props;

  useEffect(() => {
    if (!id && patient_center_pid) {
      const url = `${window.location.origin}${window.location.pathname}#/patient/${patient_center_pid}`;
      window.location.replace(url);
    }
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
  }, [xrayServerState, xrayServerError, onLeavePage, xrayOnRequest, changeXrayModalVisible]);

  useEffect(() => {
    return () => {
      onLeavePage();
    };
  }, [onLeavePage]);

  useEffect(() => {
    if (createAppointmentSuccess) {
      message.success('新增預約成功');
    }
  }, [createAppointmentSuccess]);

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

const mapStateToProps = ({ patientPageReducer, homePageReducer }) => ({
  xrayOnRequest: homePageReducer.xray.onRequest,
  xrayServerState: homePageReducer.xray.serverState,
  xrayServerError: homePageReducer.xray.serverError,
  createAppointmentSuccess: patientPageReducer.createAppointment.createSuccess,
});

const mapDispatchToProps = { changeDrawerVisible, onLeavePage, changeXrayModalVisible };

export default connect(mapStateToProps, mapDispatchToProps)(PatientPage);
