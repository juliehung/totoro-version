import { fork } from 'redux-saga/effects';
import appointmentPage from './container/AppointmentPage/sagas';
import loginPage from './container/LoginPage/sagas';
import registrationPage from './container/RegistrationPage/sagas';
import questionnairePage from './container/QuestionnairePage/sagas';

export function* rootSaga() {
  yield fork(appointmentPage);
  yield fork(loginPage);
  yield fork(registrationPage);
  yield fork(questionnairePage);
}
