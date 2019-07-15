import { Moment } from 'moment';

export const enum SourceType {
  BY_STRING64 = 'BY_STRING64',
  BY_FILE = 'BY_FILE'
}

export interface IEsign {
  id?: number;
  patientId?: number;
  lobContentType?: string;
  lob?: any;
  createdDate?: Moment;
  createdBy?: string;
  sourceType?: SourceType;
}

export const defaultValue: Readonly<IEsign> = {};
