import React, { Fragment } from 'react';
import { connect } from 'react-redux';
import { Container, Header, Content, PatientDeclarationStatusItem, PatientSpecialStatusItem } from './component';
import { Spin, Empty } from 'antd';
import { convertNhiExtendPatientToPatientDeclarationStatus, convertPatientToPatientSpecialStatus } from './utils';

//#region
//#endregion

function PatientDetailPatientStatus(props) {
  const { patientDeclarationStatus, loading, patientSpecialStatus } = props;

  const isEmpty =
    !patientDeclarationStatus.scaling.is &&
    !patientDeclarationStatus.perio.is &&
    !patientDeclarationStatus.fluoride.is &&
    patientSpecialStatus.length === 0;

  return (
    <Container>
      <Header>
        <span>病患狀態</span>
      </Header>
      <Content noPadding>
        <Spin spinning={loading}>
          {isEmpty ? (
            <Empty image={Empty.PRESENTED_IMAGE_SIMPLE} />
          ) : (
            <Fragment>
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
            </Fragment>
          )}
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
