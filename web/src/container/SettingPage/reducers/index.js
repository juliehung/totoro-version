import { combineReducers } from 'redux';
import configurations from './configurations';

const settingPageReducer = combineReducers({
  configurations,
});

export default settingPageReducer;
