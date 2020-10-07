import { combineReducers } from 'redux';
import common from './common';
import patient from './patient';
import treatmentProcedure from './treatmentProcedure';
import medicalRecord from './medicalRecord';
import nhiExtendPatient from './nhiExtendPatient';
import appointment from './appointment';
import createAppointment from './createAppointment';
import searchPatient from './searchPatient';

const patientPageReducer = combineReducers({
  common,
  patient,
  treatmentProcedure,
  medicalRecord,
  nhiExtendPatient,
  appointment,
  createAppointment,
  searchPatient,
});

export default patientPageReducer;
