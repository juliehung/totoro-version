import { combineReducers } from 'redux';
import homePageReducer from './container/Home/reducers';
import appointmentPageReducer from './container/AppointmentPage/reducers';
import loginPageReducer from './container/LoginPage/reducers';
import questionnairePageReducer from './container/QuestionnairePage/reducers';
import registrationPageReducer from './container/RegistrationPage/reducers';
import shiftPageReducer from './container/ShiftPage/reducers';
import smsPageReducer from './container/SmsPage/reducers';

const rootReducer = combineReducers({
  homePageReducer,
  appointmentPageReducer,
  loginPageReducer,
  questionnairePageReducer,
  registrationPageReducer,
  shiftPageReducer,
  smsPageReducer,
});

export default rootReducer;
