import { Moment } from 'moment';
import { IPatient } from 'app/shared/model//patient.model';

export interface INhiAccumulatedMedicalRecord {
  id?: number;
  medicalCategory?: string;
  newbornMedicalTreatmentNote?: string;
  date?: Moment;
  cardFillingNote?: string;
  seqNumber?: string;
  medicalInstitutionCode?: string;
  patient?: IPatient;
}

export const defaultValue: Readonly<INhiAccumulatedMedicalRecord> = {};
