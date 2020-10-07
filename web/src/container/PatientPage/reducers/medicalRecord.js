import produce from 'immer';
import { GET_ACCUMULATED_MEDICAL_RECORD, GET_ACCUMULATED_MEDICAL_RECORD_SUCCESS } from '../constant';

const initState = {
  loading: false,
  nhiAccumulatedMedicalRecords: [],
};

/* eslint-disable default-case, no-param-reassign */
const medicalRecord = (state = initState, action) =>
  produce(state, draft => {
    switch (action.type) {
      case GET_ACCUMULATED_MEDICAL_RECORD:
        draft.loading = true;
        break;
      case GET_ACCUMULATED_MEDICAL_RECORD_SUCCESS:
        draft.loading = false;
        draft.nhiAccumulatedMedicalRecords = action.nhiAccumulatedMedicalRecords;
        break;
      default:
        break;
    }
  });

export default medicalRecord;
