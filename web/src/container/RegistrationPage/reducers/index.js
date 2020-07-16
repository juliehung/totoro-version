import { combineReducers } from 'redux';
import registration from './registration';
import drawer from './drawer';

const registrationPageReducer = combineReducers({
  registration,
  drawer,
});

export default registrationPageReducer;
