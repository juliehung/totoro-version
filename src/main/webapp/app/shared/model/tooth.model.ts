import { ITreatmentProcedure } from 'app/shared/model//treatment-procedure.model';

export interface ITooth {
  id?: number;
  position?: string;
  surface?: string;
  status?: string;
  treatmentProcedure?: ITreatmentProcedure;
}

export const defaultValue: Readonly<ITooth> = {};
