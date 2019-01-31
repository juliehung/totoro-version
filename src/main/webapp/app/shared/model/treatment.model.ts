import { IPatient } from 'app/shared/model//patient.model';

export interface ITreatment {
  id?: number;
  name?: string;
  chiefComplaint?: string;
  goal?: string;
  note?: string;
  finding?: string;
  patient?: IPatient;
}

export const defaultValue: Readonly<ITreatment> = {};
