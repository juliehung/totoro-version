import { Moment } from 'moment';
import { INhiMonthDeclaration } from 'app/shared/model//nhi-month-declaration.model';

export const enum NhiMonthDeclarationType {
  SUBMISSION = 'SUBMISSION',
  SUPPLEMENT = 'SUPPLEMENT'
}

export interface INhiMonthDeclarationDetails {
  id?: number;
  type?: NhiMonthDeclarationType;
  way?: string;
  caseTotal?: number;
  pointTotal?: number;
  outPatientPoint?: number;
  preventiveCaseTotal?: number;
  preventivePointTotal?: number;
  generalCaseTotal?: number;
  generalPointTotal?: number;
  professionalCaseTotal?: number;
  professionalPointTotal?: number;
  partialCaseTotal?: number;
  partialPointTotal?: number;
  file?: string;
  uploadTime?: Moment;
  localId?: string;
  nhiId?: string;
  nhiMonthDeclaration?: INhiMonthDeclaration;
}

export const defaultValue: Readonly<INhiMonthDeclarationDetails> = {};
