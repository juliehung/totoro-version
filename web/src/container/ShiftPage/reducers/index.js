import { combineReducers } from 'redux';
import shift from './shift';
import defaultShift from './defaultShift';
import resourceColor from './resourceColor';
import copy from './copy';

const shiftPageReducer = combineReducers({
  shift,
  defaultShift,
  resourceColor,
  copy,
});

export default shiftPageReducer;
