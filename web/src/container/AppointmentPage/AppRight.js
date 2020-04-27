import React, { useState } from 'react';
import { connect } from 'react-redux';
import styled from 'styled-components';
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
import { putSettings } from '../Home/actions';
import { GAevent } from '../../ga';
import { appointmentPage } from './';
import FloatingActionButton from './FloatingActionButton';

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

//#endregion

function AppRight(props) {
  const { calendarDate, changeCalDate, selectedDoctors, settings, showShiftCalc, putSettings } = props;

  const [expand, setExpand] = useState(false);
  const [loaded, setLoaded] = useState(false);

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
    GAevent(appointmentPage, 'Click add event button');
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
              GAevent(appointmentPage, 'Print appt list');
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
              GAevent(appointmentPage, 'Smaller slot size');
            }}
          >
            縮小
          </button>
          <Divider type="vertical" />
          <button
            onClick={() => {
              onSlotDurationChange(false);
              GAevent(appointmentPage, 'Bigger slot size');
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
      <FloatingActionButton
        expand={expand}
        toggleExpand={toggleExpand}
        loaded={loaded}
        moonClick={fabMoonClick}
        calClick={fabCalClick}
      />
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
