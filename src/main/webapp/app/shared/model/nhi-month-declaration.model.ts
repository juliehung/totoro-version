import { INhiMonthDeclarationDetails } from 'app/shared/model//nhi-month-declaration-details.model';

export interface INhiMonthDeclaration {
  id?: number;
  yearMonth?: string;
  institution?: string;
  nhiMonthDeclarationDetails?: INhiMonthDeclarationDetails[];
}

export const defaultValue: Readonly<INhiMonthDeclaration> = {};
