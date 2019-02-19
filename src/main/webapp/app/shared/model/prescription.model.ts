import { ITreatmentDrug } from 'app/shared/model//treatment-drug.model';
import { IRegistration } from 'app/shared/model//registration.model';

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
  registration?: IRegistration;
}

export const defaultValue: Readonly<IPrescription> = {
  clinicAdministration: false,
  antiInflammatoryDrug: false,
  pain: false,
  takenAll: false
};
