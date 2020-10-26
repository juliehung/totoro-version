import produce from 'immer';
import {
  GET_PATIENT_SUCCESS,
  CHANGE_CLINIC_NOTE,
  ADD_DATE_TO_CLINIC_NOTE,
  RESTORE_CLINIC_NOTE,
  UPDATE_CLINIC_NOTE,
  UPDATE_CLINIC_NOTE_SUCCESS,
  ON_LEAVE_PAGE,
  RESTORE_CLINIC_NOTE_UPDATE_SUCCESS,
} from '../constant';
import { dateStringWithNewLine } from '../utils';

const initState = {
  patient: undefined,
  editedClinicNote: '',
  updateSuccess: false,
  updating: false,
};

/* eslint-disable default-case, no-param-reassign */
const patient = (state = initState, action) =>
  produce(state, draft => {
    switch (action.type) {
      case GET_PATIENT_SUCCESS:
        draft.patient = action.patient;
        draft.editedClinicNote = action.patient?.clinicNote ?? '';
        break;
      case CHANGE_CLINIC_NOTE:
        draft.editedClinicNote = action.clinicNote;
        break;
      case ADD_DATE_TO_CLINIC_NOTE:
        draft.editedClinicNote = dateStringWithNewLine() + state.editedClinicNote;
        break;
      case RESTORE_CLINIC_NOTE:
        draft.editedClinicNote = state.patient?.clinicNote ?? '';
        break;
      case UPDATE_CLINIC_NOTE:
        draft.updating = true;
        draft.updateSuccess = false;
        break;
      case UPDATE_CLINIC_NOTE_SUCCESS:
        draft.updating = false;
        draft.updateSuccess = true;
        draft.patient.clinicNote = action.patient.clinicNote;
        break;
      case RESTORE_CLINIC_NOTE_UPDATE_SUCCESS:
        draft.updateSuccess = false;
        break;
      case ON_LEAVE_PAGE:
        draft.updating = false;
        draft.updateSuccess = false;
        break;
      default:
        break;
    }
  });

export default patient;
