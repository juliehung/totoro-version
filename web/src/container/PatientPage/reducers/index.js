import { combineReducers } from 'redux';
import common from './common';
import patient from './patient';
import treatmentProcedure from './treatmentProcedure';
import medicalRecord from './medicalRecord';
import nhiExtendPatient from './nhiExtendPatient';
import nhiPatientStatus from './nhiPatientStatus';
import appointment from './appointment';
import searchPatient from './searchPatient';
import disposal from './disposal';
import docNpHistory from './docNpHistory';

const patientPageReducer = combineReducers({
  common,
  patient,
  treatmentProcedure,
  medicalRecord,
  nhiExtendPatient,
  nhiPatientStatus,
  appointment,
  searchPatient,
  disposal,
  docNpHistory,
});

export default patientPageReducer;
