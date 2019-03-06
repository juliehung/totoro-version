import { ITreatmentPlan } from 'app/shared/model//treatment-plan.model';

export interface ILedger {
  id?: number;
  amount?: number;
  charge?: number;
  arrears?: number;
  note?: string;
  treatmentPlan?: ITreatmentPlan;
}

export const defaultValue: Readonly<ILedger> = {};
