import { fork } from 'redux-saga/effects';
import { watchGetAppointments } from './getAppointment';
import { watchGetPrintAppointments } from './getPrintAppointments';
import { editAppointment, getPatient as getPatientEditApp } from './editAppointment';
import { createAppointment, searchPatients, getPatient, createPatient } from './createAppointment';
import { deleteAppointments } from './deleteAppointment';
import { watchGetCalendarEvents } from './getCalendarEvents';
import { createCalendarEvent } from './createCalendarEvent';
import { watchGetAllEvents } from './getAllEvents';
import { editCalendarEvent } from './editCalendarEvent';
import { deleteCalendarEvent } from './deleteCalendarEvent';
import { popoverCancelApp } from './popoverCancelApp';
import { watchGetShift } from './watchGetShift';

export default function* appointmentPage() {
  yield fork(watchGetAppointments);
  yield fork(watchGetPrintAppointments);
  yield fork(editAppointment);
  yield fork(createAppointment);
  yield fork(searchPatients);
  yield fork(getPatient);
  yield fork(createPatient);
  yield fork(getPatientEditApp);
  yield fork(deleteAppointments);
  yield fork(watchGetCalendarEvents);
  yield fork(createCalendarEvent);
  yield fork(watchGetAllEvents);
  yield fork(editCalendarEvent);
  yield fork(deleteCalendarEvent);
  yield fork(popoverCancelApp);
  yield fork(watchGetShift);
}
