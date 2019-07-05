import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { ICareerOptions, defaultValue } from 'app/shared/model/career-options.model';

export const ACTION_TYPES = {
  FETCH_CAREEROPTIONS_LIST: 'careerOptions/FETCH_CAREEROPTIONS_LIST',
  FETCH_CAREEROPTIONS: 'careerOptions/FETCH_CAREEROPTIONS',
  CREATE_CAREEROPTIONS: 'careerOptions/CREATE_CAREEROPTIONS',
  UPDATE_CAREEROPTIONS: 'careerOptions/UPDATE_CAREEROPTIONS',
  DELETE_CAREEROPTIONS: 'careerOptions/DELETE_CAREEROPTIONS',
  RESET: 'careerOptions/RESET'
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<ICareerOptions>,
  entity: defaultValue,
  updating: false,
  updateSuccess: false
};

export type CareerOptionsState = Readonly<typeof initialState>;

// Reducer

export default (state: CareerOptionsState = initialState, action): CareerOptionsState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_CAREEROPTIONS_LIST):
    case REQUEST(ACTION_TYPES.FETCH_CAREEROPTIONS):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true
      };
    case REQUEST(ACTION_TYPES.CREATE_CAREEROPTIONS):
    case REQUEST(ACTION_TYPES.UPDATE_CAREEROPTIONS):
    case REQUEST(ACTION_TYPES.DELETE_CAREEROPTIONS):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true
      };
    case FAILURE(ACTION_TYPES.FETCH_CAREEROPTIONS_LIST):
    case FAILURE(ACTION_TYPES.FETCH_CAREEROPTIONS):
    case FAILURE(ACTION_TYPES.CREATE_CAREEROPTIONS):
    case FAILURE(ACTION_TYPES.UPDATE_CAREEROPTIONS):
    case FAILURE(ACTION_TYPES.DELETE_CAREEROPTIONS):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload
      };
    case SUCCESS(ACTION_TYPES.FETCH_CAREEROPTIONS_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.FETCH_CAREEROPTIONS):
      return {
        ...state,
        loading: false,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.CREATE_CAREEROPTIONS):
    case SUCCESS(ACTION_TYPES.UPDATE_CAREEROPTIONS):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.DELETE_CAREEROPTIONS):
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

const apiUrl = 'api/career-options';

// Actions

export const getEntities: ICrudGetAllAction<ICareerOptions> = (page, size, sort) => ({
  type: ACTION_TYPES.FETCH_CAREEROPTIONS_LIST,
  payload: axios.get<ICareerOptions>(`${apiUrl}?cacheBuster=${new Date().getTime()}`)
});

export const getEntity: ICrudGetAction<ICareerOptions> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_CAREEROPTIONS,
    payload: axios.get<ICareerOptions>(requestUrl)
  };
};

export const createEntity: ICrudPutAction<ICareerOptions> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_CAREEROPTIONS,
    payload: axios.post(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<ICareerOptions> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_CAREEROPTIONS,
    payload: axios.put(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const deleteEntity: ICrudDeleteAction<ICareerOptions> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_CAREEROPTIONS,
    payload: axios.delete(requestUrl)
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET
});
