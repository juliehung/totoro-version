import { ITreatmentProcedure } from 'app/shared/model//treatment-procedure.model';

export interface ITreatmentTask {
  id?: number;
  name?: string;
  note?: string;
  treatmentProcedures?: ITreatmentProcedure[];
}

export const defaultValue: Readonly<ITreatmentTask> = {};
