import { combineReducers } from 'redux';
import homePageReducer from './container/Home/reducers';
import appointmentPageReducer from './container/AppointmentPage/reducers';
import loginPageReducer from './container/LoginPage/reducers';
import questionnairePageReducer from './container/QuestionnairePage/reducers';
import registrationPageReducer from './container/RegistrationPage/reducers';
import shiftPageReducer from './container/ShiftPage/reducers';
import smsPageReducer from './container/SmsPage/reducers';
import settingPageReducer from './container/SettingPage/reducers';
import nhiIndexPageReducer from './container/NhiIndexPage/reducers';
import patientPageReducer from './container/PatientPage/reducers';

const rootReducer = combineReducers({
  homePageReducer,
  appointmentPageReducer,
  loginPageReducer,
  questionnairePageReducer,
  registrationPageReducer,
  shiftPageReducer,
  smsPageReducer,
  settingPageReducer,
  nhiIndexPageReducer,
  patientPageReducer,
});

export default rootReducer;
