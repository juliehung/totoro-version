export interface IProcedureType {
  id?: number;
  major?: string;
  minor?: string;
  display?: boolean;
}

export const defaultValue: Readonly<IProcedureType> = {
  display: false
};
