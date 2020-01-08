import produce from 'immer';
import { parseDataToDisplayFormPage } from '../utils/parseDataToDisplayFormPage';

import { GET_DOC_SUCCESS } from '../constant';

const initState = {
  createDate: undefined,
  patient: { name: undefined },
};

export const initialState = { ...initState };

/* eslint-disable default-case, no-param-reassign */
const form = (state = initialState, action) =>
  produce(state, draft => {
    switch (action.type) {
      case GET_DOC_SUCCESS:
        draft.createDate = action.doc.createDate;
        draft.patient = parseDataToDisplayFormPage(action.doc.patient);
        break;
      default:
        break;
    }
  });

export default form;
