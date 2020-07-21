import { Modal, Button, DatePicker, Select, Input, Spin, message, Checkbox } from 'antd';
import React, { useEffect, useState } from 'react';
import { connect } from 'react-redux';
import {
  changeEditAppModalVisible,
  deleteAppointment,
  changeEditAppNote,
  changeEditAppDuration,
  changeEditAppDoctor,
  changeEditAppExpectedArrivalDate,
  changeEditAppExpectedArrivalTime,
  changeEditAppColor,
  editAppointment,
  changeEditAppSpecialNote,
  checkEditAppConfirmButtonDisable,
  getAllEvents,
  changeEditAppointmentConformDelete,
} from './actions';
import styled from 'styled-components';
import { requiredTreatmentTimeDefault, APPT_CUSTOM_COLORS } from './constant';
import moment from 'moment';
import extractDoctorsFromUser from '../../utils/extractDoctorsFromUser';
import { defaultTimeOption } from './utils/generateDefaultTime';
import { GAevent } from '../../ga';
import { appointmentPage } from './';
import { DeleteOutlined } from '@ant-design/icons';

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

const PreDeleteButton = styled(Button)`
  &:hover {
    color: red !important;
    border-color: red !important;
  }
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
    display: flex;
    align-items: center;
    & > span {
      flex-shrink: 0;
      width: 86px;
    }
  }
`;

const StyledDatePicker = styled(DatePicker)`
  margin-right: 10px !important;
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
  changeEditAppColor,
  changeEditAppSpecialNote,
  checkEditAppConfirmButtonDisable,
  editAppointment,
  getAllEvents,
  changeEditAppointmentConformDelete,
}) {
  const [expectedTimeOption, setExpectedTimeOption] = useState(defaultTimeOption);

  useEffect(() => {
    if (deleteAppSuccess) {
      message.success('刪除預約成功');
      getAllEvents();
      changeEditAppModalVisible(false);
    }
  }, [deleteAppSuccess, getAllEvents, changeEditAppModalVisible]);

  useEffect(() => {
    if (editAppSuccess) {
      changeEditAppModalVisible(false);
      message.success('編輯預約成功');
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
      colorId: appointment.colorId,
    };

    editAppointment(appt);
    GAevent(appointmentPage, 'Edit appointment click');
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
      maskClosable={false}
      closable={false}
      zIndex={1033}
      destroyOnClose
      width={620}
    >
      <Container>
        <Spin spinning={patient === undefined}>
          <PatientDetail>
            <PatientDetailCol>
              <PatientDetailElement>
                <span>{patient && patient.name}</span>
              </PatientDetailElement>
              <PatientDetailElement>
                <span>{patient && (patient.birth ? patient.birth : '')}</span>
                <span>{patient && patient.birth ? ', ' : ''}</span>
                <span>{patient && patient.age}</span>
                <span>{patient && (patient.age ? (patient.gender ? ', ' : '') : '')}</span>
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
            <StyledDatePicker
              onChange={changeEditAppExpectedArrivalDate}
              value={appointment.expectedArrivalDate}
              allowClear={false}
            />
            <StyledSelect
              placeholder="請選擇時間"
              value={appointment.expectedArrivalTime && moment(appointment.expectedArrivalTime).format('HHmm')}
              onChange={t => {
                changeEditAppExpectedArrivalTime(moment(t, 'HH:mm'));
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
            <StyledSelect value={appointment.colorId ?? 0} onChange={changeEditAppColor}>
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
            <StyledSelect placeholder="請選擇醫師" onSelect={changeEditAppDoctor} value={appointment.doctorId}>
              {doctors.map(d => (
                <Select.Option key={d.id} value={d.id} disabled={!d.activated}>
                  {d.name}
                </Select.Option>
              ))}
            </StyledSelect>
          </div>
          <div>
            <RequiredCol>治療長度：</RequiredCol>
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
          </div>
          <div>
            <span>預約內容：</span>
            <Input.TextArea
              autoSize={{ minRows: 3, maxRows: 3 }}
              onChange={changeEditAppNote}
              value={appointment.note}
            />
          </div>
          <div>
            <span>特殊註記：</span>
            <Checkbox.Group options={options} value={appointment.specialNote} onChange={onSpecialNoteChange} />
          </div>
        </InfoRowContainer>
        <BottomContainer>
          {confirmDelete ? (
            <DeleteButton icon={<DeleteOutlined />} loading={deleteLoading} onClick={onDeleteConfirm}>
              確定刪除?
            </DeleteButton>
          ) : (
            <PreDeleteButton onClick={onDeleteClick}>刪除預約</PreDeleteButton>
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
const mapStateToProps = ({ appointmentPageReducer, homePageReducer }) => ({
  visible: appointmentPageReducer.editApp.visible,
  appointment: appointmentPageReducer.editApp.appointment,
  patient: appointmentPageReducer.editApp.patient,
  deleteLoading: appointmentPageReducer.editApp.deleteLoading,
  deleteAppSuccess: appointmentPageReducer.editApp.deleteAppSuccess,
  doctors: extractDoctorsFromUser(homePageReducer.user.users),
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
  changeEditAppColor,
  changeEditAppSpecialNote,
  checkEditAppConfirmButtonDisable,
  editAppointment,
  getAllEvents,
  changeEditAppointmentConformDelete,
};

export default connect(mapStateToProps, mapDispatchToProps)(EditAppModal);
