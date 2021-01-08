import { Button, Popover } from 'antd';
import React from 'react';
import { connect } from 'react-redux';
import ManPng from '../../static/images/man.png';
import WomanPng from '../../static/images/woman.png';
import DefaultPng from '../../static/images/default.png';
import styled from 'styled-components';
import {
  setSelectedEvent,
  editTitle,
  editTemplate,
  toggleAppointmentModal,
  unselectAppointment,
  togglePreviewingModal,
  saveEvent,
  deleteEvent,
} from './action';
import AppointmentsModal from './AppointmentsModal';
import EventPreviewingModal from './EventPreviewingModal';
import moment from 'moment';
import PersonalAddFill from './svg/PersonalAddFill';
import Close from './svg/Close';
import Trash from './svg/Trash';
import { StyledMediumButton, StyledTag, StyledInput } from './StyledComponents';
import { P2, Caption, Subtitle, Title } from '../../utils/textComponents';
import _ from 'lodash';
import MixTagTextArea from './MixTagTextArea';
import { useDebouncedEffect } from './useDebouncedEffect';

//#region
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
  box-shadow: 0 0 16px 0 rgba(0, 0, 0, 0.1);
  border-radius: 10px;
  background: white;
  overflow: scroll;
  scrollbar-width: none; /* Firefox */
  -ms-overflow-style: none; /* IE 10+ */
  &::-webkit-scrollbar {
    width: 0;
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
    width: 0;
    background: transparent; /* Chrome/Safari/Webkit */
  }
`;

const AvatarImg = styled.img`
  width: 40px;
  height: 40px;
  margin-right: 24px;
  flex-shrink: 0;
`;

const PopoverTitleBox = styled.div`
  display: grid;
  grid-template-columns: 64px auto;
  grid-template-rows: 24px 20px;
  border-bottom: #edf1f7 1px solid;
  margin: 0 16px;
  padding: 20px 0 12px;
  min-width: 200px;

  & :nth-child(2) {
    grid-row: 1/2;
    grid-column: 2/3;
  }

  & :nth-child(3) {
    grid-row: 2/3;
    grid-column: 2/3;
  }
`;

//#endregion

const renderAvatarImg = gender => {
  if (gender === 'MALE') return <AvatarImg src={ManPng} alt="male" />;
  if (gender === 'FEMALE') return <AvatarImg src={WomanPng} alt="female" />;
  return <AvatarImg src={DefaultPng} alt="default" />;
};

const isDiff = (o1, o2) => {
  if (o1.metadata.template !== o2.metadata.template) return true;

  const newApp = o1.metadata.selectedAppointments.map(app => app.id);
  const oldApp = o2.metadata.selectedAppointments.map(app => app.id);

  if (!_.isEqual(newApp, oldApp)) return true;
  if (o1.title !== o2.title) return true;

  return false;
};

function EventEditing(props) {
  const {
    editingEvent,
    selectedEvent,
    setSelectedEvent,
    editTitle,
    editTemplate,
    toggleAppointmentModal,
    unselectAppointment,
    togglePreviewingModal,
    saveEvent,
    deleteEvent,
    isDeletingEvent,
  } = props;

  useDebouncedEffect(
    () => {
      if (editingEvent !== null && editingEvent.isEdit) {
        if (isDiff(editingEvent, selectedEvent)) {
          console.log('in');
          saveEvent(editingEvent);
        }
      }
      return () => {};
    },
    300,
    // eslint-disable-next-line
    [editingEvent, saveEvent, selectedEvent],
  );

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
            window.history.pushState({}, 'set', `#/sms`);
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
                        <PopoverTitleBox>
                          {renderAvatarImg(app.gender)}
                          <Subtitle>{app.patientName}</Subtitle>
                          <P2>{app.phone}</P2>
                        </PopoverTitleBox>
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
          <MixTagTextArea
            value={editingEvent.metadata?.template ?? ''}
            onChange={editTemplate}
            id={selectedEvent?.id}
          />
        </FieldsContainer>
        <ActionContainer>
          <StyledMediumButton
            className="styled-medium-btn"
            disabled={editingEvent.sms.length === 0 || editingEvent.metadata?.template.trim() === ''}
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
  visible: smsPageReducer.appointment.visible,
  isDeletingEvent: smsPageReducer.event.isDeletingEvent,
});

const mapDispatchToProps = {
  setSelectedEvent,
  editTitle,
  editTemplate,
  toggleAppointmentModal,
  unselectAppointment,
  togglePreviewingModal,
  saveEvent,
  deleteEvent,
};

export default connect(mapStateToProps, mapDispatchToProps)(EventEditing);
