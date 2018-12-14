export const enum Hepatitis {
  A = 'A',
  B = 'B',
  C = 'C',
  D = 'D',
  E = 'E'
}

export const enum Month {
  JAN = 'JAN',
  FEB = 'FEB',
  MAR = 'MAR',
  APR = 'APR',
  MAY = 'MAY',
  JUN = 'JUN',
  JUL = 'JUL',
  AUG = 'AUG',
  SEP = 'SEP',
  OCT = 'OCT',
  NOV = 'NOV',
  DEC = 'DEC'
}

export interface IQuestionnaire {
  id?: number;
  hypertension?: string;
  heartDiseases?: string;
  kidneyDiseases?: string;
  bloodDiseases?: string;
  liverDiseases?: string;
  hepatitisType?: Hepatitis;
  gastrointestinalDiseases?: string;
  receivingMedication?: string;
  anyAllergySensitivity?: string;
  glycemicAC?: number;
  glycemicPC?: number;
  smokeNumberADay?: number;
  productionYear?: number;
  productionMonth?: Month;
  other?: string;
  difficultExtractionOrContinuousBleeding?: boolean;
  nauseaOrDizziness?: boolean;
  adverseReactionsToAnestheticInjections?: boolean;
  otherInTreatment?: string;
}

export const defaultValue: Readonly<IQuestionnaire> = {
  difficultExtractionOrContinuousBleeding: false,
  nauseaOrDizziness: false,
  adverseReactionsToAnestheticInjections: false
};
