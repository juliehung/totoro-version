import produce from 'immer';
import { GET_NHI_EXTEND_PATIENT, GET_NHI_EXTEND_PATIENT_SUCCESS } from '../constant';

const initState = {
  loading: false,
  nhiExtendPatient: undefined,
};

/* eslint-disable default-case, no-param-reassign */
const nhiExtendPatient = (state = initState, action) =>
  produce(state, draft => {
    switch (action.type) {
      case GET_NHI_EXTEND_PATIENT:
        draft.loading = true;
        break;
      case GET_NHI_EXTEND_PATIENT_SUCCESS:
        draft.loading = false;
        draft.nhiExtendPatient = action.nhiExtendPatient;
        break;
      default:
        break;
    }
  });

export default nhiExtendPatient;
