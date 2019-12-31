import produce from 'immer';
import { NEXT_PAGE, NEXT_PAGE_DELAY, PREV_PAGE, PREV_PAGE_DELAY, GOTO_PAGE, GOTO_PAGE_DELAY } from '../constant';

const initState = {
  page: 1,
  reverse: false,
};

export const initialState = { ...initState };

/* eslint-disable default-case, no-param-reassign */
const flow = (state = initialState, action) =>
  produce(state, draft => {
    switch (action.type) {
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
        draft.page = state.page + 1;
        break;
      case PREV_PAGE_DELAY:
        draft.page = state.page - 1;
        break;
      case GOTO_PAGE_DELAY:
        draft.page = action.page;
        break;
      default:
        break;
    }
  });

export default flow;
