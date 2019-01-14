import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { ICalendar, defaultValue } from 'app/shared/model/calendar.model';

export const ACTION_TYPES = {
  FETCH_CALENDAR_LIST: 'calendar/FETCH_CALENDAR_LIST',
  FETCH_CALENDAR: 'calendar/FETCH_CALENDAR',
  CREATE_CALENDAR: 'calendar/CREATE_CALENDAR',
  UPDATE_CALENDAR: 'calendar/UPDATE_CALENDAR',
  DELETE_CALENDAR: 'calendar/DELETE_CALENDAR',
  RESET: 'calendar/RESET'
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<ICalendar>,
  entity: defaultValue,
  updating: false,
  updateSuccess: false
};

export type CalendarState = Readonly<typeof initialState>;

// Reducer

export default (state: CalendarState = initialState, action): CalendarState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_CALENDAR_LIST):
    case REQUEST(ACTION_TYPES.FETCH_CALENDAR):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true
      };
    case REQUEST(ACTION_TYPES.CREATE_CALENDAR):
    case REQUEST(ACTION_TYPES.UPDATE_CALENDAR):
    case REQUEST(ACTION_TYPES.DELETE_CALENDAR):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true
      };
    case FAILURE(ACTION_TYPES.FETCH_CALENDAR_LIST):
    case FAILURE(ACTION_TYPES.FETCH_CALENDAR):
    case FAILURE(ACTION_TYPES.CREATE_CALENDAR):
    case FAILURE(ACTION_TYPES.UPDATE_CALENDAR):
    case FAILURE(ACTION_TYPES.DELETE_CALENDAR):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload
      };
    case SUCCESS(ACTION_TYPES.FETCH_CALENDAR_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.FETCH_CALENDAR):
      return {
        ...state,
        loading: false,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.CREATE_CALENDAR):
    case SUCCESS(ACTION_TYPES.UPDATE_CALENDAR):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.DELETE_CALENDAR):
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

const apiUrl = 'api/calendars';

// Actions

export const getEntities: ICrudGetAllAction<ICalendar> = (page, size, sort) => ({
  type: ACTION_TYPES.FETCH_CALENDAR_LIST,
  payload: axios.get<ICalendar>(`${apiUrl}?cacheBuster=${new Date().getTime()}`)
});

export const getEntity: ICrudGetAction<ICalendar> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_CALENDAR,
    payload: axios.get<ICalendar>(requestUrl)
  };
};

export const createEntity: ICrudPutAction<ICalendar> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_CALENDAR,
    payload: axios.post(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<ICalendar> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_CALENDAR,
    payload: axios.put(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const deleteEntity: ICrudDeleteAction<ICalendar> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_CALENDAR,
    payload: axios.delete(requestUrl)
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET
});
