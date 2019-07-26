import axios from 'axios';
import { IPayload } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IAppointment, defaultValue } from 'app/shared/model/appointment.model';

import { ACTION_TYPES } from './appointment.reducer';

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IAppointment>,
  entity: defaultValue,
  updating: false,
  totalItems: 0,
  updateSuccess: false
};

export type AppointmentState = Readonly<typeof initialState>;

export declare type ICrudGetRegistrationAction<T> = (
  page?: number,
  size?: number,
  sort?: string,
  date?: Date,
  doctorId?: string
) => IPayload<T> | ((dispatch: any) => IPayload<T>);

const apiUrl = 'api/appointments';

export const getRegistrationEntities: ICrudGetRegistrationAction<IAppointment> = (page, size, sort, date, doctorId) => {
  const dateAtMidnight = new Date(date.getFullYear(), date.getMonth(), date.getDate());

  const searchTimeStartQueryString = new Date(dateAtMidnight).toISOString();
  const searchTimeEndQueryString = new Date(dateAtMidnight.setDate(dateAtMidnight.getDate() + 1)).toISOString();

  if (!doctorId || doctorId === 'all') {
    doctorId = null;
  }

  const sortParameter = `page=${page}&size=${size}&sort=${sort}`;

  const requestUrl = `${apiUrl}?${sortParameter}&arrivalTime.greaterOrEqualThan=${searchTimeStartQueryString}&arrivalTime.lessThan=${searchTimeEndQueryString}${
    doctorId ? `&doctorId.equals=${doctorId}` : ''
  }`;
  return {
    type: ACTION_TYPES.FETCH_APPOINTMENT_LIST,
    payload: axios.get<IAppointment>(`${requestUrl}${sort ? '&' : '?'}cacheBuster=${new Date().getTime()}`)
  };
};
