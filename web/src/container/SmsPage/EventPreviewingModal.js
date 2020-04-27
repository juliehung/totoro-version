import React from 'react';
import { Modal } from 'antd';
import styled from 'styled-components';
import { connect } from 'react-redux';
import {togglePreviewingModal, saveEventAndSendImmediately} from './action'
import moment from 'moment'
import PaperPlane from './svg/PaperPlane'
import { StyledLargerButton } from './Button'

const NoMarginText = styled.p`
  margin: auto 0;
`;

const HeaderContainer = styled.div`
  display: flex;
  align-items: center;
  background: #f8fafb;
  height: 56px;
  padding: 0 24px;
`;

const Header = styled(NoMarginText)`
  font-size: 18px;
  font-weight: bold;
`;

const FieldContainer = styled.div`
  display: flex;
  flex-direction: column;
  margin: 16px 24px 0 24px;
`;

const Title = styled(NoMarginText)`
  font-size: 15px;
  font-weight: 600;
  color: #8f9bb3;
`;

const ActionContainer = styled.div`
  display: flex;
  align-items: center;
  height: 56px;
  background: #3266ff;
  padding: 0 24px;
`;

const EventList = styled.div`
  overflow: scroll;
  padding: 0 4px;
  height: 55vh;
  scrollbar-width: none; /* Firefox */
  -ms-overflow-style: none;  /* IE 10+ */
  &::-webkit-scrollbar {
    width: 0px;
    background: transparent; /* Chrome/Safari/Webkit */
  }
`;

const EventListItem = styled.div`
  display: flex;
  flex-direction: column;
  width: 100%;
  padding: 32px;
  box-shadow: 0px 3px 5px 2px rgba(0,0,0,0.1);
  border-radius: 10px;
  > *:not(:first-child) {
    margin-top: 8px;
  }
  background: white;
  margin: 16px 0;
`;

const EventPatientNameText = styled(NoMarginText)`
  font-size: 18px;
  font-weight: 600;
`;

const EventOccurText = styled(NoMarginText)`
  font-size: 12px;
  color: #8f9bb3
`;

const EventContentText = styled(NoMarginText)`
  font-size: 13px;
`;

const WarningContainer =  styled.div`
  display: flex;
  align-items: center;
  height: 56px;
  background: #fe9f43;
  padding: 0 24px;
`;

const Warning = styled(NoMarginText)`
  font-size: 13px;
  color: white;
`;


const Splitter = styled.div`
  height: 1px;
  border: solid 1px #dae1e7;
`;


const TemplatePlace = styled.div`
  min-height: 80px;
  border-radius: 8px;
  border: solid 1px #e4e9f2;
  padding: 16px 32px;
  margin: 16px 0;
  background: rgba(100,100,100,0.03);
`;

function EventPreviewingModal(props) {
  const { togglePreviewingModal, saveEventAndSendImmediately, editingEvent, visible, isChargeFailed } = props

  const handleOk = () => {
    saveEventAndSendImmediately(editingEvent)
  }
  const handleClose = () => {
    togglePreviewingModal();
  }

  return ( 
    <Modal
      width={856}
      centered
      bodyStyle={{ padding: '0', margin: 'auto' }}  
      visible={visible}
      footer={null}
      onCancel={handleClose}
      >
      <HeaderContainer>
        <Header>
          確認您將傳送 {editingEvent.sms.length} 則簡訊？
        </Header>
      </HeaderContainer>
      <FieldContainer>
        <Title>範本</Title>
        <TemplatePlace>{editingEvent.metadata.template}</TemplatePlace>
      </FieldContainer>
      <FieldContainer>
        <Title>發送預覽</Title>
        <EventList>
          {editingEvent.sms.map(item => (
            <EventListItem key={item.phone}>
              <EventPatientNameText>{item.metadata.patientName}</EventPatientNameText>
              <EventOccurText>{`${moment(item.metadata.appointmentDate).format('YYYY/MM/DD HH:mm')}的預約即將發送至${item.phone}`}</EventOccurText>
              <Splitter />
              <EventContentText>{item.content}</EventContentText>
            </EventListItem>
          ))}
        </EventList>
      </FieldContainer>
      {isChargeFailed? 
        <WarningContainer>
          <Warning>您的帳戶額度不足</Warning>
        </WarningContainer> :
         <ActionContainer>
         <StyledLargerButton
          className="styled-larger-btn"
          style={{ border: 'solid 1px white' }}
          type="primary"
          shape="round"  
          onClick={handleOk}>
          <div style={{display: 'flex', alignItems: 'center', justifyContent: 'center', fill: 'white'}}>
            <PaperPlane />
            <NoMarginText>立即發送訊息</NoMarginText>
          </div>
         </StyledLargerButton>
       </ActionContainer>
      }
  </Modal>
  );
}

const mapStateToProps = ({ smsPageReducer }) => {
  return { 
    appointments: smsPageReducer.appointment.appointments,
    editingEvent: smsPageReducer.event.editingEvent,
    visible: smsPageReducer.event.visible,
    isChargeFailed: smsPageReducer.event.isChargeFailed,
  }
};

const mapDispatchToProps = { togglePreviewingModal, saveEventAndSendImmediately };

export default connect(mapStateToProps, mapDispatchToProps)(EventPreviewingModal);
