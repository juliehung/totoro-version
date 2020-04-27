import React,{ useState, useEffect, useLayoutEffect } from 'react';
import { connect } from 'react-redux';
import { Button } from 'antd';
import styled from 'styled-components';
import { Helmet } from 'react-helmet-async';
import { getEvents, setSelectedEvent, createEvent, saveEvent, filterEvents, getClinicSettings, getClinicRemaining } from './action';
import EventCard from './EventCard';
import moment from 'moment';
import InboxFill from './svg/InboxFill'
import Edit from './svg/Edit'
import Edit2 from './svg/Edit2'
import PaperPlane from './svg/PaperPlane'
import MenuIcon from './svg/Menu'
import GiftFill from './svg/GiftFill'
import AwardFill from './svg/AwardFill'
import { StyledLargerButton } from './Button'
import isEqual from 'lodash.isequal'


const Container = styled.div`
  display: flex;
  flex-direction: column;
  padding: 32px 48px;
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
  border: solid .5px #dae1e7;
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

const BalanceContainer = styled.div`
  display: flex;
  flex-direction: column;
`;

const Remaining = styled(NoMarginText)`
  font-size: 15px;
  font-weight: 600;
  color: #8f9bb3;
  margin: auto 20px ;
  margin-bottom: 20px;
`;

// TODO: SMS HIDE
const RemainingActionSection = styled.div`
  display: flex;
  flex-direction: column;
  border-top: solid 1px #dae1e7;
  padding: 16px;
  justify-content: center;
`;

const RemainingActionItem = styled.div`
  display: flex;
  align-items: center;
  height: 40px;
  margin: auto 0;
  & :first-child {
    margin-right: 8px;
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
  padding: 0 16px;
  color: #222b45;
`;

const CategoryTitle = styled(NoMarginText)`
  font-size: 18px;
  font-weight: bold;
`;

const Title = styled(NoMarginText)`
  font-size: 15px;
  font-weight: 600;
`;

const Caption = styled(NoMarginText)`
  font-size: 12px;
  grid-area: 2/2 / 3/3
  color: ${props => (props.eventSelected ? 'white' : '#8f9bb3')};
`;

const TinyBold = styled(NoMarginText)`
  font-size: 10px;
  font-weight: bold;
  text-align: right;
  margin-right: 8px;
  grid-area: 1/3/2/4;
  color: ${props => (props.eventSelected ? 'white' : '#8f9bb3')};

`;

const StatusDot = styled.div`
  width: 8px;
  height: 8px;
  border-radius: 6px;
  background: #fe9f43;
  margin: auto;
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
  -ms-overflow-style: none;  /* IE 10+ */
  &::-webkit-scrollbar {
    width: 0px;
    background: transparent; /* Chrome/Safari/Webkit */
  }
`;

const EventListItem = styled.div`
  display: grid;
  grid-template: 24px 20px / 20px auto 56px;
  background-image: ${props => (props.eventSelected ? 'linear-gradient(283deg, #0a54c5, #3266ff)' : 'none')};
  width: 100%;
  padding: 16px;
  border-bottom: solid 1px #dae1e7;
  cursor: pointer;
  color: ${props => (props.eventSelected ? 'white' : '#222b45')};
`;


const EventListContainer = styled.div`
  display: grid;
  overflow: hidden;
  grid-template-rows: 70px 1fr;
  grid-row: 1/2;
  grid-column: 2/3;
  flex-direction: column;
  box-shadow: 2px 0 6px 0 rgba(209, 209, 209, 0.5);
  background: white;
  z-index: 2;
  @media (max-width: 480px) {
    grid-column: 1/2;
    width: 100vw;
    height: 100%;
    display: ${props => props.hasEvent? 'none' : props.expanding? 'none' : null};
  }
`;

const OverallContainer = styled.div`
  grid-row: 1/2;
  display: flex;
  flex-direction: column;
  justify-content: space-between;
  width: 260px;
  box-shadow: 2px 0 10px 0 rgba(0, 0, 0, 0.05);
  background: white;
  z-index: 3;
  border-radius: 10px 0 0 10px;
  @media screen and (max-width: 480px) {
    width: 100vw;
    height: 100%;
    grid-column: 1/2;
    display: ${props => props.hasEvent? 'none' : props.expanding? null : 'none'};
  }
`;

const EventCardContainer = styled.div`  
  grid-column: 3/4;
  grid-row: 1/2;
  overflow: hidden;
  @media (max-width: 480px) {
    grid-column: 1/2
    width: 100vw;
    height: 100%;
    d/isplay: ${props => props.hasEvent ? null : 'none'}
  }
`;


const categories = ['ALL', 'DRAFT', 'SENT',]
const categoryIcons = [<InboxFill />, <Edit />, <PaperPlane />]

function SmsPage(props) {
  const { getEvents, filterEvents, currentKey, createEvent, saveEvent, getClinicSettings, getClinicRemaining, setSelectedEvent, selectedEvent, selectedEventId, editingEvent, events, remaining } = props;
  const [expanding, setExpanding] = useState(false)
  const [hasEvent, setHasEvent] = useState(false)

  useEffect(() => {
    setExpanding(false)
  }, [currentKey])


  useEffect(() => {
    getClinicSettings()
    getEvents()
    getClinicRemaining()
    // eslint-disable-next-line
  }, [])

  useLayoutEffect(() => {
    setHasEvent(selectedEvent !== null)
  }, [selectedEvent])

  const isDiff = (o1, o2) => {
    if (o1 === null || o2 === null) return false
    if (o1.metadata.template !== o2.metadata.template) return true
   
    const newApp = o1.metadata.selectedAppointments.map(app => app.id)
    const oldApp = o2.metadata.selectedAppointments.map(app => app.id)
    if(!isEqual(newApp, oldApp)) return true
    if(o1.title !== o2.title) return true
    
    return false
  }

  const handleSelectionChanging = item => {
    // if current(previous) item is able to be posted or put
    if (isDiff(editingEvent, selectedEvent)) saveEvent(editingEvent)

    // else just changing
    else setSelectedEvent(item)
  }
 
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
                <div style={{display: 'flex', alignItems: 'center', justifyContent: 'center'}}>
                  <Edit2 />
                  <NoMarginText>CREATE</NoMarginText>
                </div>
              </StyledLargerButton>
            </ButtonBox>
            {categories.map((category, i) => (
              <MenuItem 
                key={category} 
                selected={currentKey === category}  
                onClick={() => filterEvents(category)}>
                {categoryIcons[i]}
                <MenuName>{category}</MenuName>
              </MenuItem>  
            ))}
          </CategoryContainer>
          <BalanceContainer>
            <Remaining>Balanace: {remaining}</Remaining>
            <RemainingActionSection>
              <RemainingActionItem>
                <GiftFill />
                <ActionName>Add Value</ActionName>
              </RemainingActionItem>
              <RemainingActionItem>
                <AwardFill />
                <ActionName>Purchase History</ActionName>
              </RemainingActionItem>
            </RemainingActionSection>
          </BalanceContainer>
        </OverallContainer>
        <EventListContainer expanding={expanding} hasEvent={hasEvent}>
          <CategoryTitleContainer>
            <Button
              type="link"
              icon={<MenuIcon style={{ margin: 'auto 16px auto 0' }} />}
              onClick={() => setExpanding(true)}
            />
            <CategoryTitle>{currentKey}</CategoryTitle>
          </CategoryTitleContainer>
          <EventList>
            {events.map(item => (
              <EventListItem
                eventSelected={selectedEvent && (item.id !== null? selectedEventId === item.id : selectedEventId === item.tempId)}
                key={item.id !== null ? item.id : item.tempId}
                onClick={() => handleSelectionChanging(item)}
              >
                <StatusDot style={item.status === 'draft'? { gridRow: '1/2', gridColumn: '1/2'}: {display: 'none'}} />
                
                <Title style={{ gridRow: '1/2', gridColumn: '2/3'}}>{item.title}</Title>
            
            
                <Caption eventSelected={selectedEvent && (item.id !== null? selectedEventId === item.id : selectedEventId === item.tempId)}>
                  {item.status === 'completed'? `${item.createdBy}已傳送${item.sms.length}則`: ''}
                </Caption>
                
                <TinyBold eventSelected={selectedEvent && (item.id !== null? selectedEventId === item.id : selectedEventId === item.tempId)}>
                  {(() => {
                    if (moment().isSame(item.modifiedDate  , 'day')) return moment(item.modifiedDate).format('HH:mm')
                    else {
                      if(moment().startOf('date').add(-1, 'days').isSame(item.modifiedDate  , 'day')) return 'yesterday'
                      else return moment(item.modifiedDate).format('MM/DD') 
                    }
                  })()}
                </TinyBold>
              </EventListItem>
              ))}
          </EventList>
        </EventListContainer>
        <EventCardContainer hasEvent={hasEvent}>
          <EventCard />
        </EventCardContainer>
      </RootConatiner>
    </Container>
  );
}

const mapStateToProps = ({smsPageReducer}) => ({
  events: smsPageReducer.event.events,
  selectedEventId: smsPageReducer.event.selectedEventId,
  selectedEvent: smsPageReducer.event.selectedEvent,
  editingEvent: smsPageReducer.event.editingEvent,
  clinicName: smsPageReducer.event.clinicName,
  remaining: smsPageReducer.event.remaining,
  currentKey: smsPageReducer.event.currentKey,
})

// doctors: extractDoctorsFromUser(homePageReducer.user.users),
const mapDispatchToProps = {
  getEvents,
  getClinicRemaining,
  setSelectedEvent,
  createEvent,
  saveEvent,
  filterEvents,
  getClinicSettings,
};

export default connect(mapStateToProps, mapDispatchToProps)(SmsPage);
