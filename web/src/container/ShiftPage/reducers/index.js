import { combineReducers } from 'redux';
import shift from './shift';

const shiftPageReducer = combineReducers({
  shift,
});

export default shiftPageReducer;
