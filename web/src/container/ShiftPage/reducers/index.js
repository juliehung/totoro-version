import { combineReducers } from 'redux';
import shift from './shift';
import defaultShift from './defaultShift';

const shiftPageReducer = combineReducers({
  shift,
  defaultShift,
});

export default shiftPageReducer;
