import Man from '../../images/Man.png';
import Woman from '../../images/Woman.png';
import Default from '../../images/Default.png';

export const BooleanOption = [
  { key: 'A', value: true, code: true },
  { key: 'B', value: false, code: false },
];

export const BloodTypeOption = [
  { key: 'A', value: 'O', code: 'O' },
  { key: 'B', value: 'A', code: 'A' },
  { key: 'C', value: 'B', code: 'B' },
  { key: 'D', value: 'AB', code: 'AB' },
  { key: 'E', value: '未知', code: 'UNKNOWN' },
];

export const GenderOption = [
  { key: 'A', value: '男性', image: Man, code: 'MALE' },
  { key: 'B', value: '女性', image: Woman, code: 'FEMALE' },
  { key: 'C', value: '其他', image: Default, code: 'OTHER' },
];

export const CareerOption = [
  { key: 'A', value: '農、林、漁、牧', code: 'afaah', jhi_order: 1 },
  { key: 'B', value: '軍、公、教', code: 'annual', jhi_order: 2 },
  { key: 'C', value: '專業、科學及技術服務業', code: 'specialist', jhi_order: 3 },
  { key: 'D', value: '製造業', code: 'manufacturing', jhi_order: 4 },
  { key: 'E', value: '餐飲業', code: 'foodService', jhi_order: 5 },
  { key: 'F', value: '治安人員', code: 'security', jhi_order: 6 },
  { key: 'G', value: '營造業', code: 'construction', jhi_order: 7 },
  { key: 'H', value: '服務業', code: 'serviceIndustry', jhi_order: 8 },
  { key: 'I', value: '運輸及倉儲業', code: 'commonCarrier', jhi_order: 9 },
  { key: 'J', value: '資訊及通訊傳播業', code: 'info', jhi_order: 10 },
  { key: 'K', value: '金融及保險', code: 'financial', jhi_order: 11 },
  { key: 'L', value: '職業運動人員', code: 'athlete', jhi_order: 12 },
  { key: 'M', value: '家庭管理', code: 'homeManagement', jhi_order: 13 },
];

export const MarriageOption = [
  { key: 'A', value: '未婚', code: 'unmarried', jhi_order: 1 },
  { key: 'B', value: '已婚', code: 'married', jhi_order: 2 },
  { key: 'C', value: '離婚', code: 'divorce', jhi_order: 3 },
  { key: 'D', value: '配偶死亡', code: 'dead', jhi_order: 4 },
];

export const RelationshipOption = [
  { key: 'A', value: '配偶', code: 'spouse', jhi_order: 1 },
  { key: 'B', value: '父母', code: 'parent', jhi_order: 2 },
  { key: 'C', value: '子女', code: 'child', jhi_order: 3 },
  { key: 'D', value: '朋友', code: 'friend', jhi_order: 4 },
];

export const tags = [
  { id: 1, key: 'A', jhi_type: 'ALLERGY', value: 'Aspirin', code: 1 },
  { id: 2, key: 'B', jhi_type: 'ALLERGY', value: 'Penicillin', code: 2 },
  { id: 4, key: 'C', jhi_type: 'ALLERGY', value: 'Pyrine', code: 4 },
  { id: 5, key: 'D', jhi_type: 'ALLERGY', value: 'NSAID', code: 5 },
  { id: 6, key: 'E', jhi_type: 'ALLERGY', value: '磺胺', code: 6 },
  { id: 7, key: 'F', jhi_type: 'ALLERGY', value: '消炎藥', code: 7 },
  { id: 9, key: 'A', jhi_type: 'DISEASE', value: 'AIDS', code: 9 },
  { id: 10, key: 'B', jhi_type: 'DISEASE', value: '高血壓', code: 10 },
  { id: 11, key: 'C', jhi_type: 'DISEASE', value: '低血壓', code: 11 },
  { id: 12, key: 'D', jhi_type: 'DISEASE', value: '氣喘', code: 12 },
  { id: 13, key: 'E', jhi_type: 'DISEASE', value: '心臟雜音', code: 13 },
  { id: 14, key: 'F', jhi_type: 'DISEASE', value: '糖尿病', code: 14 },
  { id: 15, key: 'G', jhi_type: 'DISEASE', value: '肺炎', code: 15 },
  { id: 16, key: 'H', jhi_type: 'DISEASE', value: '肺結核', code: 16 },
  { id: 17, key: 'I', jhi_type: 'DISEASE', value: '肝病', code: 17 },
  { id: 18, key: 'J', jhi_type: 'DISEASE', value: '消化性潰瘍', code: 18 },
  { id: 19, key: 'K', jhi_type: 'DISEASE', value: '癲癇', code: 19 },
  { id: 20, key: 'L', jhi_type: 'DISEASE', value: '暈眩', code: 20 },
  { id: 21, key: 'M', jhi_type: 'DISEASE', value: '中風', code: 21 },
  { id: 22, key: 'N', jhi_type: 'DISEASE', value: '惡性腫瘤(癌症)', code: 22 },
  { id: 23, key: 'O', jhi_type: 'DISEASE', value: '風濕熱', code: 23 },
  { id: 24, key: 'P', jhi_type: 'DISEASE', value: '過敏', code: 24 },
  { id: 8, key: 'Q', jhi_type: 'DISEASE', value: '骨質疏鬆藥(雙磷酸鹽類藥物)', code: 8 },
  { id: 25, jhi_type: 'OTHER', value: '懷孕', code: 25 },
  { id: 26, jhi_type: 'OTHER', value: '抽煙者', code: 26 },
  { id: 27, key: 'A', jhi_type: 'OTHER', value: '曾經拔牙困難或血流不止', code: 27 },
  { id: 28, key: 'B', jhi_type: 'OTHER', value: '曾經治療牙齒時昏迷或暈眩', code: 28 },
  { id: 29, key: 'C', jhi_type: 'OTHER', value: '曾經注射麻藥有不良反應', code: 29 },
  { id: 30, key: 'D', jhi_type: 'OTHER', value: '曾住院或接受手術', code: 30 },
  { id: 31, key: 'E', jhi_type: 'OTHER', value: '一年內服長期藥物(含避孕藥)', code: 31 },
  { id: 32, key: 'F', jhi_type: 'OTHER', value: '曾經接受放射線治療或化學治療', code: 32 },
];
