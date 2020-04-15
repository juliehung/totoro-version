import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import styled from 'styled-components';
import { getAccount, getUserStart } from './actions';
import QuestionnairePage from '../QuestionnairePage';
import LoginPage from '../LoginPage';
import { Switch, Route } from 'react-router-dom';
import NavHome from '../NavHome';
import Form from '../QuestionnairePage/Form';

//#region
const Container = styled.div`
  display: flex;
  flex-direction: column;
`;
//#endregion

function Home(props) {
  const { loginSuccess, getAccount, getUserStart } = props;

  useEffect(() => {
    if (loginSuccess) {
      getUserStart();
    }
  }, [getUserStart, loginSuccess]);

  useEffect(() => {
    if (loginSuccess) {
      getAccount();
    }
  }, [loginSuccess, getAccount]);

  if (!loginSuccess)
    return (
      <Container>
        <LoginPage />
      </Container>
    );

  return (
    <Switch>
      <Route path="/q/history/:id">
        <Form />
      </Route>
      <Route path="/q/:pid">
        <QuestionnairePage />
      </Route>
      <Route path="/">
        <NavHome />
      </Route>
    </Switch>
  );
}

const mapStateToProps = ({ loginPageReducer }) => ({
  loginSuccess: loginPageReducer.login.loginSuccess,
});

const mapDispatchToProps = { getAccount, getUserStart };

export default connect(mapStateToProps, mapDispatchToProps)(Home);
