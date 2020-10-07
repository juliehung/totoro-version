import moment from 'moment';
import { call, take, select, put } from 'redux-saga/effects';
import { patientSearchMode, SEARCH_PATIENTS_START } from '../constant';
import Registration from '../../../models/registration';
import Patient from '../../../models/patient';
import { searchPatientSuccess } from '../actions';

export function* searchPatient() {
  while (true) {
    try {
      const action = yield take(SEARCH_PATIENTS_START);
      const { text } = action;

      let result = [];
      if (!text || text.length === 0) {
        const registrations = yield call(Registration.getBetween, {
          start: moment().startOf('d'),
          end: moment().endOf('d'),
        });

        const patients = registrations
          .filter(r => r.status !== 'CANCEL' && r.registration && r.registration.id)
          .sort((a, b) => moment(b.registration.arrivalTime).unix() - moment(a.registration.arrivalTime).unix())
          .map(r => r.patient);

        const filteredPatients = patients.filter(
          (patient, index, self) => index === self.findIndex(p => p.id === patient.id),
        );
        result = filteredPatients;
      } else {
        const getSearchMode = state => state.patientPageReducer.searchPatient.searchMode;
        const searchMode = yield select(getSearchMode);
        let patients = [];
        switch (searchMode) {
          case patientSearchMode.name:
            patients = yield call(Patient.searchByName, text);
            break;
          case patientSearchMode.birth:
            patients = yield call(Patient.searchByBirth, text);
            break;
          case patientSearchMode.phone:
            patients = yield call(Patient.searchByPhone, text);
            break;
          case patientSearchMode.medical_id:
            patients = yield call(Patient.searchByMedicalId, text);
            break;
          case patientSearchMode.national_id:
            patients = yield call(Patient.searchByNationalId, text);
            break;
          default:
            break;
        }

        result = patients;
      }

      yield put(searchPatientSuccess(result));
    } catch (error) {
      // ignore
    }
  }
}
