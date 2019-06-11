import { Moment } from 'moment';

export const enum RegistrationStatus {
  PENDING = 'PENDING',
  FINISHED = 'FINISHED',
  IN_PROGRESS = 'IN_PROGRESS'
}

export interface IRegistrationDel {
  id?: number;
  status?: RegistrationStatus;
  arrivalTime?: Moment;
  type?: string;
  onSite?: boolean;
  noCard?: boolean;
  appointmentId?: number;
  accountingId?: number;
}

export const defaultValue: Readonly<IRegistrationDel> = {
  onSite: false,
  noCard: false
};
