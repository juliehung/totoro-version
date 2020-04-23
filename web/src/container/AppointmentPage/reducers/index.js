import { combineReducers } from 'redux';
import calendar from './calendar';
import print from './print';
import drop from './drop';
import createApp from './createApp';
import editApp from './editApp';
import createCalendarEvt from './createCalendarEvt';
import editCalendarEvt from './editCalendarEvt';
import shift from './shift';

const appointmentPageReducer = combineReducers({
  calendar,
  print,
  drop,
  createApp,
  editApp,
  createCalendarEvt,
  editCalendarEvt,
  shift,
});

export default appointmentPageReducer;
