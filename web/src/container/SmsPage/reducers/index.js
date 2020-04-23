import { combineReducers } from 'redux';
import event from './event';
import appointment from './appointment';

const smsPageReducer = combineReducers({
    event,
    appointment,
});

export default smsPageReducer;
