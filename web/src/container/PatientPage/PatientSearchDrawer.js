import React, { Fragment, useEffect, useState } from 'react';
import { connect } from 'react-redux';
import styled, { createGlobalStyle } from 'styled-components';
import { Drawer, Tabs } from 'antd';
import { changeDrawerVisible, searchPatient, getRegistrationToday } from './actions';
import { appendAmountOnTitle } from './utils';
import PatientSearchPatientList from './PatientSearchPatientList';
import PatientSearchInput from './PatientSearchInput';
import close from '../../images/close-white.svg';

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

const CloseContainer = styled.div`
  width: 100%;
  height: 20%;
  position: fixed;
  bottom: ${props => (props.show ? '0px' : '-20%')};
  right: 0px;
  z-index: 10000;
  color: #fff;
  font-size: 26px;
  font-weight: bold;
  transition: all ease-in-out 300ms;
  display: flex;
  justify-content: flex-end;
  align-items: flex-start;

  & > div {
    margin-right: 20px;
    cursor: pointer;
    display: flex;
    align-items: center;
  }
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
    getRegistrationToday,
  } = props;

  const [activeKey, setActiveKey] = useState('1');

  useEffect(() => {
    setActiveKey('1');
  }, [patients]);

  useEffect(() => {
    if (drawerOpen) {
      getRegistrationToday();
    }
  }, [drawerOpen, getRegistrationToday]);

  return (
    <Fragment>
      <GlobalStyle />
      <StyledDrawer
        title={null}
        closable={false}
        onClose={() => {
          changeDrawerVisible(false);
        }}
        visible={drawerOpen}
        placement="top"
        height="80%"
      >
        <Container>
          <PatientSearchInput searchPatient={searchPatient} getRegistrationToday={getRegistrationToday} />
          <TabContainer>
            <Tabs centered activeKey={activeKey} onTabClick={setActiveKey}>
              <TabPane tab={appendAmountOnTitle('全部', patients?.length)} key="1">
                <PatientSearchPatientList
                  patients={patients}
                  changeDrawerVisible={changeDrawerVisible}
                  keyword={searchedText}
                />
              </TabPane>
              <TabPane
                tab={appendAmountOnTitle('姓名', patientsMatchedName?.length)}
                key="2"
                disabled={!patientsMatchedName.length}
              >
                <PatientSearchPatientList
                  patients={patientsMatchedName}
                  changeDrawerVisible={changeDrawerVisible}
                  keyword={searchedText}
                />
              </TabPane>
              <TabPane
                tab={appendAmountOnTitle('聯絡電話', patientsMatchedPhone?.length)}
                key="3"
                disabled={!patientsMatchedPhone.length}
              >
                <PatientSearchPatientList
                  patients={patientsMatchedPhone}
                  changeDrawerVisible={changeDrawerVisible}
                  keyword={searchedText}
                />
              </TabPane>
              <TabPane
                tab={appendAmountOnTitle('生日', patientsMatchedBirth?.length)}
                key="4"
                disabled={!patientsMatchedBirth.length}
              >
                <PatientSearchPatientList
                  patients={patientsMatchedBirth}
                  changeDrawerVisible={changeDrawerVisible}
                  keyword={searchedText}
                />
              </TabPane>
              <TabPane
                tab={appendAmountOnTitle('病歷編號', patientsMatchedMrn?.length)}
                key="5"
                disabled={!patientsMatchedMrn.length}
              >
                <PatientSearchPatientList
                  patients={patientsMatchedMrn}
                  changeDrawerVisible={changeDrawerVisible}
                  keyword={searchedText}
                />
              </TabPane>
              <TabPane
                tab={appendAmountOnTitle('身分證號', patientsMatchedNationalId?.length)}
                key="6"
                disabled={!patientsMatchedNationalId.length}
              >
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
      <CloseContainer show={drawerOpen}>
        <div
          onClick={() => {
            changeDrawerVisible(false);
          }}
        >
          <img src={close} alt="close" />
          <span>關閉</span>
        </div>
      </CloseContainer>
    </Fragment>
  );
}

const mapStateToProps = ({ patientPageReducer }) => ({
  drawerOpen: patientPageReducer.searchPatient.drawerOpen,
  patients: patientPageReducer.searchPatient.total,
  patientsMatchedName: patientPageReducer.searchPatient.name,
  patientsMatchedPhone: patientPageReducer.searchPatient.phone,
  patientsMatchedBirth: patientPageReducer.searchPatient.birth,
  patientsMatchedMrn: patientPageReducer.searchPatient.mrn,
  patientsMatchedNationalId: patientPageReducer.searchPatient.nationalId,
  searchedText: patientPageReducer.searchPatient.searchedText,
});

const mapDispatchToProps = { changeDrawerVisible, searchPatient, getRegistrationToday };

export default connect(mapStateToProps, mapDispatchToProps)(PatientSearchDrawer);
