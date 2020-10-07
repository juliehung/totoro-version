import produce from 'immer';
import {
  GET_PATIENT_SUCCESS,
  CHANGE_CLINIC_NOTE,
  ADD_DATE_TO_CLINIC_NOTE,
  RESTORE_CLINIC_NOTE,
  UPDATE_CLINIC_NOTE_SUCCESS,
  ON_LEAVE_PAGE,
} from '../constant';
import { dateStringWithNewLine } from '../utils';

const initState = {
  patient: undefined,
  editedClinicNote: '',
  updateSuccess: false,
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
      case UPDATE_CLINIC_NOTE_SUCCESS:
        draft.updateSuccess = true;
        break;
      case ON_LEAVE_PAGE:
        draft.updateSuccess = false;
        break;
      default:
        break;
    }
  });

export default patient;
