import React, { Fragment } from 'react';
import { connect } from 'react-redux';
import { Container, Header, Content, PatientDeclarationStatusItem, PatientSpecialStatusItem } from './component';
import { Spin, Empty } from 'antd';
import { toRefreshNhiPatientStatusWithHistory, convertPatientToPatientSpecialStatus } from './utils';
import { ReactComponent as EmptyDataSvg } from '../../images/empty-data.svg';

//#region
//#endregion

function PatientDetailPatientStatus(props) {
  const { loading, nhiPatientFluorideStatus, nhiPatientScalingStatus, patientSpecialStatus } = props;
  const isEmpty =
    (!nhiPatientFluorideStatus && !nhiPatientScalingStatus && !patientSpecialStatus) ||
    (nhiPatientFluorideStatus &&
      nhiPatientFluorideStatus.length === 0 &&
      nhiPatientScalingStatus &&
      nhiPatientScalingStatus.length === 0 &&
      patientSpecialStatus &&
      patientSpecialStatus.length === 0);

  return (
    <Container>
      <Header>
        <span>病患狀態</span>
      </Header>
      <Content noPadding paddingBottom>
        <Spin spinning={loading}>
          {isEmpty ? (
            <div className="empty-wrap">
              <Empty image={<EmptyDataSvg />} description={<span>無資料，請儘速讀取健保卡或雲端藥歷</span>} />
            </div>
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
  nhiPatientFluorideStatus: toRefreshNhiPatientStatusWithHistory({
    patientStatusObj: patientPageReducer.nhiPatientStatus.nhiPatientFluorideStatus,
    patient: patientPageReducer.patient.patient,
    type: 'fluoride',
  }),
  nhiPatientScalingStatus: toRefreshNhiPatientStatusWithHistory({
    patientStatusObj: patientPageReducer.nhiPatientStatus.nhiPatientScalingStatus,
    patient: patientPageReducer.patient.patient,
    type: 'scaling',
  }),
  patientSpecialStatus: convertPatientToPatientSpecialStatus(patientPageReducer.patient.patient),
});

const mapDispatchToProps = {};

export default connect(mapStateToProps, mapDispatchToProps)(PatientDetailPatientStatus);
