import { combineReducers } from 'redux';
import homePageReducer from './container/Home/reducer';
import appointmentPageReducer from './container/AppointmentPage/reducers';
import loginPageReducer from './container/LoginPage/reducers';
import questionnairePageReducer from './container/QuestionnairePage/reducers';
import registrationPageReducer from './container/RegistrationPage/reducers';

const rootReducer = combineReducers({
  homePageReducer,
  appointmentPageReducer,
  loginPageReducer,
  questionnairePageReducer,
  registrationPageReducer,
});

export default rootReducer;
