import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { INhiDayUpload, defaultValue } from 'app/shared/model/nhi-day-upload.model';

export const ACTION_TYPES = {
  FETCH_NHIDAYUPLOAD_LIST: 'nhiDayUpload/FETCH_NHIDAYUPLOAD_LIST',
  FETCH_NHIDAYUPLOAD: 'nhiDayUpload/FETCH_NHIDAYUPLOAD',
  CREATE_NHIDAYUPLOAD: 'nhiDayUpload/CREATE_NHIDAYUPLOAD',
  UPDATE_NHIDAYUPLOAD: 'nhiDayUpload/UPDATE_NHIDAYUPLOAD',
  DELETE_NHIDAYUPLOAD: 'nhiDayUpload/DELETE_NHIDAYUPLOAD',
  RESET: 'nhiDayUpload/RESET'
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<INhiDayUpload>,
  entity: defaultValue,
  updating: false,
  updateSuccess: false
};

export type NhiDayUploadState = Readonly<typeof initialState>;

// Reducer

export default (state: NhiDayUploadState = initialState, action): NhiDayUploadState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_NHIDAYUPLOAD_LIST):
    case REQUEST(ACTION_TYPES.FETCH_NHIDAYUPLOAD):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true
      };
    case REQUEST(ACTION_TYPES.CREATE_NHIDAYUPLOAD):
    case REQUEST(ACTION_TYPES.UPDATE_NHIDAYUPLOAD):
    case REQUEST(ACTION_TYPES.DELETE_NHIDAYUPLOAD):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true
      };
    case FAILURE(ACTION_TYPES.FETCH_NHIDAYUPLOAD_LIST):
    case FAILURE(ACTION_TYPES.FETCH_NHIDAYUPLOAD):
    case FAILURE(ACTION_TYPES.CREATE_NHIDAYUPLOAD):
    case FAILURE(ACTION_TYPES.UPDATE_NHIDAYUPLOAD):
    case FAILURE(ACTION_TYPES.DELETE_NHIDAYUPLOAD):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload
      };
    case SUCCESS(ACTION_TYPES.FETCH_NHIDAYUPLOAD_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.FETCH_NHIDAYUPLOAD):
      return {
        ...state,
        loading: false,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.CREATE_NHIDAYUPLOAD):
    case SUCCESS(ACTION_TYPES.UPDATE_NHIDAYUPLOAD):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.DELETE_NHIDAYUPLOAD):
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

const apiUrl = 'api/nhi-day-uploads';

// Actions

export const getEntities: ICrudGetAllAction<INhiDayUpload> = (page, size, sort) => ({
  type: ACTION_TYPES.FETCH_NHIDAYUPLOAD_LIST,
  payload: axios.get<INhiDayUpload>(`${apiUrl}?cacheBuster=${new Date().getTime()}`)
});

export const getEntity: ICrudGetAction<INhiDayUpload> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_NHIDAYUPLOAD,
    payload: axios.get<INhiDayUpload>(requestUrl)
  };
};

export const createEntity: ICrudPutAction<INhiDayUpload> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_NHIDAYUPLOAD,
    payload: axios.post(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<INhiDayUpload> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_NHIDAYUPLOAD,
    payload: axios.put(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const deleteEntity: ICrudDeleteAction<INhiDayUpload> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_NHIDAYUPLOAD,
    payload: axios.delete(requestUrl)
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET
});
