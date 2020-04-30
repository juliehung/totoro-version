import { combineReducers } from 'redux';
import event from './event';
import appointment from './appointment';
import user from './user';

const smsPageReducer = combineReducers({
    event,
    appointment,
    user
});

export default smsPageReducer;
