export interface IHospital {
  id?: number;
  hospitalId?: string;
  name?: string;
  address?: string;
  branch?: number;
}

export const defaultValue: Readonly<IHospital> = {};
