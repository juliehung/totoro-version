import React, { useState } from 'react';
import { connect } from 'react-redux';
import styled from 'styled-components';
import { Container, Header, Content, Count } from './component';
import { Badge, Switch } from 'antd';
import { convertAppointmentToCardObject } from './utils';
import analysisAppointments from '../AppointmentPage/utils/analysisAppointments';
import { getBaseUrl } from '../../utils/getBaseUrl';

//#region
const Item = styled.div`
  background: ${props => (props.isFuture ? '#cfecff' : '#f7f9fc')};
  min-height: 80px;
  border-radius: 8px;
  padding: 10px;
  font-size: 14px;
  display: grid;
  grid-template-columns: 10% 70% 20%;
  row-gap: 5px;
  &:not(:last-child) {
    margin-bottom: 10px;
  }

  & > * {
    justify-self: start;
  }

  & > :first-child {
    place-self: center;
  }

  & > :last-child {
    grid-column-start: 2;
    grid-column-end: 4;
    overflow: hidden;
    width: 100%;
    word-wrap: break-word;
  }
`;

const EmptyString = styled.div`
  width: 100%;
  height: 100%;
  color: #8f9bb3;
  font-size: 15px;
  display: flex;
  justify-content: center;
  align-items: center;
`;

const SwitchWrap = styled.div`
  margin-left: 2px;
  span {
    margin-left: 2px;
    color: #8f9bb3;
  }
`;

//#endregion

function PatientDetailRecentAppointment(props) {
  const { appointments, appointmentsAnalysis } = props;
  const [futureStatus, switchFuture] = useState(false);
  const filteredAppointments = futureStatus ? appointments.filter(a => a.isFuture && !a.isRegistration) : appointments;
  const filteredAppointmentsAmount = futureStatus
    ? appointments.filter(a => a.isFuture && !a.isRegistration).length
    : appointments?.length ?? 0;
  return (
    <Container>
      <Header>
        <div>
          <span>約診</span>
          <Count>{filteredAppointmentsAmount}</Count>
          <span style={{ color: '#8f9bb3', marginLeft: '10px', fontSize: '12px' }}>
            (爽約 {appointmentsAnalysis.noShow} 次;取消 {appointmentsAnalysis.cancel} 次)
          </span>
        </div>
        <SwitchWrap>
          <Switch onChange={() => switchFuture(!futureStatus)} />
          <span>只顯示未來</span>
        </SwitchWrap>
      </Header>
      <Content>
        {filteredAppointments?.length ? (
          filteredAppointments.map(a => (
            <Item key={a.id} isFuture={a.isFuture}>
              <div>
                {a.isRegistration ? (
                  <Badge color={'#616161'} />
                ) : a.isCancel ? (
                  <Badge color={'#ea5455'} />
                ) : a.isFuture ? (
                  <Badge status="processing" />
                ) : (
                  <Badge color={'#ea5455'} />
                )}
              </div>
              <div>
                <span style={{ fontWeight: 600 }}>
                  {a.isRegistration ? '掛號完成' : a.isCancel ? '已取消' : a.isFuture ? '即將到來' : '預約未到'} -{' '}
                  {a.expectedArrivalTimeString}
                </span>
              </div>
              <span style={{ color: '#8f9bb3' }}>{a?.doctor?.firstName}</span>
              <div>
                <span>{a.note}</span>
              </div>
            </Item>
          ))
        ) : (
          <EmptyString>
            <span>
              無即將到來的約診! 立即{' '}
              <a href={`${getBaseUrl()}#/appointment`} target="_blank" rel="noopener noreferrer">
                建立預約
              </a>
            </span>
          </EmptyString>
        )}
      </Content>
    </Container>
  );
}

const mapStateToProps = ({ patientPageReducer, homePageReducer }) => ({
  appointments: convertAppointmentToCardObject(patientPageReducer.appointment.appointment, homePageReducer.user.users),
  appointmentsAnalysis: analysisAppointments(patientPageReducer.appointment.appointment),
});

export default connect(mapStateToProps)(PatientDetailRecentAppointment);
