import { Button } from 'antd';
import React from 'react';
import { connect } from 'react-redux';
import styled from 'styled-components';
import { setSelectedEvent, executeEvent, deleteEvent } from './action';
import { parseAccountData } from '../NavHome/utils/parseAccountData';
import moment from "moment";
import Trash from './svg/Trash';
import PaperPlane from './svg/PaperPlane'

const RootContainer = styled.div`
  display: grid;
  grid-template-rows: 70px 1fr;
  height: 100%;
`;

const NoMarginText = styled.p`
  margin: auto 0;
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

const Header = styled(NoMarginText)`
  font-size: 18px;
  font-weight: 600;
  color: #222b45;
`;

const BoneContainer = styled.div`
  display: grid;
  grid-template-rows: auto 1fr;
  margin: 16px 0;
  overflow: hidden;
`;

const EventDetailContainer = styled.div`
  display: flex;
  justify-content: space-between;
  margin: 0 32px 8px;
`;


const SenderContainer = styled.div`
  display: grid;
  grid-template: 24px 24px/ 48px auto; 
`;

const AvatarImg = styled.img`
  width: 26px;
  height: 26px;
  border-radius: 50%;
  grid-row: 1/3;
  box-shadow: 0 0px 8px 0 rgba(0, 0, 0, 0.12);
  justify-self: center;
  align-self: center;
`;

const NameText = styled(NoMarginText)`
  font-size: 15px;
`;
const DateText = styled(NoMarginText)`
  font-size: 10px;
  font-weight: bold;
`;

const EventList = styled.div`
  overflow: scroll;
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
  padding: 32px;
  margin: 16px 32px;
  box-shadow: 0 4px 25px 0 rgba(0, 0, 0, 0.1);
  border-radius: 10px;
  > *:not(:first-child) {
    margin-top: 8px;
  }
  background: white;
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

const Splitter = styled.div`
  height: 1px;
  border: solid 1px #dae1e7;
`;

function EventReadOnly(props) {
  const { selectedEvent, executeEvent, deleteEvent, account, users } = props
  const createdBy = users.find(user => user.login === selectedEvent.createdBy).firstName;

  return ( 
    <RootContainer>
      <HeaderContainer>
        <Header>{selectedEvent.title}</Header>
        <Button
          style={{ justifySelf: 'flex-end', display: selectedEvent.status === 'completed' ? 'none' : null }}
          danger
          type="link" 
          icon={<Trash />}
          onClick={() => deleteEvent(selectedEvent.id)} />
      </HeaderContainer>
      <BoneContainer>
        <EventDetailContainer>
          <SenderContainer>
            {account.avatar ? (
              <AvatarImg alt="avatar" src={`data:image/png;base64,${account.avatar}`} />
            ) : (
              <AvatarImg alt={account.name[0]} src={null} style={{ textAlign: 'center' }} />
            )}
            <NameText style={{ gridRow: '1/2', gridColumn: '2/3'}}>{createdBy}</NameText>
            <DateText style={{ gridRow: '2/3', gridColumn: '2/3'}}>{moment(selectedEvent.modifiedDate).format('YYYY/MM/DD HH:mm')}</DateText>
          </SenderContainer>
            <Button
              style={{ visibility: selectedEvent.status === 'draft' ? null : 'hidden' }}
              icon={<PaperPlane />}
              type="link"
              onClick={() => executeEvent(selectedEvent)} />
        </EventDetailContainer>
        <EventList>
          {selectedEvent.sms.map(item => (
            <EventListItem key={item.phone + item.content}>
              <EventPatientNameText>{item.metadata.patientName}</EventPatientNameText>
              <EventOccurText>{`${moment(item.metadata.appointmentDate).format('YYYY/MM/DD HH:mm')}的預約已發送至${item.phone}`}</EventOccurText>
              <Splitter />
              <EventContentText>{item.content}</EventContentText>
            </EventListItem>
          ))}
        </EventList>
      </BoneContainer>
    </RootContainer>
  );
}
const mapStateToProps = ({ smsPageReducer, homePageReducer }) => ({ 
  users: smsPageReducer.user.users,
  selectedEvent: smsPageReducer.event.selectedEvent,
  account: parseAccountData(homePageReducer.account.data),
});

const mapDispatchToProps = { setSelectedEvent, executeEvent, deleteEvent};

export default connect(mapStateToProps, mapDispatchToProps)(EventReadOnly);
