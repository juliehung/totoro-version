import { Modal, Button, TimePicker, DatePicker, Select, Input, message, Checkbox } from 'antd';
import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import styled from 'styled-components';
import { repeatedCalendarEvtDefault } from './constant';
import {
  changeCreateCalEvtStartDate,
  changeCreateCalEvtStartTime,
  changeCreateCalEvtEndDate,
  changeCreateCalEvtEndTime,
  changeCreateCalEvtAllDay,
  changeCreateCalEvtDoctor,
  changeCreateCalEvtRepeat,
  changeCreateCalEvtNote,
  checkCreateCalEvtConfirmButtonDisable,
  changeCreateCalModalVisible,
  createCalEvt,
  getAllEvents,
  changeCreateCalEvtRepeatEndDate,
} from './actions';
import extractDoctorsFromUser from '../../utils/extractDoctorsFromUser';

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
  width: 80px;
  &:before {
    content: '* ';
  }
`;

const MarginSpan = styled.span`
  margin: 0 10px;
`;
//#endregion

function CreateCalendarEventModal({
  doctors,
  calendarEvt,
  disabled,
  visible,
  loading,
  createCalEvtSuccess,
  changeCreateCalEvtStartDate,
  changeCreateCalEvtStartTime,
  changeCreateCalEvtEndDate,
  changeCreateCalEvtEndTime,
  changeCreateCalEvtAllDay,
  changeCreateCalEvtDoctor,
  changeCreateCalEvtRepeat,
  changeCreateCalEvtNote,
  checkCreateCalEvtConfirmButtonDisable,
  changeCreateCalModalVisible,
  createCalEvt,
  getAllEvents,
  changeCreateCalEvtRepeatEndDate,
}) {
  useEffect(() => {
    if (createCalEvtSuccess) {
      getAllEvents();
      message.success('新增休假成功');
      changeCreateCalModalVisible(false);
    }
  }, [createCalEvtSuccess, getAllEvents, changeCreateCalModalVisible]);

  useEffect(() => {
    checkCreateCalEvtConfirmButtonDisable();
  }, [calendarEvt, checkCreateCalEvtConfirmButtonDisable]);

  const handleConfirm = () => {
    createCalEvt();
  };

  const closeModal = () => {
    changeCreateCalModalVisible(false);
  };

  const onAllDayChange = e => {
    changeCreateCalEvtAllDay(e.target.checked);
  };

  return (
    <Modal
      centered
      title={'建立休假'}
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
                  onChange={changeCreateCalEvtStartDate}
                  allowClear
                  placeholder="請選擇日期"
                />
                <StyledTimePicker
                  format="HH:mm"
                  value={calendarEvt.startTime}
                  onChange={changeCreateCalEvtStartTime}
                  allowClear
                  placeholder="請選擇時間"
                  disabled={calendarEvt.allDay}
                />
                <Checkbox onChange={onAllDayChange}>全天</Checkbox>
              </DateContainer>
              <DateContainer>
                <StyledDatePicker
                  value={calendarEvt.endDate}
                  onChange={changeCreateCalEvtEndDate}
                  allowClear
                  placeholder="請選擇日期"
                />
                <StyledTimePicker
                  format="HH:mm"
                  value={calendarEvt.endTime}
                  onChange={changeCreateCalEvtEndTime}
                  allowClear
                  placeholder="請選擇時間"
                  disabled={calendarEvt.allDay}
                />
              </DateContainer>
            </div>
          </RowContainer>
          <RowContainer>
            <RequiredCol>醫師：</RequiredCol>
            <span>
              <StyledSelect placeholder="請選擇醫師" value={calendarEvt.doctorId} onChange={changeCreateCalEvtDoctor}>
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
              onChange={changeCreateCalEvtRepeat}
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
              onChange={changeCreateCalEvtRepeatEndDate}
            />
          </RowContainer>
          <div>
            <span>內容：</span>
            <span>
              <Input.TextArea
                autoSize={{ minRows: 3, maxRows: 3 }}
                value={calendarEvt.note}
                onChange={changeCreateCalEvtNote}
              />
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
  doctors: extractDoctorsFromUser(homePageReducer.user.users),
  calendarEvt: appointmentPageReducer.createCalendarEvt.event,
  disabled: appointmentPageReducer.createCalendarEvt.disabled,
  visible: appointmentPageReducer.createCalendarEvt.visible,
  loading: appointmentPageReducer.createCalendarEvt.loading,
  createCalEvtSuccess: appointmentPageReducer.createCalendarEvt.createCalEvtSuccess,
});

const mapDispatchToProps = {
  changeCreateCalEvtStartDate,
  changeCreateCalEvtStartTime,
  changeCreateCalEvtEndDate,
  changeCreateCalEvtEndTime,
  changeCreateCalEvtAllDay,
  changeCreateCalEvtDoctor,
  changeCreateCalEvtRepeat,
  changeCreateCalEvtNote,
  checkCreateCalEvtConfirmButtonDisable,
  changeCreateCalModalVisible,
  createCalEvt,
  getAllEvents,
  changeCreateCalEvtRepeatEndDate,
};

export default connect(mapStateToProps, mapDispatchToProps)(CreateCalendarEventModal);
