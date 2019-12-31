import { ADD_COUNT, SUB_COUNT } from './constants';

export function addCount() {
  return { type: ADD_COUNT };
}

export function subCount() {
  return { type: SUB_COUNT };
}
