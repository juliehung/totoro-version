import { combineReducers } from 'redux';
import registration from './registration';
import drawer from './drawer';
import xray from './xray';

const registrationPageReducer = combineReducers({
  registration,
  drawer,
  xray,
});

export default registrationPageReducer;
