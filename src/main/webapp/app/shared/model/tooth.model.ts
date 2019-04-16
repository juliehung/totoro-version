import { ITreatmentProcedure } from 'app/shared/model//treatment-procedure.model';
import { IDisposal } from 'app/shared/model//disposal.model';
import { IPatient } from 'app/shared/model//patient.model';

export interface ITooth {
  id?: number;
  position?: string;
  surface?: string;
  status?: string;
  treatmentProcedure?: ITreatmentProcedure;
  disposal?: IDisposal;
  patient?: IPatient;
}

export const defaultValue: Readonly<ITooth> = {};
