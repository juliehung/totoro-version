import { combineReducers } from 'redux';

import account from './account';
import user from './user';
import settings from './settings';
import xray from './xray';
import nhiProcedure from './nhiProcedure';

const homePageReducer = combineReducers({
  account,
  user,
  settings,
  xray,
  nhiProcedure,
});

export default homePageReducer;
