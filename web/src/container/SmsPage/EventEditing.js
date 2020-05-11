import { Button, Radio, Popover } from 'antd';
import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import styled from 'styled-components';
import {
  setSelectedEvent,
  editTitle,
  editTemplate,
  addTag,
  toggleAppointmentModal,
  unselectAppointment,
  togglePreviewingModal,
  saveEvent,
  deleteEvent,
  setCaretPosition,
} from './action';
import AppointmentsModal from './AppointmentsModal';
import EventPreviewingModal from './EventPreviewingModal';
import moment from 'moment';
import PersonalAddFill from './svg/PersonalAddFill';
import Close from './svg/Close';
import Trash from './svg/Trash';
import { StyledMediumButton, StyledTag, StyledInput, StyledInputArea } from './StyledComponents';
import { P2, Caption, Subtitle, Title, NoMarginText } from '../../utils/textComponents';
import isEqual from 'lodash.isequal';

const RootContainer = styled.div`
  display: grid;
  grid-template-rows: 70px 1fr;
  height: 100%;
`;

const HeaderContainer = styled.div`
  display: grid;
  grid-template-columns: auto auto;
  align-items: center;
  padding: 0 32px 0 24px;
  box-shadow: 0 4px 4px 0 rgba(0, 0, 0, 0.04), inset 0 -1px 0 0 #eeeeee;
  background: white;
  border-radius: 0 10px 0 0;
`;

const BoneContainer = styled.div`
  display: flex;
  flex-direction: column;
  justify-content: space-between;
  margin: 16px;
  padding: 32px;
  box-shadow: 0 0px 16px 0 rgba(0, 0, 0, 0.1);
  border-radius: 10px;
  background: white;
  overflow: scroll;
  scrollbar-width: none; /* Firefox */
  -ms-overflow-style: none; /* IE 10+ */
  &::-webkit-scrollbar {
    width: 0px;
    background: transparent; /* Chrome/Safari/Webkit */
  }
`;

const FieldsContainer = styled.div`
  display: flex;
  flex-direction: column;
`;

const ActionContainer = styled.div`
  display: flex;
  justify-content: flex-end;
  padding: 24px;
`;

const FieldContainer = styled.div`
  display: flex;
  margin-bottom: 16px;
`;

const FieldLabel = styled(Subtitle)`
  min-width: 60px;
`;

const ContactContainer = styled.div`
  display: grid;
  grid-template: 'theOne';
  width: 100%;
  & > :first-child {
    max-height: 80px;
    padding: 0 16px;
  }
`;

const TagsContainer = styled.div`
  display: flex;
  align-items: center;
  flex-wrap: wrap;
  grid-area: theOne;
  z-index: 2;
  margin: 4px;
  background: transparent;
  max-height: 60px;
  overflow-y: scroll;
  scrollbar-width: none; /* Firefox */
  -ms-overflow-style: none; /* IE 10+ */
  &::-webkit-scrollbar {
    width: 0px;
    background: transparent; /* Chrome/Safari/Webkit */
  }
`;

const VariableText = styled(NoMarginText)`
  font-size: 13px;
  color: #8f9bb3;
  margin-right: 6px;
`;

const VariablesContainer = styled.div`
  display: grid;
  grid-template-columns: auto 1fr;
  align-items: center;
  border: solid 1px #e4e9f2;
  border-top: 0px;
  border-radius: 0 0 5px 5px;
  padding: 4px 16px;
  min-height: 36px;
  & .ant-radio-button-wrapper {
    border: 0;
    border-right: 1px;
    border-left: 1px;
    color: #222b45;
    padding: 0 16px !important;
    &::before {
      height: 18px !important;
      margin-top: 2px !important;
    }
  }

  & .ant-radio-button-wrapper:first-child {
    border-left: 0;
  }
`;

const isDiff = (o1, o2) => {
  if (o1.metadata.template !== o2.metadata.template) return true;

  const newApp = o1.metadata.selectedAppointments.map(app => app.id);
  const oldApp = o2.metadata.selectedAppointments.map(app => app.id);

  if (!isEqual(newApp, oldApp)) return true;
  if (o1.title !== o2.title) return true;

  return false;
};

function EventEditing(props) {
  const {
    editingEvent,
    selectedEvent,
    tags,
    setSelectedEvent,
    editTitle,
    editTemplate,
    addTag,
    toggleAppointmentModal,
    unselectAppointment,
    togglePreviewingModal,
    saveEvent,
    deleteEvent,
    setCaretPosition,
    isDeletingEvent,
  } = props;

  useEffect(() => {
    const interval = setInterval(() => {
      if (editingEvent !== null && editingEvent.isEdit && editingEvent.metadata.template.length !== 0) {
        if (isDiff(editingEvent, selectedEvent)) {
          saveEvent(editingEvent);
        }
      }
    }, 500);
    return () => clearInterval(interval);
    // eslint-disable-next-line
  }, [editingEvent]);

  return (
    <RootContainer>
      <HeaderContainer>
        <Title>{editingEvent.title}</Title>
        <Button
          style={{ display: editingEvent.status === 'draft' ? null : 'none', justifySelf: 'flex-end' }}
          danger
          type="link"
          icon={<Trash />}
          loading={isDeletingEvent}
          onClick={() => {
            if (editingEvent.id !== null) deleteEvent(editingEvent.id);
            else setSelectedEvent(null);
          }}
        />
      </HeaderContainer>
      <BoneContainer>
        <FieldsContainer>
          <FieldContainer>
            <FieldLabel>主題：</FieldLabel>
            <StyledInput size="large" onChange={editTitle} value={editingEvent.title} />
          </FieldContainer>
          <FieldContainer>
            <FieldLabel>寄送：</FieldLabel>
            <ContactContainer>
              <StyledInput
                placeholder={editingEvent.metadata.selectedAppointments.length === 0 ? '點擊加入對象' : ''}
                style={{ gridArea: 'theOne' }}
                size="large"
                onClick={() => toggleAppointmentModal()}
                suffix={<PersonalAddFill />}
              />
              <TagsContainer onClick={() => toggleAppointmentModal()}>
                {editingEvent.metadata.selectedAppointments.map(app => {
                  return (
                    <Popover
                      key={app.id}
                      title={
                        <div style={{ margin: '7px 0' }}>
                          <Subtitle>{app.patientName}</Subtitle>
                          <P2>{app.phone}</P2>
                        </div>
                      }
                      content={
                        <div>
                          <Caption>{`${moment(app.expectedArrivalTime).format('YYYY/MM/DD HH:mm')} 預約`}</Caption>
                          <Caption>{app.note}</Caption>
                        </div>
                      }
                    >
                      <StyledTag>
                        {`${app.patientName}(${app.phone.trim()})`}
                        {
                          <Button
                            type="link"
                            onClick={e => {
                              e.stopPropagation();
                              unselectAppointment(app);
                            }}
                          >
                            <Close />
                          </Button>
                        }
                      </StyledTag>
                    </Popover>
                  );
                })}
              </TagsContainer>
            </ContactContainer>
          </FieldContainer>
          <FieldContainer>
            <FieldLabel>訊息內容：</FieldLabel>
          </FieldContainer>
          <StyledInputArea
            className="textarea-input"
            placeholder="填寫簡訊寄送內容，至多 70 字"
            autoSize={{ minRows: 6, maxRows: 6 }}
            onClick={e => setCaretPosition(e.target.selectionStart, 'click')}
            onKeyDown={e => setCaretPosition(e.target.selectionStart, 'keydown')}
            onChange={editTemplate}
            value={editingEvent.metadata?.template}
          />
          <VariablesContainer>
            <VariableText>加入變數：</VariableText>
            <Radio.Group value={null} size="small">
              {tags.map(tag => (
                <Radio.Button key={tag} value={tag} onClick={() => addTag(tag)}>
                  {tag}
                </Radio.Button>
              ))}
            </Radio.Group>
          </VariablesContainer>
        </FieldsContainer>

        <ActionContainer>
          <StyledMediumButton
            className="styled-medium-btn"
            disabled={editingEvent.sms.length === 0}
            shape="round"
            type="primary"
            onClick={() => {
              togglePreviewingModal();
            }}
          >
            預覽及寄送
          </StyledMediumButton>
        </ActionContainer>
        <AppointmentsModal />
        <EventPreviewingModal />
      </BoneContainer>
    </RootContainer>
  );
}
const mapStateToProps = ({ smsPageReducer }) => ({
  editingEvent: smsPageReducer.event.editingEvent,
  selectedEvent: smsPageReducer.event.selectedEvent,
  appointments: smsPageReducer.appointment.appointments,
  tags: smsPageReducer.event.tags,
  visible: smsPageReducer.appointment.visible,
  isDeletingEvent: smsPageReducer.event.isDeletingEvent,
});

const mapDispatchToProps = {
  setSelectedEvent,
  editTitle,
  editTemplate,
  addTag,
  toggleAppointmentModal,
  unselectAppointment,
  togglePreviewingModal,
  saveEvent,
  deleteEvent,
  setCaretPosition,
};

export default connect(mapStateToProps, mapDispatchToProps)(EventEditing);
