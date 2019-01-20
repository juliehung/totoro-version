import { Moment } from 'moment';

export const enum TimeInterval {
  MORNING = 'MORNING',
  NOON = 'NOON',
  EVENING = 'EVENING',
  NIGHT = 'NIGHT',
  ALL = 'ALL'
}

export const enum WeekDay {
  SUN = 'SUN',
  MON = 'MON',
  TUE = 'TUE',
  WED = 'WED',
  THU = 'THU',
  FRI = 'FRI',
  SAT = 'SAT'
}

export interface ICalendarSetting {
  id?: number;
  startTime?: string;
  endTime?: string;
  timeInterval?: TimeInterval;
  weekday?: WeekDay;
  startDate?: Moment;
  endDate?: Moment;
}

export const defaultValue: Readonly<ICalendarSetting> = {};
