import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { INhiProcedureType, defaultValue } from 'app/shared/model/nhi-procedure-type.model';

export const ACTION_TYPES = {
  FETCH_NHIPROCEDURETYPE_LIST: 'nhiProcedureType/FETCH_NHIPROCEDURETYPE_LIST',
  FETCH_NHIPROCEDURETYPE: 'nhiProcedureType/FETCH_NHIPROCEDURETYPE',
  CREATE_NHIPROCEDURETYPE: 'nhiProcedureType/CREATE_NHIPROCEDURETYPE',
  UPDATE_NHIPROCEDURETYPE: 'nhiProcedureType/UPDATE_NHIPROCEDURETYPE',
  DELETE_NHIPROCEDURETYPE: 'nhiProcedureType/DELETE_NHIPROCEDURETYPE',
  RESET: 'nhiProcedureType/RESET'
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<INhiProcedureType>,
  entity: defaultValue,
  updating: false,
  updateSuccess: false
};

export type NhiProcedureTypeState = Readonly<typeof initialState>;

// Reducer

export default (state: NhiProcedureTypeState = initialState, action): NhiProcedureTypeState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_NHIPROCEDURETYPE_LIST):
    case REQUEST(ACTION_TYPES.FETCH_NHIPROCEDURETYPE):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true
      };
    case REQUEST(ACTION_TYPES.CREATE_NHIPROCEDURETYPE):
    case REQUEST(ACTION_TYPES.UPDATE_NHIPROCEDURETYPE):
    case REQUEST(ACTION_TYPES.DELETE_NHIPROCEDURETYPE):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true
      };
    case FAILURE(ACTION_TYPES.FETCH_NHIPROCEDURETYPE_LIST):
    case FAILURE(ACTION_TYPES.FETCH_NHIPROCEDURETYPE):
    case FAILURE(ACTION_TYPES.CREATE_NHIPROCEDURETYPE):
    case FAILURE(ACTION_TYPES.UPDATE_NHIPROCEDURETYPE):
    case FAILURE(ACTION_TYPES.DELETE_NHIPROCEDURETYPE):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload
      };
    case SUCCESS(ACTION_TYPES.FETCH_NHIPROCEDURETYPE_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.FETCH_NHIPROCEDURETYPE):
      return {
        ...state,
        loading: false,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.CREATE_NHIPROCEDURETYPE):
    case SUCCESS(ACTION_TYPES.UPDATE_NHIPROCEDURETYPE):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.DELETE_NHIPROCEDURETYPE):
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

const apiUrl = 'api/nhi-procedure-types';

// Actions

export const getEntities: ICrudGetAllAction<INhiProcedureType> = (page, size, sort) => ({
  type: ACTION_TYPES.FETCH_NHIPROCEDURETYPE_LIST,
  payload: axios.get<INhiProcedureType>(`${apiUrl}?cacheBuster=${new Date().getTime()}`)
});

export const getEntity: ICrudGetAction<INhiProcedureType> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_NHIPROCEDURETYPE,
    payload: axios.get<INhiProcedureType>(requestUrl)
  };
};

export const createEntity: ICrudPutAction<INhiProcedureType> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_NHIPROCEDURETYPE,
    payload: axios.post(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<INhiProcedureType> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_NHIPROCEDURETYPE,
    payload: axios.put(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const deleteEntity: ICrudDeleteAction<INhiProcedureType> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_NHIPROCEDURETYPE,
    payload: axios.delete(requestUrl)
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET
});
