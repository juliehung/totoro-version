import { Moment } from 'moment';
import { IAppointment } from 'app/shared/model//appointment.model';
import { IAccounting } from 'app/shared/model//accounting.model';
import { IDisposal } from 'app/shared/model//disposal.model';

export const enum RegistrationStatus {
  PENDING = 'PENDING',
  FINISHED = 'FINISHED',
  IN_PROGRESS = 'IN_PROGRESS'
}

export interface IRegistration {
  id?: number;
  status?: RegistrationStatus;
  arrivalTime?: Moment;
  type?: string;
  onSite?: boolean;
  noCard?: boolean;
  appointment?: IAppointment;
  accounting?: IAccounting;
  disposal?: IDisposal;
}

export const defaultValue: Readonly<IRegistration> = {
  onSite: false,
  noCard: false
};
