import { ITreatmentProcedure } from 'app/shared/model//treatment-procedure.model';

export const enum TreatmentTaskStatus {
  PLANNED = 'PLANNED',
  IN_PROGRESS = 'IN_PROGRESS',
  COMPLETED = 'COMPLETED',
  CANCEL = 'CANCEL'
}

export interface ITreatmentTask {
  id?: number;
  status?: TreatmentTaskStatus;
  description?: string;
  teeth?: string;
  surfaces?: string;
  note?: string;
  treatmentProcedures?: ITreatmentProcedure[];
}

export const defaultValue: Readonly<ITreatmentTask> = {};
