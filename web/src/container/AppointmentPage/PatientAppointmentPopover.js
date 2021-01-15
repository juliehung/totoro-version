import styled from 'styled-components';
import { Badge, Popover } from 'antd';
import React from 'react';

const PopoverContainer = styled(Popover)`
  cursor: pointer;
  font-size: 15px;
  line-height: 1.33;
  color: #3366ff;
  margin-left: 5px;
`;
const Content = styled.div`
  height: 100%;
  max-height: 250px;
  overflow-y: scroll;
  padding: ${props => (props.noPadding ? 0 : '10px')};
  ${props => props.paddingBottom && 'padding-bottom: 20px;'}
  scrollbar-width: none;
  border-radius: 0 8px 8px;
  &::-webkit-scrollbar {
    display: none;
  }
  .ant-spin-nested-loading {
    ${props => props.paddingBottom && 'overflow: hidden;'}
  }
  .ant-spin-container {
    > div {
      &:last-child {
        overflow: hidden;
      }
    }
  }

  .selected-patient-title {
    font-size: 15px;
    font-weight: 600;
    line-height: 1.6;
    color: #222b45;
    display: flex;
    justify-content: flex-start;
    align-items: center;
    margin-bottom: 10px;

    > div {
      width: 20px;
      height: 20px;
      border-radius: 10px;
      margin-left: 5px;
      background-color: #e4eaff;
      font-size: 12px;
      font-weight: 600;
      line-height: 1.33;
      color: #8f9bb3;
      text-align: center;
      display: flex;
      justify-content: center;
      align-items: center;
    }
  }
`;
const Item = styled.div`
  background: ${props => (props.isFuture ? '#cfecff' : '#f7f9fc')};
  min-height: 80px;
  border-radius: 8px;
  padding: 10px;
  font-size: 14px;
  display: grid;
  grid-template-columns: 6% 74% 20%;
  row-gap: 5px;
  &:not(:last-child) {
    margin-bottom: 10px;
  }

  & > * {
    justify-self: start;
  }

  & > :first-child {
    justify-self: flex-end;
  }

  & > :nth-child(3) {
    margin-left: 4px;
  }

  & > :last-child {
    grid-column-start: 2;
    grid-column-end: 4;
    overflow: hidden;
    width: 100%;
    word-wrap: break-word;
  }
`;

const PatientAppointmentPopover = ({ patient, patientAppointments }) => {
  const selectedPatientAppointmentsContent = (
    <Content>
      <div className="selected-patient-title">
        <span>{patient && patient.name} 的未來約診</span>
        <div>{patientAppointments?.length}</div>
      </div>
      {patientAppointments?.length &&
        patientAppointments.map(a => (
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
        ))}
    </Content>
  );
  return patient && patient?.name && patientAppointments && patientAppointments?.length !== 0 ? (
    <PopoverContainer
      content={selectedPatientAppointmentsContent}
      placement={window.innerWidth <= 767 ? 'bottom' : 'leftTop'}
      trigger={['hover', 'click']}
      overlayClassName={'selected-patient-content'}
    >
      <span>(更多)</span>
    </PopoverContainer>
  ) : null;
};

export default PatientAppointmentPopover;
