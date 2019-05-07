import { INhiIcd10Cm } from 'app/shared/model//nhi-icd-10-cm.model';

export interface INhiIcd9Cm {
  id?: number;
  code?: string;
  name?: string;
  englishName?: string;
  nhiIcd10Cms?: INhiIcd10Cm[];
}

export const defaultValue: Readonly<INhiIcd9Cm> = {};
