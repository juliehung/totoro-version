import { Modal, Button, DatePicker, Select, Input, Spin, message, Checkbox, Empty } from 'antd';
import React, { useEffect, useState } from 'react';
import { connect } from 'react-redux';
import moment from 'moment';
import {
  changeCreateAppModalVisible,
  searchPatients,
  getPatient,
  changePatientSelected,
  changeCreateAppNote,
  changeCreateAppDuration,
  changeCreateAppDefaultDuration,
  changeCreateAppDoctor,
  changeCreateAppDefaultDoctor,
  changeCreateAppExpectedArrivalDate,
  changeCreateAppExpectedArrivalTime,
  createAppointment,
  changeCreateAppSpecialNote,
  checkConfirmButtonDisable,
  changeCreateAppPatientName,
  changeCreateAppPatientPhone,
  changeCreateAppPatientNationalId,
  changeCreateAppPatientBirth,
  createPatient,
  getAllEvents,
} from './actions';
import styled from 'styled-components';
import { requiredTreatmentTimeDefault } from './constant';
import convertMrnTo5Digits from './utils/convertMrnTo5Digits';
import { GAevent } from '../../ga';
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

const StyledSearchSelect = styled(Select)`
  width: 100%;
`;

const NewPatientContainer = styled.div`
  width: 100%;
  margin-top: 10px;
  padding: 10px 10px;
  background: #f5f6fa;
  border-radius: 10px;
`;

const NewPatientRow = styled.div`
  width: 100%;
  margin: 5px 0;
  display: flex;
`;

const NewPatientElement = styled.div`
  width: 50%;
  display: flex;
  align-items: baseline;
  padding: 0 3px;
  & > span {
    flex-shrink: 0;
  }
`;

const PatientDetail = styled.div`
  width: 100%;
  margin-top: 10px;
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

function CreateAppModal({
  visible,
  patients,
  patientSelected,
  selectedPatient,
  doctors,
  appointment,
  patient,
  createAppSuccess,
  disabled,
  changeCreateAppModalVisible,
  searchPatients,
  getPatient,
  changePatientSelected,
  changeCreateAppNote,
  changeCreateAppDuration,
  changeCreateAppDefaultDuration,
  changeCreateAppDoctor,
  changeCreateAppDefaultDoctor,
  changeCreateAppExpectedArrivalDate,
  changeCreateAppExpectedArrivalTime,
  createAppointment,
  changeCreateAppSpecialNote,
  checkConfirmButtonDisable,
  changeCreateAppPatientName,
  changeCreateAppPatientPhone,
  changeCreateAppPatientNationalId,
  changeCreateAppPatientBirth,
  createPatient,
  getAllEvents,
  setting,
  account,
  loading,
}) {
  const [expectedTimeOption, setExpectedTimeOption] = useState(defaultTimeOption);

  useEffect(() => {
    if (createAppSuccess) {
      getAllEvents();
      message.success('新增預約成功');
      changeCreateAppModalVisible(false);
    }
  }, [createAppSuccess, getAllEvents, changeCreateAppModalVisible]);

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
    changeCreateAppModalVisible(false);
  };

  let debounceSearch;
  const onSearchTextChange = e => {
    clearTimeout(debounceSearch);
    debounceSearch = setTimeout(() => {
      searchPatients(e);
    }, 300);
  };

  const onSearchTextFocus = () => {
    clearTimeout(debounceSearch);
    debounceSearch = setTimeout(() => {
      searchPatients(undefined);
    }, 300);
  };

  const onPatientSelect = id => {
    getPatient(id);
    changePatientSelected(true);
  };

  const handleConfirm = () => {
    GAevent('Appointment page', 'Create appointment clicked');
    if (appointment.patientId) {
      createAppointment();
    } else {
      createPatient();
    }
  };

  const options = [
    { label: 'micro', value: 'micro' },
    { label: '行動不便', value: 'baseFloor' },
  ];

  const onSpecialNoteChange = value => {
    changeCreateAppSpecialNote(value);
  };

  const onChangePatientName = e => {
    changeCreateAppPatientName(e.target.value);
  };

  const onChangePatientNationalId = e => {
    changeCreateAppPatientNationalId(e.target.value);
  };

  const onChangePatientPhone = e => {
    changeCreateAppPatientPhone(e.target.value);
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
        <StyledSearchSelect
          showSearch
          placeholder="請輸入病患病歷編號或姓名"
          filterOption={false}
          onSearch={onSearchTextChange}
          onFocus={onSearchTextFocus}
          onSelect={onPatientSelect}
          notFoundContent={<Empty description="沒有資料" />}
        >
          {patients.map(({ id, name }) => (
            <Select.Option key={id} value={id}>
              {`${name}, ${convertMrnTo5Digits(id)}`}
            </Select.Option>
          ))}
        </StyledSearchSelect>
        {!patientSelected && (
          <NewPatientContainer>
            <NewPatientRow>
              <NewPatientElement>
                <span>病患姓名：</span>
                <Input onChange={onChangePatientName} value={patient.name} placeholder="(必填)" />
              </NewPatientElement>
              <NewPatientElement>
                <span>生日：</span>
                <DatePicker allowClear onChange={changeCreateAppPatientBirth} value={patient.birth} placeholder="" />
              </NewPatientElement>
            </NewPatientRow>
            <NewPatientRow>
              <NewPatientElement>
                <span>身分證號：</span>
                <Input onChange={onChangePatientNationalId} value={patient.nationalId} />
              </NewPatientElement>
              <NewPatientElement>
                <span>電話：</span>
                <Input onChange={onChangePatientPhone} value={patient.phone} placeholder="(必填)" />
              </NewPatientElement>
            </NewPatientRow>
          </NewPatientContainer>
        )}
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
                value={appointment.expectedArrivalTime && moment(appointment.expectedArrivalTime).format('HHmm')}
                onChange={t => {
                  changeCreateAppExpectedArrivalTime(moment(t, 'HH:mm'));
                }}
                showSearch
                onSearch={e => {
                  if (e.length === 4) {
                    const time = moment(e, 'HHmm');
                    if (time.isValid()) {
                      if (expectedTimeOption.map(et => et.format('HHmm')).includes(time.format('HHmm'))) {
                        return;
                      }
                      setExpectedTimeOption([...defaultTimeOption, time]);
                    }
                  }
                }}
              >
                {expectedTimeOption.map(t => {
                  const time24 = t.format('HHmm');
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
  visible: appointmentPageReducer.createApp.visible,
  patients: appointmentPageReducer.createApp.searchPatients,
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
  changeCreateAppModalVisible,
  searchPatients,
  getPatient,
  changePatientSelected,
  changeCreateAppNote,
  changeCreateAppDuration,
  changeCreateAppDefaultDuration,
  changeCreateAppDoctor,
  changeCreateAppDefaultDoctor,
  changeCreateAppExpectedArrivalDate,
  changeCreateAppExpectedArrivalTime,
  createAppointment,
  changeCreateAppSpecialNote,
  checkConfirmButtonDisable,
  changeCreateAppPatientName,
  changeCreateAppPatientPhone,
  changeCreateAppPatientNationalId,
  changeCreateAppPatientBirth,
  createPatient,
  getAllEvents,
};

export default connect(mapStateToProps, mapDispatchToProps)(CreateAppModal);
