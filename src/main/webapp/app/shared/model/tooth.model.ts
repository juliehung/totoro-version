import { ITreatmentProcedure } from 'app/shared/model//treatment-procedure.model';

export interface ITooth {
  id?: number;
  position?: string;
  before?: string;
  planned?: string;
  after?: string;
  treatmentProcedure?: ITreatmentProcedure;
}

export const defaultValue: Readonly<ITooth> = {};
