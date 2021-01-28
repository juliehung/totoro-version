import React from 'react';
import moment from 'moment';
import styled from 'styled-components';
import { Tooltip, Typography } from 'antd';
import ManPng from '../../../static/images/man.png';
import WomanPng from '../../../static/images/woman.png';
import DefaultPng from '../../../static/images/default.png';
import NP from '../../../static/images/NP.svg';
import { B1, G1, Gray700 } from '../../../utils/colors';
import { parsePatientNameWithVipMark } from '../../../utils/patientHelper';

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

export const allDoctors = 'THESE_ARE_ALL_DOCTORS';

const renderAvatarImg = (gender, firstVisitStatus) => {
  if (firstVisitStatus) {
    return <AvatarImg src={NP} alt="first-visit" />;
  } else {
    if (gender === 'MALE') return <AvatarImg src={ManPng} alt="male" />;
    if (gender === 'FEMALE') return <AvatarImg src={WomanPng} alt="female" />;
    return <AvatarImg src={DefaultPng} alt="default" />;
  }
};

const renderName = patient => {
  return (
    <Tooltip title={patient.name}>
      <NameContainer>
        {renderAvatarImg(patient.gender, patient.firstVisit)}
        <div>
          <span>{patient.medicalId}</span>
          <Title level={4}>{parsePatientNameWithVipMark(patient?.patientVipPatient, patient.name)}</Title>
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

const renderSubject = note => {
  if (!note || note === '') {
    return '無';
  }
  return note;
};

export function convertToTableSource(registrations, selectedDoctor) {
  const filterBySelectedDoctor = registrations.filter(r => {
    if (selectedDoctor === allDoctors || !selectedDoctor) {
      return true;
    }
    return r.userFirstName === selectedDoctor;
  });

  const sortByArrivalTime = filterBySelectedDoctor
    .slice()
    .sort((a, b) => moment(a.registrationArrivalTime).unix() - moment(b.registrationArrivalTime).unix());

  return sortByArrivalTime.map((r, i) => {
    const hasOwn = r.procedureCounter !== 0;
    const hasNhi = r.nhiProcedureCounter !== 0;
    const a23 = r.nhiExtendDisposalA23;
    const typeResult = [];
    if (hasNhi) typeResult.push(a23 ? '健(' + a23 + ')' : '健');
    if (hasOwn) typeResult.push('自');
    const type = typeResult.length === 0 ? r.registrationType.substring(0, 1) : typeResult.join(', ');

    const patient = {
      id: r.patientId,
      name: r.patientName,
      gender: r.patientGender,
      medicalId: r.patientMedicalId,
      birth: r.patientBirth,
      nationalId: r.patientNationalId,
      firstVisit: r.appointmentFirstVisit,
      patientVipPatient: !!r?.patientVipPatient,
    };

    return {
      key: r.registrationId,
      rowIndex: renderRowIndex(i + 1, r.registrationStatus),
      name: renderName(patient),
      arrivalTime: moment(r.registrationArrivalTime).local().format('YYYY-MM-DD HH:mm'),
      expectedArrivalTime: moment(r.appointmentExpectedArrivalTime).local().format('YYYY-MM-DD HH:mm'),
      age: moment().diff(r.patientBirth, 'years') + 'Y',
      doctor: r.userFirstName,
      subject: renderSubject(r.appointmentNote),
      type,
      patient,
      appointmentBaseFloor: r?.appointmentBaseFloor,
    };
  });
}
