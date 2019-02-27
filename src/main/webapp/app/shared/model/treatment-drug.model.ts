import { IPrescription } from 'app/shared/model//prescription.model';
import { IDrug } from 'app/shared/model//drug.model';

export interface ITreatmentDrug {
  id?: number;
  day?: number;
  frequency?: string;
  prescription?: IPrescription;
  drug?: IDrug;
}

export const defaultValue: Readonly<ITreatmentDrug> = {};
