import { ITreatmentProcedure } from 'app/shared/model//treatment-procedure.model';
import { ITreatmentPlan } from 'app/shared/model//treatment-plan.model';

export interface ITreatmentTask {
  id?: number;
  name?: string;
  note?: string;
  treatmentProcedures?: ITreatmentProcedure[];
  treatmentPlan?: ITreatmentPlan;
}

export const defaultValue: Readonly<ITreatmentTask> = {};
