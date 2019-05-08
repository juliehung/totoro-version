import { INhiProcedure } from 'app/shared/model//nhi-procedure.model';

export interface INhiIcd10Pcs {
  id?: number;
  code?: string;
  nhiName?: string;
  englishName?: string;
  chineseName?: string;
  nhiProcedure?: INhiProcedure;
}

export const defaultValue: Readonly<INhiIcd10Pcs> = {};
