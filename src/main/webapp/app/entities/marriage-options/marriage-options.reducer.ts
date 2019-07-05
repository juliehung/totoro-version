import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IMarriageOptions, defaultValue } from 'app/shared/model/marriage-options.model';

export const ACTION_TYPES = {
  FETCH_MARRIAGEOPTIONS_LIST: 'marriageOptions/FETCH_MARRIAGEOPTIONS_LIST',
  FETCH_MARRIAGEOPTIONS: 'marriageOptions/FETCH_MARRIAGEOPTIONS',
  CREATE_MARRIAGEOPTIONS: 'marriageOptions/CREATE_MARRIAGEOPTIONS',
  UPDATE_MARRIAGEOPTIONS: 'marriageOptions/UPDATE_MARRIAGEOPTIONS',
  DELETE_MARRIAGEOPTIONS: 'marriageOptions/DELETE_MARRIAGEOPTIONS',
  RESET: 'marriageOptions/RESET'
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IMarriageOptions>,
  entity: defaultValue,
  updating: false,
  updateSuccess: false
};

export type MarriageOptionsState = Readonly<typeof initialState>;

// Reducer

export default (state: MarriageOptionsState = initialState, action): MarriageOptionsState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_MARRIAGEOPTIONS_LIST):
    case REQUEST(ACTION_TYPES.FETCH_MARRIAGEOPTIONS):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true
      };
    case REQUEST(ACTION_TYPES.CREATE_MARRIAGEOPTIONS):
    case REQUEST(ACTION_TYPES.UPDATE_MARRIAGEOPTIONS):
    case REQUEST(ACTION_TYPES.DELETE_MARRIAGEOPTIONS):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true
      };
    case FAILURE(ACTION_TYPES.FETCH_MARRIAGEOPTIONS_LIST):
    case FAILURE(ACTION_TYPES.FETCH_MARRIAGEOPTIONS):
    case FAILURE(ACTION_TYPES.CREATE_MARRIAGEOPTIONS):
    case FAILURE(ACTION_TYPES.UPDATE_MARRIAGEOPTIONS):
    case FAILURE(ACTION_TYPES.DELETE_MARRIAGEOPTIONS):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload
      };
    case SUCCESS(ACTION_TYPES.FETCH_MARRIAGEOPTIONS_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.FETCH_MARRIAGEOPTIONS):
      return {
        ...state,
        loading: false,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.CREATE_MARRIAGEOPTIONS):
    case SUCCESS(ACTION_TYPES.UPDATE_MARRIAGEOPTIONS):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.DELETE_MARRIAGEOPTIONS):
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

const apiUrl = 'api/marriage-options';

// Actions

export const getEntities: ICrudGetAllAction<IMarriageOptions> = (page, size, sort) => ({
  type: ACTION_TYPES.FETCH_MARRIAGEOPTIONS_LIST,
  payload: axios.get<IMarriageOptions>(`${apiUrl}?cacheBuster=${new Date().getTime()}`)
});

export const getEntity: ICrudGetAction<IMarriageOptions> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_MARRIAGEOPTIONS,
    payload: axios.get<IMarriageOptions>(requestUrl)
  };
};

export const createEntity: ICrudPutAction<IMarriageOptions> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_MARRIAGEOPTIONS,
    payload: axios.post(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IMarriageOptions> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_MARRIAGEOPTIONS,
    payload: axios.put(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const deleteEntity: ICrudDeleteAction<IMarriageOptions> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_MARRIAGEOPTIONS,
    payload: axios.delete(requestUrl)
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET
});
