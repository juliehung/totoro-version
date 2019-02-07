import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IFindingType, defaultValue } from 'app/shared/model/finding-type.model';

export const ACTION_TYPES = {
  FETCH_FINDINGTYPE_LIST: 'findingType/FETCH_FINDINGTYPE_LIST',
  FETCH_FINDINGTYPE: 'findingType/FETCH_FINDINGTYPE',
  CREATE_FINDINGTYPE: 'findingType/CREATE_FINDINGTYPE',
  UPDATE_FINDINGTYPE: 'findingType/UPDATE_FINDINGTYPE',
  DELETE_FINDINGTYPE: 'findingType/DELETE_FINDINGTYPE',
  RESET: 'findingType/RESET'
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IFindingType>,
  entity: defaultValue,
  updating: false,
  updateSuccess: false
};

export type FindingTypeState = Readonly<typeof initialState>;

// Reducer

export default (state: FindingTypeState = initialState, action): FindingTypeState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_FINDINGTYPE_LIST):
    case REQUEST(ACTION_TYPES.FETCH_FINDINGTYPE):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true
      };
    case REQUEST(ACTION_TYPES.CREATE_FINDINGTYPE):
    case REQUEST(ACTION_TYPES.UPDATE_FINDINGTYPE):
    case REQUEST(ACTION_TYPES.DELETE_FINDINGTYPE):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true
      };
    case FAILURE(ACTION_TYPES.FETCH_FINDINGTYPE_LIST):
    case FAILURE(ACTION_TYPES.FETCH_FINDINGTYPE):
    case FAILURE(ACTION_TYPES.CREATE_FINDINGTYPE):
    case FAILURE(ACTION_TYPES.UPDATE_FINDINGTYPE):
    case FAILURE(ACTION_TYPES.DELETE_FINDINGTYPE):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload
      };
    case SUCCESS(ACTION_TYPES.FETCH_FINDINGTYPE_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.FETCH_FINDINGTYPE):
      return {
        ...state,
        loading: false,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.CREATE_FINDINGTYPE):
    case SUCCESS(ACTION_TYPES.UPDATE_FINDINGTYPE):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.DELETE_FINDINGTYPE):
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

const apiUrl = 'api/finding-types';

// Actions

export const getEntities: ICrudGetAllAction<IFindingType> = (page, size, sort) => ({
  type: ACTION_TYPES.FETCH_FINDINGTYPE_LIST,
  payload: axios.get<IFindingType>(`${apiUrl}?cacheBuster=${new Date().getTime()}`)
});

export const getEntity: ICrudGetAction<IFindingType> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_FINDINGTYPE,
    payload: axios.get<IFindingType>(requestUrl)
  };
};

export const createEntity: ICrudPutAction<IFindingType> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_FINDINGTYPE,
    payload: axios.post(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IFindingType> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_FINDINGTYPE,
    payload: axios.put(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const deleteEntity: ICrudDeleteAction<IFindingType> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_FINDINGTYPE,
    payload: axios.delete(requestUrl)
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET
});
