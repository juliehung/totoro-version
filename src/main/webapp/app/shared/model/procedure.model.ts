import { IProcedureType } from 'app/shared/model//procedure-type.model';

export interface IProcedure {
  id?: number;
  content?: string;
  price?: number;
  procedureType?: IProcedureType;
}

export const defaultValue: Readonly<IProcedure> = {};
