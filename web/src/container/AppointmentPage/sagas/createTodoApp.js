import { CREATE_TODO_APP, DisposalStatus } from '../constant';
import { call, put, take, select } from 'redux-saga/effects';
import Appointment from '../../../models/appointment';
import { createTodoAppSuccess } from '../actions';
import { handleAppointmentForApi } from '../utils/handleAppointmentForApi';
import Disposal from '../../../models/disposal';

export function* createTodoApp() {
  while (true) {
    try {
      yield take(CREATE_TODO_APP);
      const getAppointment = state => state.appointmentPageReducer.createApp.appointment;
      const appointment = yield select(getAppointment);
      const data = handleAppointmentForApi(appointment);
      yield call(Appointment.create, data);
      const getDisposalId = state => state.appointmentPageReducer.createApp.disposalId;
      const disposalId = yield select(getDisposalId);
      yield call(Disposal.put, { id: disposalId, status: DisposalStatus.MADE_APPT });
      yield put(createTodoAppSuccess(disposalId));
    } catch (error) {
      // ignore
    }
  }
}
