import { INhiProcedureType } from 'app/shared/model//nhi-procedure-type.model';
import { INhiIcd9Cm } from 'app/shared/model//nhi-icd-9-cm.model';

export interface INhiProcedure {
  id?: number;
  code?: string;
  name?: string;
  point?: number;
  englishName?: string;
  defaultIcd10CmId?: number;
  nhiProcedureType?: INhiProcedureType;
  nhiIcd9Cm?: INhiIcd9Cm;
}

export const defaultValue: Readonly<INhiProcedure> = {};
