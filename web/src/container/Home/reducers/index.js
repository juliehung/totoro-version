import { combineReducers } from 'redux';

import account from './account';
import user from './user';
import settings from './settings';
import xray from './xray';

const homePageReducer = combineReducers({
  account,
  user,
  settings,
  xray,
});

export default homePageReducer;
