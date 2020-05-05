import React, { useState, useEffect, useLayoutEffect } from 'react';
import { connect } from 'react-redux';
import styled from 'styled-components';
import { Helmet } from 'react-helmet-async';
import {
  getEvents,
  setSelectedEvent,
  createEvent,
  saveEvent,
  filterEvents,
  getClinicSettings,
  getClinicRemaining,
  getUsers,
} from './action';
import EventCard from './EventCard';
import moment from 'moment';
import InboxFill from './svg/InboxFill';
import Edit from './svg/Edit';
import Edit2 from './svg/Edit2';
import PaperPlane from './svg/PaperPlane';
import GiftFill from './svg/GiftFill';
import AwardFill from './svg/AwardFill';
import { StyledLargerButton } from './StyledComponents';
import isEqual from 'lodash.isequal';
import { O1 } from '../../utils/colors';

const Container = styled.div`
  display: flex;
  flex-direction: column;
  height: 100%;
  @media screen and (max-width: 480px) {
    padding: 0px;
  }
`;

const NoMarginText = styled.p`
  margin: auto 0;
`;

const RootConatiner = styled.div`
  display: grid;
  height: 100%;
  grid-template-columns: 260px 270px auto;
  box-shadow: 0 0 15px 0 rgba(0, 0, 0, 0.05);
  border: solid 0.5px #dae1e7;
  border-radius: 10px;
  background: #f8fafb;
  @media (max-width: 480px) {
    display: grid;
    grid-template: auto / auto;
  }
`;

const ButtonBox = styled.div`
  display: grid;
  height: 92px;
  padding: 0 24px;
  align-content: center;
`;

const CategoryContainer = styled.div`
  display: flex;
  flex-direction: column;
`;

const RemainingActionSection = styled.div`
  display: flex;
  height: 126px;
  flex-direction: column;
  border-top: solid 1px #dae1e7;
  padding: 12px 36px;
  justify-content: flex-start;
`;

const RemainingActionItem = styled.div`
  display: flex;
  align-items: center;
  height: 40px;
  cursor: pointer;
  & :first-child {
    margin-right: 8px;
  }
  & :hover {
    color: #7e8aa2;
  }
`;

const ActionName = styled(NoMarginText)`
  font-size: 14px;
  font-weight: bold;
  color: #8f9bb3;
`;

const CategoryTitleContainer = styled.div`
  display: flex;
  align-items: center;
  padding: 0 24px;
  color: #222b45;
`;

const CategoryTitle = styled(NoMarginText)`
  font-size: 18px;
  font-weight: bold;
`;

const Title = styled(NoMarginText)`
  font-size: 15px;
  font-weight: 600;
  text-overflow: ellipsis;
  overflow: hidden;
  white-space: nowrap;
`;

const Caption = styled(NoMarginText)`
  font-size: 12px;
  grid-area: 2/2 / 3/3;
  color: ${props => (props.eventSelected ? 'white' : props.isDraft ? O1 : '#8f9bb3')};
`;

const TinyBold = styled(NoMarginText)`
  font-size: 10px;
  font-weight: bold;
  text-align: right;
  margin-right: 8px;
  grid-area: 1/3/2/4;
  color: ${props => (props.eventSelected ? 'white' : '#8f9bb3')};
`;

const MenuItem = styled.div`
  display: flex;
  height: 64px;
  align-items: center;
  color: ${props => (props.selected ? 'rgba(50, 102, 255)' : '#222b45')};
  fill: ${props => (props.selected ? 'rgba(50, 102, 255)' : '#222b45')};
  cursor: pointer;
  padding: 0 32px;
  & > svg {
    margin-right: 16px;
  }
`;

const MenuName = styled.div`
  font-size: 15px;
  font-weight: 600;
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
  display: grid;
  grid-template: 24px 20px / 20px auto 56px;
  background-image: ${props => (props.eventSelected ? 'linear-gradient(283deg, #0a54c5, #3266ff)' : 'none')};
  box-shadow: ${props => (props.eventSelected ? '0 15px 30px 0 rgba(48, 101, 252, 0.11)' : 'none')};
  width: 100%;
  padding: 16px;
  border-bottom: solid 1px #dae1e7;
  cursor: pointer;
  color: ${props => (props.eventSelected ? 'white' : '#222b45')};

  &:hover {
    background: ${props => (props.eventSelected ? null : '#f7f9fc')};
  }
`;

const EventListContainer = styled.div`
  display: grid;
  overflow: hidden;
  grid-template-rows: 70px 1fr;
  grid-row: 1/2;
  grid-column: 2/3;
  flex-direction: column;
  box-shadow: 1px 0 0 0 rgba(209, 209, 209, 0.5);
  background: white;
  z-index: 2;
  @media (max-width: 480px) {
    grid-column: 1/2;
    width: 100vw;
    height: 100%;
    display: ${props => (props.hasEvent ? 'none' : props.expanding ? 'none' : null)};
  }
`;

const OverallContainer = styled.div`
  grid-row: 1/2;
  display: flex;
  flex-direction: column;
  justify-content: space-between;
  width: 260px;
  box-shadow: 0 0 15px 0 rgba(0, 0, 0, 0.05);
  border-right: 1px #dae1e7 solid;
  background: white;
  z-index: 3;
  border-radius: 10px 0 0 10px;
  @media screen and (max-width: 480px) {
    width: 100vw;
    height: 100%;
    grid-column: 1/2;
    display: ${props => (props.hasEvent ? 'none' : props.expanding ? null : 'none')};
  }
`;

const EventCardContainer = styled.div`
  grid-column: 3/4;
  grid-row: 1/2;
  overflow: hidden;
  @media (max-width: 480px) {
    grid-column: 1/2;
    width: 100vw;
    height: 100%;
    display: ${props => (props.hasEvent ? null : 'none')};
  }
`;

const categories = ['ALL', 'DRAFT', 'SENT'];
const categoriesChinese = ['全部', '草稿', '寄送備份'];
const categoryIcons = [<InboxFill key={1} />, <Edit key={2} />, <PaperPlane key={3} />];

function SmsPage(props) {
  const {
    getEvents,
    filterEvents,
    currentKey,
    createEvent,
    saveEvent,
    getClinicSettings,
    getClinicRemaining,
    getUsers,
    setSelectedEvent,
    selectedEvent,
    selectedEventId,
    editingEvent,
    events,
    remaining,
    isRemainingLoaded,
    users,
  } = props;
  const [expanding, setExpanding] = useState(false);
  const [hasEvent, setHasEvent] = useState(false);

  useEffect(() => {
    setExpanding(false);
  }, [currentKey]);

  useEffect(() => {
    getUsers();
    getClinicSettings();
    getEvents();
    getClinicRemaining();
    // eslint-disable-next-line
  }, []);

  useLayoutEffect(() => {
    setHasEvent(selectedEvent !== null);
  }, [selectedEvent]);

  const isDiff = (o1, o2) => {
    if (o1 === null || o2 === null) return false;
    if (o1.metadata.template !== o2.metadata.template) return true;

    const newApp = o1.metadata.selectedAppointments.map(app => app.id);
    const oldApp = o2.metadata.selectedAppointments.map(app => app.id);

    if (!isEqual(newApp, oldApp)) return true;
    if (o1.title !== o2.title) return true;

    return false;
  };

  const handleSelectionChanging = item => {
    // if current(previous) item is able to be posted or put
    if (isDiff(editingEvent, selectedEvent) && editingEvent.metadata.template.length !== 0) saveEvent(editingEvent);
    // else just changing
    else setSelectedEvent(item);
  };

  return (
    <Container>
      <Helmet>
        <title>簡訊</title>
      </Helmet>
      <RootConatiner>
        <OverallContainer expanding={expanding} hasEvent={hasEvent}>
          <CategoryContainer>
            <ButtonBox>
              <StyledLargerButton
                className="styled-larger-btn"
                type="primary"
                shape="round"
                block
                onClick={createEvent}
              >
                <div style={{ display: 'flex', alignItems: 'center', justifyContent: 'center' }}>
                  <Edit2 />
                  <NoMarginText>CREATE</NoMarginText>
                </div>
              </StyledLargerButton>
            </ButtonBox>
            {categories.map((category, i) => (
              <MenuItem key={category} selected={currentKey === category} onClick={() => filterEvents(category)}>
                {categoryIcons[i]}
                <MenuName>{categoriesChinese[i]}</MenuName>
              </MenuItem>
            ))}
          </CategoryContainer>
          {/* TODO: SMS: link update */}
          <RemainingActionSection>
            <RemainingActionItem
              style={{ visibility: isRemainingLoaded ? null : 'hidden' }}
              onClick={() =>
                window.open(
                  'https://www.dentaltw.com/market/5ea67d0f3b81210000fed79c?vip_token=5ea67de24b169a000084252b',
                )
              }
            >
              <GiftFill fill={remaining === 0 ? O1 : null} />
              <ActionName style={{ color: remaining === 0 ? O1 : null }}>{`儲值 (剩餘 ${remaining} 封)`}</ActionName>
            </RemainingActionItem>
            <RemainingActionItem onClick={() => window.open('https://www.dentaltw.com/myOrders')}>
              <AwardFill />
              <ActionName>購買紀錄</ActionName>
            </RemainingActionItem>
          </RemainingActionSection>
        </OverallContainer>
        <EventListContainer expanding={expanding} hasEvent={hasEvent}>
          <CategoryTitleContainer>
            <CategoryTitle>{categoriesChinese[categories.indexOf(currentKey)]}</CategoryTitle>
          </CategoryTitleContainer>
          <EventList>
            {events.map(item => {
              const user = users.find(user => user.login === item.createdBy);
              const createdBy = user ? user.firstName : '';

              return (
                <EventListItem
                  eventSelected={
                    selectedEvent && (item.id !== null ? selectedEventId === item.id : selectedEventId === item.tempId)
                  }
                  key={item.id !== null ? item.id : item.tempId}
                  onClick={() => handleSelectionChanging(item)}
                >
                  <Title style={{ gridRow: '1/2', gridColumn: '2/3' }}>{item.title}</Title>

                  <Caption
                    eventSelected={
                      selectedEvent &&
                      (item.id !== null ? selectedEventId === item.id : selectedEventId === item.tempId)
                    }
                    isDraft={item.status === 'draft'}
                  >
                    {item.status === 'completed' ? `${createdBy}已傳送${item.sms.length}則` : '草稿'}
                  </Caption>

                  <TinyBold
                    eventSelected={
                      selectedEvent &&
                      (item.id !== null ? selectedEventId === item.id : selectedEventId === item.tempId)
                    }
                  >
                    {(() => {
                      if (moment().isSame(item.modifiedDate, 'day')) return moment(item.modifiedDate).format('HH:mm');
                      else {
                        if (moment().startOf('date').add(-1, 'days').isSame(item.modifiedDate, 'day'))
                          return 'yesterday';
                        else return moment(item.modifiedDate).format('MM/DD');
                      }
                    })()}
                  </TinyBold>
                </EventListItem>
              );
            })}
          </EventList>
        </EventListContainer>
        <EventCardContainer hasEvent={hasEvent}>
          <EventCard />
        </EventCardContainer>
      </RootConatiner>
    </Container>
  );
}

const mapStateToProps = ({ smsPageReducer }) => ({
  users: smsPageReducer.user.users,
  events: smsPageReducer.event.events,
  selectedEventId: smsPageReducer.event.selectedEventId,
  selectedEvent: smsPageReducer.event.selectedEvent,
  editingEvent: smsPageReducer.event.editingEvent,
  clinicName: smsPageReducer.event.clinicName,
  remaining: smsPageReducer.event.remaining,
  isRemainingLoaded: smsPageReducer.event.isRemainingLoaded,
  currentKey: smsPageReducer.event.currentKey,
});

// doctors: extractDoctorsFromUser(homePageReducer.user.users),
const mapDispatchToProps = {
  getEvents,
  getClinicRemaining,
  getUsers,
  setSelectedEvent,
  createEvent,
  saveEvent,
  filterEvents,
  getClinicSettings,
};

export default connect(mapStateToProps, mapDispatchToProps)(SmsPage);
