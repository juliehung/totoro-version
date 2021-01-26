import { Modal, Button, Select, Input, Spin, message, Checkbox, Empty, AutoComplete, Tooltip } from 'antd';
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
  changeCreateAppSpecialNoteAddFirstVisit,
  changeCreateAppSpecialNoteRemoveFirstVisit,
  checkConfirmButtonDisable,
  changeCreateAppPatientName,
  changeCreateAppPatientPhone,
  changeCreateAppPatientNationalId,
  changeCreateAppPatientBirth,
  createPatient,
  getAllEvents,
  changePatientSearchMode,
  getExistNationalId,
} from './actions';
import styled from 'styled-components';
import { requiredTreatmentTimeDefault, patientSearchMode, APPT_CUSTOM_COLORS } from './constant';
import GAHelper from '../../ga';
import extractDoctorsFromUser from '../../utils/extractDoctorsFromUser';
import { defaultTimeOption } from './utils/generateDefaultTime';
import { appointmentPage } from './';
import parseDateToString from './utils/parseDateToString';
import DatePicker from '../../component/DatePicker';
import { parsePatientNameWithVipMark } from '../../utils/patientHelper';
import { convertAppointmentToCardObject } from '../PatientPage/utils';
import PatientAppointmentPopover from './PatientAppointmentPopover';
import AlertCircleFill from '../../images/alert-circle-fill.svg';

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

const StyledSearchSelect = styled(AutoComplete)`
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
  position: relative;
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

  .nationalId-exist-alert-wrap {
    position: absolute;
    width: 25px;
    height: 25px;
    right: 0;
    top: 50%;
    transform: translate(0, -50%);
    display: ${props => (props.isPatientExist ? 'block' : 'none')};
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

const Width100 = styled.div`
  width: 100%;
`;

//#endregion

function CreateAppModal({
  visible,
  patients,
  patientSelected,
  selectedPatient,
  selectedPatientAppointments,
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
  changeCreateAppSpecialNoteAddFirstVisit,
  changeCreateAppSpecialNoteRemoveFirstVisit,
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
  getExistNationalId,
  isPatientExist = false,
}) {
  const [expectedTimeOption, setExpectedTimeOption] = useState(defaultTimeOption);
  const { name, phone, nationalId, birth } = patient;
  const [openControl, setOpenControl] = useState(false);
  useEffect(() => {
    if (createAppSuccess) {
      getAllEvents();
      message.success('新增預約成功');
      changeCreateAppModalVisible(false);
    }
  }, [createAppSuccess, getAllEvents, changeCreateAppModalVisible]);

  useEffect(() => {
    checkConfirmButtonDisable();
  }, [appointment, patient, checkConfirmButtonDisable, isPatientExist]);

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

  useEffect(() => {
    if (
      !selectedPatient &&
      ((name && name !== '') || (phone && phone !== '') || (nationalId && nationalId !== '') || birth)
    ) {
      changeCreateAppSpecialNoteAddFirstVisit(['firstVisit']);
    } else {
      changeCreateAppSpecialNoteRemoveFirstVisit();
    }
  }, [
    name,
    phone,
    nationalId,
    birth,
    selectedPatient,
    changeCreateAppSpecialNoteAddFirstVisit,
    changeCreateAppSpecialNoteRemoveFirstVisit,
  ]);

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

  const onSearchTextFocus = e => {
    setOpenControl(true);
    const value = e?.target?.value;
    clearTimeout(debounceSearch);
    debounceSearch = setTimeout(() => {
      searchPatients(value);
    }, 300);
  };

  const onPatientSelect = id => {
    getPatient(id);
    changePatientSelected(true);
    setOpenControl(false);
  };

  const handleConfirm = () => {
    GAHelper.event(appointmentPage, 'Create appointment click');
    if (appointment.patientId) {
      createAppointment();
    } else {
      createPatient();
    }
  };

  const options = [
    { label: '初診病患', value: 'firstVisit' },
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
  patients = patients.map(patient => {
    const { id, medicalId, name, vipPatient } = patient;
    return { id, name, value: `${parsePatientNameWithVipMark(vipPatient, name)}, ${medicalId}` };
  });

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
            showArrow={true}
            showSearch={true}
            filterOption={false}
            options={patients}
            placeholder={searchPlaceholder}
            onSearch={onSearchTextChange}
            onFocus={onSearchTextFocus}
            onSelect={(data, { id = null }) => onPatientSelect(id)}
            open={openControl}
            onBlur={() => setOpenControl(false)}
            onChange={() => setOpenControl(true)}
            notFoundContent={<Empty description="沒有資料" />}
          >
            {patients.map(({ medicalId, name, id, vipPatient }) => (
              <Select.Option key={id} value={id}>
                {`${parsePatientNameWithVipMark(vipPatient, name)}, ${medicalId}`}
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
                <BoldSpan>電話：</BoldSpan>
                <Input onChange={onChangePatientPhone} value={patient.phone} placeholder="(必填)" />
              </NewPatientElement>
            </NewPatientRow>
            <NewPatientRow>
              <NewPatientElement isPatientExist={isPatientExist}>
                <span>身分證號：</span>
                <Input
                  onChange={onChangePatientNationalId}
                  onBlur={() =>
                    patient?.nationalId && patient.nationalId.length !== 0 && getExistNationalId(patient.nationalId)
                  }
                  value={patient.nationalId}
                />
                <div className="nationalId-exist-alert-wrap">
                  <Tooltip placement="top" title={<span>身分證號重複</span>}>
                    <img src={AlertCircleFill} alt="alert-circle-fill" />
                  </Tooltip>
                </div>
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
                  size={'small'}
                />
              </NewPatientElement>
            </NewPatientRow>
          </NewPatientContainer>
        )}
        {patientSelected && (
          <Spin spinning={selectedPatient === undefined}>
            <PatientDetail>
              <PatientDetailCol>
                <PatientDetailElement>
                  <span>
                    {selectedPatient && parsePatientNameWithVipMark(selectedPatient?.vipPatient, selectedPatient?.name)}
                  </span>
                </PatientDetailElement>
                <PatientDetailElement>
                  <span>{selectedPatient && parseDateToString(selectedPatient.birth)}</span>
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
                      `最近治療:  ${parseDateToString(selectedPatient.appointmentsAnalysis.recentRegistration)}`}
                  </span>
                </PatientDetailElement>
                <PatientDetailElement>
                  <span>
                    {selectedPatient &&
                      `即將到來:  ${parseDateToString(selectedPatient.appointmentsAnalysis.recentAppointment)}`}
                    <PatientAppointmentPopover
                      patient={selectedPatient}
                      patientAppointments={selectedPatientAppointments}
                    />
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
            <Width100>
              <Input.TextArea
                autoSize={{ minRows: 3, maxRows: 3 }}
                onChange={changeCreateAppNote}
                value={appointment.note}
              />
            </Width100>
          </div>
          <div>
            <span>預約註記：</span>
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
  selectedPatientAppointments: convertAppointmentToCardObject(
    appointmentPageReducer.createApp?.selectedPatient?.appointments,
    homePageReducer.user.users,
  ).filter(a => (a.isFuture && !a.isRegistration) || a.isCancel),
  doctors: extractDoctorsFromUser(homePageReducer.user.users),
  appointment: appointmentPageReducer.createApp.appointment,
  patient: appointmentPageReducer.createApp.patient,
  createAppSuccess: appointmentPageReducer.createApp.createAppSuccess,
  disabled: appointmentPageReducer.createApp.disabled,
  requiredTreatmentTime: homePageReducer.settings.settings?.preferences?.generalSetting?.requiredTreatmentTime,
  account: homePageReducer.account,
  loading: appointmentPageReducer.createApp.loading,
  isPatientExist: appointmentPageReducer.createApp.isPatientExist,
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
  changeCreateAppSpecialNoteAddFirstVisit,
  changeCreateAppSpecialNoteRemoveFirstVisit,
  checkConfirmButtonDisable,
  changeCreateAppPatientName,
  changeCreateAppPatientPhone,
  changeCreateAppPatientNationalId,
  changeCreateAppPatientBirth,
  createPatient,
  getAllEvents,
  changePatientSearchMode,
  getExistNationalId,
};

export default connect(mapStateToProps, mapDispatchToProps)(CreateAppModal);
