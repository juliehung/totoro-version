import { IPatient } from 'app/shared/model//patient.model';

export const enum TagType {
  ALLERGY = 'ALLERGY',
  DISEASE = 'DISEASE',
  OTHER = 'OTHER'
}

export interface ITag {
  id?: number;
  type?: TagType;
  name?: string;
  modifiable?: boolean;
  order?: number;
  patients?: IPatient[];
}

export const defaultValue: Readonly<ITag> = {
  modifiable: false
};
