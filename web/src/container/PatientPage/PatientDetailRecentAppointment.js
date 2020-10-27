import React from 'react';
import { connect } from 'react-redux';
import styled from 'styled-components';
import { Container, Header, Content, Count, BlueDottedUnderlineText } from './component';
import { Badge } from 'antd';
import { convertAppointmentToCardObject } from './utils';
import analysisAppointments from '../AppointmentPage/utils/analysisAppointments';
import { changeAppointmentListModalVisible } from './actions';

//#region
const Item = styled.div`
  background: ${props => (props.isCanceled ? '#f7f9fc' : '#cfecff')};
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
//#endregion

function PatientDetailRecentAppointment(props) {
  const { futureAppointments, appointmentsAnalysis, changeAppointmentListModalVisible } = props;

  const futureAppointmentsAmount = futureAppointments?.length ?? 0;

  return (
    <Container>
      <Header>
        <div>
          <span>約診</span>
          <Count>{futureAppointmentsAmount}</Count>
          <span style={{ color: '#8f9bb3', marginLeft: '10px', fontSize: '12px' }}>
            (爽約 {appointmentsAnalysis.noShow} 次;取消 {appointmentsAnalysis.cancel} 次)
          </span>
        </div>
        <div
          onClick={() => {
            changeAppointmentListModalVisible(true);
          }}
        >
          <BlueDottedUnderlineText text={'預約紀錄'} />
        </div>
      </Header>
      <Content>
        {futureAppointments?.length ? (
          futureAppointments.map(a => (
            <Item key={a.id} isCanceled={a.isCancel}>
              <div>{a.isCancel ? <Badge status="error" /> : <Badge status="processing" />}</div>
              <div>
                <span style={{ fontWeight: 600 }}>
                  {a.isCancel ? '已取消' : '即將到來'} - {a.expectedArrivalTime}
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
              目前沒有約診! 立即{' '}
              <a href="/#/appointment" target="_blank" rel="noopener noreferrer">
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
  futureAppointments: convertAppointmentToCardObject(
    patientPageReducer.appointment.appointment,
    homePageReducer.user.users,
  ).filter(a => a.isFuture && !a.isRegistration),
  appointmentsAnalysis: analysisAppointments(patientPageReducer.appointment.appointment),
});

const mapDispatchToProps = { changeAppointmentListModalVisible };

export default connect(mapStateToProps, mapDispatchToProps)(PatientDetailRecentAppointment);
