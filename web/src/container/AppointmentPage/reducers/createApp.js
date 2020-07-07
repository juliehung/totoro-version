import produce from 'immer';
import {
  CHANGE_CREATE_APPOINTMENT_VISIBLE,
  SEARCH_PATIENTS_SUCCESS,
  CHANGE_PATIENT_SELECTED,
  GET_PATIENT_SUCCESS_CREATE_APP,
  CHANGE_CREATE_APP_NOTE,
  CHANGE_CREATE_APP_DURATION,
  CHANGE_CREATE_APP_DEFAULT_DURATION,
  CHANGE_CREATE_APP_DOCTOR,
  CHANGE_CREATE_APP_DEFAULT_DOCTOR,
  CHANGE_CREATE_APP_EXPECTED_ARRIVAL_DATE,
  CHANGE_CREATE_APP_EXPECTED_ARRIVAL_TIME,
  CHANGE_CREATE_APP_SPECIAL_NOTE,
  CREATE_APPOINTMENT,
  CREATE_APPOINTMENT_SUCCESS,
  CHECK_CONFIRM_BUTTON_DISABLE,
  CHANGE_CREATE_APP_PATIENT_NAME,
  CHANGE_CREATE_APP_PATIENT_PHONE,
  CHANGE_CREATE_APP_PATIENT_NATIONAL_ID,
  CHANGE_CREATE_APP_PATIENT_BIRTH,
  CREATE_PATIENT_SUCCESS,
  CHANGE_PATIENT_SEARCH_MODE,
  patientSearchMode,
} from '../constant';

const defaultSearchMode = patientSearchMode.birth;

const initState = {
  loading: false,
  visible: false,
  disabled: true,
  patientSelected: false,
  searchPatients: [],
  selectedPatient: undefined,
  searchMode: defaultSearchMode,
  createAppSuccess: false,
  appointment: {
    patientId: undefined,
    expectedArrivalDate: undefined,
    expectedArrivalTime: undefined,
    doctorId: undefined,
    duration: undefined,
    note: undefined,
    specialNote: [],
  },
  patient: {
    name: undefined,
    phone: undefined,
    nationalId: undefined,
    birth: undefined,
    newPatient: false,
    patientIdentity: {
      code: 'H10',
      freeBurden: false,
      id: 1,
      name: '一般門診',
    },
    vip: '無',
  },
  default: { duration: undefined, doctorId: undefined },
  disposalId: undefined,
};

export const initialState = { ...initState };

/* eslint-disable default-case, no-param-reassign */
const createApp = (state = initialState, action) =>
  produce(state, draft => {
    switch (action.type) {
      case CHANGE_CREATE_APPOINTMENT_VISIBLE:
        draft.visible = action.visible;
        if (!action.visible) {
          draft.patientSelected = initState.patientSelected;
          draft.selectedPatient = initState.selectedPatient;
          draft.appointment = {
            ...initState.appointment,
            duration: state.default.duration,
            doctorId: state.default.doctorId,
          };
          draft.patient = initState.patient;
          draft.loading = initState.loading;
          draft.searchPatients = initState.searchPatients;
        }
        draft.createAppSuccess = initState.createAppSuccess;
        break;
      case SEARCH_PATIENTS_SUCCESS:
        draft.searchPatients = action.patients;
        break;
      case CHANGE_PATIENT_SELECTED:
        draft.patientSelected = action.selected;
        break;
      case GET_PATIENT_SUCCESS_CREATE_APP:
        draft.selectedPatient = action.patient;
        draft.appointment.patientId = action.patient.id;
        break;
      case CHANGE_CREATE_APP_NOTE:
        draft.appointment.note = action.e.target.value;
        break;
      case CHANGE_CREATE_APP_DEFAULT_DURATION:
        draft.default.duration = action.duration;
        draft.appointment.duration = action.duration;
        break;
      case CHANGE_CREATE_APP_DURATION:
        if (action.duration) {
          draft.appointment.duration = action.duration;
        }
        break;
      case CHANGE_CREATE_APP_DOCTOR:
        draft.appointment.doctorId = action.doctorId;
        break;
      case CHANGE_CREATE_APP_DEFAULT_DOCTOR:
        draft.default.doctorId = action.doctorId;
        draft.appointment.doctorId = action.doctorId;
        break;
      case CHANGE_CREATE_APP_EXPECTED_ARRIVAL_DATE:
        draft.appointment.expectedArrivalDate = action.date;
        break;
      case CHANGE_CREATE_APP_EXPECTED_ARRIVAL_TIME:
        draft.appointment.expectedArrivalTime = action.time;
        break;
      case CHANGE_CREATE_APP_SPECIAL_NOTE:
        draft.appointment.specialNote = action.value;
        break;
      case CREATE_APPOINTMENT:
        draft.loading = true;
        break;
      case CREATE_APPOINTMENT_SUCCESS:
        draft.createAppSuccess = true;
        break;
      case CHECK_CONFIRM_BUTTON_DISABLE:
        if (
          state.appointment.expectedArrivalDate &&
          state.appointment.expectedArrivalTime &&
          state.appointment.doctorId &&
          state.appointment.duration
        ) {
          if (state.appointment.patientId) {
            draft.disabled = false;
            return;
          } else if (
            draft.patient.name &&
            draft.patient.name.length > 0 &&
            draft.patient.phone &&
            draft.patient.phone.length > 0
          ) {
            draft.disabled = false;
            return;
          }
        }
        draft.disabled = true;
        break;
      case CHANGE_CREATE_APP_PATIENT_NAME:
        draft.patient.name = action.name;
        break;
      case CHANGE_CREATE_APP_PATIENT_PHONE:
        draft.patient.phone = action.phone;
        break;
      case CHANGE_CREATE_APP_PATIENT_NATIONAL_ID:
        draft.patient.nationalId = action.nationalId;
        break;
      case CHANGE_CREATE_APP_PATIENT_BIRTH:
        draft.patient.birth = action.birth;
        break;
      case CREATE_PATIENT_SUCCESS:
        draft.appointment.patientId = action.id;
        break;
      case CHANGE_PATIENT_SEARCH_MODE:
        draft.searchMode = action.mode;
        break;
      default:
        break;
    }
  });

export default createApp;
