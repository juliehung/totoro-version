import produce from 'immer';
import { GET_TODOS_START, GET_TODOS_SUCCESS, CREATE_TODO_APP_SUCCESS } from '../constant';
import { filterDisposalsToSortedTodos } from '../utils/filterDisposalsToSortedTodos';

const initState = {
  todos: [],
  loading: false,
};

export const initialState = { ...initState };

/* eslint-disable default-case, no-param-reassign */
const todo = (state = initialState, action) =>
  produce(state, draft => {
    switch (action.type) {
      case GET_TODOS_START:
        draft.loading = true;
        break;
      case GET_TODOS_SUCCESS:
        draft.todos = filterDisposalsToSortedTodos(action.todos);
        draft.loading = initState.loading;
        break;
      case CREATE_TODO_APP_SUCCESS:
        draft.todos = state.todos.filter(t => {
          return t.id !== action.id;
        });
        break;
      default:
        break;
    }
  });

export default todo;
