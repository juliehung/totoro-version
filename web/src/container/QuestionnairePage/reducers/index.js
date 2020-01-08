import { combineReducers } from 'redux';

import flow from './flow';
import data from './data';
import form from './form';

const questionnairePageReducer = combineReducers({
  flow,
  data,
  form,
});

export default questionnairePageReducer;
