import { Moment } from 'moment';
import { IAppointment } from 'app/shared/model//appointment.model';

export const enum RegistrationStatus {
  PENDING = 'PENDING',
  FINISHED = 'FINISHED'
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
}

export const defaultValue: Readonly<IRegistration> = {
  onSite: false
};
