import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { INhiDayUploadDetails, defaultValue } from 'app/shared/model/nhi-day-upload-details.model';

export const ACTION_TYPES = {
  FETCH_NHIDAYUPLOADDETAILS_LIST: 'nhiDayUploadDetails/FETCH_NHIDAYUPLOADDETAILS_LIST',
  FETCH_NHIDAYUPLOADDETAILS: 'nhiDayUploadDetails/FETCH_NHIDAYUPLOADDETAILS',
  CREATE_NHIDAYUPLOADDETAILS: 'nhiDayUploadDetails/CREATE_NHIDAYUPLOADDETAILS',
  UPDATE_NHIDAYUPLOADDETAILS: 'nhiDayUploadDetails/UPDATE_NHIDAYUPLOADDETAILS',
  DELETE_NHIDAYUPLOADDETAILS: 'nhiDayUploadDetails/DELETE_NHIDAYUPLOADDETAILS',
  RESET: 'nhiDayUploadDetails/RESET'
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<INhiDayUploadDetails>,
  entity: defaultValue,
  updating: false,
  updateSuccess: false
};

export type NhiDayUploadDetailsState = Readonly<typeof initialState>;

// Reducer

export default (state: NhiDayUploadDetailsState = initialState, action): NhiDayUploadDetailsState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_NHIDAYUPLOADDETAILS_LIST):
    case REQUEST(ACTION_TYPES.FETCH_NHIDAYUPLOADDETAILS):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true
      };
    case REQUEST(ACTION_TYPES.CREATE_NHIDAYUPLOADDETAILS):
    case REQUEST(ACTION_TYPES.UPDATE_NHIDAYUPLOADDETAILS):
    case REQUEST(ACTION_TYPES.DELETE_NHIDAYUPLOADDETAILS):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true
      };
    case FAILURE(ACTION_TYPES.FETCH_NHIDAYUPLOADDETAILS_LIST):
    case FAILURE(ACTION_TYPES.FETCH_NHIDAYUPLOADDETAILS):
    case FAILURE(ACTION_TYPES.CREATE_NHIDAYUPLOADDETAILS):
    case FAILURE(ACTION_TYPES.UPDATE_NHIDAYUPLOADDETAILS):
    case FAILURE(ACTION_TYPES.DELETE_NHIDAYUPLOADDETAILS):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload
      };
    case SUCCESS(ACTION_TYPES.FETCH_NHIDAYUPLOADDETAILS_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.FETCH_NHIDAYUPLOADDETAILS):
      return {
        ...state,
        loading: false,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.CREATE_NHIDAYUPLOADDETAILS):
    case SUCCESS(ACTION_TYPES.UPDATE_NHIDAYUPLOADDETAILS):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.DELETE_NHIDAYUPLOADDETAILS):
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

const apiUrl = 'api/nhi-day-upload-details';

// Actions

export const getEntities: ICrudGetAllAction<INhiDayUploadDetails> = (page, size, sort) => ({
  type: ACTION_TYPES.FETCH_NHIDAYUPLOADDETAILS_LIST,
  payload: axios.get<INhiDayUploadDetails>(`${apiUrl}?cacheBuster=${new Date().getTime()}`)
});

export const getEntity: ICrudGetAction<INhiDayUploadDetails> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_NHIDAYUPLOADDETAILS,
    payload: axios.get<INhiDayUploadDetails>(requestUrl)
  };
};

export const createEntity: ICrudPutAction<INhiDayUploadDetails> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_NHIDAYUPLOADDETAILS,
    payload: axios.post(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<INhiDayUploadDetails> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_NHIDAYUPLOADDETAILS,
    payload: axios.put(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const deleteEntity: ICrudDeleteAction<INhiDayUploadDetails> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_NHIDAYUPLOADDETAILS,
    payload: axios.delete(requestUrl)
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET
});
