import { Moment } from 'moment';
import { ITreatmentPlan } from 'app/shared/model//treatment-plan.model';

export interface ILedger {
  id?: number;
  amount?: number;
  charge?: number;
  arrears?: number;
  note?: string;
  doctor?: string;
  gid?: number;
  displayName?: string;
  createdDate?: Moment;
  createdBy?: string;
  lastModifiedDate?: Moment;
  lastModifiedBy?: string;
  treatmentPlan?: ITreatmentPlan;
}

export const defaultValue: Readonly<ILedger> = {};
