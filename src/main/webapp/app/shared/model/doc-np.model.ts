import { Moment } from 'moment';

export interface IDocNp {
  id?: number;
  patient?: object;
  patientId?: number;
  esignId?: number;
  createdDate?: Moment;
  createdBy?: string;
}

export const defaultValue: Readonly<IDocNp> = {};
