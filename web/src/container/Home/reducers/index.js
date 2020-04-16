import { combineReducers } from 'redux';

import account from './account';
import user from './user';
import settings from './settings';

const homePageReducer = combineReducers({
  account,
  user,
  settings,
});

export default homePageReducer;
