import React from 'react';
import moment from 'moment';
import styled from 'styled-components';
import { Tooltip, Typography } from 'antd';
import ManPng from '../../../static/images/man.png';
import WomanPng from '../../../static/images/woman.png';
import DefaultPng from '../../../static/images/default.png';
import { B1, G1, Gray700 } from '../../../utils/colors';

const { Title } = Typography;

//#region
const NameContainer = styled.div`
  display: flex;
  align-items: center;
  & > div {
    flex: 1 1;
    width: 5px;
    & > * {
      white-space: nowrap;
      overflow: hidden;
      text-overflow: ellipsis;
    }
  }
  @media (max-width: 768px) {
    & img {
      display: none;
    }
  }
`;

const RowIndexContainer = styled.div`
  display: flex;
  align-items: center;
  justify-content: flex-start;
  min-width: 50px;
`;

const Status = styled.div`
  width: 6px;
  height: 50px;
  border-radius: 25px;
  margin-right: 10px;
`;

const StyledTitle = styled(Title)`
  margin: 0 !important;
`;

const AvatarImg = styled.img`
  width: 48px;
  height: 48px;
  margin-right: 10px;
  flex-shrink: 0;
`;

//#endregion

const renderAvatarImg = gender => {
  if (gender === 'MALE') return <AvatarImg src={ManPng} alt="male" />;
  if (gender === 'FEMALE') return <AvatarImg src={WomanPng} alt="female" />;
  return <AvatarImg src={DefaultPng} alt="default" />;
};

const renderName = patient => {
  return (
    <Tooltip title={patient.name}>
      <NameContainer>
        {renderAvatarImg(patient.gender)}
        <div>
          <span>{patient.medicalId}</span>
          <Title level={4}>{patient.name}</Title>
        </div>
      </NameContainer>
    </Tooltip>
  );
};

const renderRowIndex = (rowIndex, status) => {
  let c = Gray700;
  if (status === 'IN_PROGRESS') c = G1;
  if (status === 'PENDING') c = B1;
  return (
    <RowIndexContainer>
      <Status style={{ background: c }} />
      <StyledTitle level={3}>{rowIndex}</StyledTitle>
    </RowIndexContainer>
  );
};

const renderSubject = (subject, note) => {
  if ((!subject || subject === '') && (!note || note === '')) {
    return '無';
  }
  return subject && subject !== '' ? subject + ',' + note : note;
};

export const allDoctors = 'THESE_ARE_ALL_DOCTORS';

export const convertToTableSource = (registrations, selectedDoctor) => {
  const tableSource = registrations
    // Don't show cancel appointment and no registration
    .filter(appt => appt.status !== 'CANCEL' && appt.registration && appt.registration.id)
    .filter(appt => {
      if (!selectedDoctor || !appt.doctor.user || selectedDoctor === allDoctors) {
        return true;
      }
      return appt.doctor.user.login === selectedDoctor;
    })
    .slice()
    .sort((a, b) => moment(a.registration.arrivalTime).unix() - moment(b.registration.arrivalTime).unix());

  let i = 1;
  return tableSource.map(appt => {
    return {
      key: appt.id,
      rowIndex: renderRowIndex(i++, appt.registration.status),
      name: renderName(appt.patient),
      patient: appt.patient,
      arrivalTime:
        appt.registration.arrivalTime && moment(appt.registration.arrivalTime).local().format('YYYY-MM-DD HH:mm'),
      expectedArrivalTime:
        appt.expectedArrivalTime && moment(appt.expectedArrivalTime).local().format('YYYY-MM-DD HH:mm'),
      age: moment().diff(appt.patient.birth, 'years') + 'Y',
      type: appt.registration.type,
      doctor: appt.doctor.user.firstName,
      subject: renderSubject(appt.subject, appt.note),
    };
  });
};
