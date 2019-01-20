import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { ICalendarSetting, defaultValue } from 'app/shared/model/calendar-setting.model';

export const ACTION_TYPES = {
  FETCH_CALENDARSETTING_LIST: 'calendarSetting/FETCH_CALENDARSETTING_LIST',
  FETCH_CALENDARSETTING: 'calendarSetting/FETCH_CALENDARSETTING',
  CREATE_CALENDARSETTING: 'calendarSetting/CREATE_CALENDARSETTING',
  UPDATE_CALENDARSETTING: 'calendarSetting/UPDATE_CALENDARSETTING',
  DELETE_CALENDARSETTING: 'calendarSetting/DELETE_CALENDARSETTING',
  RESET: 'calendarSetting/RESET'
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<ICalendarSetting>,
  entity: defaultValue,
  updating: false,
  updateSuccess: false
};

export type CalendarSettingState = Readonly<typeof initialState>;

// Reducer

export default (state: CalendarSettingState = initialState, action): CalendarSettingState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_CALENDARSETTING_LIST):
    case REQUEST(ACTION_TYPES.FETCH_CALENDARSETTING):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true
      };
    case REQUEST(ACTION_TYPES.CREATE_CALENDARSETTING):
    case REQUEST(ACTION_TYPES.UPDATE_CALENDARSETTING):
    case REQUEST(ACTION_TYPES.DELETE_CALENDARSETTING):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true
      };
    case FAILURE(ACTION_TYPES.FETCH_CALENDARSETTING_LIST):
    case FAILURE(ACTION_TYPES.FETCH_CALENDARSETTING):
    case FAILURE(ACTION_TYPES.CREATE_CALENDARSETTING):
    case FAILURE(ACTION_TYPES.UPDATE_CALENDARSETTING):
    case FAILURE(ACTION_TYPES.DELETE_CALENDARSETTING):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload
      };
    case SUCCESS(ACTION_TYPES.FETCH_CALENDARSETTING_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.FETCH_CALENDARSETTING):
      return {
        ...state,
        loading: false,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.CREATE_CALENDARSETTING):
    case SUCCESS(ACTION_TYPES.UPDATE_CALENDARSETTING):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.DELETE_CALENDARSETTING):
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

const apiUrl = 'api/calendar-settings';

// Actions

export const getEntities: ICrudGetAllAction<ICalendarSetting> = (page, size, sort) => ({
  type: ACTION_TYPES.FETCH_CALENDARSETTING_LIST,
  payload: axios.get<ICalendarSetting>(`${apiUrl}?cacheBuster=${new Date().getTime()}`)
});

export const getEntity: ICrudGetAction<ICalendarSetting> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_CALENDARSETTING,
    payload: axios.get<ICalendarSetting>(requestUrl)
  };
};

export const createEntity: ICrudPutAction<ICalendarSetting> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_CALENDARSETTING,
    payload: axios.post(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<ICalendarSetting> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_CALENDARSETTING,
    payload: axios.put(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const deleteEntity: ICrudDeleteAction<ICalendarSetting> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_CALENDARSETTING,
    payload: axios.delete(requestUrl)
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET
});
