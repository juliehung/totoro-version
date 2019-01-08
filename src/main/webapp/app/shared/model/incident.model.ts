import { Moment } from 'moment';

export const enum IncidentType {
  UNUSUAL = 'UNUSUAL'
}

export interface IIncident {
  id?: number;
  type?: IncidentType;
  start?: Moment;
  end?: Moment;
  content?: string;
}

export const defaultValue: Readonly<IIncident> = {};
