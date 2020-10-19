import React from 'react';
import styled from 'styled-components';
import { connect } from 'react-redux';
import PatientDetailPatientStatus from './PatientDetailPatientStatus';
import PatientDetailRercentTreatment from './PatientDetailRercentTreatment';
import PatientDetailRecentAppointment from './PatientDetailRecentAppointment';
import PatientDetailDiagnosisNote from './PatientDetailDiagnosisNote';
import PatientDetailHelthICCardTreatmentRecord from './PatientDetailHelthICCardTreatmentRecord';
import PatientDetailAccumulatedMedicalRecord from './PatientDetailAccumulatedMedicalRecord';

//#region
const Container = styled.div`
  background-color: #f8fafb;
  padding: 20px 0 10px;
  @media (min-width: 1000px) {
    display: grid;
    grid-template-columns: 20fr 10fr 30fr 5fr 10fr 25fr;
    grid-gap: 15px;

    & > :nth-child(2) {
      grid-row: 1;
      grid-column: 2/6;
    }
    & > :nth-child(4) {
      grid-row: 2;
      grid-column: 1/3;
      z-index: 400;

      &:focus-within {
        grid-column: 1/4;
      }
    }
    & > :nth-child(5) {
      grid-row: 2;
      grid-column: 3/5;
    }
    & > :nth-child(6) {
      grid-row: 2;
      grid-column: 5/7;
    }
  }
  @media (max-width: 1000px) {
    & > * {
      margin-bottom: 10px;
    }
  }
`;

//#endregion

function PatientDetailContent() {
  return (
    <Container>
      <PatientDetailPatientStatus />
      <PatientDetailRercentTreatment />
      <PatientDetailRecentAppointment />
      <PatientDetailDiagnosisNote />
      <PatientDetailAccumulatedMedicalRecord />
      <PatientDetailHelthICCardTreatmentRecord />
    </Container>
  );
}

const mapStateToProps = () => ({});

const mapDispatchToProps = {};

export default connect(mapStateToProps, mapDispatchToProps)(PatientDetailContent);
