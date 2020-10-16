import React, { useEffect, useState } from 'react';
import { Modal, Button, Select, Input, Checkbox } from 'antd';
// import { message } from 'antd';
import { connect } from 'react-redux';
import moment from 'moment';
import {
  changeCreateAppointmentModalVisible,
  changeCreateAppointmentNote,
  changeCreateAppointmenSpecialtNote,
  changeCreateAppointmentDuration,
  changeCreateAppointmentDoctor,
  changeCreateAppointmentExpectedArrivalTime,
  changeCreateAppointmentExpectedArrivalDate,
  checkConfirmButtonDisable,
  createAppointment,
  changeAppointmentColor,
} from './actions';
import styled from 'styled-components';
import { requiredTreatmentTimeDefault } from '../AppointmentPage/constant';
import { APPT_CUSTOM_COLORS } from '../AppointmentPage/constant';
import DatePicker from '../../component/DatePicker';
import { toRocString } from './utils';
import analysisAppointments from '../AppointmentPage/utils/analysisAppointments';
import extractDoctorsFromUser from '../../utils/extractDoctorsFromUser';
import { defaultTimeOption } from '../AppointmentPage/utils/generateDefaultTime';

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

function CreateAppointmentModal(props) {
  const {
    modalVisible,
    doctors,
    appointment,
    patient,
    disabled,
    changeCreateAppointmentModalVisible,
    changeCreateAppointmentDuration,
    createAppointment,
    changeCreateAppointmenSpecialtNote,
    checkConfirmButtonDisable,
    requiredTreatmentTime,
    // account,
    loading,
    appointmentsAnalysis,
    changeCreateAppointmentNote,
    changeCreateAppointmentDoctor,
    changeCreateAppointmentExpectedArrivalTime,
    changeCreateAppointmentExpectedArrivalDate,
    changeAppointmentColor,
    createAppointmentSuccess,
  } = props;

  const [expectedTimeOption, setExpectedTimeOption] = useState(defaultTimeOption);

  useEffect(() => {
    checkConfirmButtonDisable();
  }, [appointment, checkConfirmButtonDisable]);

  useEffect(() => {
    if (createAppointmentSuccess) {
      changeCreateAppointmentModalVisible(false);
    }
  }, [createAppointmentSuccess, changeCreateAppointmentModalVisible]);

  useEffect(() => {
    if (!appointment.duration) {
      changeCreateAppointmentDuration(requiredTreatmentTime);
    }
  }, [appointment, requiredTreatmentTime, changeCreateAppointmentDuration]);

  // useEffect(() => {
  //   if (account.data && account.data.authorities) {
  //     if (account.data.authorities[0].includes('DOCTOR')) {
  //       changeCreateAppDefaultDoctor(account.data.id);
  //     }
  //   }
  // }, [changeCreateAppDefaultDoctor, account]);

  useEffect(() => {
    const lastDoctor = appointmentsAnalysis?.lastDoctorId;
    if (!appointment.doctorId && lastDoctor) {
      changeCreateAppointmentDoctor(lastDoctor);
    }
  }, [changeCreateAppointmentDoctor, appointment, appointmentsAnalysis]);

  const closeModal = () => {
    changeCreateAppointmentModalVisible(false);
  };

  const options = [
    { label: 'micro', value: 'micro' },
    { label: '行動不便', value: 'baseFloor' },
  ];

  const handleConfirm = () => {
    createAppointment();
  };

  return (
    <Modal
      centered
      title={'建立預約'}
      footer={null}
      visible={modalVisible}
      onCancel={closeModal}
      maskClosable={false}
      closable={false}
      destroyOnClose
      width={620}
    >
      <Container>
        <PatientDetail>
          <PatientDetailCol>
            <PatientDetailElement>
              <span>{patient?.name}</span>
            </PatientDetailElement>
            <PatientDetailElement>
              <span>{toRocString(patient?.birth)}</span>
              <span>{patient?.birth ? ', ' : ''}</span>
              <span>{patient?.age}</span>
              <span>{patient?.age ? (patient?.gender ? ', ' : '') : ''}</span>
              <span>{`${patient?.gender ? patient?.gender : ''}`}</span>
            </PatientDetailElement>
            <PatientDetailElement>
              <span>
                {`預約: ${appointmentsAnalysis.appointment},
                      爽約: ${appointmentsAnalysis.noShow},
                      取消: ${appointmentsAnalysis.cancel}`}
              </span>
            </PatientDetailElement>
          </PatientDetailCol>
          <PatientDetailCol>
            <PatientDetailElement>
              <span>{`最近治療:  ${toRocString(appointmentsAnalysis.recentRegistration)}`}</span>
            </PatientDetailElement>
            <PatientDetailElement>
              <span>{`最近預約:  ${toRocString(appointmentsAnalysis.recentAppointment)}`}</span>
            </PatientDetailElement>
          </PatientDetailCol>
        </PatientDetail>
        <InfoRowContainer>
          <div>
            <RequiredCol>預約時間：</RequiredCol>
            <div style={{ marginRight: '10px' }}>
              <DatePicker
                date={appointment.expectedArrivalDate}
                onDateChange={changeCreateAppointmentExpectedArrivalDate}
                size="small"
                readOnly
              />
            </div>
            <StyledSelect
              placeholder="請選擇時間"
              value={appointment.expectedArrivalTime && moment(appointment.expectedArrivalTime).format('HHmm')}
              onChange={t => {
                changeCreateAppointmentExpectedArrivalTime(moment(t, 'HH:mm'));
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
            <StyledSelect defaultValue={0} onChange={changeAppointmentColor}>
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
            <StyledSelect
              placeholder="請選擇醫師"
              onSelect={changeCreateAppointmentDoctor}
              value={appointment.doctorId}
            >
              {doctors.map(d => (
                <Select.Option key={d.id} value={d.id}>
                  {d.name}
                </Select.Option>
              ))}
            </StyledSelect>
          </div>
          <div>
            <RequiredCol>治療長度：</RequiredCol>
            <StyledSelect
              placeholder="請選擇治療長度"
              onSelect={changeCreateAppointmentDuration}
              value={appointment.duration}
            >
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
                onChange={e => {
                  changeCreateAppointmentNote(e.target.value);
                }}
                value={appointment.note}
              />
            </Width100>
          </div>
          <div>
            <span>特殊註記：</span>
            <Checkbox.Group
              options={options}
              value={appointment.specialNote}
              onChange={changeCreateAppointmenSpecialtNote}
            />
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
const mapStateToProps = ({ patientPageReducer, homePageReducer }) => {
  return {
    modalVisible: patientPageReducer.createAppointment.modalVisible,
    patient: patientPageReducer.patient.patient,
    appointment: patientPageReducer.createAppointment.appointment,
    requiredTreatmentTime: homePageReducer.settings.settings?.preferences?.generalSetting?.requiredTreatmentTime,
    appointmentsAnalysis: analysisAppointments(patientPageReducer.appointment.appointment),
    doctors: extractDoctorsFromUser(homePageReducer.user.users)?.filter(d => d.activated) ?? [],
    disabled: patientPageReducer.createAppointment.disabled,
    account: homePageReducer.account,
    loading: patientPageReducer.createAppointment.loading,
    createAppointmentSuccess: patientPageReducer.createAppointment.createSuccess,
  };
};

const mapDispatchToProps = {
  changeCreateAppointmentModalVisible,
  changeCreateAppointmentNote,
  changeCreateAppointmenSpecialtNote,
  changeCreateAppointmentDuration,
  changeCreateAppointmentDoctor,
  changeCreateAppointmentExpectedArrivalTime,
  changeCreateAppointmentExpectedArrivalDate,
  checkConfirmButtonDisable,
  createAppointment,
  changeAppointmentColor,
};

export default connect(mapStateToProps, mapDispatchToProps)(CreateAppointmentModal);
