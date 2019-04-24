export interface IPatientIdentity {
  id?: number;
  code?: string;
  name?: string;
  freeBurden?: boolean;
}

export const defaultValue: Readonly<IPatientIdentity> = {
  freeBurden: false
};
