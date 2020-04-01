import { combineReducers } from 'redux';
import shift from './shift';
import defaultShift from './defaultShift';
import resourceColor from './resourceColor';

const shiftPageReducer = combineReducers({
  shift,
  defaultShift,
  resourceColor,
});

export default shiftPageReducer;
