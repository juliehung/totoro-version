import { Moment } from 'moment';
import { ITreatmentProcedure } from 'app/shared/model//treatment-procedure.model';
import { IPrescription } from 'app/shared/model//prescription.model';
import { ITodo } from 'app/shared/model//todo.model';
import { IRegistration } from 'app/shared/model//registration.model';
import { ITooth } from 'app/shared/model//tooth.model';

export const enum DisposalStatus {
  TEMPORARY = 'TEMPORARY',
  PERMANENT = 'PERMANENT',
  MADE_APPT = 'MADE_APPT'
}

export interface IDisposal {
  id?: number;
  status?: DisposalStatus;
  total?: number;
  dateTime?: Moment;
  treatmentProcedures?: ITreatmentProcedure[];
  prescription?: IPrescription;
  todo?: ITodo;
  registration?: IRegistration;
  teeth?: ITooth[];
}

export const defaultValue: Readonly<IDisposal> = {};
