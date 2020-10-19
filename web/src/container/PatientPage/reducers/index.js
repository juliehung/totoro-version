import { combineReducers } from 'redux';
import common from './common';
import patient from './patient';
import treatmentProcedure from './treatmentProcedure';
import medicalRecord from './medicalRecord';
import nhiExtendPatient from './nhiExtendPatient';
import appointment from './appointment';
import createAppointment from './createAppointment';
import searchPatient from './searchPatient';
import disposal from './disposal';
import docNpHistory from './docNpHistory';

const patientPageReducer = combineReducers({
  common,
  patient,
  treatmentProcedure,
  medicalRecord,
  nhiExtendPatient,
  appointment,
  createAppointment,
  searchPatient,
  disposal,
  docNpHistory,
});

export default patientPageReducer;
