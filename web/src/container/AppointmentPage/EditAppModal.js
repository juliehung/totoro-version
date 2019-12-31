import { Modal, Button, TimePicker, DatePicker, Select, Input, Spin, message, Checkbox } from 'antd';
import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import {
  changeEditAppModalVisible,
  deleteAppointment,
  changeEditAppNote,
  changeEditAppDuration,
  changeEditAppDoctor,
  changeEditAppExpectedArrivalDate,
  changeEditAppExpectedArrivalTime,
  editAppointment,
  changeEditAppSpecialNote,
  checkEditAppConfirmButtonDisable,
  getAllEvents,
  changeEditAppointmentConformDelete,
} from './actions';
import styled from 'styled-components';
import { requiredTreatmentTimeDefault } from './constant';
import moment from 'moment';

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

const DeleteButton = styled(Button)`
  color: #fff !important;
  border: red 1px solid !important;
  background: red !important;
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

function EditAppModal({
  visible,
  appointment,
  patient,
  deleteLoading,
  deleteAppSuccess,
  doctors,
  disabled,
  loading,
  editAppSuccess,
  confirmDelete,
  changeEditAppModalVisible,
  deleteAppointment,
  changeEditAppNote,
  changeEditAppDuration,
  changeEditAppDoctor,
  changeEditAppExpectedArrivalDate,
  changeEditAppExpectedArrivalTime,
  changeEditAppSpecialNote,
  checkEditAppConfirmButtonDisable,
  editAppointment,
  getAllEvents,
  changeEditAppointmentConformDelete,
}) {
  useEffect(() => {
    if (deleteAppSuccess) {
      message.success('刪除預約成功');
      getAllEvents();
      changeEditAppModalVisible(false);
    }
  }, [deleteAppSuccess, getAllEvents, changeEditAppModalVisible]);

  useEffect(() => {
    if (editAppSuccess) {
      message.success('編輯預約成功');
      getAllEvents();
      changeEditAppModalVisible(false);
    }
  }, [editAppSuccess, getAllEvents, changeEditAppModalVisible]);

  useEffect(() => {
    checkEditAppConfirmButtonDisable();
    changeEditAppointmentConformDelete(false);
  }, [appointment, checkEditAppConfirmButtonDisable, changeEditAppointmentConformDelete]);

  const closeModal = () => {
    changeEditAppModalVisible(false);
  };

  const handleConfirm = () => {
    const appt = {
      id: appointment.id,
      expectedArrivalTime: moment(
        appointment.expectedArrivalDate.format('YYYY-MM-DD') + ' ' + appointment.expectedArrivalTime.format('HH:mm'),
        'YYYY-MM-DD HH:mm',
      ).toISOString(),
      doctor: { id: appointment.doctorId },
      requiredTreatmentTime: appointment.requiredTreatmentTime,
      note: appointment.note,
      microscope: appointment.specialNote.includes('micro'),
      baseFloor: appointment.specialNote.includes('baseFloor'),
    };

    editAppointment(appt);
  };

  const options = [
    { label: 'micro', value: 'micro' },
    { label: '行動不便', value: 'baseFloor' },
  ];

  const onSpecialNoteChange = value => {
    changeEditAppSpecialNote(value);
  };

  const onDeleteConfirm = () => {
    deleteAppointment(appointment.id);
  };

  const onDeleteClick = () => {
    changeEditAppointmentConformDelete(true);
  };

  return (
    <Modal
      centered
      title={'編輯預約'}
      footer={null}
      visible={visible}
      onCancel={closeModal}
      closable={false}
      zIndex={1033}
      destroyOnClose
    >
      <Container>
        <Spin spinning={patient === undefined}>
          <PatientDetail>
            <PatientDetailCol>
              <PatientDetailElement>
                <span>{patient && patient.name}</span>
              </PatientDetailElement>
              <PatientDetailElement>
                <span>{patient && patient.age}</span>
                <span>{patient ? (patient.age ? (patient.gender ? ', ' : '') : '') : ''}</span>
                <span>{patient && `${patient.gender ? patient.gender : ''}`}</span>
              </PatientDetailElement>
              <PatientDetailElement>
                <span>
                  {patient &&
                    `預約: ${patient.appointmentsAnalysis.appointment},
                      爽約: ${patient.appointmentsAnalysis.noShow},
                      取消: ${patient.appointmentsAnalysis.cancel}`}
                </span>
              </PatientDetailElement>
            </PatientDetailCol>
            <PatientDetailCol>
              <PatientDetailElement>
                <span>{patient && `最近治療:  ${patient.appointmentsAnalysis.recentRegistration}`}</span>
              </PatientDetailElement>
              <PatientDetailElement>
                <span>{patient && `最近預約:  ${patient.appointmentsAnalysis.recentAppointment}`}</span>
              </PatientDetailElement>
            </PatientDetailCol>
          </PatientDetail>
        </Spin>
        <InfoRowContainer>
          <div>
            <RequiredCol>預約時間：</RequiredCol>
            <span>
              <StyledDatePicker
                onChange={changeEditAppExpectedArrivalDate}
                value={appointment.expectedArrivalDate}
                allowClear={false}
              />
              <TimePicker
                format="HH:mm"
                onChange={changeEditAppExpectedArrivalTime}
                value={appointment.expectedArrivalTime}
                allowClear={false}
              />
            </span>
          </div>
          <div>
            <RequiredCol>主治醫師：</RequiredCol>
            <span>
              <StyledSelect placeholder="請選擇醫師" onSelect={changeEditAppDoctor} value={appointment.doctorId}>
                {doctors.map(d => (
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
                onSelect={changeEditAppDuration}
                value={appointment.requiredTreatmentTime}
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
                onChange={changeEditAppNote}
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
          {confirmDelete ? (
            <DeleteButton icon="delete" loading={deleteLoading} onClick={onDeleteConfirm}>
              確定刪除?
            </DeleteButton>
          ) : (
            <Button onClick={onDeleteClick}>刪除預約</Button>
          )}
          <ButtonsContainer>
            <StyledButton onClick={closeModal}>取消</StyledButton>
            <StyledButton type="primary" onClick={handleConfirm} disabled={disabled} loading={loading}>
              儲存
            </StyledButton>
          </ButtonsContainer>
        </BottomContainer>
      </Container>
    </Modal>
  );
}
const mapStateToProps = ({ appointmentPageReducer }) => ({
  visible: appointmentPageReducer.editApp.visible,
  appointment: appointmentPageReducer.editApp.appointment,
  patient: appointmentPageReducer.editApp.patient,
  deleteLoading: appointmentPageReducer.editApp.deleteLoading,
  deleteAppSuccess: appointmentPageReducer.editApp.deleteAppSuccess,
  doctors: appointmentPageReducer.calendar.doctors,
  disabled: appointmentPageReducer.editApp.disabled,
  loading: appointmentPageReducer.editApp.loading,
  editAppSuccess: appointmentPageReducer.editApp.editAppSuccess,
  confirmDelete: appointmentPageReducer.editApp.confirmDelete,
});

const mapDispatchToProps = {
  changeEditAppModalVisible,
  deleteAppointment,
  changeEditAppNote,
  changeEditAppDuration,
  changeEditAppDoctor,
  changeEditAppExpectedArrivalDate,
  changeEditAppExpectedArrivalTime,
  changeEditAppSpecialNote,
  checkEditAppConfirmButtonDisable,
  editAppointment,
  getAllEvents,
  changeEditAppointmentConformDelete,
};

export default connect(mapStateToProps, mapDispatchToProps)(EditAppModal);
