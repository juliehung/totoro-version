import React, { useEffect } from 'react';
import styled from 'styled-components';
import { connect } from 'react-redux';
import { togglePreviewingModal, executeEvent } from './action';
import PaperPlane from './svg/PaperPlane';
import CreditCardFill from './svg/CreditCardFill';
import AlertCircleFill from './svg/AlertCircleFill';
import { StyledMediumButton, StyledModal } from './StyledComponents';
import { message } from 'antd';
import { momentToRocString } from './utils';

//#region
const NoMarginText = styled.p`
  margin: auto 0;
`;

const HeaderContainer = styled.div`
  display: flex;
  align-items: center;
  background: #f8fafb;
  height: 56px;
  padding: 0 24px;
  border-radius: 4px 4px 0 0;
`;

const Header = styled(NoMarginText)`
  font-size: 18px;
  font-weight: bold;
`;

const FieldContainer = styled.div`
  display: flex;
  flex-direction: column;
  padding: 16px 0 0;
`;

const Title = styled(NoMarginText)`
  font-size: 15px;
  font-weight: 600;
  color: #8f9bb3;
  margin: 0 24px;
`;

const ActionContainer = styled.div`
  display: flex;
  align-items: center;
  height: 56px;
  background: #3266ff;
  padding: 0 24px;
  border-radius: 0 0 4px 4px;
`;

const EventList = styled.div`
  overflow-y: scroll;
  height: 55vh;
  scrollbar-width: none; /* Firefox */
  -ms-overflow-style: none; /* IE 10+ */
  &::-webkit-scrollbar {
    width: 0px;
    background: transparent; /* Chrome/Safari/Webkit */
  }
`;

const EventListItem = styled.div`
  display: flex;
  flex-direction: column;
  padding: 32px;
  box-shadow: 0 4px 25px 0 rgba(0, 0, 0, 0.1);
  border-radius: 10px;
  > *:not(:first-child) {
    margin-top: 8px;
  }
  background: white;
  margin: 16px 24px;
`;

const EventPatientNameText = styled(NoMarginText)`
  font-size: 18px;
  font-weight: 600;
`;

const EventOccurText = styled(NoMarginText)`
  font-size: 12px;
  color: #8f9bb3;
`;

const EventContentText = styled(NoMarginText)`
  font-size: 13px;
`;

const WarningContainer = styled.div`
  display: flex;
  align-items: center;
  justify-content: space-between;
  height: 56px;
  background: #fe9f43;
  padding: 0 24px;
`;

const Warning = styled.div`
  font-size: 13px;
  color: white;
  display: flex;
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
  margin: 16px 24px 0;
  background: rgba(100, 100, 100, 0.03);
`;

const Styled2MediumButton = styled(StyledMediumButton)`
  & svg {
    margin-right: 8px;
  }
`;
//#endregion

function EventPreviewingModal(props) {
  const { togglePreviewingModal, executeEvent, editingEvent, visible, isSentFailed, remaining } = props;

  const handleOk = () => {
    executeEvent(editingEvent);
    message.loading({ content: '正在發送中...' });
  };

  const handleClose = () => {
    togglePreviewingModal();
  };

  useEffect(() => {
    if (isSentFailed === null) return;
    if (isSentFailed) message.error({ content: '簡訊發送出現問題，請重新嘗試。' });
    else message.success({ content: '簡訊已發送完成！' });
  }, [isSentFailed]);

  const total = editingEvent.sms.map(m => Math.ceil(m.content.length / 70)).reduce((a, b) => a + b, 0);
  const isEnableSend = total <= remaining;

  return (
    <StyledModal
      width={856}
      centered
      bodyStyle={{ padding: '0', margin: 'auto' }}
      visible={visible}
      footer={null}
      onCancel={handleClose}
    >
      <HeaderContainer>
        <Header>
          {isEnableSend ? `確認您將傳送 ${total} 則簡訊？` : `不足寄送 ${total} 封簡訊 ( 帳戶餘額 ${remaining} )`}
        </Header>
      </HeaderContainer>
      <FieldContainer>
        <Title>範本</Title>
        <TemplatePlace>{editingEvent.metadata.template}</TemplatePlace>
      </FieldContainer>
      <FieldContainer>
        <Title>發送預覽</Title>
        <EventList>
          {editingEvent.sms.map((item, index) => (
            <EventListItem key={`${item.phone}${index}`}>
              <EventPatientNameText>{item.metadata.patientName}</EventPatientNameText>
              <EventOccurText>{`${momentToRocString(item.metadata.appointmentDate)}的預約即將發送${Math.ceil(
                item.content.length / 70,
              )}封至${item.phone}`}</EventOccurText>
              <Splitter />
              <EventContentText>{item.content}</EventContentText>
            </EventListItem>
          ))}
        </EventList>
      </FieldContainer>
      {!isEnableSend ? (
        <WarningContainer>
          <Warning>
            <AlertCircleFill />
            {`由於您的帳戶額度不足(剩餘${remaining}則)，簡訊無法發送。請前往儲值後重新操作！`}
          </Warning>
          <Styled2MediumButton
            className="styled-medium-btn"
            style={{ border: 'solid 1px white', background: 'rgba(255,255,255,0.08)' }}
            type="primary"
            shape="round"
            onClick={() =>
              window.open(
                'https://www.dentaltw.com/market/5ea67d0f3b81210000fed79c?vip_token=5ea67de24b169a000084252b',
                '_blank',
                'noopener',
              )
            }
          >
            <div style={{ display: 'flex', alignItems: 'center', justifyContent: 'center', fill: 'white' }}>
              <CreditCardFill />
              <NoMarginText>前往購買簡訊額度</NoMarginText>
            </div>
          </Styled2MediumButton>
        </WarningContainer>
      ) : (
        <ActionContainer>
          <Styled2MediumButton
            className="styled-medium-btn"
            style={{ border: 'solid 1px white', background: 'rgba(255,255,255,0.08)' }}
            type="primary"
            shape="round"
            onClick={handleOk}
          >
            <div style={{ display: 'flex', alignItems: 'center', justifyContent: 'center', fill: 'white' }}>
              <PaperPlane />
              <NoMarginText>立即發送訊息</NoMarginText>
            </div>
          </Styled2MediumButton>
        </ActionContainer>
      )}
    </StyledModal>
  );
}

const mapStateToProps = ({ smsPageReducer }) => {
  return {
    appointments: smsPageReducer.appointment.appointments,
    editingEvent: smsPageReducer.event.editingEvent,
    visible: smsPageReducer.event.visible,
    isSentFailed: smsPageReducer.event.isSentFailed,
    remaining: smsPageReducer.event.remaining,
  };
};

const mapDispatchToProps = { togglePreviewingModal, executeEvent };

export default connect(mapStateToProps, mapDispatchToProps)(EventPreviewingModal);
