import { Moment } from 'moment';

export const enum RegistrationStatus {
  PENDING = 'PENDING',
  FINISHED = 'FINISHED',
  IN_PROGRESS = 'IN_PROGRESS'
}

export const enum RegistrationType {
  OWN_EXPENSE = 'OWN_EXPENSE',
  NHI = 'NHI',
  NHI_NO_CARD = 'NHI_NO_CARD'
}

export interface IRegistrationDel {
  id?: number;
  status?: RegistrationStatus;
  arrivalTime?: Moment;
  type?: RegistrationType;
  onSite?: boolean;
  appointmentId?: number;
  accountingId?: number;
}

export const defaultValue: Readonly<IRegistrationDel> = {
  onSite: false
};
