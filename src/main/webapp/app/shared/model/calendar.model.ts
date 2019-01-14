import { Moment } from 'moment';

export const enum IntervalType {
  MORNING = 'MORNING',
  NOON = 'NOON',
  EVENING = 'EVENING',
  NIGHT = 'NIGHT',
  ALL = 'ALL'
}

export const enum DateType {
  WORKTIME = 'WORKTIME',
  HOLIDAY = 'HOLIDAY',
  NHIPOINTEXCLUDE = 'NHIPOINTEXCLUDE',
  OTHER = 'OTHER'
}

export interface ICalendar {
  id?: number;
  date?: Moment;
  intervalType?: IntervalType;
  dateType?: DateType;
  start?: string;
  end?: string;
}

export const defaultValue: Readonly<ICalendar> = {};
