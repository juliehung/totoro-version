import React, { useEffect, Fragment } from 'react';
import { connect } from 'react-redux';
import styled from 'styled-components';
import { getAccount, getUserStart, getSettings, changeXrayModalVisible } from './actions';
import { getConfig } from '../SettingPage/actions';
import QuestionnairePage from '../QuestionnairePage';
import LoginPage from '../LoginPage';
import { Switch, Route } from 'react-router-dom';
import NavHome from '../NavHome';
import Form from '../QuestionnairePage/Form';
import XrayModal from './XrayModal';
import NhiIndexPage from '../NhiIndexPage';

//#region
const Container = styled.div`
  display: flex;
  flex-direction: column;
`;
//#endregion

function Home(props) {
  const {
    loginSuccess,
    getAccount,
    getUserStart,
    getSettings,
    xrayModalVisible,
    changeXrayModalVisible,
    getConfig,
  } = props;

  useEffect(() => {
    if (loginSuccess) {
      getAccount();
      getSettings();
      getUserStart();
      getConfig();
    }
  }, [loginSuccess, getAccount, getSettings, getUserStart, getConfig]);

  if (!loginSuccess)
    return (
      <Container>
        <LoginPage />
      </Container>
    );

  return (
    <Fragment>
      <Switch>
        <Route path="/q/history/:id">
          <Form />
        </Route>
        <Route path="/q/:pid">
          <QuestionnairePage />
        </Route>
        <Route path="/nhi-index">
          <NhiIndexPage />
        </Route>
        <Route path="/">
          <NavHome />
        </Route>
      </Switch>
      <XrayModal changeVisible={changeXrayModalVisible} visible={xrayModalVisible} />
    </Fragment>
  );
}

const mapStateToProps = ({ loginPageReducer, homePageReducer }) => ({
  loginSuccess: loginPageReducer.login.loginSuccess,
  xrayModalVisible: homePageReducer.xray.modalVisible,
});

const mapDispatchToProps = { getAccount, getUserStart, getSettings, changeXrayModalVisible, getConfig };

export default connect(mapStateToProps, mapDispatchToProps)(Home);
