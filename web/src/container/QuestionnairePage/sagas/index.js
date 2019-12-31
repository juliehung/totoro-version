import { fork } from 'redux-saga/effects';
import { nextPage } from './nextPage';
import { prevPage } from './prevPage';
import { gotoPage } from './gotoPage';
import { watchChangeGender } from './gender';
import { watchChangeBloodType } from './bloodType';
import { watchChangeCareer } from './career';
import { watchChangeMarriage } from './marriage';
import { watchChangeEmergencyRelationship } from './emergencyRelationship';
import { watchChangeDoDrugQ } from './doDrugQ';
import { watchChangePregnantQ } from './pregnantQ';
import { watchChangeSmokingQ } from './smokingQ';

export default function* questionnairePage() {
  yield fork(nextPage);
  yield fork(prevPage);
  yield fork(gotoPage);
  yield fork(watchChangeGender);
  yield fork(watchChangeBloodType);
  yield fork(watchChangeCareer);
  yield fork(watchChangeMarriage);
  yield fork(watchChangeEmergencyRelationship);
  yield fork(watchChangeDoDrugQ);
  yield fork(watchChangePregnantQ);
  yield fork(watchChangeSmokingQ);
}
