import React, { useEffect } from 'react';
import styled from 'styled-components';
import { connect } from 'react-redux';
import PatientDetailHeader from './PatientDetailHeader';
import PatientDetailContent from './PatientDetailContent';
import { Spin } from 'antd';
import { initPatientDetail } from './actions';
import PatientNotFound from './PatientNotFound';
import CreateAppointmentModal from './CreateAppointmentModal';
import AppointmentListModal from './AppointmentListModal';
import TreatmentListModal from './TreatmentListModal';
import SarchPatientButton from './SarchPatientButton';

//#region
const Container = styled.div`
  background-color: #f8fafb;
`;
//#endregion

function PatientDetail(props) {
  const { id, initPatientDetail, isPatientNotFound, spinning, searchPatientDrawerOpen } = props;

  useEffect(() => {
    initPatientDetail(id);
  }, [initPatientDetail, id]);

  return isPatientNotFound ? (
    <PatientNotFound />
  ) : (
    <Spin spinning={spinning}>
      <Container>
        <PatientDetailHeader />
        <PatientDetailContent />
        <CreateAppointmentModal />
        <AppointmentListModal />
        <TreatmentListModal />
        {!searchPatientDrawerOpen && <SarchPatientButton />}
      </Container>
    </Spin>
  );
}

const mapStateToProps = ({ patientPageReducer }) => ({
  isPatientNotFound: patientPageReducer.common.isPatientNotFound,
  spinning: patientPageReducer.common.loading,
  searchPatientDrawerOpen: patientPageReducer.common.drawerOpen,
});

const mapDispatchToProps = { initPatientDetail };

export default connect(mapStateToProps, mapDispatchToProps)(PatientDetail);
