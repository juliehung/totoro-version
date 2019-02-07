export interface IQuestionnaire {
  id?: number;
  drug?: boolean;
  drugName?: string;
  glycemicAC?: number;
  glycemicPC?: number;
  smokeNumberADay?: number;
  otherInTreatment?: string;
}

export const defaultValue: Readonly<IQuestionnaire> = {
  drug: false
};
