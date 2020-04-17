import produce from 'immer';
import { GET_ACCOUNT_SUCCESS } from '../constant';

const initState = { data: undefined };

/* eslint-disable default-case, no-param-reassign */
const account = (state = initState, action) =>
  produce(state, draft => {
    switch (action.type) {
      case GET_ACCOUNT_SUCCESS:
        draft.data = action.account;
        break;
      default:
        break;
    }
  });

export default account;
