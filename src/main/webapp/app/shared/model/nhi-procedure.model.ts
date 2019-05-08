import { INhiProcedureType } from 'app/shared/model//nhi-procedure-type.model';
import { INhiIcd9Cm } from 'app/shared/model//nhi-icd-9-cm.model';
import { INhiIcd10Pcs } from 'app/shared/model//nhi-icd-10-pcs.model';

export interface INhiProcedure {
  id?: number;
  code?: string;
  name?: string;
  point?: number;
  englishName?: string;
  defaultIcd10CmId?: number;
  nhiProcedureType?: INhiProcedureType;
  nhiIcd9Cm?: INhiIcd9Cm;
  nhiIcd10Pcs?: INhiIcd10Pcs[];
}

export const defaultValue: Readonly<INhiProcedure> = {};
