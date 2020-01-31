import { combineReducers } from 'redux';

import account from './account';

const homePageReducer = combineReducers({
  account,
});

export default homePageReducer;
