import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import styled from 'styled-components';
import AppointmentPage from '../AppointmentPage';
import RegistrationPage from '../RegistrationPage';
import { Switch, Route, Link, useLocation } from 'react-router-dom';
import { useCookies } from 'react-cookie';
import ShiftPage from '../ShiftPage';
import SettingPage from '../SettingPage';
import SmsPage from '../SmsPage';
import BookOpen from './svg/BookOpen';
import CalendarFill from './svg/CalendarFill';
import Message from './svg/Message';
import DentallHisLogo from '../../images/DentallHisLogo.svg';
import { Menu, Dropdown, Drawer } from 'antd';
import { parseAccountData } from './utils/parseAccountData';
import IconBookOpen from '../../images/icon-book-open.svg';
import IconBookOpenFill from '../../images/icon-book-open-fill.svg';
import IconCalendar from '../../images/icon-calendar.svg';
import IconCalendarFill from '../../images/icon-calendar-fill.svg';
import MessageCircle from '../../images/message-circle.svg';
import MessageCircleFill from '../../images/message-circle-fill.svg';

//#region
const Container = styled.div`
  height: 100vh;
  display: flex;
  flex-direction: column;
  background-color: #f8fafb;
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

  @media (max-width: 960px) {
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
    a {
      text-decoration: none;
      & > div {
        display: flex;
        align-items: center;
        padding: 6px 12px;
        & > :first-child {
          margin-right: 6px;
          height: 16px;
        }
      }
      & .svg {
        fill: ${props => (props.focus ? '#fff' : '#333')};
        color: ${props => (props.focus ? '#fff' : '#333')};
      }
    }
  }
`;

const UserContainer = styled.div`
  display: flex;
  align-items: center;
  & > :first-child {
    display: flex;
    flex-direction: column;
    align-items: flex-end;
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

function NavHome(props) {
  const { account } = props;

  const [, , removeCookie] = useCookies(['token']);

  const logout = () => {
    removeCookie('token', { path: '/' });
    window.location.reload();
  };

  const [currentLocation, setCurrentLocation] = useState(undefined);
  const [drawerVisible, setDrawerVisible] = useState(false);

  const menuClick = ({ key }) => {
    if (key === 'logout') {
      logout();
    }
  };

  let location = useLocation();
  useEffect(() => {
    const route = location.pathname.split('/')[1];
    if (route !== currentLocation) {
      setCurrentLocation(route);
    }
  }, [location, currentLocation]);

  return (
    <Container>
      <Banner />
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
            <NavItem focus={currentLocation === 'registration'}>
              <Link to="/registration">
                <div>
                  {currentLocation === 'registration' ? (
                    <img src={IconBookOpenFill} alt="bookIcon" />
                  ) : (
                    <img src={IconBookOpen} alt="bookIcon" />
                  )}
                  <span className="svg">就診列表</span>
                </div>
              </Link>
            </NavItem>
            <NavItem focus={currentLocation === 'sms'}>
              <Link to="/sms">
                <div>
                  {currentLocation === 'sms' ? (
                    <img src={MessageCircleFill} alt="smsIcon" />
                  ) : (
                    <img src={MessageCircle} alt="smsIcon" />
                  )}
                  <span className="svg">SMS</span>
                </div>
              </Link>
            </NavItem>
            <NavItem focus={currentLocation === ''}>
              <Link to="/">
                <div>
                  {currentLocation === '' ? (
                    <img src={IconCalendarFill} alt="calendarIcon" />
                  ) : (
                    <img src={IconCalendar} alt="calendarIcon" />
                  )}
                  <span className="svg">約診排程</span>
                </div>
              </Link>
            </NavItem>
          </ul>
        </div>
        <Dropdown
          trigger="click"
          overlay={
            <Menu onClick={menuClick}>
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
        <div to="/" style={{ marginBottom: '30px' }}>
          <img src={DentallHisLogo} alt="dentallHis" />
        </div>
        <DrawerItem to="/registration">
          <div>
            <BookOpen />
            <span>就診列表</span>
          </div>
        </DrawerItem>
        <DrawerItem to="/sms">
          <div>
            <Message />
            <span>SMS</span>
          </div>
        </DrawerItem>
        <DrawerItem to="/">
          <div>
            <CalendarFill />
            <span>約診排程</span>
          </div>
        </DrawerItem>
      </Drawer>
      <ContentContainer>
        <Switch>
          <Route exact path="/shift">
            <ShiftPage />
          </Route>
          <Route exact path="/">
            <AppointmentPage />
          </Route>
          <Route exact path="/registration">
            <RegistrationPage />
          </Route>
          <Route path="/setting">
            <SettingPage />
          </Route>
          <Route path="/sms">
            <SmsPage />
          </Route>
          <Route path="*">
            <AppointmentPage />
          </Route>
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
