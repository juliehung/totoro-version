export interface IFindingType {
  id?: number;
  major?: string;
  minor?: string;
  display?: boolean;
}

export const defaultValue: Readonly<IFindingType> = {
  display: false
};
