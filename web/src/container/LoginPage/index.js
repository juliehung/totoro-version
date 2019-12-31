import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import styled from 'styled-components';
import LoginForm from './LoginForm';
import { useCookies } from 'react-cookie';
import { changeLoginFail, changeLoginSuccess, checkTokenValidate, checkTokenValidateFail } from './actions';
import DentallLogo from '../../static/images/logo_dentall_icon.svg';
import SVG from 'react-inlinesvg';
import { message, Spin } from 'antd';
import { parseUWPBase64Token } from './utils/parseUWPBase64Token';
import { Helmet } from 'react-helmet-async';

//#region
const Container = styled.div`
  display: flex;
  flex-wrap: wrap;
  height: 100vh;
`;
const LoginFormContainer = styled.div`
  display: flex;
  margin: auto;
  align-items: center;
  flex-direction: column;
`;
const FullHeightSVG = styled(SVG)`
  height: 100%;
  margin-bottom: 20px;
`;

const SpinContainer = styled.div`
  width: 100%;
  display: flex;
  justify-content: center;
  align-items: center;
`;
//#endregion

function LoginPage({
  id_token,
  loginFail,
  tokenValidateChecked,
  changeLoginFail,
  changeLoginSuccess,
  checkTokenValidate,
  checkTokenValidateFail,
}) {
  const [cookies, setCookie] = useCookies(['token']);

  useEffect(() => {
    const search = window.location.search;
    const params = new URLSearchParams(search);
    const tokenFromUrl = params.get('secret');
    if (tokenFromUrl) {
      const token = parseUWPBase64Token(tokenFromUrl);
      setCookie('token', token, { path: '/' });
      const url = window.location.origin + window.location.pathname;
      window.location.replace(url);
    } else {
      if (cookies.token) {
        checkTokenValidate();
      } else {
        checkTokenValidateFail();
      }
    }
  }, [checkTokenValidate, checkTokenValidateFail, cookies.token, setCookie]);

  useEffect(() => {
    if (id_token) {
      setCookie('token', id_token, { path: '/' });
      changeLoginSuccess(true);
    }
  }, [id_token, changeLoginSuccess, setCookie]);

  useEffect(() => {
    if (loginFail) {
      message.error('登入失敗');
      changeLoginFail(false);
    }
  }, [loginFail, changeLoginFail]);

  return (
    <Container>
      <Helmet>
        <title>登入</title>
      </Helmet>
      {!tokenValidateChecked ? (
        <SpinContainer>
          <Spin />
        </SpinContainer>
      ) : (
        <LoginFormContainer>
          <FullHeightSVG src={DentallLogo} />
          <LoginForm />
        </LoginFormContainer>
      )}
    </Container>
  );
}

const mapStateToProps = ({ loginPageReducer }) => ({
  id_token: loginPageReducer.login.id_token,
  loginFail: loginPageReducer.login.loginFail,
  loginSuccess: loginPageReducer.login.loginSuccess,
  tokenValidateChecked: loginPageReducer.login.tokenValidateChecked,
});

const mapDispatchToProps = { changeLoginFail, changeLoginSuccess, checkTokenValidate, checkTokenValidateFail };

export default connect(mapStateToProps, mapDispatchToProps)(LoginPage);
