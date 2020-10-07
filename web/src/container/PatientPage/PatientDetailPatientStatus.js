import React from 'react';
import styled from 'styled-components';
import { connect } from 'react-redux';
import { Container, Header, Content } from './component';
import { Spin } from 'antd';
import { convertNhiExtendPatientToPatientStatus } from './utils';
import allDone from '../../images/all-done.svg';
import close from '../../images/close.svg';
import { toRocString } from './utils';

//#region
const Item = styled.div`
  display: flex;
  align-items: center;
  min-height: 60px;
  border-bottom: 1px solid rgba(0, 0, 0, 0.1);
  padding: 10px 0;
  border-left: 4px solid ${props => (props.can ? '#00b383' : '#f2877d')};
  & > * {
    margin-left: 10px;
  }
`;
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
          <Item can={patientStatus.scaling.can}>
            <img src={patientStatus.scaling.can ? allDone : close} height={'90%'} alt="icon" />
            <span>洗牙91004C</span>
            <span>
              {`${patientStatus.scaling?.next ? toRocString(patientStatus.scaling?.next) : ''}`}
              {patientStatus.scaling.can ? '可申報' : '不可申報'}
            </span>
          </Item>
          <Item can={patientStatus.perio.can}>
            <img src={patientStatus.perio.can ? allDone : close} height={'90%'} alt="icon" />
            <span>牙周病控制</span>
            <span>
              {`${patientStatus.perio?.next ? toRocString(patientStatus.perio?.next) : ''}`}
              {patientStatus.perio.can ? '可申報' : '不可申報'}
            </span>
          </Item>
          <Item can={patientStatus.fluoride.can}>
            <img src={patientStatus.fluoride.can ? allDone : close} height={'90%'} alt="icon" />
            <span>塗氟</span>
            <span>
              {`${patientStatus.fluoride?.next ? toRocString(patientStatus.fluoride?.next) : ''}`}
              {patientStatus.fluoride.can ? '可申報' : '不可申報'}
            </span>
          </Item>
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
