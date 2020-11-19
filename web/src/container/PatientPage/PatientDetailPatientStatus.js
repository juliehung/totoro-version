import React, { Fragment } from 'react';
import { connect } from 'react-redux';
import { Container, Header, Content, PatientDeclarationStatusItem, PatientSpecialStatusItem } from './component';
import { Spin, Empty } from 'antd';
import { toRefreshNhiPatientStatusWithHistory, convertPatientToPatientSpecialStatus } from './utils';

//#region
//#endregion

function PatientDetailPatientStatus(props) {
  const { loading, nhiPatientFluorideStatus, nhiPatientScalingStatus, patientSpecialStatus } = props;
  const isEmpty = !nhiPatientFluorideStatus && !nhiPatientScalingStatus && patientSpecialStatus.length === 0;

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
              {nhiPatientScalingStatus &&
                nhiPatientScalingStatus.map((status, index) => (
                  <PatientDeclarationStatusItem
                    key={`${status?.msg || 'PatientScalingStatusMsg'}-${index}`}
                    {...status}
                    title="91004C 牙結石清除-全口"
                  />
                ))}
              {nhiPatientFluorideStatus &&
                nhiPatientFluorideStatus.map((status, index) => (
                  <PatientDeclarationStatusItem
                    key={`${status?.msg || 'PatientFluorideStatusMsg'}-${index}`}
                    {...status}
                    title="81 塗氟"
                  />
                ))}
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
  loading: patientPageReducer.nhiPatientStatus.loading,
  nhiPatientFluorideStatus: toRefreshNhiPatientStatusWithHistory(
    patientPageReducer.nhiPatientStatus.nhiPatientFluorideStatus,
  ),
  nhiPatientScalingStatus: toRefreshNhiPatientStatusWithHistory(
    patientPageReducer.nhiPatientStatus.nhiPatientScalingStatus,
  ),
  patientSpecialStatus: convertPatientToPatientSpecialStatus(patientPageReducer.patient.patient),
});

const mapDispatchToProps = {};

export default connect(mapStateToProps, mapDispatchToProps)(PatientDetailPatientStatus);
