export interface IPatientIdentity {
  id?: number;
  code?: string;
  name?: string;
  freeBurden?: number;
}

export const defaultValue: Readonly<IPatientIdentity> = {};
