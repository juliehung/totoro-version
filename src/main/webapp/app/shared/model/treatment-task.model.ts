import { ITreatmentProcedure } from 'app/shared/model//treatment-procedure.model';
import { ITooth } from 'app/shared/model//tooth.model';

export interface ITreatmentTask {
  id?: number;
  name?: string;
  note?: string;
  treatmentProcedures?: ITreatmentProcedure[];
  teeth?: ITooth[];
}

export const defaultValue: Readonly<ITreatmentTask> = {};
