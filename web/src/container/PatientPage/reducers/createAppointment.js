import produce from 'immer';
import {
  CHANGE_APPOINTMENT_MODAL_VISIBLE,
  CHANGE_APPOINTMENT_NOTE,
  CHANGE_APPOINTMENT_SPECIAL_NOTE,
  CHANGE_APPOINTMENT_DURATION,
  CHANGE_APPOINTMENT_DOCTOR,
  CHANGE_APPOINTMENT_EXPECT_ARRIVAL_TIME,
  CHANGE_APPOINTMENT_EXPECT_ARRIVAL_DATE,
  CHECK_CONFIRM_BUTTON_DISABLE,
  CHANGE_APPOINTMENT_COLOR,
  CREATE_APPOINTMENT,
  CREATE_APPOINTMENT_SUCCESS,
  ON_LEAVE_PAGE,
} from '../constant';

const initState = {
  modalVisible: false,
  disabled: false,
  createSuccess: false,
  loading: false,
  appointment: {
    expectedArrivalDate: undefined,
    expectedArrivalTime: undefined,
    doctorId: undefined,
    duration: undefined,
    note: '',
    specialNote: [],
    colorId: 0,
  },
};

/* eslint-disable default-case, no-param-reassign */
const createAppointment = (state = initState, action) =>
  produce(state, draft => {
    switch (action.type) {
      case CHANGE_APPOINTMENT_MODAL_VISIBLE:
        draft.modalVisible = action.visible;
        if (!draft.modalVisible) {
          draft.appointment = initState.appointment;
        }
        break;
      case CHANGE_APPOINTMENT_NOTE:
        draft.appointment.note = action.note;
        break;
      case CHANGE_APPOINTMENT_SPECIAL_NOTE:
        draft.appointment.specialNote = action.value;
        break;
      case CHANGE_APPOINTMENT_DURATION:
        draft.appointment.duration = action.duration;
        break;
      case CHANGE_APPOINTMENT_DOCTOR:
        draft.appointment.doctorId = action.doctorId;
        break;
      case CHANGE_APPOINTMENT_EXPECT_ARRIVAL_TIME:
        draft.appointment.expectedArrivalTime = action.time;
        break;
      case CHANGE_APPOINTMENT_EXPECT_ARRIVAL_DATE:
        draft.appointment.expectedArrivalDate = action.date;
        break;
      case CHANGE_APPOINTMENT_COLOR:
        draft.appointment.colorId = action.colorId;
        break;
      case CHECK_CONFIRM_BUTTON_DISABLE:
        if (
          state.appointment.expectedArrivalDate &&
          state.appointment.expectedArrivalTime &&
          state.appointment.doctorId &&
          state.appointment.duration
        ) {
          draft.disabled = false;
          return;
        }
        draft.disabled = true;
        break;
      case CREATE_APPOINTMENT:
        draft.loading = true;
        draft.createSuccess = false;
        break;
      case ON_LEAVE_PAGE:
        draft.createSuccess = false;
        break;
      case CREATE_APPOINTMENT_SUCCESS:
        draft.loading = false;
        draft.createSuccess = true;
        break;
      default:
        break;
    }
  });

export default createAppointment;
