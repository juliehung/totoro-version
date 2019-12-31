import { combineReducers } from 'redux';
import login from './login';

const loginPageReducer = combineReducers({
  login,
});

export default loginPageReducer;
