import { Moment } from 'moment';

export interface INhiExtendPatient {
  id?: number;
  cardNumber?: string;
  cardAnnotation?: string;
  cardValidDate?: Moment;
  cardIssueDate?: Moment;
  nhiIdentity?: string;
  availableTimes?: number;
}

export const defaultValue: Readonly<INhiExtendPatient> = {};
