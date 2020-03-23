import { fork } from 'redux-saga/effects';
import homePage from './container/Home/sagas';
import appointmentPage from './container/AppointmentPage/sagas';
import loginPage from './container/LoginPage/sagas';
import registrationPage from './container/RegistrationPage/sagas';
import questionnairePage from './container/QuestionnairePage/sagas';
import shiftPage from './container/ShiftPage/sagas';

export function* rootSaga() {
  yield fork(homePage);
  yield fork(appointmentPage);
  yield fork(loginPage);
  yield fork(registrationPage);
  yield fork(questionnairePage);
  yield fork(shiftPage);
}
