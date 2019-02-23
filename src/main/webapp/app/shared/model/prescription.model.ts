import { ITreatmentDrug } from 'app/shared/model//treatment-drug.model';
import { IDisposal } from 'app/shared/model//disposal.model';

export const enum PrescriptionStatus {
  TEMPORARY = 'TEMPORARY',
  PERMANENT = 'PERMANENT'
}

export interface IPrescription {
  id?: number;
  clinicAdministration?: boolean;
  antiInflammatoryDrug?: boolean;
  pain?: boolean;
  takenAll?: boolean;
  status?: PrescriptionStatus;
  treatmentDrugs?: ITreatmentDrug[];
  disposal?: IDisposal;
}

export const defaultValue: Readonly<IPrescription> = {
  clinicAdministration: false,
  antiInflammatoryDrug: false,
  pain: false,
  takenAll: false
};
