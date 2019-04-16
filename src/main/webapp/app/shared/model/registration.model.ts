import { Moment } from 'moment';
import { IAppointment } from 'app/shared/model//appointment.model';
import { IAccounting } from 'app/shared/model//accounting.model';
import { IDisposal } from 'app/shared/model//disposal.model';

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

export interface IRegistration {
  id?: number;
  status?: RegistrationStatus;
  arrivalTime?: Moment;
  type?: RegistrationType;
  onSite?: boolean;
  appointment?: IAppointment;
  accounting?: IAccounting;
  disposal?: IDisposal;
}

export const defaultValue: Readonly<IRegistration> = {
  onSite: false
};
