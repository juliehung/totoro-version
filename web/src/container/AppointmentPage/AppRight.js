import React, { useState, useEffect } from 'react';
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
} from './actions';
import moment from 'moment';
import 'moment/locale/zh-tw';
import GridIcon from '../../images/grid.svg';
import PointerIcon from '../../images/printer.svg';
import SettingsIcon from '../../images/settings.svg';
import { putSettings } from '../Home/actions';
import { GAevent } from '../../ga';
import { appointmentPage } from './';
import FloatingActionButton from './FloatingActionButton';
import { useIsFirstRender } from '../../utils/hooks/useIsFirstRender';
import { useCookies } from 'react-cookie';

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
  margin-top: 20px;
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

//#endregion

function AppRight(props) {
  const {
    calendarDate,
    changeCalDate,
    settings,
    showShiftCalc,
    putSettings,
    putSettingSuccess,
    changeCalSlotDuration,
    changeCalendarFullscreen,
  } = props;

  const [expand, setExpand] = useState(false);
  const [loaded, setLoaded] = useState(false);
  const [cookies, setCookie] = useCookies(['token']);

  useEffect(() => {
    const slotDuration = cookies.slotDuration;
    const calendarFullScreen = cookies.calendarFullScreen;
    if (slotDuration) {
      changeCalSlotDuration(slotDuration);
    }
    if (calendarFullScreen) {
      changeCalendarFullscreen(calendarFullScreen);
    }
  }, [cookies, changeCalSlotDuration, changeCalendarFullscreen]);

  const isFirstRender = useIsFirstRender();
  useEffect(() => {
    if (isFirstRender) {
      return;
    }
    if (putSettingSuccess && showShiftCalc) {
      message.success('班表啟用成功!');
    } else if (putSettingSuccess && !showShiftCalc) {
      message.warning('班表已關閉。');
    }
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, [putSettingSuccess, showShiftCalc]);

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
      changeCalendarFullscreen(false);
      changeCalSlotDuration(value);
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
    const year = value.year();
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
              {month} {year}
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
    if (settings?.id) {
      const showShift = !showShiftCalc;
      const generalSetting = {
        ...{ ...(settings.preferences ? settings.preferences.generalSetting : {}) },
        showShift,
      };
      const preferences = { ...settings.preferences, generalSetting };
      const newSettings = { ...settings, preferences };
      putSettings(newSettings);
    }
  };

  const toggleExpand = () => {
    setLoaded(true);
    setExpand(!expand);
    GAevent(appointmentPage, 'Click add event button');
  };

  const closeExpand = () => {
    setLoaded(true);
    setExpand(false);
  };

  const fabMoonClick = () => {
    GAevent(appointmentPage, 'Click show create calendar event modal button');
    props.changeCreateCalModalVisible(true);
    toggleExpand();
  };

  const fabCalClick = () => {
    GAevent(appointmentPage, 'Click show create appt modal button');
    props.changeCreateAppModalVisible(true);
    toggleExpand();
  };

  return (
    <Container>
      <CalendarContainer>
        <Calendar
          fullscreen={false}
          headerRender={headerRender}
          onSelect={props.changeCalDate}
          value={props.calendarDate}
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
              GAevent(appointmentPage, 'Print appt list');
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
              GAevent(appointmentPage, 'slot full screen clicked');
            }}
          >
            天
          </button>
          <Divider type="vertical" />
          <button
            onClick={() => {
              onSlotDurationChange(30);
              GAevent(appointmentPage, 'slot duration 30 clicked');
            }}
          >
            30
          </button>
          <Divider type="vertical" />
          <button
            onClick={() => {
              onSlotDurationChange(15);
              GAevent(appointmentPage, 'slot duration 15 clicked');
            }}
          >
            15
          </button>
          <Divider type="vertical" />
          <button
            onClick={() => {
              onSlotDurationChange(10);
              GAevent(appointmentPage, 'slot duration 10 clicked');
            }}
          >
            10
          </button>
        </div>
      </ItemContainer>
      <ItemContainer>
        <div>
          <div>
            <img src={SettingsIcon} alt="排班" />
            <StatusIncdicator on={showShiftCalc ? 1 : 0} />
          </div>
          <span>排班</span>
        </div>
        <div>
          <button
            onClick={() => {
              onShiftButtonClick();
              GAevent(appointmentPage, 'Turn on/off shift');
            }}
          >
            {showShiftCalc ? '停用' : '啟用'}
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
  settings: homePageReducer.settings.settings,
  showShiftCalc: homePageReducer.settings.settings?.preferences?.generalSetting?.showShift,
  putSettingSuccess: homePageReducer.settings.putSuccess,
});

const mapDispatchToProps = {
  changeCalDate,
  changePrintModalVisible,
  changeCreateAppModalVisible,
  changeCreateCalModalVisible,
  changeCalSlotDuration,
  changeCalendarFullscreen,
  putSettings,
};

export default connect(mapStateToProps, mapDispatchToProps)(AppRight);
