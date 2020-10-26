import React from 'react';
import { connect } from 'react-redux';
import { Container, Header, Content, PatientDeclarationStatusItem, PatientSpecialStatusItem } from './component';
import { Spin } from 'antd';
import { convertNhiExtendPatientToPatientDeclarationStatus, convertPatientToPatientSpecialStatus } from './utils';

//#region
//#endregion

function PatientDetailPatientStatus(props) {
  const { patientDeclarationStatus, loading, patientSpecialStatus } = props;

  return (
    <Container>
      <Header>
        <span>病患狀態</span>
      </Header>
      <Content noPadding>
        <Spin spinning={loading}>
          {patientDeclarationStatus.scaling.is && (
            <PatientDeclarationStatusItem {...patientDeclarationStatus.scaling} title="洗牙91004C" />
          )}
          {patientDeclarationStatus.perio.is && (
            <PatientDeclarationStatusItem {...patientDeclarationStatus.perio} title="牙周病控制" />
          )}
          {patientDeclarationStatus.fluoride.is && (
            <PatientDeclarationStatusItem {...patientDeclarationStatus.fluoride} title="塗氟" />
          )}
          {patientSpecialStatus.map((s, i) => (
            <PatientSpecialStatusItem key={i} {...s} />
          ))}
        </Spin>
      </Content>
    </Container>
  );
}

const mapStateToProps = ({ patientPageReducer }) => ({
  patientDeclarationStatus: convertNhiExtendPatientToPatientDeclarationStatus(
    patientPageReducer.nhiExtendPatient.nhiExtendPatient,
    patientPageReducer.patient.patient,
  ),
  patientSpecialStatus: convertPatientToPatientSpecialStatus(patientPageReducer.patient.patient),
  loading: patientPageReducer.nhiExtendPatient.loading,
});

const mapDispatchToProps = {};

export default connect(mapStateToProps, mapDispatchToProps)(PatientDetailPatientStatus);
