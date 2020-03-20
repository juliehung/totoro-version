import { combineReducers } from 'redux';

import account from './account';
import user from './user';

const homePageReducer = combineReducers({
  account,
  user,
});

export default homePageReducer;
