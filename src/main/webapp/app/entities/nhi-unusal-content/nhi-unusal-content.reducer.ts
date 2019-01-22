import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { INHIUnusalContent, defaultValue } from 'app/shared/model/nhi-unusal-content.model';

export const ACTION_TYPES = {
  FETCH_NHIUNUSALCONTENT_LIST: 'nHIUnusalContent/FETCH_NHIUNUSALCONTENT_LIST',
  FETCH_NHIUNUSALCONTENT: 'nHIUnusalContent/FETCH_NHIUNUSALCONTENT',
  CREATE_NHIUNUSALCONTENT: 'nHIUnusalContent/CREATE_NHIUNUSALCONTENT',
  UPDATE_NHIUNUSALCONTENT: 'nHIUnusalContent/UPDATE_NHIUNUSALCONTENT',
  DELETE_NHIUNUSALCONTENT: 'nHIUnusalContent/DELETE_NHIUNUSALCONTENT',
  RESET: 'nHIUnusalContent/RESET'
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<INHIUnusalContent>,
  entity: defaultValue,
  updating: false,
  updateSuccess: false
};

export type NHIUnusalContentState = Readonly<typeof initialState>;

// Reducer

export default (state: NHIUnusalContentState = initialState, action): NHIUnusalContentState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_NHIUNUSALCONTENT_LIST):
    case REQUEST(ACTION_TYPES.FETCH_NHIUNUSALCONTENT):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true
      };
    case REQUEST(ACTION_TYPES.CREATE_NHIUNUSALCONTENT):
    case REQUEST(ACTION_TYPES.UPDATE_NHIUNUSALCONTENT):
    case REQUEST(ACTION_TYPES.DELETE_NHIUNUSALCONTENT):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true
      };
    case FAILURE(ACTION_TYPES.FETCH_NHIUNUSALCONTENT_LIST):
    case FAILURE(ACTION_TYPES.FETCH_NHIUNUSALCONTENT):
    case FAILURE(ACTION_TYPES.CREATE_NHIUNUSALCONTENT):
    case FAILURE(ACTION_TYPES.UPDATE_NHIUNUSALCONTENT):
    case FAILURE(ACTION_TYPES.DELETE_NHIUNUSALCONTENT):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload
      };
    case SUCCESS(ACTION_TYPES.FETCH_NHIUNUSALCONTENT_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.FETCH_NHIUNUSALCONTENT):
      return {
        ...state,
        loading: false,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.CREATE_NHIUNUSALCONTENT):
    case SUCCESS(ACTION_TYPES.UPDATE_NHIUNUSALCONTENT):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.DELETE_NHIUNUSALCONTENT):
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

const apiUrl = 'api/nhi-unusal-contents';

// Actions

export const getEntities: ICrudGetAllAction<INHIUnusalContent> = (page, size, sort) => ({
  type: ACTION_TYPES.FETCH_NHIUNUSALCONTENT_LIST,
  payload: axios.get<INHIUnusalContent>(`${apiUrl}?cacheBuster=${new Date().getTime()}`)
});

export const getEntity: ICrudGetAction<INHIUnusalContent> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_NHIUNUSALCONTENT,
    payload: axios.get<INHIUnusalContent>(requestUrl)
  };
};

export const createEntity: ICrudPutAction<INHIUnusalContent> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_NHIUNUSALCONTENT,
    payload: axios.post(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<INHIUnusalContent> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_NHIUNUSALCONTENT,
    payload: axios.put(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const deleteEntity: ICrudDeleteAction<INHIUnusalContent> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_NHIUNUSALCONTENT,
    payload: axios.delete(requestUrl)
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET
});
