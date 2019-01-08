export default {
  pages: [
    {
      name: 'page1',
      questions: [
        {
          name: 'npdate',
          type: 'text',
          inputType: 'date',
          title: '初診日期',
          isRequired: true
        },
        {
          name: 'name',
          type: 'text',
          title: '姓名',
          placeHolder: 'Dr. C',
          isRequired: true
        },
        {
          name: 'sex',
          type: 'radiogroup',
          title: '性別',
          isRequired: true,
          defaultValue: 'item3',
          choices: [
            {
              value: 'item1',
              text: '男'
            },
            {
              value: 'item2',
              text: '女'
            },
            {
              value: 'item3',
              text: '不指定'
            }
          ],
          colCount: 1
        },
        {
          name: 'birthdate',
          type: 'text',
          inputType: 'date',
          title: '出生日期',
          isRequired: true
        },
        {
          name: 'id',
          type: 'text',
          title: '身分證字號',
          isRequired: true
        },
        {
          name: 'phone',
          type: 'text',
          title: '連絡電話'
        },
        {
          name: 'mobile',
          type: 'text',
          title: '行動電話'
        },
        {
          name: 'email',
          type: 'text',
          inputType: 'email',
          title: 'e-mail',
          validators: [
            {
              type: 'email'
            }
          ]
        },
        {
          name: 'socialLINE',
          type: 'text',
          title: 'LINE帳號'
        },
        {
          name: 'socialFB',
          type: 'text',
          title: '臉書帳號'
        },
        {
          name: 'address',
          type: 'text',
          title: '通訊地址'
        },
        {
          name: 'registeredAddr',
          type: 'radiogroup',
          title: '戶籍地址',
          isRequired: true,
          defaultValue: 'item1',
          hasOther: true,
          otherText: '其他',
          choices: [
            {
              value: 'item1',
              text: '同通訊住址'
            }
          ],
          colCount: 1
        },
        {
          name: 'marriage',
          type: 'radiogroup',
          title: '婚姻',
          isRequired: true,
          defaultValue: 'item1',
          choices: [
            {
              value: 'item1',
              text: '未婚'
            },
            {
              value: 'item2',
              text: '已婚'
            },
            {
              value: 'item3',
              text: '離婚'
            },
            {
              value: 'item4',
              text: '配偶死亡'
            }
          ],
          colCount: 1
        },
        {
          name: 'introducer',
          type: 'text',
          title: '介紹人'
        },
        {
          name: 'emergencyContact',
          type: 'text',
          title: '緊急聯絡人'
        },
        {
          name: 'ecPhone',
          type: 'text',
          title: '緊急聯絡人電話'
        },
        {
          name: 'ecAddr',
          type: 'text',
          title: '緊急聯絡人地址'
        },
        {
          name: 'relationship',
          type: 'radiogroup',
          title: '與病患關係',
          choices: [
            {
              value: 'item1',
              text: '配偶'
            },
            {
              value: 'item2',
              text: '父母'
            },
            {
              value: 'item3',
              text: '子女'
            }
          ],
          colCount: 1
        },
        {
          name: 'employmentHistory',
          type: 'checkbox',
          title: '職業史',
          hasOther: true,
          otherText: '其他',
          choices: [
            {
              value: 'item1',
              text: '農、林、漁、牧'
            },
            {
              value: 'item2',
              text: '軍、公、教'
            },
            {
              value: 'item3',
              text: '專業、科學及技術服務業'
            },
            {
              value: 'item4',
              text: '製造業'
            },
            {
              value: 'item5',
              text: '餐飲業'
            },
            {
              value: 'item6',
              text: '治安人員'
            },
            {
              value: 'item7',
              text: '營造業'
            },
            {
              value: 'item8',
              text: '服務業'
            },
            {
              value: 'item9',
              text: '運輸及倉儲業'
            },
            {
              value: 'item10',
              text: '資訊及通訊傳播業'
            },
            {
              value: 'item11',
              text: '金融及保險'
            },
            {
              value: 'item12',
              text: '職業運動人員'
            },
            {
              value: 'item13',
              text: '家庭管理'
            }
          ]
        },
        {
          name: 'diseaseHistory',
          type: 'checkbox',
          title: '疾病史',
          hasOther: true,
          otherText: '其他',
          hasNone: true,
          noneText: '以上皆無',
          choices: [
            {
              value: 'item1',
              text: '胃腸疾病'
            },
            {
              value: 'item2',
              text: '高血壓'
            },
            {
              value: 'item3',
              text: '糖尿病'
            },
            {
              value: 'item4',
              text: '氣喘'
            },
            {
              value: 'item5',
              text: '腦中風'
            },
            {
              value: 'item6',
              text: '高血脂'
            },
            {
              value: 'item7',
              text: '心臟病'
            },
            {
              value: 'item8',
              text: '腎臟病'
            },
            {
              value: 'item9',
              text: '肝病'
            }
          ]
        },
        {
          name: 'drug',
          type: 'checkbox',
          title: '正在服用藥物',
          hasOther: true,
          otherText: '其他',
          hasNone: true,
          noneText: '以上皆無',
          choices: [
            {
              value: 'item1',
              text: '藥物1'
            },
            {
              value: 'item2',
              text: '藥物2'
            },
            {
              value: 'item3',
              text: '藥物3'
            }
          ]
        },
        {
          name: 'drugAllergy',
          type: 'checkbox',
          title: '藥物過敏',
          hasOther: true,
          otherText: '其他',
          hasNone: true,
          noneText: '以上皆無',
          choices: [
            {
              value: 'item1',
              text: '藥物1'
            },
            {
              value: 'item2',
              text: '藥物2'
            },
            {
              value: 'item3',
              text: '藥物3'
            }
          ]
        },
        {
          type: 'multipletext',
          name: 'glycemic',
          title: '血糖',
          colCount: 2,
          items: [
            {
              name: 'ac',
              title: '飯前'
            },
            {
              name: 'pc',
              title: '飯後'
            }
          ]
        },
        {
          type: 'radiogroup',
          name: 'smoking',
          title: '吸菸',
          hasOther: true,
          otherText: '有：一天抽菸量',
          choices: [
            {
              value: 'item1',
              text: '無'
            }
          ],
          colCount: 1
        },
        {
          type: 'radiogroup',
          name: 'pregnant',
          title: '懷孕',
          hasOther: true,
          otherText: '有：日期 (年)(月)',
          choices: [
            {
              value: 'item1',
              text: '無'
            }
          ],
          colCount: 1
        },
        {
          name: 'problems',
          type: 'checkbox',
          title: '牙科治療中曾遇到的問題',
          isRequired: true,
          hasOther: true,
          choices: ['曾經拔牙困難或血流不止', '曾經治療牙齒時昏倒或暈眩', '曾經注射麻藥有不良反應'],
          colCount: 1,
          otherText: '其他',
          hasNone: true,
          noneText: '以上皆無'
        }
      ]
    }
  ],
  completedHtml: '<p class="text-center"><h4>問卷已完成</h4></p>'
};
