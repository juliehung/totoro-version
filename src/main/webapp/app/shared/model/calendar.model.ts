import { Moment } from 'moment';

export const enum TimeInterval {
  MORNING = 'MORNING',
  NOON = 'NOON',
  EVENING = 'EVENING',
  NIGHT = 'NIGHT',
  ALL = 'ALL'
}

export const enum TimeType {
  WORK_TIME = 'WORK_TIME',
  HOLIDAY = 'HOLIDAY',
  NHI_POINT_EXCLUDE = 'NHI_POINT_EXCLUDE',
  OTHER = 'OTHER'
}

export interface ICalendar {
  id?: number;
  date?: Moment;
  timeInterval?: TimeInterval;
  timeType?: TimeType;
  startTime?: string;
  endTime?: string;
}

export const defaultValue: Readonly<ICalendar> = {};
