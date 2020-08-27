import { Modal, Button, Select, Input, Spin, message, Checkbox, Empty } from 'antd';
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
  changeCreateAppColor,
  createAppointment,
  changeCreateAppSpecialNote,
  checkConfirmButtonDisable,
  changeCreateAppPatientName,
  changeCreateAppPatientPhone,
  changeCreateAppPatientNationalId,
  changeCreateAppPatientBirth,
  createPatient,
  getAllEvents,
  changePatientSearchMode,
} from './actions';
import styled from 'styled-components';
import { requiredTreatmentTimeDefault, patientSearchMode, APPT_CUSTOM_COLORS } from './constant';
import { GAevent } from '../../ga';
import extractDoctorsFromUser from '../../utils/extractDoctorsFromUser';
import { defaultTimeOption } from './utils/generateDefaultTime';
import { appointmentPage } from './';
import parseDateToString from './utils/parseDateToString';
import DatePicker from '../../component/DatePicker';

const { Option } = Select;

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
    display: flex;
    align-items: center;
    & > span {
      flex-shrink: 0;
      width: 86px;
    }
  }
`;

const StyledSelect = styled(Select)`
  min-width: 150px !important;
  &:not(:last-child) {
    margin-right: 10px !important;
  }
`;

const RequiredCol = styled.span`
  font-weight: bold;
  &:before {
    content: '* ';
  }
`;

const StyledSearchSelect = styled(Select)`
  flex-grow: 1;
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
  justify-content: center;
  padding: 0 3px;
  & > :nth-child(1) {
    flex-shrink: 0;
  }
  & > :nth-child(2) {
    width: 145px;
  }
  &:nth-child(n + 1) {
    & > span {
      width: 75px;
    }
  }
  &:nth-child(2n) {
    & > span {
      width: 50px;
    }
  }
`;

const BoldSpan = styled.span`
  font-weight: bold;
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

const ColorOptionContainer = styled.div`
  display: flex;
  align-items: center;
  & > div:first-child {
    width: 15px;
    height: 15px;
    border-radius: 50%;
    background-color: ${props => props.color};
    margin-right: 5px;
  }
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
  changeCreateAppColor,
  createAppointment,
  changeCreateAppSpecialNote,
  checkConfirmButtonDisable,
  changeCreateAppPatientName,
  changeCreateAppPatientPhone,
  changeCreateAppPatientNationalId,
  changeCreateAppPatientBirth,
  createPatient,
  getAllEvents,
  requiredTreatmentTime,
  account,
  loading,
  searchMode,
  changePatientSearchMode,
  isRoc,
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
    if (requiredTreatmentTime) {
      changeCreateAppDefaultDuration(requiredTreatmentTime);
    }
  }, [requiredTreatmentTime, changeCreateAppDefaultDuration]);

  useEffect(() => {
    if (account.data && account.data.authorities) {
      if (account.data.authorities[0].includes('DOCTOR')) {
        changeCreateAppDefaultDoctor(account.data.id);
      }
    }
  }, [changeCreateAppDefaultDoctor, account]);

  useEffect(() => {
    const lastDoctor = selectedPatient?.appointmentsAnalysis?.lastDoctorId;
    if (!appointment.doctorId && lastDoctor) {
      changeCreateAppDoctor(lastDoctor);
    }
  }, [selectedPatient, changeCreateAppDoctor, appointment.doctorId]);

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
    GAevent(appointmentPage, 'Create appointment click');
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

  const searchPlaceholderBase = '搜尋複診病患';
  let searchPlaceholder;
  switch (searchMode) {
    case patientSearchMode.name:
      searchPlaceholder = `${searchPlaceholderBase}姓名`;
      break;
    case patientSearchMode.birth:
      searchPlaceholder = `${searchPlaceholderBase}生日民國年月日`;
      break;
    case patientSearchMode.phone:
      searchPlaceholder = `${searchPlaceholderBase}聯絡電話`;
      break;
    case patientSearchMode.medical_id:
      searchPlaceholder = `${searchPlaceholderBase}病歷編號`;
      break;
    case patientSearchMode.national_id:
      searchPlaceholder = `${searchPlaceholderBase}身分證號`;
      break;
    default:
      searchPlaceholder = searchPlaceholderBase;
      break;
  }

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
      width={620}
    >
      <Container>
        <div style={{ display: 'flex' }}>
          <StyledSearchSelect
            showSearch
            placeholder={searchPlaceholder}
            filterOption={false}
            onSearch={onSearchTextChange}
            onFocus={onSearchTextFocus}
            onSelect={onPatientSelect}
            notFoundContent={<Empty description="沒有資料" />}
          >
            {patients.map(({ medicalId, name, id }) => (
              <Select.Option key={medicalId} value={id}>
                {`${name}, ${medicalId}`}
              </Select.Option>
            ))}
          </StyledSearchSelect>
          <Select value={searchMode} style={{ width: 120 }} onChange={changePatientSearchMode}>
            <Option value={patientSearchMode.name}>姓名</Option>
            <Option value={patientSearchMode.birth}>生日</Option>
            <Option value={patientSearchMode.phone}>聯絡電話</Option>
            <Option value={patientSearchMode.medical_id}>病歷編號</Option>
            <Option value={patientSearchMode.national_id}>身分證號</Option>
          </Select>
        </div>

        {!patientSelected && (
          <NewPatientContainer>
            <NewPatientRow>
              <NewPatientElement>
                <BoldSpan>新病患名：</BoldSpan>
                <Input onChange={onChangePatientName} value={patient.name} placeholder="(必填)" />
              </NewPatientElement>
              <NewPatientElement>
                <span>生日：</span>
                <DatePicker
                  onDateChange={changeCreateAppPatientBirth}
                  date={patient.birth}
                  placeholder="請輸入生日"
                  readOnly
                  upperYearLimit={0}
                  lowerYearLimit={120}
                />
              </NewPatientElement>
            </NewPatientRow>
            <NewPatientRow>
              <NewPatientElement>
                <span>身分證號：</span>
                <Input onChange={onChangePatientNationalId} value={patient.nationalId} />
              </NewPatientElement>
              <NewPatientElement>
                <BoldSpan>電話：</BoldSpan>
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
                  <span>{selectedPatient && parseDateToString(selectedPatient.birth, isRoc)}</span>
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
                    {selectedPatient &&
                      `最近治療:  ${parseDateToString(selectedPatient.appointmentsAnalysis.recentRegistration, isRoc)}`}
                  </span>
                </PatientDetailElement>
                <PatientDetailElement>
                  <span>
                    {selectedPatient &&
                      `最近預約:  ${parseDateToString(selectedPatient.appointmentsAnalysis.recentAppointment, isRoc)}`}
                  </span>
                </PatientDetailElement>
              </PatientDetailCol>
            </PatientDetail>
          </Spin>
        )}
        <InfoRowContainer>
          <div>
            <RequiredCol>預約時間：</RequiredCol>
            <div style={{ marginRight: '10px' }}>
              <DatePicker
                date={appointment.expectedArrivalDate}
                onDateChange={changeCreateAppExpectedArrivalDate}
                size="small"
                readOnly
              />
            </div>
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
                const key = t.format('HHmm');
                const time = t.format('HH:mm');

                return (
                  <Select.Option key={key} value={key}>
                    {time}
                  </Select.Option>
                );
              })}
            </StyledSelect>
            <StyledSelect defaultValue={0} onChange={changeCreateAppColor}>
              {APPT_CUSTOM_COLORS.map(c => {
                return (
                  <Select.Option key={c.id} value={c.id}>
                    <ColorOptionContainer color={c.color}>
                      <div />
                      <span> {c.text}</span>
                    </ColorOptionContainer>
                  </Select.Option>
                );
              })}
            </StyledSelect>
          </div>
          <div>
            <RequiredCol>主治醫師：</RequiredCol>
            <StyledSelect placeholder="請選擇醫師" onSelect={changeCreateAppDoctor} value={appointment.doctorId}>
              {doctors
                .filter(d => d.activated)
                .map(d => (
                  <Select.Option key={d.id} value={d.id} disabled={!d.activated}>
                    {d.name}
                  </Select.Option>
                ))}
            </StyledSelect>
          </div>
          <div>
            <RequiredCol>治療長度：</RequiredCol>
            <StyledSelect placeholder="請選擇治療長度" onSelect={changeCreateAppDuration} value={appointment.duration}>
              {requiredTreatmentTimeDefault.map(t => (
                <Select.Option key={t} value={t}>
                  {t}
                </Select.Option>
              ))}
            </StyledSelect>
          </div>
          <div>
            <span>預約內容：</span>
            <Input.TextArea
              autoSize={{ minRows: 3, maxRows: 3 }}
              onChange={changeCreateAppNote}
              value={appointment.note}
            />
          </div>
          <div>
            <span>特殊註記：</span>
            <Checkbox.Group options={options} value={appointment.specialNote} onChange={onSpecialNoteChange} />
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
  searchMode: appointmentPageReducer.createApp.searchMode,
  patients: appointmentPageReducer.createApp.searchPatients,
  patientSelected: appointmentPageReducer.createApp.patientSelected,
  selectedPatient: appointmentPageReducer.createApp.selectedPatient,
  doctors: extractDoctorsFromUser(homePageReducer.user.users),
  appointment: appointmentPageReducer.createApp.appointment,
  patient: appointmentPageReducer.createApp.patient,
  createAppSuccess: appointmentPageReducer.createApp.createAppSuccess,
  disabled: appointmentPageReducer.createApp.disabled,
  requiredTreatmentTime: homePageReducer.settings.settings?.preferences?.generalSetting?.requiredTreatmentTime,
  account: homePageReducer.account,
  loading: appointmentPageReducer.createApp.loading,
  isRoc: homePageReducer.settings.isRoc,
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
  changeCreateAppColor,
  createAppointment,
  changeCreateAppSpecialNote,
  checkConfirmButtonDisable,
  changeCreateAppPatientName,
  changeCreateAppPatientPhone,
  changeCreateAppPatientNationalId,
  changeCreateAppPatientBirth,
  createPatient,
  getAllEvents,
  changePatientSearchMode,
};

export default connect(mapStateToProps, mapDispatchToProps)(CreateAppModal);
