import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IDisposal, defaultValue } from 'app/shared/model/disposal.model';

export const ACTION_TYPES = {
  FETCH_DISPOSAL_LIST: 'disposal/FETCH_DISPOSAL_LIST',
  FETCH_DISPOSAL: 'disposal/FETCH_DISPOSAL',
  CREATE_DISPOSAL: 'disposal/CREATE_DISPOSAL',
  UPDATE_DISPOSAL: 'disposal/UPDATE_DISPOSAL',
  DELETE_DISPOSAL: 'disposal/DELETE_DISPOSAL',
  RESET: 'disposal/RESET'
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IDisposal>,
  entity: defaultValue,
  updating: false,
  totalItems: 0,
  updateSuccess: false
};

export type DisposalState = Readonly<typeof initialState>;

// Reducer

export default (state: DisposalState = initialState, action): DisposalState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_DISPOSAL_LIST):
    case REQUEST(ACTION_TYPES.FETCH_DISPOSAL):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true
      };
    case REQUEST(ACTION_TYPES.CREATE_DISPOSAL):
    case REQUEST(ACTION_TYPES.UPDATE_DISPOSAL):
    case REQUEST(ACTION_TYPES.DELETE_DISPOSAL):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true
      };
    case FAILURE(ACTION_TYPES.FETCH_DISPOSAL_LIST):
    case FAILURE(ACTION_TYPES.FETCH_DISPOSAL):
    case FAILURE(ACTION_TYPES.CREATE_DISPOSAL):
    case FAILURE(ACTION_TYPES.UPDATE_DISPOSAL):
    case FAILURE(ACTION_TYPES.DELETE_DISPOSAL):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload
      };
    case SUCCESS(ACTION_TYPES.FETCH_DISPOSAL_LIST):
      return {
        ...state,
        loading: false,
        totalItems: action.payload.headers['x-total-count'],
        entities: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.FETCH_DISPOSAL):
      return {
        ...state,
        loading: false,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.CREATE_DISPOSAL):
    case SUCCESS(ACTION_TYPES.UPDATE_DISPOSAL):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.DELETE_DISPOSAL):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: {}
      };
    case ACTION_TYPES.RESET:
      return {
        ...initialState
      };
    default:
      return state;
  }
};

const apiUrl = 'api/disposals';

// Actions

export const getEntities: ICrudGetAllAction<IDisposal> = (page, size, sort) => {
  const requestUrl = `${apiUrl}${sort ? `?page=${page}&size=${size}&sort=${sort}` : ''}`;
  return {
    type: ACTION_TYPES.FETCH_DISPOSAL_LIST,
    payload: axios.get<IDisposal>(`${requestUrl}${sort ? '&' : '?'}cacheBuster=${new Date().getTime()}`)
  };
};

export const getEntity: ICrudGetAction<IDisposal> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_DISPOSAL,
    payload: axios.get<IDisposal>(requestUrl)
  };
};

export const createEntity: ICrudPutAction<IDisposal> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_DISPOSAL,
    payload: axios.post(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IDisposal> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_DISPOSAL,
    payload: axios.put(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const deleteEntity: ICrudDeleteAction<IDisposal> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_DISPOSAL,
    payload: axios.delete(requestUrl)
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET
});
