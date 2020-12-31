import { combineReducers } from 'redux';
import nhiIndex from './nhiIndex';
import common from './common';

const nhiIndexPageReducer = combineReducers({
  common,
  nhiIndex,
});

export default nhiIndexPageReducer;
