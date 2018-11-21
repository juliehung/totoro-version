import { Moment } from 'moment';
import { IPatient } from 'app/shared/model//patient.model';
import { IRegistration } from 'app/shared/model//registration.model';

export const enum AppointmentStatus {
  TO_BE_CONFIRMED = 'TO_BE_CONFIRMED',
  CONFIRMED = 'CONFIRMED',
  CANCEL = 'CANCEL',
  NO = 'NO'
}

export interface IAppointment {
  id?: number;
  status?: AppointmentStatus;
  subject?: string;
  note?: string;
  expectedArrivalTime?: Moment;
  requiredTreatmentTime?: number;
  microscope?: boolean;
  newPatient?: boolean;
  patient?: IPatient;
  registration?: IRegistration;
}

export const defaultValue: Readonly<IAppointment> = {
  microscope: false,
  newPatient: false
};
