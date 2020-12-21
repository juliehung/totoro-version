import React, { useState, useEffect, Fragment } from 'react';
import { connect } from 'react-redux';
import styled from 'styled-components';
import { LeftOutlined, RightOutlined } from '@ant-design/icons';
import { Link } from 'react-router-dom';
import { Button, Calendar, Row, Col, Divider, message } from 'antd';
import {
  changeCalDate,
  changePrintModalVisible,
  changeCreateAppModalVisible,
  changeCreateCalModalVisible,
  changeCalSlotDuration,
  changeCalendarFullscreen,
  changeShiftOpen,
  changeSelectedDoctors,
} from './actions';
import moment from 'moment';
import 'moment/locale/zh-tw';
import GridIcon from '../../images/grid.svg';
import PointerIcon from '../../images/printer.svg';
import SettingsIcon from '../../images/settings.svg';
import GAHelper from '../../ga';
import { appointmentPage } from './';
import FloatingActionButton from './FloatingActionButton';
import { useCookies } from 'react-cookie';
import { handleResources } from './utils/handleResources';
import { convertShitToBackgroundEvent } from './utils/convertShitToBackgroundEvent';
import extractDoctorsFromUser from '../../utils/extractDoctorsFromUser';
import TimeDisplay from './TimeDisplay';

//#region
const Container = styled.div`
  width: 15%;
  min-width: 300px;
  padding: 6px;
  display: flex;
  flex-direction: column;
  background: #fff;
  z-index: 400;
  position: relative;
  box-shadow: 0 0 15px 0 rgba(0, 0, 0, 0.05);
  @media (max-width: 800px) {
    width: 100%;
    min-height: 90vh;
  }
`;

const CalendarContainer = styled.div`
  margin-top: 10px;
  border-radius: 6px;
  border: 1px solid #e4e9f2;
  padding: 3px 0px;
`;

const ItemContainer = styled.div`
  margin: 0 -6px;
  font-size: 15px;
  height: 64px;
  display: flex;
  align-items: center;
  justify-content: space-between;
  border-bottom: 1px solid #edf1f7;
  padding: 0 16px;
  font-weight: bold;
  & img {
    width: 20px;
  }

  & button {
    border: 0;
    padding: 0;
    font-size: 100%;
    outline: none;
    background: transparent;
    cursor: pointer;
    color: #3366ff;
    font-weight: bold;
  }

  & button:active {
    color: red;
  }

  & button:disabled {
    color: #8f9bb3;
    cursor: not-allowed;
  }

  & > :nth-child(1) {
    display: flex;
    & > div {
      display: flex;
      align-items: center;
      margin-right: 16px;
      position: relative;
    }
  }
  & > :nth-child(2) {
    font-size: 12px;
  }
`;

const StatusIncdicator = styled.div`
  width: 10px;
  height: 10px;
  position: absolute;
  right: 0;
  bottom: 0;
  background-color: ${props => (props.on ? '#00e096' : '#8f9bb3')};
  border-radius: 50%;
  border: 2px solid #ffffff;
`;

const DateTitleContainer = styled(Col)`
  display: flex;
  align-items: center;
  & > h4 {
    margin-bottom: 0;
  }
`;

const TodayContainer = styled.div`
  display: flex;
  align-items: center;
  justify-content: center;
  font-size: 13px;
  color: #8f9bb3;
  font-weight: bold;
  margin-top: 10px;
  & > div {
    font-size: 10px;
    padding: 3px 12px;
    border: solid 1px #8f9bb3;
    border-radius: 34px;
    margin-left: 7px;
    cursor: pointer;
    background-color: rgba(143, 155, 179, 0.08);
    transition: all 300ms ease-in-out;
    &:hover {
      background-color: rgba(143, 155, 179, 0.4);
    }
  }
  @media (max-width: 850px) {
    display: none;
  }
`;

//#endregion

function AppRight(props) {
  const {
    calendarDate,
    changeCalDate,
    changeCalSlotDuration,
    changeCalendarFullscreen,
    shiftOpen,
    changeShiftOpen,
    changeSelectedDoctors,
    backgroundEvent,
    doctors,
  } = props;

  const [expand, setExpand] = useState(false);
  const [loaded, setLoaded] = useState(false);
  const [cookies, setCookie] = useCookies([]);

  useEffect(() => {
    const slotDuration = cookies.slotDuration;
    const calendarFullScreen = cookies.calendarFullScreen;
    const shiftOpen = cookies.shiftOpen;

    if (!isNaN(Number(slotDuration))) {
      changeCalSlotDuration(Number(slotDuration));
    }

    if (calendarFullScreen) {
      changeCalendarFullscreen(calendarFullScreen === 'true');
    }

    if (shiftOpen) {
      changeShiftOpen(shiftOpen === 'true');
    }
  }, [cookies, changeCalSlotDuration, changeCalendarFullscreen, changeShiftOpen]);

  const simulateMouseClick = element => {
    const mouseClickEvents = ['mousedown', 'click', 'mouseup'];
    mouseClickEvents.forEach(mouseEventType =>
      element.dispatchEvent(
        new MouseEvent(mouseEventType, {
          view: window,
          bubbles: true,
          cancelable: true,
          buttons: 1,
        }),
      ),
    );
  };

  const onSlotDurationChange = value => {
    const title = document.querySelector('.fc-center');
    if (title) {
      simulateMouseClick(title);
      changeCalSlotDuration(value);
      changeCalendarFullscreen(false);
      setCookie('slotDuration', value);
      setCookie('calendarFullScreen', false);
    }
  };

  const onFullHeightClick = () => {
    const title = document.querySelector('.fc-center');
    if (title) {
      simulateMouseClick(title);
      changeCalendarFullscreen(true);
      changeCalSlotDuration(30);
      setCookie('slotDuration', 30);
      setCookie('calendarFullScreen', true);
    }
  };

  const headerRender = ({ value }) => {
    const month = value.locale('zh-tw').format('MMMM');
    const year = value.year() - 1911;
    return (
      <div>
        <Row type="flex" justify="space-between">
          <Col>
            <Button
              type="link"
              onClick={() => {
                changeCalDate(moment(calendarDate).add(-1, 'M'));
              }}
            >
              <LeftOutlined />
            </Button>
          </Col>
          <DateTitleContainer>
            <h4>
              {year}年 {month}
            </h4>
          </DateTitleContainer>
          <Col>
            <Button
              type="link"
              onClick={() => {
                changeCalDate(moment(calendarDate).add(1, 'M'));
              }}
            >
              <RightOutlined />
            </Button>
          </Col>
        </Row>
      </div>
    );
  };

  const onShiftButtonClick = () => {
    setCookie('shiftOpen', !shiftOpen);
    changeShiftOpen(!shiftOpen);
    if (!shiftOpen) {
      const selectedDoctors = handleResources(doctors, backgroundEvent);
      changeSelectedDoctors(selectedDoctors.map(d => d.id));
      message.success('班表啟用成功!');
    } else {
      message.warning('班表已關閉。');
    }
  };

  const toggleExpand = () => {
    setLoaded(true);
    setExpand(!expand);
    GAHelper.event(appointmentPage, 'Click add event button');
  };

  const closeExpand = () => {
    setLoaded(true);
    setExpand(false);
  };

  const fabMoonClick = () => {
    GAHelper.event(appointmentPage, 'Click show create calendar event modal button');
    props.changeCreateCalModalVisible(true);
    toggleExpand();
  };

  const fabCalClick = () => {
    GAHelper.event(appointmentPage, 'Click show create appt modal button');
    props.changeCreateAppModalVisible(true);
    toggleExpand();
  };

  return (
    <Container>
      <TodayContainer>
        <TimeDisplay />
        <div onClick={() => changeCalDate(moment(new Date()))}>
          <span>今日</span>
        </div>
      </TodayContainer>
      <CalendarContainer>
        <Calendar
          fullscreen={false}
          headerRender={headerRender}
          onSelect={props.changeCalDate}
          value={props.calendarDate}
          locale={{ lang: { locale: 'zh-tw' } }}
        />
      </CalendarContainer>
      <ItemContainer>
        <div>
          <div>
            <img src={PointerIcon} alt="列印" />
          </div>
          <span>列印</span>
        </div>
        <div>
          <button
            onClick={() => {
              props.changePrintModalVisible();
              GAHelper.event(appointmentPage, 'Print appt list');
            }}
          >
            預約表
          </button>
        </div>
      </ItemContainer>
      <ItemContainer>
        <div>
          <div>
            <img src={GridIcon} alt="密度" />
          </div>
          <span>密度</span>
        </div>
        <div>
          <button
            onClick={() => {
              onFullHeightClick();
              GAHelper.event(appointmentPage, 'slot full screen clicked');
            }}
          >
            天
          </button>
          {[15, 10].map(n => (
            <Fragment key={n}>
              <Divider type="vertical" />
              <button
                onClick={() => {
                  onSlotDurationChange(n);
                  GAHelper.event(appointmentPage, `slot duration ${n} clicked`);
                }}
              >
                {`${n} 分`}
              </button>
            </Fragment>
          ))}
        </div>
      </ItemContainer>
      <ItemContainer>
        <div>
          <div>
            <img src={SettingsIcon} alt="排班" />
            <StatusIncdicator on={shiftOpen ? 1 : 0} />
          </div>
          <span>排班</span>
        </div>
        <div>
          <button
            onClick={() => {
              onShiftButtonClick();
              GAHelper.event(appointmentPage, 'Turn on/off shift');
            }}
          >
            {shiftOpen ? '停用' : '啟用'}
          </button>
          <Divider type="vertical" />
          <Link to="/shift">
            <button>編輯</button>
          </Link>
        </div>
      </ItemContainer>
      <FloatingActionButton
        expand={expand}
        toggleExpand={toggleExpand}
        closeExpand={closeExpand}
        loaded={loaded}
        moonClick={fabMoonClick}
        calClick={fabCalClick}
      />
    </Container>
  );
}
const mapStateToProps = ({ appointmentPageReducer, homePageReducer }) => ({
  calendarDate: appointmentPageReducer.calendar.calendarDate,
  slotDuration: appointmentPageReducer.calendar.slotDuration,
  shiftOpen: appointmentPageReducer.shift.shiftOpen,
  backgroundEvent: convertShitToBackgroundEvent(appointmentPageReducer.shift.shift),
  doctors: extractDoctorsFromUser(homePageReducer.user.users),
});

const mapDispatchToProps = {
  changeCalDate,
  changePrintModalVisible,
  changeCreateAppModalVisible,
  changeCreateCalModalVisible,
  changeCalSlotDuration,
  changeCalendarFullscreen,
  changeShiftOpen,
  changeSelectedDoctors,
};

export default connect(mapStateToProps, mapDispatchToProps)(AppRight);
