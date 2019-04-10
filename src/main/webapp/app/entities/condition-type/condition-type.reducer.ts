import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IConditionType, defaultValue } from 'app/shared/model/condition-type.model';

export const ACTION_TYPES = {
  FETCH_CONDITIONTYPE_LIST: 'conditionType/FETCH_CONDITIONTYPE_LIST',
  FETCH_CONDITIONTYPE: 'conditionType/FETCH_CONDITIONTYPE',
  CREATE_CONDITIONTYPE: 'conditionType/CREATE_CONDITIONTYPE',
  UPDATE_CONDITIONTYPE: 'conditionType/UPDATE_CONDITIONTYPE',
  DELETE_CONDITIONTYPE: 'conditionType/DELETE_CONDITIONTYPE',
  RESET: 'conditionType/RESET'
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IConditionType>,
  entity: defaultValue,
  updating: false,
  updateSuccess: false
};

export type ConditionTypeState = Readonly<typeof initialState>;

// Reducer

export default (state: ConditionTypeState = initialState, action): ConditionTypeState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_CONDITIONTYPE_LIST):
    case REQUEST(ACTION_TYPES.FETCH_CONDITIONTYPE):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true
      };
    case REQUEST(ACTION_TYPES.CREATE_CONDITIONTYPE):
    case REQUEST(ACTION_TYPES.UPDATE_CONDITIONTYPE):
    case REQUEST(ACTION_TYPES.DELETE_CONDITIONTYPE):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true
      };
    case FAILURE(ACTION_TYPES.FETCH_CONDITIONTYPE_LIST):
    case FAILURE(ACTION_TYPES.FETCH_CONDITIONTYPE):
    case FAILURE(ACTION_TYPES.CREATE_CONDITIONTYPE):
    case FAILURE(ACTION_TYPES.UPDATE_CONDITIONTYPE):
    case FAILURE(ACTION_TYPES.DELETE_CONDITIONTYPE):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload
      };
    case SUCCESS(ACTION_TYPES.FETCH_CONDITIONTYPE_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.FETCH_CONDITIONTYPE):
      return {
        ...state,
        loading: false,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.CREATE_CONDITIONTYPE):
    case SUCCESS(ACTION_TYPES.UPDATE_CONDITIONTYPE):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.DELETE_CONDITIONTYPE):
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

const apiUrl = 'api/condition-types';

// Actions

export const getEntities: ICrudGetAllAction<IConditionType> = (page, size, sort) => ({
  type: ACTION_TYPES.FETCH_CONDITIONTYPE_LIST,
  payload: axios.get<IConditionType>(`${apiUrl}?cacheBuster=${new Date().getTime()}`)
});

export const getEntity: ICrudGetAction<IConditionType> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_CONDITIONTYPE,
    payload: axios.get<IConditionType>(requestUrl)
  };
};

export const createEntity: ICrudPutAction<IConditionType> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_CONDITIONTYPE,
    payload: axios.post(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IConditionType> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_CONDITIONTYPE,
    payload: axios.put(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const deleteEntity: ICrudDeleteAction<IConditionType> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_CONDITIONTYPE,
    payload: axios.delete(requestUrl)
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET
});
