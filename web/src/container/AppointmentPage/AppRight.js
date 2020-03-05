import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import styled from 'styled-components';
import { Button, Calendar, Dropdown, Icon, Menu, Row, Col, Slider, Select, Card } from 'antd';
import {
  changeCalDate,
  changePrintModalVisible,
  changeSelectedDoctors,
  changeCreateAppModalVisible,
  changeCreateCalModalVisible,
  changeTodoAppModalVisible,
  changeCalSlotDuration,
  getTodos,
  changeCreateAppDoctor,
  changeCreateAppExpectedArrivalDate,
  getPatient,
  changePatientSelected,
  changeCreateAppNote,
  changeCreateAppDuration,
  changeTodoAppDisposalId,
} from './actions';
import moment from 'moment';
import 'moment/locale/zh-tw';

//#region
const Container = styled.div`
  width: 15%;
  min-width: 300px;
  padding: 20px;
  display: flex;
  flex-direction: column;
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

const TodoContainer = styled.div`
  margin: 20px 0;
  flex: 1;
  min-height: 200px;
  display: flex;
  flex-direction: column;
  overflow-y: scroll;

  & > span {
    font-weight: bold;
  }
`;

const StyledCardContainer = styled(Card)`
  flex: 1 !important;
  overflow-y: scroll;
`;

const CardContent = styled.span`
  user-select: none;
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

const generalGridStyle = {
  width: '100%',
  padding: '20px 5px',
};

const todoGridStyle = { cursor: 'pointer' };
//#endregion

function AppRight(props) {
  const { getTodos, calendarDate, selectedDoctors } = props;

  useEffect(() => {
    getTodos();
  }, [getTodos, calendarDate]);

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
          props.changeSelectedDoctors(['all']);
        } else {
          props.changeSelectedDoctors(d);
        }
      }
    }
  };

  const onSliderChange = value => {
    props.changeCalSlotDuration(value);
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
                const { changeCalDate, calendarDate } = props;
                changeCalDate(moment(calendarDate).add(-1, 'M'));
              }}
            >
              <Icon type="left" />
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
                const { changeCalDate, calendarDate } = props;
                changeCalDate(moment(calendarDate).add(1, 'M'));
              }}
            >
              <Icon type="right" />
            </Button>
          </Col>
        </Row>
      </div>
    );
  };

  const onTodoCardClick = disposal => {
    const doctor = props.doctors.find(d => d.login === disposal.createdBy);
    const app = {
      doctorId: doctor ? doctor.id : undefined,
      expectedDate: disposal.todo.expectedDate,
      patientId: disposal.todo.patient.id,
      note: disposal.todo.note,
      duration: disposal.todo.requiredTreatmentTime,
      disposalId: disposal.id,
    };

    insertAppToCreateAppModal(app);
  };

  const insertAppToCreateAppModal = app => {
    props.changeTodoAppModalVisible(true);
    props.changeCreateAppDoctor(app.doctorId);
    props.changeCreateAppExpectedArrivalDate(moment(app.expectedDate), 'YYYY-MM-DD');
    props.getPatient(app.patientId);
    props.changePatientSelected(true);
    props.changeCreateAppNote({ target: { value: app.note } });
    props.changeCreateAppDuration(app.duration);
    props.changeTodoAppDisposalId(app.disposalId);
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
        >
          <DropdownButton size={'large'} type="primary">
            <Icon type="down" />
          </DropdownButton>
        </Dropdown>
        <PrintButton size={'large'} onClick={props.changePrintModalVisible}>
          <Icon type="printer" size={'large'} />
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
      <TodoContainer>
        <span>待辦清單 ({props.calendarDate.format('llddd')})</span>
        <StyledCardContainer bordered={false} loading={props.todosLoading}>
          {props.todos.length === 0 ? (
            <Card.Grid style={{ ...generalGridStyle }} key={'none'}>
              <CardContent>無待辦事項</CardContent>
            </Card.Grid>
          ) : (
            props.todos.map(t => {
              const doctor = props.doctors.find(d => d.login === t.createdBy);
              const doctorName = doctor ? doctor.name : '';
              return (
                <Card.Grid
                  style={{ ...generalGridStyle, ...todoGridStyle }}
                  key={t.id}
                  onClick={() => {
                    onTodoCardClick(t);
                  }}
                >
                  <CardContent>
                    {`${moment(t.createdDate).format('HH:mm')} ${doctorName}
                    ${t.todo.patient.name}需要預約`}
                  </CardContent>
                </Card.Grid>
              );
            })
          )}
        </StyledCardContainer>
      </TodoContainer>
      <ChangeSlotDurationContainer>
        <span>縮放</span>
        <Slider min={10} max={30} step={5} onChange={onSliderChange} value={props.slotDuration} />
      </ChangeSlotDurationContainer>
    </Container>
  );
}
const mapStateToProps = ({ appointmentPageReducer }) => ({
  calendarDate: appointmentPageReducer.calendar.calendarDate,
  doctors: appointmentPageReducer.calendar.doctors,
  selectedDoctors: appointmentPageReducer.calendar.selectedDoctors,
  doctorAppCount: appointmentPageReducer.calendar.doctorAppCount,
  slotDuration: appointmentPageReducer.calendar.slotDuration,
  todos: appointmentPageReducer.todo.todos,
  todosLoading: appointmentPageReducer.todo.loading,
});

const mapDispatchToProps = {
  changeCalDate,
  changePrintModalVisible,
  changeSelectedDoctors,
  changeCreateAppModalVisible,
  changeCreateCalModalVisible,
  changeTodoAppModalVisible,
  changeCalSlotDuration,
  getTodos,
  changeCreateAppDoctor,
  changeCreateAppExpectedArrivalDate,
  getPatient,
  changePatientSelected,
  changeCreateAppNote,
  changeCreateAppDuration,
  changeTodoAppDisposalId,
};

export default connect(mapStateToProps, mapDispatchToProps)(AppRight);
