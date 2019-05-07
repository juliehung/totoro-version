import { ITreatmentDrug } from 'app/shared/model//treatment-drug.model';
import { IDisposal } from 'app/shared/model//disposal.model';

export const enum PrescriptionStatus {
  TEMPORARY = 'TEMPORARY',
  PERMANENT = 'PERMANENT'
}

export const enum PrescriptionMode {
  SELF = 'SELF',
  DELIVERY = 'DELIVERY',
  NONE = 'NONE'
}

export interface IPrescription {
  id?: number;
  clinicAdministration?: boolean;
  antiInflammatoryDrug?: boolean;
  pain?: boolean;
  takenAll?: boolean;
  status?: PrescriptionStatus;
  mode?: PrescriptionMode;
  treatmentDrugs?: ITreatmentDrug[];
  disposal?: IDisposal;
}

export const defaultValue: Readonly<IPrescription> = {
  clinicAdministration: false,
  antiInflammatoryDrug: false,
  pain: false,
  takenAll: false
};
