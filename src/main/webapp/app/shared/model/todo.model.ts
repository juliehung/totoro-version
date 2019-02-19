import { Moment } from 'moment';
import { IPatient } from 'app/shared/model//patient.model';
import { ITreatmentProcedure } from 'app/shared/model//treatment-procedure.model';

export const enum TodoStatus {
  TEMPORARY = 'TEMPORARY',
  PENDING = 'PENDING',
  FINISHED = 'FINISHED'
}

export interface ITodo {
  id?: number;
  ststus?: TodoStatus;
  expectedDate?: Moment;
  requiredTreatmentTime?: number;
  note?: string;
  patient?: IPatient;
  treatmentProcedures?: ITreatmentProcedure[];
}

export const defaultValue: Readonly<ITodo> = {};
