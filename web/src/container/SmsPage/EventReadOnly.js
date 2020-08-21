import { Button } from 'antd';
import React from 'react';
import { connect } from 'react-redux';
import styled from 'styled-components';
import { setSelectedEvent, executeEvent, deleteEvent } from './action';
import moment from 'moment';
import Trash from './svg/Trash';
import PaperPlane from './svg/PaperPlane';
import { P1, Small } from '../../utils/textComponents';
import { momentToRocString } from './utils';

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
  grid-template: 32px 16px/ 48px auto;

  & div {
    display: flex;
    flex-direction: column;
    grid-row: 1/3;
    height: 30px;
    margin-top: 4px;
  }
`;

const AvatarImg = styled.img`
  width: 30px;
  height: 30px;
  border-radius: 50%;
  grid-row: 1/3;
  box-shadow: 0 0px 8px 0 rgba(0, 0, 0, 0.12);
  justify-self: center;
  align-self: center;
  object-fit: cover;
`;

const EventList = styled.div`
  overflow: scroll;
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
  color: #8f9bb3;
`;

const EventContentText = styled(NoMarginText)`
  font-size: 13px;
`;

const Splitter = styled.div`
  height: 1px;
  background: #dae1e7;
`;

function EventReadOnly(props) {
  const { selectedEvent, executeEvent, deleteEvent, users } = props;
  const createdBy = users.find(user => user.login === selectedEvent.createdBy);
  moment.locale('en');
  return (
    <RootContainer>
      <HeaderContainer>
        <Header>{selectedEvent.title}</Header>
        <Button
          style={{ justifySelf: 'flex-end', display: selectedEvent.status === 'completed' ? 'none' : null }}
          danger
          type="link"
          icon={<Trash />}
          onClick={() => deleteEvent(selectedEvent.id)}
        />
      </HeaderContainer>
      <BoneContainer>
        <EventDetailContainer>
          <SenderContainer>
            {createdBy.extendUser.avatar ? (
              <AvatarImg alt="avatar" src={`data:image/png;base64,${createdBy.extendUser.avatar}`} />
            ) : (
              <AvatarImg alt={createdBy.firstName[0]} src={null} style={{ textAlign: 'center', lineHeight: '30px' }} />
            )}
            <div>
              <P1>{createdBy.firstName}</P1>
              <Small style={{ marginTop: '-2px' }}>
                {moment(selectedEvent.modifiedDate).format('dddd, MMM DD, HH:mm')}
              </Small>
            </div>
          </SenderContainer>
          <Button
            style={{ visibility: selectedEvent.status === 'draft' ? null : 'hidden' }}
            icon={<PaperPlane />}
            type="link"
            onClick={() => executeEvent(selectedEvent)}
          />
        </EventDetailContainer>
        <EventList>
          {selectedEvent.sms.map(item => (
            <EventListItem key={item.phone + item.content}>
              <EventPatientNameText>{item.metadata.patientName}</EventPatientNameText>
              <EventOccurText>{`${momentToRocString(item.metadata.appointmentDate)}的預約已發送至${
                item.phone
              }`}</EventOccurText>
              <Splitter />
              <EventContentText>{item.content}</EventContentText>
            </EventListItem>
          ))}
        </EventList>
      </BoneContainer>
    </RootContainer>
  );
}
const mapStateToProps = ({ smsPageReducer }) => ({
  users: smsPageReducer.user.users,
  selectedEvent: smsPageReducer.event.selectedEvent,
});

const mapDispatchToProps = { setSelectedEvent, executeEvent, deleteEvent };

export default connect(mapStateToProps, mapDispatchToProps)(EventReadOnly);
