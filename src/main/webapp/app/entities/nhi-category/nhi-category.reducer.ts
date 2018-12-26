import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { INHICategory, defaultValue } from 'app/shared/model/nhi-category.model';

export const ACTION_TYPES = {
  FETCH_NHICATEGORY_LIST: 'nHICategory/FETCH_NHICATEGORY_LIST',
  FETCH_NHICATEGORY: 'nHICategory/FETCH_NHICATEGORY',
  CREATE_NHICATEGORY: 'nHICategory/CREATE_NHICATEGORY',
  UPDATE_NHICATEGORY: 'nHICategory/UPDATE_NHICATEGORY',
  DELETE_NHICATEGORY: 'nHICategory/DELETE_NHICATEGORY',
  RESET: 'nHICategory/RESET'
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<INHICategory>,
  entity: defaultValue,
  updating: false,
  updateSuccess: false
};

export type NHICategoryState = Readonly<typeof initialState>;

// Reducer

export default (state: NHICategoryState = initialState, action): NHICategoryState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_NHICATEGORY_LIST):
    case REQUEST(ACTION_TYPES.FETCH_NHICATEGORY):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true
      };
    case REQUEST(ACTION_TYPES.CREATE_NHICATEGORY):
    case REQUEST(ACTION_TYPES.UPDATE_NHICATEGORY):
    case REQUEST(ACTION_TYPES.DELETE_NHICATEGORY):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true
      };
    case FAILURE(ACTION_TYPES.FETCH_NHICATEGORY_LIST):
    case FAILURE(ACTION_TYPES.FETCH_NHICATEGORY):
    case FAILURE(ACTION_TYPES.CREATE_NHICATEGORY):
    case FAILURE(ACTION_TYPES.UPDATE_NHICATEGORY):
    case FAILURE(ACTION_TYPES.DELETE_NHICATEGORY):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload
      };
    case SUCCESS(ACTION_TYPES.FETCH_NHICATEGORY_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.FETCH_NHICATEGORY):
      return {
        ...state,
        loading: false,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.CREATE_NHICATEGORY):
    case SUCCESS(ACTION_TYPES.UPDATE_NHICATEGORY):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.DELETE_NHICATEGORY):
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

const apiUrl = 'api/nhi-categories';

// Actions

export const getEntities: ICrudGetAllAction<INHICategory> = (page, size, sort) => ({
  type: ACTION_TYPES.FETCH_NHICATEGORY_LIST,
  payload: axios.get<INHICategory>(`${apiUrl}?cacheBuster=${new Date().getTime()}`)
});

export const getEntity: ICrudGetAction<INHICategory> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_NHICATEGORY,
    payload: axios.get<INHICategory>(requestUrl)
  };
};

export const createEntity: ICrudPutAction<INHICategory> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_NHICATEGORY,
    payload: axios.post(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<INHICategory> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_NHICATEGORY,
    payload: axios.put(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const deleteEntity: ICrudDeleteAction<INHICategory> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_NHICATEGORY,
    payload: axios.delete(requestUrl)
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET
});
