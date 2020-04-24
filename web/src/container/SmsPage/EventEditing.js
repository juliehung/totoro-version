import { Button, Input, Tag, Radio, Popover } from 'antd';
import React from 'react';
import { connect } from 'react-redux';
import styled from 'styled-components';
import { setSelectedEvent, editTitle, editTemplate, addTag, toggleAppointmentModal, unselectAppointment, togglePreviewingModal, saveEvent } from './action';
import AppointmentsModal from './AppointmentsModal';
import EventPreviewingModal from './EventPreviewingModal'
import moment from 'moment'
import Close from './svg/Close'
import PersonalAddFill from './svg/PersonalAddFill'
import AlertTriangle from './svg/AlertTriangle'
import { P2, Caption, Subtitle, Title, NoMarginText } from '../../utils/textComponents';

const RootContainer = styled.div`
  display: grid;
  grid-template-rows: 70px 1fr;
  height: 100%;
`;

const HeaderContainer = styled.div`
  display: grid;
  grid-template-columns: 32px auto auto;
  align-items: center;
  padding: 0 32px 0 16px; 
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
  border-radius: 10px
  background: white;
  overflow: scroll;
  scrollbar-width: none; /* Firefox */
  -ms-overflow-style: none;  /* IE 10+ */
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
  width: 60px; 
`;

const ContactContainer = styled.div`
  display: grid;
  grid-template: "theOne";
  width: 100%;
`;

const TagsContainer = styled.div`
  display: flex;
  align-items: center;
  flex-wrap: wrap;
  grid-area: theOne;
  z-index: 2;
  margin: 4px;
  background: #f8fafb;
`;

const VariablesContainer = styled.div`
  display: flex;
  border: solid 1px #e4e9f2;
  border-top: 0px;
  border-radius: 0 0 5px 5px;
  padding: 4px 16px;

  & .ant-radio-button-wrapper {
    border: 0;
    border-right: 1px;
    border-left: 1px;
  }

  & .ant-radio-button-wrapper:first-child {
    border-left: 0;
  }
`;

const VariableText = styled(NoMarginText)`
  font-size: 13px;
  margin-right: 24px;
  color: #8f9bb3;
`;

const Warning = styled(NoMarginText)`
  font-weight: 600;
  color: red;
`;

function EventEditing(props) {
  const {
    editingEvent,
    tags,
    setSelectedEvent,
    editTitle,
    editTemplate,
    addTag,
    toggleAppointmentModal,
    unselectAppointment,
    togglePreviewingModal,
    isWrongNumberLength,
    isWrongContentLength,
    isLoaded,
    saveEvent,
  } = props

  const handleTagChange = value => {
    addTag(value)
  }
  return ( 
    <RootContainer>
      <HeaderContainer>
        <Button 
          icon={<Close />}
          type="link" 
          onClick={() => {
            if (editingEvent.id == null) setSelectedEvent(null, true)
            else setSelectedEvent(null)
          }}>
        </Button>
        <Title>{editingEvent.title}</Title>
      </HeaderContainer>
      <BoneContainer>
        <FieldsContainer>
          <FieldContainer>
            <FieldLabel>主題：</FieldLabel>
            <Input 
              style={{background:'#f8fafb'}}
              size="large" 
              onChange={editTitle} value={editingEvent.title} />
          </FieldContainer>
          <FieldContainer>
            <FieldLabel>寄送：</FieldLabel>
            <ContactContainer>
              <Input
                style={{background:'#f8fafb', gridArea: 'theOne'}}
                size="large"
                onClick={() => toggleAppointmentModal()} 
                suffix={<PersonalAddFill style={{ margin: 'auto 4px' }}/>} />
              <TagsContainer onClick={() => toggleAppointmentModal()}>
                {editingEvent.selectedAppointments.map(app => {
                  return (
                    <Popover
                      title={
                        <div style={{margin: '7px 0'}}>
                          <FieldLabel>{app.patientName}</FieldLabel>
                          <P2>{app.phone}</P2>
                        </div>
                      }
                      content={ 
                        <div>
                          <Caption>{`${moment(app.expectedArrivalTime).format('YYYY/MM/DD HH:mm')} 預約`}</Caption>
                          <Caption>{app.note}</Caption>
                        </div>
                      }>
                      <Tag 
                        key={app.id} 
                        closable
                        onClose={e => {
                          unselectAppointment(app)
                        }}
                        >
                          {`${app.patientName}(${app.phone})`}
                      </Tag>
                    </Popover>
                  )}
                )}
              </TagsContainer>
            </ContactContainer>
          </FieldContainer>      
          <FieldContainer>
          <FieldLabel style={{ width: '76px' }}>訊息內容：</FieldLabel>
            <FieldLabel style={{ color: '#FE9F43', width: 'auto', visibility : isWrongContentLength ? null : 'hidden' }}>
              <AlertTriangle /> 內容文字已達一封簡訊 70 字上限
            </FieldLabel>
          </FieldContainer>
          <Input.TextArea
            style={{background:'#f8fafb'}}
            autoSize={{ minRows: 6 }}
            onChange={editTemplate}
            value={editingEvent.template}
          />
          <VariablesContainer>
          <VariableText>加入變數：</VariableText>
          <Radio.Group value={null} size="small">
            {tags.map(tag => 
              <Radio.Button
                key={tag}
                value={tag}                
                onClick={() => handleTagChange(tag)}
              >
                {tag}
              </Radio.Button>
            )}
          </Radio.Group>
        </VariablesContainer>
        </FieldsContainer>

        <ActionContainer>
          <Warning style={{ visibility: isWrongNumberLength? null : 'hidden' }}>手機號碼格式錯誤</Warning>
          <Button 
            type="link"
            disabled={editingEvent.sms.length === 0 || isWrongNumberLength || isWrongContentLength || !isLoaded }
            style={{ margin: ' 0 16px' }}
            onClick={()=> saveEvent(editingEvent)}
          >
            儲存
          </Button>
          <Button 
            disabled={editingEvent.sms.length === 0 || !isLoaded}
            shape="round"
            type="primary"
            onClick={()=> {
              togglePreviewingModal()
            }}
          >
            預覽及寄送
          </Button>
        </ActionContainer>
        <AppointmentsModal />
        <EventPreviewingModal />
      </BoneContainer>

    </RootContainer>
  );
}
const mapStateToProps = ({ smsPageReducer }) => ({ 
  editingEvent: smsPageReducer.event.editingEvent,
  appointments: smsPageReducer.appointment.appointments,
  tags: smsPageReducer.event.tags,
  visible: smsPageReducer.appointment.visible,
  isWrongNumberLength: smsPageReducer.event.isWrongNumberLength,
  isWrongContentLength: smsPageReducer.event.isWrongContentLength,
  isLoaded: smsPageReducer.event.isLoaded,
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
 };

export default connect(mapStateToProps, mapDispatchToProps)(EventEditing);
