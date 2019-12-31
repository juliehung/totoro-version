import { combineReducers } from 'redux';
import calendar from './calendar';
import print from './print';
import drop from './drop';
import createApp from './createApp';
import editApp from './editApp';
import createCalendarEvt from './createCalendarEvt';
import editCalendarEvt from './editCalendarEvt';
import todo from './todo';
import settings from './settings';

const appointmentPageReducer = combineReducers({
  calendar,
  print,
  drop,
  createApp,
  editApp,
  createCalendarEvt,
  editCalendarEvt,
  todo,
  settings,
});

export default appointmentPageReducer;
