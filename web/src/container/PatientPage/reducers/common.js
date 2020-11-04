import produce from 'immer';
import { INIT_PATIENT_DETAIL, PATIENT_NOT_FOUND, GET_PATIENT_SUCCESS } from '../constant';
const initState = {
  isPatientNotFound: false,
  loading: false,
};

/* eslint-disable default-case, no-param-reassign */
const common = (state = initState, action) =>
  produce(state, draft => {
    switch (action.type) {
      case INIT_PATIENT_DETAIL:
        draft.isPatientNotFound = false;
        draft.loading = true;
        break;
      case PATIENT_NOT_FOUND:
        draft.isPatientNotFound = true;
        break;
      case GET_PATIENT_SUCCESS:
        draft.loading = false;
        break;
      default:
        break;
    }
  });

export default common;
