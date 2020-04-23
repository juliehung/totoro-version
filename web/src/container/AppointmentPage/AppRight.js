import React, { useState } from 'react';
import { connect } from 'react-redux';
import styled, { keyframes } from 'styled-components';
import { LeftOutlined, RightOutlined } from '@ant-design/icons';
import { Link } from 'react-router-dom';
import { Button, Calendar, Row, Col, Select, Divider } from 'antd';
import {
  changeCalDate,
  changePrintModalVisible,
  changeSelectedDoctors,
  changeCreateAppModalVisible,
  changeCreateCalModalVisible,
  changeCalSlotDuration,
} from './actions';
import moment from 'moment';
import 'moment/locale/zh-tw';
import extractDoctorsFromUser from '../../utils/extractDoctorsFromUser';
import GridIcon from '../../images/grid.svg';
import PointerIcon from '../../images/printer.svg';
import SettingsIcon from '../../images/settings.svg';
import PlusIcon from '../../images/plus.svg';
import CalendarIcon from '../../images/calendar-fill.svg';
import MoonIcon from '../../images/moon-fill.svg';
import { putSettings } from '../Home/actions';
import { GAevent } from '../../ga';

//#region
const Container = styled.div`
  width: 15%;
  min-width: 300px;
  padding: 20px;
  display: flex;
  flex-direction: column;
  background: #fff;
  z-index: 400;
  position: relative;
  @media (max-width: 800px) {
    width: 100%;
    min-height: 90vh;
  }
`;

const StyledCalendar = styled(Calendar)`
  margin-top: 15px;
`;

const ItemContainer = styled.div`
  font-size: 15px;
  height: 60px;
  display: flex;
  align-items: baseline;
  justify-content: space-between;
  & img {
    width: 20px;
    margin-right: 10px;
  }

  & button {
    border: 0;
    padding: 0;
    font-size: 100%;
    outline: none;
    background: transparent;
    cursor: pointer;
    color: #3366ff;
  }

  & button:active {
    color: red;
  }

  & > :nth-child(2) {
    font-size: 10px;
  }
`;

const DateTitleContainer = styled(Col)`
  display: flex;
  align-items: center;
  & > h4 {
    margin-bottom: 0;
  }
`;

const SelectDoctorContainer = styled.div`
  display: flex;
  align-items: center;
  & > span {
    font-weight: bold;
    margin-right: 10px;
    white-space: nowrap;
  }
`;

const StyledSelect = styled(Select)`
  margin-top: 15px;
  width: 100%;
`;

const MenuContainer = styled.div`
  position: fixed;
  right: 20px;
  bottom: 20px;
  z-index: 100000;
`;

const RoundContainer = styled.div`
  border-radius: 50%;
  display: flex;
  justify-content: center;
  align-items: center;
  cursor: pointer;
  transition: all ease-in-out 300ms;
  & img {
    width: 28px;
  }
`;

const rotate = keyframes`
  0% { transform: rotate(0deg); }
  100% { transform: rotate(405deg); }
`;

const rotateReverse = keyframes`
  0% { transform: rotate(405deg); }
  100% { transform: rotate(0deg); }
`;

const MenuMainItemContainer = styled(RoundContainer)`
  width: 56px;
  height: 56px;
  background: #3266ff;
  border: 1px solid #3266ff;
  & img {
    width: 28px;
  }
  transform: ${props => (props.expand ? 'rotate(45deg)' : 'rotate(0)')};
  animation: ${props => (props.loaded ? (props.expand ? rotate : rotateReverse) : '')} 300ms;
`;

const MenuSubItemContainer = styled(RoundContainer)`
  width: 48px;
  height: 48px;
  background: #edf1f7;
  border: 1px solid #edf1f7;
  margin-left: auto;
  margin-right: auto;
  & img {
    width: 24px;
  }
  position: absolute;
  z-index: -1;
  left: 0;
  right: 0;
  bottom: ${props => (props.expand ? props.distance : 0)}px;
  box-shadow: 0 8px 16px -4px rgba(50, 102, 255, 0.2);
`;

//#endregion

function AppRight(props) {
  const { calendarDate, changeCalDate, selectedDoctors, settings, showShiftCalc, putSettings } = props;

  const [expand, setExpand] = useState(false);
  const [loaded, setLoaded] = useState(false);

  const moonClick = () => {
    props.changeCreateCalModalVisible(true);
  };

  const handleDoctorChange = d => {
    if (props.selectedDoctors.includes('dayOff') !== d.includes('dayOff')) {
      props.changeSelectedDoctors(d);
    } else {
      if (props.selectedDoctors.includes('all')) {
        props.changeSelectedDoctors(d.filter(d => d !== 'all'));
      } else {
        if (d.includes('all')) {
          props.changeSelectedDoctors(d.filter(d => ['all', 'dayOff'].includes(d)));
        } else {
          props.changeSelectedDoctors(d);
        }
      }
    }
  };

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
    const max = 30;
    const min = 10;
    const step = 5;
    const title = document.querySelector('.fc-center');
    if (title) {
      simulateMouseClick(title);
      if (value) {
        if (props.slotDuration + step > max) {
          props.changeCalSlotDuration(max);
        } else {
          props.changeCalSlotDuration(props.slotDuration + step);
        }
      } else {
        if (props.slotDuration - step < min) {
          props.changeCalSlotDuration(min);
        } else {
          props.changeCalSlotDuration(props.slotDuration - step);
        }
      }
    }
  };

  const headerRender = ({ value }) => {
    const month = value.format('MMMM');
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
    const showShift = !showShiftCalc;
    if (settings.id) {
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
    GAevent('Appointment page', 'Click add event button');
  };

  return (
    <Container>
      <StyledCalendar
        fullscreen={false}
        headerRender={headerRender}
        onSelect={props.changeCalDate}
        value={props.calendarDate}
      />
      <ItemContainer>
        <div>
          <img src={PointerIcon} alt="列印" />
          <span>列印</span>
        </div>
        <div>
          <button
            onClick={() => {
              props.changePrintModalVisible();
              GAevent('Appointment page', 'Print appt list');
            }}
          >
            預約表
          </button>
        </div>
      </ItemContainer>
      <ItemContainer>
        <div>
          <img src={GridIcon} alt="密度" />
          <span>密度</span>
        </div>
        <div>
          <button
            onClick={() => {
              onSlotDurationChange(true);
              GAevent('Appointment page', 'Smaller slot size');
            }}
          >
            縮小
          </button>
          <Divider type="vertical" />
          <button
            onClick={() => {
              onSlotDurationChange(false);
              GAevent('Appointment page', 'Bigger slot size');
            }}
          >
            放大
          </button>
        </div>
      </ItemContainer>
      <ItemContainer>
        <div>
          <img src={SettingsIcon} alt="排班" />
          <span>排班</span>
        </div>
        <div>
          <button
            onClick={() => {
              onShiftButtonClick();
              GAevent('Appointment page', 'Turn on/off shift');
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
      <SelectDoctorContainer>
        <span>顯示</span>
        <StyledSelect onChange={handleDoctorChange} mode="tags" allowClear maxTagCount={1} value={selectedDoctors}>
          {[
            <Select.Option key={'all'}>{`全部醫師`}</Select.Option>,
            ...props.doctors
              .filter(d => d.activated)
              .map(d => (
                <Select.Option key={d.id}>
                  {`${d.name} (${props.doctorAppCount[d.id] ? props.doctorAppCount[d.id] : 0})`}
                </Select.Option>
              )),
            <Select.Option key={'dayOff'}>{`休假`}</Select.Option>,
          ]}
        </StyledSelect>
      </SelectDoctorContainer>
      <MenuContainer>
        <MenuMainItemContainer onClick={toggleExpand} expand={expand} loaded={loaded}>
          <img src={PlusIcon} alt="plus" />
        </MenuMainItemContainer>
        <MenuSubItemContainer
          onClick={() => {
            GAevent('Appointment page', 'Click show create calendar event modal button');
            moonClick();
            toggleExpand();
          }}
          expand={expand}
          distance={130}
        >
          <img src={MoonIcon} alt="moon" />
        </MenuSubItemContainer>
        <MenuSubItemContainer
          onClick={() => {
            GAevent('Appointment page', 'Click show create appt modal button');
            props.changeCreateAppModalVisible(true);
            toggleExpand();
          }}
          expand={expand}
          distance={70}
        >
          <img src={CalendarIcon} alt="calendar" />
        </MenuSubItemContainer>
      </MenuContainer>
    </Container>
  );
}
const mapStateToProps = ({ appointmentPageReducer, homePageReducer }) => ({
  calendarDate: appointmentPageReducer.calendar.calendarDate,
  doctors: extractDoctorsFromUser(homePageReducer.user.users),
  selectedDoctors: appointmentPageReducer.calendar.selectedDoctors,
  doctorAppCount: appointmentPageReducer.calendar.doctorAppCount,
  slotDuration: appointmentPageReducer.calendar.slotDuration,
  generalSetting: homePageReducer.settings.generalSetting,
  settings: homePageReducer.settings.settings,
  showShiftCalc: homePageReducer.settings.generalSetting && homePageReducer.settings.generalSetting.showShift,
});

const mapDispatchToProps = {
  changeCalDate,
  changePrintModalVisible,
  changeSelectedDoctors,
  changeCreateAppModalVisible,
  changeCreateCalModalVisible,
  changeCalSlotDuration,
  putSettings,
};

export default connect(mapStateToProps, mapDispatchToProps)(AppRight);
