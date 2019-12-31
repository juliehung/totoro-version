import produce from 'immer';
import moment from 'moment';
import {
  CHANGE_EDIT_APP_MODAL_VISIBLE,
  INSERT_APP_TO_EDIT_APP_MODAL,
  CHECK_EDIT_APP_CONFIRM_MODAL_BUTTON_DISABLE,
  GET_PATIENT_SUCCESS_EDIT_APP,
  DELETE_APPOINTMENT_START,
  DELETE_APPOINTMENT_SUCCESS,
  CHANGE_EDIT_APP_CONFIRM_DELETE,
  CHANGE_EDIT_APP_EXPECTED_ARRIVAL_DATE,
  CHANGE_EDIT_APP_EXPECTED_ARRIVAL_TIME,
  CHANGE_EDIT_APP_DOCTOR,
  CHANGE_EDIT_APP_DURATION,
  CHANGE_EDIT_APP_NOTE,
  CHANGE_EDIT_APP_SPECIAL_NOTE,
  EDIT_APPOINTMENT_START,
  EDIT_APPOINTMENT_SUCCESS,
} from '../constant';

const initState = {
  loading: false,
  deleteLoading: false,
  visible: false,
  disabled: true,
  deleteAppSuccess: false,
  editAppSuccess: false,
  confirmDelete: false,
  appointment: {
    id: undefined,
    patientId: undefined,
    expectedArrivalDate: undefined,
    expectedArrivalTime: undefined,
    doctorId: undefined,
    requiredTreatmentTime: undefined,
    note: undefined,
    specialNote: [],
  },
  patient: undefined,
};

export const initialState = { ...initState };

/* eslint-disable default-case, no-param-reassign */
const editApp = (state = initialState, action) =>
  produce(state, draft => {
    switch (action.type) {
      case CHANGE_EDIT_APP_MODAL_VISIBLE:
        draft.visible = action.visible;
        if (!action.visible) {
          draft.appointment = initState.appointment;
          draft.patient = initState.patient;
          draft.loading = initState.loading;
          draft.deleteLoading = initState.deleteLoading;
          draft.deleteAppSuccess = initState.deleteAppSuccess;
          draft.editAppSuccess = initState.editAppSuccess;
          draft.confirmDelete = initState.confirmDelete;
        }
        draft.createAppSuccess = initState.createAppSuccess;
        break;
      case INSERT_APP_TO_EDIT_APP_MODAL:
        draft.appointment.id = action.app.id;
        draft.appointment.patientId = action.app.patientId;
        draft.appointment.expectedArrivalDate = moment(action.app.expectedArrivalTime);
        draft.appointment.expectedArrivalTime = moment(action.app.expectedArrivalTime);
        draft.appointment.doctorId = action.app.doctor.id;
        draft.appointment.requiredTreatmentTime = action.app.requiredTreatmentTime;
        draft.appointment.note = action.app.note;
        draft.appointment.specialNote = [];
        if (action.app.microscope) {
          draft.appointment.specialNote.push('micro');
        }
        if (action.app.baseFloor) {
          draft.appointment.specialNote.push('baseFloor');
        }
        break;
      case GET_PATIENT_SUCCESS_EDIT_APP:
        draft.patient = action.patient;
        break;
      case DELETE_APPOINTMENT_START:
        draft.deleteLoading = true;
        break;
      case DELETE_APPOINTMENT_SUCCESS:
        draft.deleteAppSuccess = true;
        break;
      case CHANGE_EDIT_APP_NOTE:
        draft.appointment.note = action.e.target.value;
        break;
      case CHANGE_EDIT_APP_DURATION:
        draft.appointment.requiredTreatmentTime = action.duration;
        break;
      case CHANGE_EDIT_APP_DOCTOR:
        draft.appointment.doctorId = action.doctorId;
        break;
      case CHANGE_EDIT_APP_EXPECTED_ARRIVAL_DATE:
        draft.appointment.expectedArrivalDate = action.date;
        break;
      case CHANGE_EDIT_APP_EXPECTED_ARRIVAL_TIME:
        draft.appointment.expectedArrivalTime = action.time;
        break;
      case CHANGE_EDIT_APP_SPECIAL_NOTE:
        draft.appointment.specialNote = action.value;
        break;

      case CHECK_EDIT_APP_CONFIRM_MODAL_BUTTON_DISABLE:
        if (
          state.appointment.expectedArrivalDate &&
          state.appointment.expectedArrivalTime &&
          state.appointment.doctorId &&
          state.appointment.requiredTreatmentTime &&
          state.appointment.patientId
        ) {
          draft.disabled = false;
          return;
        }
        draft.disabled = true;
        break;
      case EDIT_APPOINTMENT_START:
        draft.loading = true;
        break;
      case EDIT_APPOINTMENT_SUCCESS:
        draft.editAppSuccess = true;
        break;
      case CHANGE_EDIT_APP_CONFIRM_DELETE:
        draft.confirmDelete = action.confirm;
        break;
      default:
        break;
    }
  });

export default editApp;
