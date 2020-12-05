import React, { useEffect } from 'react';
import styled from 'styled-components';
import { connect } from 'react-redux';
import PatientDetailHeader from './PatientDetailHeader';
import PatientDetailContent from './PatientDetailContent';
import { Spin } from 'antd';
import { useCookies } from 'react-cookie';
import { initPatientDetail } from './actions';
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
  const { id, initPatientDetail, isPatientNotFound, spinning, searchPatientDrawerOpen } = props;
  const [, setCookie] = useCookies();

  useEffect(() => {
    initPatientDetail(id);
    setCookie('patient_center_pid', id);
  }, [initPatientDetail, id, setCookie]);

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
});

const mapDispatchToProps = { initPatientDetail };

export default connect(mapStateToProps, mapDispatchToProps)(PatientDetail);
