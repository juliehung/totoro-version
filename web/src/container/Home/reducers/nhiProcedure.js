import produce from 'immer';
import { GET_NHI_PROCEDURE_SUCCESS } from '../constant';

const initState = { nhiProcedure: [] };

/* eslint-disable default-case, no-param-reassign */
const account = (state = initState, action) =>
  produce(state, draft => {
    switch (action.type) {
      case GET_NHI_PROCEDURE_SUCCESS:
        draft.nhiProcedure = action.nhiProcedure;
        break;
      default:
        break;
    }
  });

export default account;
