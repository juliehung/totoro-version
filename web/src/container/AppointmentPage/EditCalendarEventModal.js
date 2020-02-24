import { Modal, Button, TimePicker, DatePicker, Select, Input, message, Checkbox } from 'antd';
import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import styled from 'styled-components';
import { repeatedCalendarEvtDefault } from './constant';
import {
  changeEditCalModalVisible,
  changeEditCalEvtStartDate,
  changeEditCalEvtStartTime,
  changeEditCalEvtEndDate,
  changeEditCalEvtEndTime,
  changeEditCalEvtAllDay,
  changeEditCalEvtDoctor,
  changeEditCalEvtRepeat,
  changeEditCalEvtNote,
  checkEditCalEvtConfirmButtonDisable,
  getAllEvents,
  editCalendarEvt,
  deleteCalEvt,
  changeEditCalEvtConfirmDelete,
  changeEditCalEvtRepeatEndDate,
} from './actions';

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

const RowContainer = styled.div`
  display: flex;
  align-items: baseline;
`;

const DateContainer = styled.div`
  display: flex;
  align-items: center;
  margin-bottom: 10px;
`;

const StyledDatePicker = styled(DatePicker)`
  margin-right: 15px !important;
`;

const StyledTimePicker = styled(TimePicker)`
  margin-right: 15px !important;
`;

const StyledSelect = styled(Select)`
  min-width: 150px !important;
`;

const RequiredCol = styled.span`
  font-weight: bold;
  width: 100px;
  &:before {
    content: '* ';
  }
`;

const DeleteButton = styled(Button)`
  color: #fff !important;
  border: red 1px solid !important;
  background: red !important;
`;

const MarginSpan = styled.span`
  margin: 0 10px;
`;
//#endregion

function EditCalendarEventModal({
  doctors,
  calendarEvt,
  disabled,
  visible,
  loading,
  editCalEvtSuccess,
  deleteLoading,
  deleteSuccess,
  confirmDelete,
  changeEditCalEvtStartDate,
  changeEditCalEvtStartTime,
  changeEditCalEvtEndDate,
  changeEditCalEvtEndTime,
  changeEditCalEvtAllDay,
  changeEditCalEvtDoctor,
  changeEditCalEvtRepeat,
  changeEditCalEvtRepeatEndDate,
  changeEditCalEvtNote,
  checkEditCalEvtConfirmButtonDisable,
  getAllEvents,
  editCalendarEvt,
  deleteCalEvt,
  changeEditCalEvtConfirmDelete,
  changeEditCalModalVisible,
}) {
  useEffect(() => {
    if (editCalEvtSuccess) {
      getAllEvents();
      message.success('編輯休假成功');
      changeEditCalModalVisible(false);
    }
  }, [editCalEvtSuccess, getAllEvents, changeEditCalModalVisible]);

  useEffect(() => {
    if (deleteSuccess) {
      getAllEvents();
      message.success('刪除休假成功');
      changeEditCalModalVisible(false);
    }
  }, [deleteSuccess, getAllEvents, changeEditCalModalVisible]);

  useEffect(() => {
    changeEditCalEvtConfirmDelete(false);
    checkEditCalEvtConfirmButtonDisable();
  }, [calendarEvt, changeEditCalEvtConfirmDelete, checkEditCalEvtConfirmButtonDisable]);

  const handleConfirm = () => {
    editCalendarEvt();
  };

  const closeModal = () => {
    changeEditCalModalVisible(false);
  };

  const onDeleteConfirm = () => {
    deleteCalEvt(calendarEvt.id);
  };

  const onDeleteClick = () => {
    changeEditCalEvtConfirmDelete(true);
  };

  const onAllDayChange = e => {
    changeEditCalEvtAllDay(e.target.checked);
  };

  return (
    <Modal
      centered
      title={'編輯休假'}
      footer={null}
      visible={visible}
      onCancel={closeModal}
      closable={false}
      width={600}
      destroyOnClose
    >
      <Container>
        <InfoRowContainer>
          <RowContainer>
            <RequiredCol>時間：</RequiredCol>
            <div>
              <DateContainer>
                <StyledDatePicker
                  value={calendarEvt.startDate}
                  onChange={changeEditCalEvtStartDate}
                  allowClear={false}
                />
                <StyledTimePicker
                  format="HH:mm"
                  value={calendarEvt.startTime}
                  onChange={changeEditCalEvtStartTime}
                  allowClear={false}
                  disabled={calendarEvt.allDay}
                />
                <Checkbox onChange={onAllDayChange} checked={calendarEvt.allDay}>
                  全天
                </Checkbox>
              </DateContainer>
              <DateContainer>
                <StyledDatePicker value={calendarEvt.endDate} onChange={changeEditCalEvtEndDate} allowClear={false} />
                <StyledTimePicker
                  format="HH:mm"
                  value={calendarEvt.endTime}
                  onChange={changeEditCalEvtEndTime}
                  allowClear={false}
                  disabled={calendarEvt.allDay}
                />
              </DateContainer>
            </div>
          </RowContainer>
          <RowContainer>
            <RequiredCol>醫師：</RequiredCol>
            <span>
              <StyledSelect
                placeholder="請選擇醫師"
                value={calendarEvt.doctorId}
                onChange={changeEditCalEvtDoctor}
                disabled
              >
                {[...[{ id: 'none', name: '診所休假', activated: true }], ...doctors]
                  .filter(d => d.activated)
                  .map(d => (
                    <Select.Option key={d.id} value={d.id}>
                      {d.name}
                    </Select.Option>
                  ))}
              </StyledSelect>
            </span>
          </RowContainer>
          <RowContainer>
            <RequiredCol>重複：</RequiredCol>
            <StyledSelect
              placeholder="請選擇重複"
              defaultValue={'none'}
              value={calendarEvt.repeat}
              onChange={changeEditCalEvtRepeat}
            >
              {repeatedCalendarEvtDefault.map(d => (
                <Select.Option key={d.id} value={d.id}>
                  {d.value}
                </Select.Option>
              ))}
            </StyledSelect>
            <MarginSpan>至</MarginSpan>
            <DatePicker
              disabled={calendarEvt.repeat === 'none'}
              value={calendarEvt.repeatEndDate}
              onChange={changeEditCalEvtRepeatEndDate}
              allowClear={false}
            />
          </RowContainer>
          <div>
            <span>內容：</span>
            <span>
              <Input.TextArea
                autoSize={{ minRows: 3, maxRows: 3 }}
                value={calendarEvt.note}
                onChange={changeEditCalEvtNote}
              ></Input.TextArea>
            </span>
          </div>
        </InfoRowContainer>
        <BottomContainer>
          {confirmDelete ? (
            <DeleteButton icon="delete" loading={deleteLoading} onClick={onDeleteConfirm}>
              確定刪除?
            </DeleteButton>
          ) : (
            <Button icon="delete" loading={deleteLoading} onClick={onDeleteClick}>
              刪除休假
            </Button>
          )}
          <ButtonsContainer>
            <StyledButton onClick={closeModal}>取消</StyledButton>
            <StyledButton type="primary" onClick={handleConfirm} disabled={disabled} loading={loading}>
              確定
            </StyledButton>
          </ButtonsContainer>
        </BottomContainer>
      </Container>
    </Modal>
  );
}
const mapStateToProps = ({ appointmentPageReducer }) => ({
  doctors: appointmentPageReducer.calendar.doctors,
  calendarEvt: appointmentPageReducer.editCalendarEvt.event,
  disabled: appointmentPageReducer.editCalendarEvt.disabled,
  visible: appointmentPageReducer.editCalendarEvt.visible,
  loading: appointmentPageReducer.editCalendarEvt.loading,
  editCalEvtSuccess: appointmentPageReducer.editCalendarEvt.editCalEvtSuccess,
  deleteLoading: appointmentPageReducer.editCalendarEvt.deleteLoading,
  deleteSuccess: appointmentPageReducer.editCalendarEvt.deleteSuccess,
  confirmDelete: appointmentPageReducer.editCalendarEvt.confirmDelete,
});

const mapDispatchToProps = {
  changeEditCalModalVisible,
  changeEditCalEvtStartDate,
  changeEditCalEvtStartTime,
  changeEditCalEvtEndDate,
  changeEditCalEvtEndTime,
  changeEditCalEvtAllDay,
  changeEditCalEvtDoctor,
  changeEditCalEvtRepeat,
  changeEditCalEvtRepeatEndDate,
  changeEditCalEvtNote,
  checkEditCalEvtConfirmButtonDisable,
  getAllEvents,
  editCalendarEvt,
  deleteCalEvt,
  changeEditCalEvtConfirmDelete,
};

export default connect(mapStateToProps, mapDispatchToProps)(EditCalendarEventModal);
