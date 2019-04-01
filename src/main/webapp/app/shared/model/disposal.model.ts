import { ITreatmentProcedure } from 'app/shared/model//treatment-procedure.model';
import { IPrescription } from 'app/shared/model//prescription.model';
import { ITodo } from 'app/shared/model//todo.model';
import { IRegistration } from 'app/shared/model//registration.model';

export const enum DisposalStatus {
  TEMPORARY = 'TEMPORARY',
  PERMANENT = 'PERMANENT',
  MADE_APPT = 'MADE_APPT'
}

export interface IDisposal {
  id?: number;
  status?: DisposalStatus;
  total?: number;
  treatmentProcedures?: ITreatmentProcedure[];
  prescription?: IPrescription;
  todo?: ITodo;
  registration?: IRegistration;
}

export const defaultValue: Readonly<IDisposal> = {};
