import Man from '../../images/Man.png';
import Woman from '../../images/Woman.png';
import Default from '../../images/Default.png';

export const BloodTypeOption = [
  { key: 'A', value: 'O' },
  { key: 'B', value: 'A' },
  { key: 'C', value: 'B' },
  { key: 'D', value: 'AB' },
  { key: 'E', value: '未知' },
];

export const GenderOption = [
  { key: 'A', value: '男性', image: Man },
  { key: 'B', value: '女性', image: Woman },
  { key: 'C', value: '其他', image: Default },
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
  { key: 'C', value: '女', code: 'child', jhi_order: 3 },
  { key: 'D', value: '朋友', code: 'friend', jhi_order: 4 },
];

export const DiseaseOption = [
  { key: 'A', value: 'AIDS' },
  { key: 'B', value: '高血壓' },
  { key: 'C', value: '氣喘' },
  { key: 'D', value: '心臟雜音' },
  { key: 'E', value: '糖尿病' },
  { key: 'F', value: '肺炎' },
  { key: 'G', value: '肝病' },
  { key: 'H', value: '肺結核' },
  { key: 'I', value: '消化性潰瘍' },
  { key: 'J', value: '癲癇' },
  { key: 'K', value: '暈眩' },
  { key: 'L', value: '中風' },
  { key: 'M', value: '風濕熱' },
  { key: 'N', value: '過敏' },
  { key: 'O', value: '癌症' },
];

export const tags = [
  { id: 1, key: 'A', jhi_type: 'ALLERGY', value: 'Aspirin' },
  { id: 2, key: 'B', jhi_type: 'ALLERGY', value: 'Penicillin' },
  { id: 4, key: 'C', jhi_type: 'ALLERGY', value: 'Pyrine' },
  { id: 5, key: 'D', jhi_type: 'ALLERGY', value: 'NSAID' },
  { id: 6, key: 'E', jhi_type: 'ALLERGY', value: '磺胺' },
  { id: 7, key: 'F', jhi_type: 'ALLERGY', value: '消炎藥' },
  { id: 8, key: 'G', jhi_type: 'ALLERGY', value: '骨質疏鬆藥' },
  { id: 9, key: 'A', jhi_type: 'DISEASE', value: 'AIDS' },
  { id: 10, key: 'B', jhi_type: 'DISEASE', value: '高血壓' },
  { id: 11, key: 'C', jhi_type: 'DISEASE', value: '低血壓' },
  { id: 12, key: 'D', jhi_type: 'DISEASE', value: '氣喘' },
  { id: 13, key: 'E', jhi_type: 'DISEASE', value: '心臟雜音' },
  { id: 14, key: 'F', jhi_type: 'DISEASE', value: '糖尿病' },
  { id: 15, key: 'G', jhi_type: 'DISEASE', value: '肺炎' },
  { id: 16, key: 'H', jhi_type: 'DISEASE', value: '肺結核' },
  { id: 17, key: 'I', jhi_type: 'DISEASE', value: '肝病' },
  { id: 18, key: 'J', jhi_type: 'DISEASE', value: '消化性潰瘍' },
  { id: 19, key: 'K', jhi_type: 'DISEASE', value: '癲癇' },
  { id: 20, key: 'L', jhi_type: 'DISEASE', value: '暈眩' },
  { id: 21, key: 'M', jhi_type: 'DISEASE', value: '中風' },
  { id: 22, key: 'N', jhi_type: 'DISEASE', value: '惡性腫瘤(癌症)' },
  { id: 23, key: 'O', jhi_type: 'DISEASE', value: '風濕熱' },
  { id: 24, key: 'P', jhi_type: 'DISEASE', value: '過敏' },
  { id: 25, jhi_type: 'OTHER', value: '懷孕' },
  { id: 26, jhi_type: 'OTHER', value: '抽煙者' },
  { id: 27, key: 'A', jhi_type: 'OTHER', value: '曾經拔牙困難或血流不止' },
  { id: 28, key: 'B', jhi_type: 'OTHER', value: '曾經治療牙齒時昏迷或暈眩' },
  { id: 29, key: 'C', jhi_type: 'OTHER', value: '曾經注射麻藥有不良反應' },
  { id: 30, key: 'D', jhi_type: 'OTHER', value: '曾住院或接受手術' },
  { id: 31, key: 'E', jhi_type: 'OTHER', value: '一年內服長期藥物(含避孕藥)' },
  { id: 32, key: 'F', jhi_type: 'OTHER', value: '曾經接受放射線治療或化學治療' },
];
