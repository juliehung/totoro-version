import React from 'react';
import { connect } from 'react-redux';
import EventReadOnly from './EventReadOnly'
import EventEditing from './EventEditing'
import EventCardEmpty from './EventCardEmpty';

function EventCard(props) {
  const { selectedEvent } = props
  return ( 
    selectedEvent === null ? <EventCardEmpty /> :
      (selectedEvent.isEdit ? <EventEditing /> : <EventReadOnly />)
  );
}
const mapStateToProps = ({ smsPageReducer }) => ({ 
  selectedEvent: smsPageReducer.event.selectedEvent 
});

const mapDispatchToProps = {};

export default connect(mapStateToProps, mapDispatchToProps)(EventCard);
