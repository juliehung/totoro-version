import { INhiIcd9Cm } from 'app/shared/model//nhi-icd-9-cm.model';

export interface INhiIcd10Cm {
  id?: number;
  code?: string;
  name?: string;
  englishName?: string;
  nhiIcd9Cm?: INhiIcd9Cm;
}

export const defaultValue: Readonly<INhiIcd10Cm> = {};
