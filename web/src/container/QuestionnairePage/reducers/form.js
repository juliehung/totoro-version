import produce from 'immer';
import { parseDataToDisplayFormPage } from '../utils/parseDataToDisplayFormPage';

import { GET_DOC_SUCCESS } from '../constant';

const initState = {
  createDate: undefined,
  patient: { name: undefined },
  esign: undefined,
};

export const initialState = { ...initState };

/* eslint-disable default-case, no-param-reassign */
const form = (state = initialState, action) =>
  produce(state, draft => {
    switch (action.type) {
      case GET_DOC_SUCCESS:
        draft.createDate = action.doc.createDate;
        draft.patient = parseDataToDisplayFormPage(action.doc.patient);
        draft.esign = action.doc.esign ? `data:image/png;base64, ${action.doc.esign.base64}` : undefined;
        break;
      default:
        break;
    }
  });

export default form;
