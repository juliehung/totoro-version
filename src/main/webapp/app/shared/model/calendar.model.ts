import { Moment } from 'moment';

export const enum TimeType {
  WORK_TIME = 'WORK_TIME',
  HOLIDAY = 'HOLIDAY',
  NHI_POINT_EXCLUDE = 'NHI_POINT_EXCLUDE',
  OTHER = 'OTHER'
}

export const enum TimeInterval {
  MORNING = 'MORNING',
  NOON = 'NOON',
  EVENING = 'EVENING',
  NIGHT = 'NIGHT',
  ALL = 'ALL'
}

export interface ICalendar {
  id?: number;
  start?: Moment;
  end?: Moment;
  timeType?: TimeType;
  timeInterval?: TimeInterval;
  note?: string;
}

export const defaultValue: Readonly<ICalendar> = {};
