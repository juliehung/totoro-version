import React, { Fragment } from 'react';
import { connect } from 'react-redux';
import styled, { createGlobalStyle } from 'styled-components';
import { Drawer, Tabs } from 'antd';
import { changeDrawerVisible, searchPatient } from './actions';
import { appendAmountOnTitle } from './utils';
import PatientSearchPatientList from './PatientSearchPatientList';
import PatientSearchInput from './PatientSearchInput';
const { TabPane } = Tabs;

//#region
export const GlobalStyle = createGlobalStyle`
  .ant-drawer-body {
    padding: 24px 0 !important;
  }
`;

const StyledDrawer = styled(Drawer)`
  padding: 0;
`;

const Container = styled.div`
  display: flex;
  flex-direction: column;
  align-items: center;
  height: 100%;
`;

const TabContainer = styled.div`
  width: 100%;
  flex-grow: 1;
`;
//#endregion

function PatientSearchDrawer(props) {
  const {
    drawerOpen,
    changeDrawerVisible,
    searchPatient,
    patients,
    patientsMatchedName,
    patientsMatchedPhone,
    patientsMatchedBirth,
    patientsMatchedMrn,
    patientsMatchedNationalId,
    searchedText,
  } = props;

  return (
    <Fragment>
      <GlobalStyle />
      <StyledDrawer
        title="搜尋病患"
        closable={false}
        onClose={() => {
          changeDrawerVisible(false);
        }}
        visible={drawerOpen}
        placement="top"
        height="80%"
      >
        <Container>
          <PatientSearchInput searchPatient={searchPatient} />
          <TabContainer>
            <Tabs defaultActiveKey="1" centered>
              <TabPane tab={appendAmountOnTitle('全部', patients?.length)} key="1">
                <PatientSearchPatientList
                  patients={patients}
                  changeDrawerVisible={changeDrawerVisible}
                  keyword={searchedText}
                />
              </TabPane>
              <TabPane tab={appendAmountOnTitle('姓名', patientsMatchedName?.length)} key="2">
                <PatientSearchPatientList
                  patients={patientsMatchedName}
                  changeDrawerVisible={changeDrawerVisible}
                  keyword={searchedText}
                />
              </TabPane>
              <TabPane tab={appendAmountOnTitle('連絡電話', patientsMatchedPhone?.length)} key="3">
                <PatientSearchPatientList
                  patients={patientsMatchedPhone}
                  changeDrawerVisible={changeDrawerVisible}
                  keyword={searchedText}
                />
              </TabPane>
              <TabPane tab={appendAmountOnTitle('生日', patientsMatchedBirth?.length)} key="4">
                <PatientSearchPatientList
                  patients={patientsMatchedBirth}
                  changeDrawerVisible={changeDrawerVisible}
                  keyword={searchedText}
                />
              </TabPane>
              <TabPane tab={appendAmountOnTitle('病例編號', patientsMatchedMrn?.length)} key="5">
                <PatientSearchPatientList
                  patients={patientsMatchedMrn}
                  changeDrawerVisible={changeDrawerVisible}
                  keyword={searchedText}
                />
              </TabPane>
              <TabPane tab={appendAmountOnTitle('身分證號', patientsMatchedNationalId?.length)} key="6">
                <PatientSearchPatientList
                  patients={patientsMatchedNationalId}
                  changeDrawerVisible={changeDrawerVisible}
                  keyword={searchedText}
                />
              </TabPane>
            </Tabs>
          </TabContainer>
        </Container>
      </StyledDrawer>
    </Fragment>
  );
}

const mapStateToProps = ({ patientPageReducer }) => ({
  drawerOpen: patientPageReducer.common.drawerOpen,
  patients: patientPageReducer.searchPatient.total,
  patientsMatchedName: patientPageReducer.searchPatient.name,
  patientsMatchedPhone: patientPageReducer.searchPatient.phone,
  patientsMatchedBirth: patientPageReducer.searchPatient.birth,
  patientsMatchedMrn: patientPageReducer.searchPatient.mrn,
  patientsMatchedNationalId: patientPageReducer.searchPatient.nationalId,
  searchedText: patientPageReducer.searchPatient.searchedText,
});

const mapDispatchToProps = { changeDrawerVisible, searchPatient };

export default connect(mapStateToProps, mapDispatchToProps)(PatientSearchDrawer);
