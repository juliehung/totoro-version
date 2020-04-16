import { Modal, Button, DatePicker, Select, Input, Spin, Checkbox } from 'antd';
import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import moment from 'moment';
import {
  changeTodoAppModalVisible,
  changeCreateAppNote,
  changeCreateAppDuration,
  changeCreateAppDefaultDuration,
  changeCreateAppDoctor,
  changeCreateAppDefaultDoctor,
  changeCreateAppExpectedArrivalDate,
  changeCreateAppExpectedArrivalTime,
  createTodoApp,
  changeCreateAppSpecialNote,
  checkConfirmButtonDisable,
  getAllEvents,
} from './actions';
import styled from 'styled-components';
import { requiredTreatmentTimeDefault } from './constant';
import extractDoctorsFromUser from '../../utils/extractDoctorsFromUser';
import { defaultTimeOption } from './utils/generateDefaultTime';

//#region
const Container = styled.div`
  width: 100%;
`;

const BottomContainer = styled.div`
  display: flex;
  justify-content: space-between;
`;

const ButtonsContainer = styled.div`
  width: 100%;
  display: flex;
  justify-content: flex-end;
`;

const StyledButton = styled(Button)`
  margin: 0 5px;
`;

const InfoRowContainer = styled.div`
  & > div {
    margin: 10px 0;
  }
`;

const StyledDatePicker = styled(DatePicker)`
  margin-right: 15px !important;
`;

const StyledSelect = styled(Select)`
  min-width: 150px !important;
`;

const RequiredCol = styled.span`
  font-weight: bold;
  &:before {
    content: '* ';
  }
`;

const PatientDetail = styled.div`
  width: 100%;
  padding: 10px 10px;
  background: #f5f6fa;
  display: flex;
  border-radius: 10px;
  min-height: 40px;
`;

const PatientDetailCol = styled.div`
  width: 50%;
`;

const PatientDetailElement = styled.div`
  width: 100%;
`;

//#endregion

function TodoAppModal({
  visible,
  patientSelected,
  selectedPatient,
  doctors,
  appointment,
  patient,
  createAppSuccess,
  disabled,
  changeTodoAppModalVisible,
  changeCreateAppNote,
  changeCreateAppDuration,
  changeCreateAppDefaultDuration,
  changeCreateAppDoctor,
  changeCreateAppDefaultDoctor,
  changeCreateAppExpectedArrivalDate,
  changeCreateAppExpectedArrivalTime,
  createTodoApp,
  changeCreateAppSpecialNote,
  checkConfirmButtonDisable,
  getAllEvents,
  setting,
  account,
  loading,
}) {
  useEffect(() => {
    if (createAppSuccess) {
      changeTodoAppModalVisible(false);
    }
  }, [createAppSuccess, getAllEvents, changeTodoAppModalVisible]);

  useEffect(() => {
    checkConfirmButtonDisable();
  }, [appointment, patient, checkConfirmButtonDisable]);

  useEffect(() => {
    if (setting) {
      changeCreateAppDefaultDuration(setting.requiredTreatmentTime);
    }
  }, [setting, changeCreateAppDefaultDuration]);

  useEffect(() => {
    if (account.data && account.data.authorities) {
      if (account.data.authorities[0].includes('DOCTOR')) {
        changeCreateAppDefaultDoctor(account.data.id);
      }
    }
  }, [changeCreateAppDefaultDoctor, account]);

  const closeModal = () => {
    changeTodoAppModalVisible(false);
  };

  const handleConfirm = () => {
    if (appointment.patientId) {
      createTodoApp();
    }
  };

  const options = [
    { label: 'micro', value: 'micro' },
    { label: '行動不便', value: 'baseFloor' },
  ];

  const onSpecialNoteChange = value => {
    changeCreateAppSpecialNote(value);
  };

  return (
    <Modal
      centered
      title={'建立預約'}
      footer={null}
      visible={visible}
      onCancel={closeModal}
      maskClosable={false}
      closable={false}
      destroyOnClose
    >
      <Container>
        {patientSelected && (
          <Spin spinning={selectedPatient === undefined}>
            <PatientDetail>
              <PatientDetailCol>
                <PatientDetailElement>
                  <span>{selectedPatient && selectedPatient.name}</span>
                </PatientDetailElement>
                <PatientDetailElement>
                  <span>{selectedPatient && (selectedPatient.birth ? selectedPatient.birth : '')}</span>
                  <span>{selectedPatient && selectedPatient.birth ? ', ' : ''}</span>
                  <span>{selectedPatient && selectedPatient.age}</span>
                  <span>{selectedPatient && (selectedPatient.age ? (selectedPatient.gender ? ', ' : '') : '')}</span>
                  <span>{selectedPatient && `${selectedPatient.gender ? selectedPatient.gender : ''}`}</span>
                </PatientDetailElement>
                <PatientDetailElement>
                  <span>
                    {selectedPatient &&
                      `預約: ${selectedPatient.appointmentsAnalysis.appointment},
                      爽約: ${selectedPatient.appointmentsAnalysis.noShow},
                      取消: ${selectedPatient.appointmentsAnalysis.cancel}`}
                  </span>
                </PatientDetailElement>
              </PatientDetailCol>
              <PatientDetailCol>
                <PatientDetailElement>
                  <span>
                    {selectedPatient && `最近治療:  ${selectedPatient.appointmentsAnalysis.recentRegistration}`}
                  </span>
                </PatientDetailElement>
                <PatientDetailElement>
                  <span>
                    {selectedPatient && `最近預約:  ${selectedPatient.appointmentsAnalysis.recentAppointment}`}
                  </span>
                </PatientDetailElement>
              </PatientDetailCol>
            </PatientDetail>
          </Spin>
        )}
        <InfoRowContainer>
          <div>
            <RequiredCol>預約時間：</RequiredCol>
            <span>
              <StyledDatePicker
                onChange={changeCreateAppExpectedArrivalDate}
                value={appointment.expectedArrivalDate}
                placeholder="請選擇日期"
              />
              <StyledSelect
                placeholder="請選擇時間"
                value={appointment.expectedArrivalTime && moment(appointment.expectedArrivalTime).format('HH:mm')}
                onChange={t => {
                  changeCreateAppExpectedArrivalTime(moment(t, 'HH:mm'));
                }}
              >
                {defaultTimeOption.map(t => {
                  const time24 = t.format('HH:mm');
                  const time12 = t.format('hh:mm');
                  const prefix = t.locale('en-US').format('a') === 'am' ? '上午' : '下午';

                  return (
                    <Select.Option key={time24} value={time24}>
                      {prefix} {time12}
                    </Select.Option>
                  );
                })}
              </StyledSelect>
            </span>
          </div>
          <div>
            <RequiredCol>主治醫師：</RequiredCol>
            <span>
              <StyledSelect placeholder="請選擇醫師" onSelect={changeCreateAppDoctor} value={appointment.doctorId}>
                {doctors
                  .filter(d => d.activated)
                  .map(d => (
                    <Select.Option key={d.id} value={d.id}>
                      {d.name}
                    </Select.Option>
                  ))}
              </StyledSelect>
            </span>
          </div>
          <div>
            <RequiredCol>治療長度：</RequiredCol>
            <span>
              <StyledSelect
                placeholder="請選擇治療長度"
                onSelect={changeCreateAppDuration}
                value={appointment.duration}
              >
                {requiredTreatmentTimeDefault.map(t => (
                  <Select.Option key={t} value={t}>
                    {t}
                  </Select.Option>
                ))}
              </StyledSelect>
            </span>
          </div>
          <div>
            <span>預約內容：</span>
            <span>
              <Input.TextArea
                autoSize={{ minRows: 3, maxRows: 3 }}
                onChange={changeCreateAppNote}
                value={appointment.note}
              />
            </span>
          </div>
          <div>
            <span>特殊註記：</span>
            <span>
              <Checkbox.Group options={options} value={appointment.specialNote} onChange={onSpecialNoteChange} />
            </span>
          </div>
        </InfoRowContainer>
        <BottomContainer>
          <ButtonsContainer>
            <StyledButton onClick={closeModal}>取消</StyledButton>
            <StyledButton type="primary" onClick={handleConfirm} disabled={disabled} loading={loading}>
              新增
            </StyledButton>
          </ButtonsContainer>
        </BottomContainer>
      </Container>
    </Modal>
  );
}
const mapStateToProps = ({ appointmentPageReducer, homePageReducer }) => ({
  visible: appointmentPageReducer.createApp.todoModalVisible,
  patientSelected: appointmentPageReducer.createApp.patientSelected,
  selectedPatient: appointmentPageReducer.createApp.selectedPatient,
  doctors: extractDoctorsFromUser(homePageReducer.user.users),
  appointment: appointmentPageReducer.createApp.appointment,
  patient: appointmentPageReducer.createApp.patient,
  createAppSuccess: appointmentPageReducer.createApp.createAppSuccess,
  disabled: appointmentPageReducer.createApp.disabled,
  setting: homePageReducer.settings.generalSetting,
  account: homePageReducer.account,
  loading: appointmentPageReducer.createApp.loading,
});

const mapDispatchToProps = {
  changeTodoAppModalVisible,
  changeCreateAppNote,
  changeCreateAppDuration,
  changeCreateAppDefaultDuration,
  changeCreateAppDoctor,
  changeCreateAppDefaultDoctor,
  changeCreateAppExpectedArrivalDate,
  changeCreateAppExpectedArrivalTime,
  createTodoApp,
  changeCreateAppSpecialNote,
  checkConfirmButtonDisable,
  getAllEvents,
};

export default connect(mapStateToProps, mapDispatchToProps)(TodoAppModal);
