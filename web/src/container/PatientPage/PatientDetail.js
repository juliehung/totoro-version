import React, { useEffect } from 'react';
import styled from 'styled-components';
import { connect, useDispatch } from 'react-redux';
import PatientDetailHeader from './PatientDetailHeader';
import PatientDetailContent from './PatientDetailContent';
import { Spin } from 'antd';
import { useCookies } from 'react-cookie';
import { initPatientDetail, getMedicalInstitutionCodeZhTw } from './actions';
import PatientNotFound from './PatientNotFound';
import AppointmentListModal from './AppointmentListModal';
import TreatmentListModal from './TreatmentListModal';
import SearchPatientButton from './SearchPatientButton';

//#region
const Container = styled.div`
  background-color: #f8fafb;
`;
//#endregion

function PatientDetail(props) {
  const dispatch = useDispatch();
  const {
    id,
    initPatientDetail,
    isPatientNotFound,
    spinning,
    searchPatientDrawerOpen,
    nhiAccumulatedMedicalRecords,
  } = props;
  const [, setCookie] = useCookies();

  useEffect(() => {
    initPatientDetail(id);
    setCookie('patient_center_pid', id);
  }, [initPatientDetail, id, setCookie]);

  useEffect(() => {
    if (nhiAccumulatedMedicalRecords.length > 0) {
      dispatch(getMedicalInstitutionCodeZhTw(nhiAccumulatedMedicalRecords));
    }
  }, [nhiAccumulatedMedicalRecords, dispatch]);

  return isPatientNotFound ? (
    <PatientNotFound />
  ) : (
    <Spin spinning={spinning}>
      <Container>
        <PatientDetailHeader />
        <PatientDetailContent id={id} />
        <AppointmentListModal />
        <TreatmentListModal />
        {!searchPatientDrawerOpen && <SearchPatientButton />}
      </Container>
    </Spin>
  );
}

const mapStateToProps = ({ patientPageReducer }) => ({
  isPatientNotFound: patientPageReducer.common.isPatientNotFound,
  spinning: patientPageReducer.common.loading,
  searchPatientDrawerOpen: patientPageReducer.searchPatient.drawerOpen,
  nhiAccumulatedMedicalRecords: patientPageReducer.medicalRecord.nhiAccumulatedMedicalRecords,
});

const mapDispatchToProps = { initPatientDetail, getMedicalInstitutionCodeZhTw };

export default connect(mapStateToProps, mapDispatchToProps)(PatientDetail);
