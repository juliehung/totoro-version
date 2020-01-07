import React from 'react';
import { connect } from 'react-redux';
import styled from 'styled-components';
import QuestionnairePage from '../QuestionnairePage';
import LoginPage from '../LoginPage';
import { Switch, Route } from 'react-router-dom';
import NavHome from '../NavHome';
import RegistrationPage from '../RegistrationPage';

//#region
const Container = styled.div`
  display: flex;
  flex-direction: column;
`;
//#endregion

function Home(props) {
  if (!props.loginSuccess)
    return (
      <Container>
        <LoginPage />
      </Container>
    );

  return (
    <Switch>
      <Route path="/q/:pid">
        <QuestionnairePage />
      </Route>
      <Route exact path="/registration">
        <RegistrationPage />
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

const mapDispatchToProps = {};

export default connect(mapStateToProps, mapDispatchToProps)(Home);
