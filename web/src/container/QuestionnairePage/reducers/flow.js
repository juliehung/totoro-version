import produce from 'immer';
import {
  NEXT_PAGE,
  NEXT_PAGE_DELAY,
  PREV_PAGE,
  PREV_PAGE_DELAY,
  GOTO_PAGE,
  GOTO_PAGE_DELAY,
  CHANGE_IS_SIG_EMPTY,
  CREATE_Q_SUCCESS,
  INIT_PAGE,
  CREATE_Q_WITH_SIGN,
  CREATE_Q_WITHOUT_SIGN,
} from '../constant';

const initState = {
  page: 20,
  reverse: false,
  sendLoading: false,
  isSigEmpty: true,
  createQSuccess: false,
};

export const initialState = { ...initState };

/* eslint-disable default-case, no-param-reassign */
const flow = (state = initialState, action) =>
  produce(state, draft => {
    switch (action.type) {
      case INIT_PAGE:
        draft.page = initState.page;
        draft.sendLoading = initialState.sendLoading;
        draft.isSigEmpty = initialState.isSigEmpty;
        draft.createQSuccess = initialState.createQSuccess;
        break;
      case NEXT_PAGE:
        draft.reverse = false;
        break;
      case PREV_PAGE:
        draft.reverse = true;
        break;
      case GOTO_PAGE:
        draft.reverse = state.page > action.page;
        break;
      case NEXT_PAGE_DELAY:
        if (state.page !== 20 && state.page !== 21) {
          draft.page = state.page + 1;
        }
        break;
      case PREV_PAGE_DELAY:
        if (state.page !== 1 && state.page !== 20 && state.page !== 21) {
          draft.page = state.page - 1;
        }
        break;
      case GOTO_PAGE_DELAY:
        draft.page = action.page;
        break;
      case CHANGE_IS_SIG_EMPTY:
        draft.isSigEmpty = action.isEmpty;
        break;
      case CREATE_Q_SUCCESS:
        draft.createQSuccess = true;
        break;
      case CREATE_Q_WITH_SIGN:
      case CREATE_Q_WITHOUT_SIGN:
        draft.sendLoading = true;
        break;
      default:
        break;
    }
  });

export default flow;
