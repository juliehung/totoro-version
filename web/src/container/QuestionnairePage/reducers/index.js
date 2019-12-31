import { combineReducers } from 'redux';

import flow from './flow';
import data from './data';

const questionnairePageReducer = combineReducers({
  flow,
  data,
});

export default questionnairePageReducer;
