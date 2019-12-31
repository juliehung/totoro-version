import { ADD_COUNT, SUB_COUNT } from './constants';

export default function counter(state = 10, action) {
  switch (action.type) {
    case ADD_COUNT:
      return state + 1;
    case SUB_COUNT:
      return state - 1;
    default:
      return state;
  }
}
