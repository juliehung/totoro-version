import React from 'react';
import { connect } from 'react-redux';
import { Container, Header, Content, PatientDeclarationStatusItem } from './component';
import { Spin } from 'antd';
import { convertNhiExtendPatientToPatientStatus } from './utils';

//#region
//#endregion

function PatientDetailPatientStatus(props) {
  const { patientStatus, loading } = props;

  return (
    <Container>
      <Header>
        <span>病患狀態</span>
      </Header>
      <Content noPadding>
        <Spin spinning={loading}>
          {patientStatus.scaling.is && <PatientDeclarationStatusItem {...patientStatus.scaling} title="洗牙91004C" />}
          {patientStatus.perio.is && <PatientDeclarationStatusItem {...patientStatus.perio} title="牙周病控制" />}
          {patientStatus.fluoride.is && <PatientDeclarationStatusItem {...patientStatus.fluoride} title="塗氟" />}
        </Spin>
      </Content>
    </Container>
  );
}

const mapStateToProps = ({ patientPageReducer }) => ({
  patientStatus: convertNhiExtendPatientToPatientStatus(
    patientPageReducer.nhiExtendPatient.nhiExtendPatient,
    patientPageReducer.patient.patient,
  ),
  loading: patientPageReducer.nhiExtendPatient.loading,
});

const mapDispatchToProps = {};

export default connect(mapStateToProps, mapDispatchToProps)(PatientDetailPatientStatus);
