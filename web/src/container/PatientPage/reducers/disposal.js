import produce from 'immer';
import { CHANGE_TREATMENT_LIST_MODAL_VISIBLE, GET_DISPOSAL, GET_DISPOSAL_SUCCESS } from '../constant';

const initState = {
  treatmentListModalVisible: false,
  loading: false,
  disposals: [],
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
      default:
        break;
    }
  });

export default disposal;
