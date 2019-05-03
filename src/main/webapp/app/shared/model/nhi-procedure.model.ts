import { INHIProcedureType } from 'app/shared/model//nhi-procedure-type.model';

export interface INHIProcedure {
  id?: number;
  code?: string;
  name?: string;
  point?: number;
  englishName?: string;
  nhiProcedureType?: INHIProcedureType;
}

export const defaultValue: Readonly<INHIProcedure> = {};
