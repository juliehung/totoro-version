import { IPatient } from 'app/shared/model//patient.model';
import { ITreatmentPlan } from 'app/shared/model//treatment-plan.model';

export const enum TreatmentType {
  GENERAL = 'GENERAL',
  PROFESSIONAL = 'PROFESSIONAL'
}

export interface ITreatment {
  id?: number;
  name?: string;
  chiefComplaint?: string;
  goal?: string;
  note?: string;
  finding?: string;
  type?: TreatmentType;
  patient?: IPatient;
  treatmentPlans?: ITreatmentPlan[];
}

export const defaultValue: Readonly<ITreatment> = {};
