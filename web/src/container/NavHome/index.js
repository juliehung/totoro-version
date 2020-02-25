import React, { useState } from 'react';
import { connect } from 'react-redux';
import AppointmentPage from '../AppointmentPage';
import { Switch, Route, Link } from 'react-router-dom';
import { Layout, Menu, Icon } from 'antd';
import { useCookies } from 'react-cookie';

const { Content, Sider } = Layout;

function NavHome() {
  const [collapsed, setCollapsed] = useState(true);
  const [, , removeCookie] = useCookies(['token']);

  const logout = () => {
    removeCookie('token', { path: '/' });
    window.location.reload();
  };

  return (
    <Layout style={{ minHeight: '100vh' }}>
      <Sider
        collapsedWidth={0}
        zeroWidthTriggerStyle={{ zIndex: 5, top: '15px' }}
        width={150}
        collapsible
        collapsed={collapsed}
        onCollapse={c => setCollapsed(c)}
      >
        <div className="logo" />
        <Menu theme="dark" defaultSelectedKeys={['2']}>
          {process.env.NODE_ENV !== 'production' && (
            <Menu.Item key="1">
              <Icon type="unordered-list" />
              <span>掛號</span>
              <Link to="/registration" />
            </Menu.Item>
          )}
          <Menu.Item key="2">
            <Icon type="calendar" />
            <span>預約</span>
            <Link to="/" />
          </Menu.Item>
          <Menu.Item key="9" onClick={logout}>
            <Icon type="logout" />
            <span>登出</span>
          </Menu.Item>
        </Menu>
      </Sider>
      <Layout style={{ backgroundColor: 'white' }}>
        <Content style={{ backgroundColor: 'white' }}>
          <Switch>
            <Route exact path="/">
              <AppointmentPage />
            </Route>
            <Route path="*">
              <AppointmentPage />
            </Route>
          </Switch>
        </Content>
      </Layout>
    </Layout>
  );
}

const mapStateToProps = ({ loginPageReducer }) => ({ loginSuccess: loginPageReducer.login.loginSuccess });

const mapDispatchToProps = {};

export default connect(mapStateToProps, mapDispatchToProps)(NavHome);
