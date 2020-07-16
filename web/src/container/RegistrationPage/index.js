import React, { useEffect, useState } from 'react';
import { connect } from 'react-redux';
import { getRegistrations, updateSelectedDate, onSelectPatient, onLeavePage } from './actions';
import { DatePicker, Empty, Select, Table, Typography, message } from 'antd';
import styled from 'styled-components';
import moment from 'moment';
import { Helmet } from 'react-helmet-async';
import RegistDrawer from './RegistDrawer';
import extractDoctorsFromUser from '../../utils/extractDoctorsFromUser';
import { LeftOutlined, RightOutlined } from '@ant-design/icons';
import { GAevent, GApageView } from '../../ga';
import { columns } from './utils/columns';
import { convertToTableSource, allDoctors } from './utils/convertToTableSource';
import { openXray, changeXrayModalVisible } from '../Home/actions';

export const registrationPage = 'Registration page';

const { Title } = Typography;
const { Option } = Select;

const XRAY_GREETING_MESSAGE = 'XRAY_GREETING_MESSAGE';

//#region
const Container = styled.div`
  display: flex;
  flex-direction: column;
  width: 100%;
  margin: 0 auto;
  height: 100%;
`;
const DatePickerContainer = styled.div`
  display: flex;
  align-items: center;
  height: 70px;
  padding: 10px;
`;
const StyledTable = styled(Table)`
  flex-grow: 1;
  height: 80vh;
  touch-action: pan-y;
`;
const StyledRightOutlined = styled(RightOutlined)`
  font-size: 36px;
`;
const StyledLeftOutlined = styled(LeftOutlined)`
  font-size: 36px;
`;
const StyledDatePicker = styled(DatePicker)`
  margin-top: 5px;
`;
const StyledTitle = styled(Title)`
  margin: 0 !important;
`;

//#endregion

function RegistrationPage(props) {
  const {
    getRegistrations,
    updateSelectedDate,
    openXray,
    xrayServerState,
    xrayServerError,
    xRayVendors,
    onLeavePage,
    xrayOnRequest,
    changeXrayModalVisible,
  } = props;

  const [selectedDoctor, setSelectedDoctor] = useState();

  useEffect(() => {
    GApageView();
  }, []);

  useEffect(() => {
    if (xrayOnRequest) {
      message.loading({ content: '載入中...', key: XRAY_GREETING_MESSAGE, duration: 10 });
    } else {
      if (xrayServerState && !xrayServerError) {
        message.success({ content: '開啟 xray 軟體中...', key: XRAY_GREETING_MESSAGE });
      } else if (!xrayServerState && xrayServerError) {
        message.destroy();
        changeXrayModalVisible(true);
      }
    }

    return () => {
      onLeavePage();
    };
  }, [xrayServerState, xrayServerError, onLeavePage, xrayOnRequest, changeXrayModalVisible]);

  useEffect(() => {
    const updateRegistrations = arrival => {
      let sessionSelectedDate = null;
      try {
        sessionSelectedDate = JSON.parse(sessionStorage.getItem('selectedDate'));
      } catch (e) {
        console.log(e);
      }
      const date = sessionSelectedDate ? moment(sessionSelectedDate) : moment();

      if (arrival && !arrival.startOf('day').isSame(date.startOf('day'))) {
        return;
      }

      const start = date.clone().startOf('day');
      const end = date.clone().endOf('day');
      getRegistrations(start, end);
    };

    updateRegistrations();
  }, [getRegistrations]);

  const fetchRegistrations = date => {
    const start = date.clone().startOf('day');
    const end = date.clone().endOf('day');
    getRegistrations(start, end);
  };

  const renderDoctorSelect = () => {
    if (props.doctors) {
      const options = props.doctors.map(doctor => {
        return (
          <Option key={doctor.id} value={doctor.id}>
            {doctor.name}
          </Option>
        );
      });
      options.unshift(
        <Option key={-1} value={allDoctors}>
          全診所醫師
        </Option>,
      );
      return (
        <Select defaultValue={allDoctors} style={{ width: 120 }} onChange={onDoctorChange}>
          {options}
        </Select>
      );
    }
  };

  const onDatePickerChange = date => {
    updateSelectedDate(date);
    if (date) {
      fetchRegistrations(date);
    }
  };

  const onDoctorChange = doctor => {
    setSelectedDoctor(doctor);
    GAevent(registrationPage, 'Change doctor');
  };

  const moveDate = days => () => {
    const date = props.selectedDate.clone().add(days, 'day');
    onDatePickerChange(date);
    GAevent(registrationPage, 'Change date');
  };

  const withMargin = width => <div style={{ width: width + 'px' }} />;

  return (
    <Container>
      <Helmet>
        <title>掛號</title>
      </Helmet>
      <DatePickerContainer>
        <StyledTitle level={3}>就診列表</StyledTitle>
        {withMargin(10)}
        <StyledLeftOutlined onClick={moveDate(-1)} />
        <StyledDatePicker onChange={onDatePickerChange} value={props.selectedDate} allowClear={false} />
        <StyledRightOutlined onClick={moveDate(1)} />
        {withMargin(10)}
        {renderDoctorSelect()}
      </DatePickerContainer>
      <StyledTable
        columns={columns(xRayVendors)}
        allowClear={true}
        pagination={false}
        loading={props.loading}
        locale={{ emptyText: <Empty description="無掛號" /> }}
        onRow={row => {
          return {
            onClick: e => {
              if (e.target.className && e.target.className.includes('xray')) {
                const vendor = e.target.className
                  .split(' ')
                  .find(c => c.includes('XRAYVENDORS'))
                  .split('_')[1];
                const { patient } = row;

                const appointment = {
                  medicalId: patient.medicalId,
                  nationalId: patient.nationalId,
                  patientName: patient.name,
                  birth: patient.birth,
                  gender: patient.gender,
                };
                openXray({ vendor, appointment });
                return;
              }

              props.onSelectPatient(row.patient);
              GAevent(registrationPage, 'Click patient');
            },
          };
        }}
        dataSource={convertToTableSource(props.registrations, selectedDoctor)}
        scroll={{ x: 1200 }}
      />
      <RegistDrawer />
    </Container>
  );
}

const mapStateToProps = ({ registrationPageReducer, homePageReducer, settingPageReducer }) => ({
  registrations: registrationPageReducer.registration.registrations,
  loading: registrationPageReducer.registration.loading,
  selectedDate: registrationPageReducer.registration.selectedDate,
  doctors: extractDoctorsFromUser(homePageReducer.user.users),
  xrayServerState: homePageReducer.xray.serverState,
  xrayServerError: homePageReducer.xray.serverError,
  xRayVendors: settingPageReducer.configurations.config.xRayVendors,
  xrayOnRequest: homePageReducer.xray.onRequest,
});

const mapDispatchToProps = {
  getRegistrations,
  updateSelectedDate,
  onSelectPatient,
  openXray,
  onLeavePage,
  changeXrayModalVisible,
};

export default connect(mapStateToProps, mapDispatchToProps)(RegistrationPage);
