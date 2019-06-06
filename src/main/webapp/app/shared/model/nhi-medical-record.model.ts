import { INhiExtendPatient } from 'app/shared/model//nhi-extend-patient.model';

export interface INhiMedicalRecord {
  id?: number;
  date?: string;
  nhiCategory?: string;
  nhiCode?: string;
  part?: string;
  usage?: string;
  total?: string;
  note?: string;
  nhiExtendPatient?: INhiExtendPatient;
}

export const defaultValue: Readonly<INhiMedicalRecord> = {};
