import produce from 'immer';
import { GET_DOC_NP_HISTORY_SUCCESS } from '../constant';

const initState = {
  docNps: [],
};

/* eslint-disable default-case, no-param-reassign */
const docNpHistory = (state = initState, action) =>
  produce(state, draft => {
    switch (action.type) {
      case GET_DOC_NP_HISTORY_SUCCESS:
        draft.docNps = action.docNps;
        break;
      default:
        break;
    }
  });

export default docNpHistory;
