import { Moment } from 'moment';
import { IPatient } from 'app/shared/model//patient.model';
import { IRegistration } from 'app/shared/model//registration.model';
import { ITreatmentProcedure } from 'app/shared/model//treatment-procedure.model';

export const enum AppointmentStatus {
  CONFIRMED = 'CONFIRMED',
  CANCEL = 'CANCEL'
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
  baseFloor?: boolean;
  colorId?: number;
  archived?: boolean;
  contacted?: boolean;
  patient?: IPatient;
  registration?: IRegistration;
  treatmentProcedures?: ITreatmentProcedure[];
}

export const defaultValue: Readonly<IAppointment> = {
  microscope: false,
  newPatient: false,
  baseFloor: false,
  archived: false,
  contacted: false
};
