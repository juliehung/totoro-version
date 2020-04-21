import React from 'react';
import { connect } from 'react-redux';
import styled from 'styled-components';
import { DownOutlined, LeftOutlined, PrinterOutlined, RightOutlined } from '@ant-design/icons';
import { Button, Calendar, Dropdown, Menu, Row, Col, Slider, Select } from 'antd';
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
import { GAevent } from '../../ga';
import extractDoctorsFromUser from '../../utils/extractDoctorsFromUser';

//#region
const Container = styled.div`
  width: 15%;
  min-width: 300px;
  padding: 20px;
  display: flex;
  flex-direction: column;
  background: #fff;
  z-index: 400;
  @media (max-width: 800px) {
    width: 100%;
    min-height: 90vh;
  }
`;

const TopContainer = styled.div`
  display: flex;
`;

const StyledCalendar = styled(Calendar)`
  margin-top: 15px;
`;

const AppButton = styled(Button)`
  margin-right: 0px;
`;

const DropdownButton = styled(Button)`
  margin-left: -5px;
  border-left: 0.5px solid #fff;
  border-top-left-radius: 0;
  border-bottom-left-radius: 0;
`;

const PrintButton = styled(Button)`
  margin-left: 5px;
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

const ChangeSlotDurationContainer = styled.div`
  display: flex;
  align-items: baseline;
  & > span {
    font-weight: bold;
    margin-right: 10px;
  }
  & > *:not(span) {
    flex-grow: 1;
  }
`;

//#endregion

function AppRight(props) {
  const { calendarDate, changeCalDate, selectedDoctors } = props;

  const handleMenuClick = () => {
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

  const onSliderChange = value => {
    const title = document.querySelector('.fc-center');
    if (title) {
      simulateMouseClick(title);
      props.changeCalSlotDuration(value);
    }

    GAevent('Appointment page', 'Change slot duration');
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

  return (
    <Container>
      <TopContainer>
        <AppButton
          type="primary"
          size={'large'}
          block
          onClick={() => {
            props.changeCreateAppModalVisible(true);
          }}
        >
          新增預約
        </AppButton>
        <Dropdown
          overlay={
            <Menu onClick={handleMenuClick}>
              <Menu.Item key="1">醫師/診所休假</Menu.Item>
            </Menu>
          }
          trigger={['click']}
        >
          <DropdownButton size={'large'} type="primary">
            <DownOutlined />
          </DropdownButton>
        </Dropdown>
        <PrintButton size={'large'} onClick={props.changePrintModalVisible}>
          <PrinterOutlined size={'large'} />
        </PrintButton>
      </TopContainer>
      <StyledCalendar
        fullscreen={false}
        headerRender={headerRender}
        onSelect={props.changeCalDate}
        value={props.calendarDate}
      />
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
      <ChangeSlotDurationContainer>
        <span>縮放</span>
        <Slider min={10} max={30} step={5} onAfterChange={onSliderChange} defaultValue={props.slotDuration} />
      </ChangeSlotDurationContainer>
    </Container>
  );
}
const mapStateToProps = ({ appointmentPageReducer, homePageReducer }) => ({
  calendarDate: appointmentPageReducer.calendar.calendarDate,
  doctors: extractDoctorsFromUser(homePageReducer.user.users),
  selectedDoctors: appointmentPageReducer.calendar.selectedDoctors,
  doctorAppCount: appointmentPageReducer.calendar.doctorAppCount,
  slotDuration: appointmentPageReducer.calendar.slotDuration,
});

const mapDispatchToProps = {
  changeCalDate,
  changePrintModalVisible,
  changeSelectedDoctors,
  changeCreateAppModalVisible,
  changeCreateCalModalVisible,
  changeCalSlotDuration,
};

export default connect(mapStateToProps, mapDispatchToProps)(AppRight);
