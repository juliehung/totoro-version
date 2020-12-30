import produce from 'immer';
import {
  CHANGE_TREATMENT_LIST_MODAL_VISIBLE,
  GET_DISPOSAL,
  GET_DISPOSAL_SUCCESS,
  GET_NHI_ICD10_CMS,
  GET_NHI_ICD10_CMS_SUCCESS,
} from '../constant';

const initState = {
  treatmentListModalVisible: false,
  loading: false,
  disposals: [],
  nhiIcd10cms: [],
};

/* eslint-disable default-case, no-param-reassign */
const disposal = (state = initState, action) =>
  produce(state, draft => {
    switch (action.type) {
      case CHANGE_TREATMENT_LIST_MODAL_VISIBLE:
        draft.treatmentListModalVisible = action.visible;
        break;
      case GET_DISPOSAL:
        draft.loading = true;
        break;
      case GET_DISPOSAL_SUCCESS:
        draft.loading = false;
        draft.disposals = action.disposals;
        break;
      case GET_NHI_ICD10_CMS:
        draft.loading = true;
        break;
      case GET_NHI_ICD10_CMS_SUCCESS:
        draft.loading = false;
        draft.nhiIcd10cms = action.nhiIcd10cms;
        break;
      default:
        break;
    }
  });

export default disposal;
