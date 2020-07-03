import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import styled, { createGlobalStyle } from 'styled-components';
import AppointmentPage from '../AppointmentPage';
import RegistrationPage from '../RegistrationPage';
import { Switch, Route, Link, Redirect, useLocation } from 'react-router-dom';
import { useCookies } from 'react-cookie';
import ShiftPage from '../ShiftPage';
import SettingPage from '../SettingPage';
import SmsPage from '../SmsPage';
import DentallHisLogo from '../../images/DentallHisLogo.svg';
import { Menu, Dropdown, Drawer, Popover } from 'antd';
import { parseAccountData } from './utils/parseAccountData';
import { determineRouteOrLinkShow } from './utils/determineRouteShow';
import IconBookOpen from '../../images/icon-book-open.svg';
import IconBookOpenFill from '../../images/icon-book-open-fill.svg';
import IconCalendar from '../../images/icon-calendar.svg';
import IconCalendarFill from '../../images/icon-calendar-fill.svg';
import MessageCircle from '../../images/message-circle.svg';
import MessageCircleFill from '../../images/message-circle-fill.svg';
import Pantone from '../../images/pantone.svg';
import Cube from '../../images/cube.svg';
import FileText from '../../images/file-text.svg';

//#region
const Container = styled.div`
  height: 100vh;
  display: flex;
  flex-direction: column;
  background-color: #f8fafb;
  user-select: none;
`;

export const GlobalStyle = createGlobalStyle`
  .linkPopover {
    .ant-popover-content {
      box-shadow: 0 4px 25px 0 rgba(0, 0, 0, 0.1);
    }

    .ant-popover-inner {
      width: 124px;
    }

    .ant-popover-arrow {
      display: none !important;
    }
  }

  .ant-dropdown-menu {
    border-radius: 10px !important;
  }

`;

const Banner = styled.div`
  position: fixed;
  width: 100%;
  height: 4px;
  background-color: #3266ff;
`;

const NavContainer = styled.nav`
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 10px 30px;
  box-shadow: 0 4px 20px 0 rgba(0, 0, 0, 0.05);
  z-index: 100;
  margin: 15px 1%;
  border-radius: 8px;
  background: #fff;
  & > :nth-child(1) {
    font-size: 1.5rem;
    display: none;
  }

  & > div:nth-child(3) {
    ul {
      display: flex;
      margin: 0;
      padding: 0;
    }
  }

  @media (max-width: 1100px) {
    & > :nth-child(1) {
      display: block;
    }

    padding: 10px 1%;
    & > div:nth-child(3) {
      display: none;
    }
  }
`;

const NavItem = styled.li`
  box-shadow: ${props => (props.focus ? '0 2px 10px 0 rgba(50, 102, 255, 0.5)' : 'none')};
  background-image: ${props => (props.focus ? 'linear-gradient(288deg, #0a54c5, #3266ff)' : 'none')};
  color: ${props => (props.focus ? '#fff' : '#333')};
  list-style-type: none;
  margin: 0 2vw;
  font-size: 12px;
  font-weight: bold;
  border-radius: 34px;
  & {
    a,
    span {
      text-decoration: none;
      & > div {
        display: flex;
        align-items: center;
        padding: 6px 12px;
        & > div {
          margin-right: 6px;
          height: 16px;
          width: 16px;
          & > img {
            height: 16px;
          }
          & > :first-child {
            display: ${props => (props.focus ? 'block' : 'none')};
          }

          & .oneModeimg {
            display: block;
          }

          & > :nth-child(2) {
            display: ${props => (props.focus ? 'none' : 'block')};
          }
        }
      }
      & .svg {
        fill: ${props => (props.focus ? '#fff' : '#333')};
        color: ${props => (props.focus ? '#fff' : '#333')};
      }
    }
  }
`;

const LinkNavItem = styled(NavItem)`
  margin: 16px 0 0 0;
  &:first-child {
    margin: 0;
  }
`;

const UserContainer = styled.div`
  display: flex;
  justify-content: flex-end;
  align-items: center;
  & > :first-child {
    display: flex;
    flex-direction: column;
    align-items: flex-end;
    min-width: 100px;
    & > :nth-child(2) {
      font-size: 12px;
      color: rgba(0, 0, 0, 0.45);
    }
  }
  & > :nth-child(2) {
    width: 40px;
    height: 40px;
    object-fit: cover;
    background: #eee;
    border-radius: 50%;
    margin-left: 20px;
    overflow: hidden;
    display: flex;
    justify-content: center;
    align-items: center;
    box-shadow: 0 4px 8px 0 rgba(0, 0, 0, 0.12);
  }
`;

const DrawerItem = styled(Link)`
  text-decoration: none;
  font-size: 14px;
  color: #222b45;
  font-weight: bold;
  padding: 15px;
  margin: 15px 0;
  & > div {
    display: flex;
    align-items: center;
    & > :first-child {
      margin-right: 10px;
    }
  }
`;

const LaboDrawerItem = styled.a`
  text-decoration: none;
  font-size: 14px;
  color: #222b45;
  font-weight: bold;
  padding: 15px;
  margin: 15px 0;
  & > div {
    display: flex;
    align-items: center;
    & > :first-child {
      margin-right: 10px;
    }
  }
`;

const ContentContainer = styled.div`
  height: 100%;
  margin: 0 1% 15px;
  border-radius: 8px;
  background-color: #fff;
  box-shadow: 0 4px 25px 0 rgba(0, 0, 0, 0.1);
  overflow-y: scroll;
  scrollbar-width: none;
  &::-webkit-scrollbar {
    display: none;
  }
`;

//#endregion

const route = [
  {
    key: 'registration',
    name: '就診列表',
    icon: { on: IconBookOpenFill, off: IconBookOpen },
    navigation: true,
    exact: true,
    component: <RegistrationPage />,
    localVersion: true,
  },
  {
    key: 'appointment',
    name: '約診排程',
    icon: { on: IconCalendarFill, off: IconCalendar },
    navigation: true,
    exact: true,
    component: <AppointmentPage />,
    localVersion: true,
  },
  {
    key: 'sms',
    name: 'SMS',
    icon: { on: MessageCircleFill, off: MessageCircle },
    navigation: true,
    exact: true,
    component: <SmsPage />,
    localVersion: false,
  },
  { key: 'shift', navigation: false, exact: true, component: <ShiftPage />, localVersion: true },
  { key: 'setting', navigation: false, exact: true, component: <SettingPage />, localVersion: true },
  { key: '', navigation: false, exact: false, component: <Redirect to="/appointment" />, localVersion: true },
];

const navLink = [
  {
    key: 'labo',
    name: '技工',
    href: 'https://docs.google.com/spreadsheets/d/1LiCYNyCO2nx91g1VIke_DlZP8gqt3qEHTrKg1z7JT7k/edit?usp=sharing',
    icon: Pantone,
    clinic: ['pin-cui', 'rakumi'],
  },
  {
    key: 'material',
    name: '牙材',
    href: 'https://docs.google.com/spreadsheets/d/1TpdSnYV0LOCirS2i9u4-QZToMyiEIMyxOWoOkeU0x3k/edit?usp=sharing',
    icon: Cube,
    clinic: ['pin-cui', 'rakumi'],
  },
];

function NavHome(props) {
  const { account } = props;

  const [, , removeCookie] = useCookies(['token']);

  const logout = () => {
    removeCookie('token', { path: '/' });
    window.location = window.location.pathname;
  };

  const [currentLocation, setCurrentLocation] = useState(undefined);
  const [drawerVisible, setDrawerVisible] = useState(false);

  const menuClick = ({ key }) => {
    if (key === 'logout') {
      logout();
    }
  };

  const location = useLocation();
  useEffect(() => {
    const route = location.pathname.split('/')[1];
    if (route !== currentLocation) {
      setCurrentLocation('/' + route);
    }
  }, [location, currentLocation]);

  return (
    <Container>
      <Banner />
      <GlobalStyle />
      <NavContainer>
        <span
          onClick={() => {
            setDrawerVisible(true);
          }}
        >
          ☰
        </span>
        <Link to="/">
          <img src={DentallHisLogo} alt="dentallHis" />
        </Link>
        <div>
          <ul>
            {[
              ...route
                .filter(r => r.navigation && determineRouteOrLinkShow(r))
                .map(n => (
                  <NavItem key={n.key} focus={currentLocation === `/${n.key}`}>
                    <Link to={`/${n.key}`}>
                      <div>
                        <div>
                          <img src={n.icon.on} alt="bookIcon" />
                          <img src={n.icon.off} alt="bookIcon" />
                        </div>
                        <span className="svg">{n.name}</span>
                      </div>
                    </Link>
                  </NavItem>
                )),
              <Popover
                overlayClassName="linkPopover"
                placement="bottomLeft"
                key="web"
                content={
                  <div style={{ display: 'flex', flexDirection: 'column' }}>
                    {navLink
                      .filter(n => determineRouteOrLinkShow(n))
                      .map(n => (
                        <LinkNavItem key={n.key}>
                          <a href={n.href} target="_blank" rel="noopener noreferrer">
                            <div>
                              <div>
                                <img src={n.icon} alt="bookIcon" />
                                <img src={n.icon} alt="bookIcon" />
                              </div>
                              <span className="svg">{n.name}</span>
                            </div>
                          </a>
                        </LinkNavItem>
                      ))}
                  </div>
                }
              >
                <NavItem key={'web'}>
                  <span>
                    <div>
                      <div>
                        <img className="oneModeimg" src={FileText} alt="管理表" />
                      </div>
                      <span className="svg">管理表</span>
                    </div>
                  </span>
                </NavItem>
              </Popover>,
            ]}
          </ul>
        </div>
        <Dropdown
          style={{ borderRadius: '10px' }}
          trigger="click"
          overlay={
            <Menu onClick={menuClick}>
              <Menu.Item key="settings">
                <Link to="/setting">設定</Link>
              </Menu.Item>
              <Menu.Item key="logout">登出</Menu.Item>
            </Menu>
          }
          placement="bottomCenter"
        >
          <UserContainer>
            <div>
              <span>{account.name}</span>
              <span>{account.role}</span>
            </div>
            {account.avatar ? (
              <img alt="avatar" src={`data:image/png;base64,${account.avatar}`} />
            ) : (
              <div>{account.name[0]}</div>
            )}
          </UserContainer>
        </Dropdown>
      </NavContainer>
      <Drawer
        visible={drawerVisible}
        closable
        placement="left"
        onClose={() => {
          setDrawerVisible(false);
        }}
        onClick={() => {
          setDrawerVisible(false);
        }}
      >
        <div style={{ marginBottom: '30px' }}>
          <img src={DentallHisLogo} alt="dentallHis" />
        </div>
        {[
          ...route
            .filter(r => r.navigation && determineRouteOrLinkShow(r))
            .map(n => (
              <DrawerItem key={n.key} to={`/${n.key}`}>
                <div>
                  <img src={n.icon.off} height="16px" alt="icon" />
                  <span>{n.name}</span>
                </div>
              </DrawerItem>
            )),
          ...navLink
            .filter(n => determineRouteOrLinkShow(n))
            .map(n => (
              <LaboDrawerItem key={n.key} href={n.href} target="_blank" rel="noopener noreferrer">
                <div>
                  <img src={n.icon} alt={n.name} />
                  <span>{n.name}</span>
                </div>
              </LaboDrawerItem>
            )),
        ]}
      </Drawer>
      <ContentContainer>
        <Switch>
          {route
            .filter(r => determineRouteOrLinkShow(r))
            .map(r => (
              <Route key={r.key} exact={r.exact} path={`/${r.key}`}>
                {r.component}
              </Route>
            ))}
        </Switch>
      </ContentContainer>
    </Container>
  );
}

const mapStateToProps = ({ homePageReducer }) => ({
  account: parseAccountData(homePageReducer.account.data),
});

const mapDispatchToProps = {};

export default connect(mapStateToProps, mapDispatchToProps)(NavHome);
