import React, { useEffect, useState } from 'react';
import { connect } from "react-redux";
import { Link } from 'react-router-dom';
import { getRegistrations, updateSelectedDate } from './actions';
import { Button, DatePicker, Empty, Icon, Select, Table, Tooltip, Typography } from 'antd';
import ManPng from '../../static/images/man.png';
import WomanPng from '../../static/images/woman.png';
import DefaultPng from '../../static/images/default.png';
import styled from "styled-components";
import moment from "moment";
import { Helmet } from "react-helmet-async";
import { getUsersStart } from '../AppointmentPage/actions';
import { B1, G1, Gray700 } from '../../utils/colors';
import MqttHelper from '../../utils/mqtt';

const { Title } = Typography;
const { Option } = Select;

const Container = styled.div`
  display: flex;
  flex-direction: column;
  height: 100vh;
  width: 1250px;
  margin: 0 auto;
`;
const DatePickerContainer = styled.div`
  display: flex;
  align-items: center;
  height: 70px;
`;
const NameContainer = styled.div`
  display: flex;
  width: 200px;
  align-items: center;
`;
const StyledTable = styled(Table)`
  flex-grow: 1;
  height: 80vh;
`;
const RowIndexContainer = styled.div`
  display: flex;
  align-items: center;
  justify-content: center;
`;
const Status = styled.div`
  width: 6px;
  height: 50px;
  border-radius: 25px;
  margin-right: 10px;
`;
const StyledTitle = styled(Title)`
  margin: 0 !important;
`;
const StyledIcon = styled(Icon)`
  font-size: 36px;
`;
const StyledDatePicker = styled(DatePicker)`
  margin-top: 5px;
`;
const AvatarImg = styled.img`
  width: 48px;
  height: 48px;
  margin-right: 10px;
`;

const columns = [
  {
    title: '序位',
    dataIndex: 'rowIndex',
    key: 'rowIndex',
    width: 80
  },
  { title: '姓名', dataIndex: 'name', key: 'name', width: 250 },
  {
    title: '年齡',
    dataIndex: 'age',
    key: 'age',
    sorter: (a, b) => a.age.replace('Y', '') - b.age.replace('Y', ''),
    width: 80
  },
  {
    title: '掛號時間',
    dataIndex: 'arrivalTime',
    key: 'arrivalTime',
    defaultSortOrder: 'ascend',
    sorter: (a, b) => moment(a.arrivalTime).unix() - moment(b.arrivalTime).unix(),
    width: 150
  },
  {
    title: '預約時間',
    dataIndex: 'expectedArrivalTime',
    key: 'expectedArrivalTime',
    sorter: (a, b) => moment(a.expectedArrivalTime).unix() - moment(b.expectedArrivalTime).unix(),
    width: 150
  },
  {
    title: '掛號類別',
    dataIndex: 'type',
    key: 'type',
    filters: [
      { text: '健保', value: '健保' },
      { text: '自費', value: '自費' }
    ],
    filterMultiple: false,
    onFilter: (value, record) => record.type.indexOf(value) === 0,
    width: 100
  },
  { title: '醫師', dataIndex: 'doctor', key: 'doctor', width: 90 },
  { title: '治療事項', dataIndex: 'subject', key: 'subject', ellipsis: true, width: 200 },
];

const allDoctors = "THESE_ARE_ALL_DOCTORS";

function RegistrationPage(props) {
  const { getRegistrations, updateSelectedDate, getUsersStart } = props;

  const [selectedDoctor, setSelectedDoctor] = useState();

  useEffect(() => {
    getUsersStart();
  }, [getUsersStart]);

  useEffect(() => {
    const updateRegistrations = (arrival) => {
      let sessionSelectedDate = null;
      try {
        sessionSelectedDate = JSON.parse(sessionStorage.getItem('selectedDate'));
      } catch (e) {
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

    MqttHelper.subscribeAppointment(RegistrationPage.name, message => {
      let messageObj;
      try {
        messageObj = JSON.parse(message);
      } catch (e) {
        // ignored
      }
      if (!messageObj || !messageObj.registration) {
        return;
      }

      const arrivalTime = moment(messageObj.registration.arrivalTime);
      if (arrivalTime) {
        // TODO: refactoring with better registration update mechanism
        updateRegistrations(arrivalTime);
      }
    });

    return () => {
      MqttHelper.unsubscribeAppointment(RegistrationPage.name);
    };
  }, [getRegistrations]);

  const fetchRegistrations = date => {
    const start = date.clone().startOf('day');
    const end = date.clone().endOf('day');
    getRegistrations(start, end);
  };

  const renderRowIndex = (rowIndex, status) => {
    let c = Gray700;
    if (status === 'IN_PROGRESS') c = G1;
    if (status === 'PENDING') c = B1;
    return (
      <RowIndexContainer>
        <Status style={{ background: c }}/>
        <StyledTitle level={3}>{rowIndex}</StyledTitle>
      </RowIndexContainer>
    );
  };

  const renderAvatarImg = gender => {
    if (gender === 'MALE') return <AvatarImg src={ManPng} alt="male" />;
    if (gender === 'FEMALE') return <AvatarImg src={WomanPng} alt="female" />;
    return <AvatarImg src={DefaultPng} alt="default" />
  };

  const renderName = patient => {
    return (
      <Tooltip title={patient.name}>
        <NameContainer>
          {renderAvatarImg(patient.gender)}
          <div><span>{patient.medicalId}</span><Title level={4}>{patient.name}</Title></div>
        </NameContainer>
      </Tooltip>
    );
  };

  const renderExpandedRow = patient => {
    return (
      <div>
        <Link to={{
          pathname: "/q",
          state: { patient }
        }}>
          <Button type="primary">初診單</Button>
        </Link>
      </div>
    );
  };

  const renderDoctorSelect = () => {
    if (props.doctors) {
      const options = props.doctors.map(doctor => {
        return <Option key={doctor.id} value={doctor.login}>{doctor.name}</Option>;
      });
      options.unshift(<Option key={-1} value={allDoctors}>全診所醫師</Option>);
      return <Select defaultValue={allDoctors} style={{ width: 120 }} onChange={onDoctorChange}>{options}</Select>;
    }
  };

  const renderSubject =  (subject, note) => {
    if ((!subject || subject === '') && (!note || note === '')) {
      return '無';
    }
    return (subject && subject !== '') ? subject + ',' + note : note;
  };

  const convertToTableSource = registrations => {
    const tableSource = registrations
      // Don't show cancel appointment and no registration
      .filter(appt => appt.status !== 'CANCEL' && appt.registration.id)
      .filter(appt => {
        if (!selectedDoctor || !appt.doctor.user || selectedDoctor === allDoctors) {
          return true;
        }
        return (appt.doctor.user.login === selectedDoctor);
      })
      .slice()
      .sort((a, b) => moment(a.registration.arrivalTime).unix() - moment(b.registration.arrivalTime).unix());

    let i = 1;
    return tableSource
      .map(appt => {
        return {
          key: appt.id,
          rowIndex: renderRowIndex(i++, appt.registration.status),
          name: renderName(appt.patient),
          patient: appt.patient,
          arrivalTime: appt.registration.arrivalTime && moment(appt.registration.arrivalTime).local().format('YYYY-MM-DD HH:mm'),
          expectedArrivalTime: appt.expectedArrivalTime && moment(appt.expectedArrivalTime).local().format('YYYY-MM-DD HH:mm'),
          age: moment().diff(appt.patient.birth, 'years') + 'Y',
          type: appt.registration.type,
          doctor: appt.doctor.user.firstName,
          subject: renderSubject(appt.subject, appt.note),
        };
      });
  };

  const onDatePickerChange = date => {
    updateSelectedDate(date);
    if (date) {
      fetchRegistrations(date);
    }
  };

  const onDoctorChange = doctor => {
    setSelectedDoctor(doctor);
  };

  const onExpand = (expanded, record) => {
    if (expanded) {

    }
    console.log(expanded, record);
  };

  const moveDate = days => () => {
    const date = props.selectedDate.clone().add(days, 'day');
    onDatePickerChange(date);
  };

  const withMargin = width => <div style={{ width: width + 'px' }} />;

  return (
    <Container>
      <Helmet><title>掛號</title></Helmet>
      <DatePickerContainer>
        <StyledTitle level={3}>就診列表</StyledTitle>
        {withMargin(10)}
        <StyledIcon type="caret-left" onClick={moveDate(-1)} />
        <StyledDatePicker onChange={onDatePickerChange} value={props.selectedDate} />
        <StyledIcon type="caret-right" onClick={moveDate(1)} />
        {withMargin(10)}
        {renderDoctorSelect()}
      </DatePickerContainer>
      <StyledTable
        columns={columns}
        allowClear={true}
        pagination={false}
        loading={props.loading}
        locale={{ emptyText: <Empty description="無掛號" /> }}
        expandedRowRender={row => renderExpandedRow(row.patient)}
        onExpand={onExpand}
        dataSource={convertToTableSource(props.registrations)}
      />
    </Container>
  );
}

const mapStateToProps = ({ registrationPageReducer, appointmentPageReducer }) => ({
  registrations: registrationPageReducer.registration.registrations,
  loading: registrationPageReducer.registration.loading,
  selectedDate: registrationPageReducer.registration.selectedDate,
  doctors: appointmentPageReducer.calendar.doctors,
});

const mapDispatchToProps = {
  getRegistrations,
  updateSelectedDate,
  getUsersStart,
};

export default connect(mapStateToProps, mapDispatchToProps)(RegistrationPage);
