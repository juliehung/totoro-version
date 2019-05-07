import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { INhiProcedure, defaultValue } from 'app/shared/model/nhi-procedure.model';

export const ACTION_TYPES = {
  FETCH_NHIPROCEDURE_LIST: 'nhiProcedure/FETCH_NHIPROCEDURE_LIST',
  FETCH_NHIPROCEDURE: 'nhiProcedure/FETCH_NHIPROCEDURE',
  CREATE_NHIPROCEDURE: 'nhiProcedure/CREATE_NHIPROCEDURE',
  UPDATE_NHIPROCEDURE: 'nhiProcedure/UPDATE_NHIPROCEDURE',
  DELETE_NHIPROCEDURE: 'nhiProcedure/DELETE_NHIPROCEDURE',
  RESET: 'nhiProcedure/RESET'
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<INhiProcedure>,
  entity: defaultValue,
  updating: false,
  totalItems: 0,
  updateSuccess: false
};

export type NhiProcedureState = Readonly<typeof initialState>;

// Reducer

export default (state: NhiProcedureState = initialState, action): NhiProcedureState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_NHIPROCEDURE_LIST):
    case REQUEST(ACTION_TYPES.FETCH_NHIPROCEDURE):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true
      };
    case REQUEST(ACTION_TYPES.CREATE_NHIPROCEDURE):
    case REQUEST(ACTION_TYPES.UPDATE_NHIPROCEDURE):
    case REQUEST(ACTION_TYPES.DELETE_NHIPROCEDURE):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true
      };
    case FAILURE(ACTION_TYPES.FETCH_NHIPROCEDURE_LIST):
    case FAILURE(ACTION_TYPES.FETCH_NHIPROCEDURE):
    case FAILURE(ACTION_TYPES.CREATE_NHIPROCEDURE):
    case FAILURE(ACTION_TYPES.UPDATE_NHIPROCEDURE):
    case FAILURE(ACTION_TYPES.DELETE_NHIPROCEDURE):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload
      };
    case SUCCESS(ACTION_TYPES.FETCH_NHIPROCEDURE_LIST):
      return {
        ...state,
        loading: false,
        totalItems: action.payload.headers['x-total-count'],
        entities: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.FETCH_NHIPROCEDURE):
      return {
        ...state,
        loading: false,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.CREATE_NHIPROCEDURE):
    case SUCCESS(ACTION_TYPES.UPDATE_NHIPROCEDURE):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.DELETE_NHIPROCEDURE):
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

const apiUrl = 'api/nhi-procedures';

// Actions

export const getEntities: ICrudGetAllAction<INhiProcedure> = (page, size, sort) => {
  const requestUrl = `${apiUrl}${sort ? `?page=${page}&size=${size}&sort=${sort}` : ''}`;
  return {
    type: ACTION_TYPES.FETCH_NHIPROCEDURE_LIST,
    payload: axios.get<INhiProcedure>(`${requestUrl}${sort ? '&' : '?'}cacheBuster=${new Date().getTime()}`)
  };
};

export const getEntity: ICrudGetAction<INhiProcedure> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_NHIPROCEDURE,
    payload: axios.get<INhiProcedure>(requestUrl)
  };
};

export const createEntity: ICrudPutAction<INhiProcedure> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_NHIPROCEDURE,
    payload: axios.post(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<INhiProcedure> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_NHIPROCEDURE,
    payload: axios.put(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const deleteEntity: ICrudDeleteAction<INhiProcedure> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_NHIPROCEDURE,
    payload: axios.delete(requestUrl)
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET
});
