import { Moment } from 'moment';
import { IPatient } from 'app/shared/model//patient.model';
import { ITreatmentProcedure } from 'app/shared/model//treatment-procedure.model';
import { IDisposal } from 'app/shared/model//disposal.model';

export const enum TodoStatus {
  TEMPORARY = 'TEMPORARY',
  PENDING = 'PENDING',
  FINISHED = 'FINISHED'
}

export interface ITodo {
  id?: number;
  status?: TodoStatus;
  expectedDate?: Moment;
  requiredTreatmentTime?: number;
  note?: string;
  patient?: IPatient;
  treatmentProcedures?: ITreatmentProcedure[];
  disposal?: IDisposal;
}

export const defaultValue: Readonly<ITodo> = {};
