export interface IDocNp {
  id?: number;
  patient?: object;
  patientId?: number;
  esignId?: number;
}

export const defaultValue: Readonly<IDocNp> = {};
