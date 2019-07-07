import { createEntity, getEntity, reset, updateEntity } from 'app/entities/questionnaire/questionnaire.reducer';
import { getEntity as getPatient, updateEntity as updatePatient } from 'app/entities/patient/patient.reducer';
import { getEntities as getTags } from 'app/entities/tag/tag.reducer';
import { IRootState } from 'app/shared/reducers';

import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Col, CustomInput, Form, FormGroup, Input, Label, Row, Progress } from 'reactstrap';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faChevronLeft } from '@fortawesome/free-solid-svg-icons';
import Lottie from 'react-lottie';
import './survey.css';
import { Gender } from 'app/shared/model/patient.model';
import moment from 'moment';

import Signature from '../signature/signature';

export interface ISurveyProps extends StateProps, DispatchProps, RouteComponentProps<{}> {}

export interface ISurveyState {
  drugNameDisabled: boolean;
  showDino: boolean;
  smoking: boolean;
  smokeNumberADay: string;
  smokingDisabled: boolean;
  pregnant: boolean;
  drug1: boolean;
  drug2: boolean;
  drugAllergy: boolean[];
  disease: boolean[];
  problems: boolean[];
  drugName: string;
  loaded: boolean;
  imgDataURL: string;
}

export class Survey extends React.Component<ISurveyProps, ISurveyState> {
  state: ISurveyState = {
    drugNameDisabled: true,
    showDino: false,
    smoking: false,
    smokeNumberADay: '',
    smokingDisabled: true,
    pregnant: false,
    drug1: false,
    drug2: false,
    drugAllergy: new Array(8).fill(false),
    disease: new Array(16).fill(false),
    problems: new Array(6).fill(false),
    drugName: '',
    loaded: false,
    imgDataURL: ''
  };

  componentDidMount() {
    const params = new URLSearchParams(this.props.location.search);
    const pid = params.get('pid');
    if (pid) {
      this.props.getPatient(pid);
    }
    this.props.getTags(0, 40, 'id,asc');
  }

  componentDidUpdate(prevProps) {
    if (this.props.patientEntity !== prevProps.patientEntity) {
      const { patientEntity } = this.props;
      let drug1 = false;
      let drug2 = false;
      if (patientEntity.questionnaire && patientEntity.questionnaire.drug) {
        drug2 = patientEntity.questionnaire.drugName && patientEntity.questionnaire.drugName !== '';
        drug1 = !drug2;
      }
      const state = this.parseTags(patientEntity.tags);
      const drugNameDisabled = !drug2;
      const drugName = patientEntity.questionnaire ? patientEntity.questionnaire.drugName : '';
      const loaded = true;
      this.setState({ ...state, drug1, drug2, drugNameDisabled, drugName, loaded });
    }
  }

  onFormSubmit = event => {
    event.preventDefault();
    const data = new FormData(event.target);
    const blood = data.get('blood');
    const phone = data.get('phone');
    const address = data.get('address');
    const lineId = data.get('lineId');
    const fbId = data.get('fbId');
    const contactType = data.get('contactType');
    const jobType = data.get('jobType');
    const maritalStatus = data.get('maritalStatus');
    const introducor = data.get('introducor');
    const emergencyName = data.get('emergencyName');
    const emergencyPhone = data.get('emergencyPhone');
    const emergencyRelationship = data.get('emergencyRelationship');
    const drug = data.get('drug') === 'no' ? false : true;
    const drugName = data.get('drugName');
    const glycemicAC = data.get('glycemicAC');
    const glycemicPC = data.get('glycemicPC');
    const smokeNumberADay = data.get('smokeNumberADay');
    const smoking = data.get('smoking');
    const pregnant = data.get('pregnant');
    const disease = [];

    for (let i = 1; i <= 16; i++) {
      const d = data.get('disease' + i);
      if (d) {
        disease.push(d);
      }
    }
    const drugAllergy = [];
    for (let i = 1; i <= 8; i++) {
      const d = data.get('drugAllergy' + i);
      if (d) {
        drugAllergy.push(d);
      }
    }
    const problems = [];
    for (let i = 1; i <= 6; i++) {
      const d = data.get('problems' + i);
      if (d) {
        problems.push(d);
      }
    }

    const tags = [];

    if (drugAllergy.length || disease.length || problems.length) {
      for (const ex of this.props.tagList) {
        if (drugAllergy.indexOf(ex.name) !== -1 || disease.indexOf(ex.name) !== -1 || problems.indexOf(ex.name) !== -1) {
          tags.push(ex);
        }
      }
    }

    // additional information
    // contactType, jobType, emergencyRelationship, maritalStatus, introducor,
    const questionnaire = { ...this.props.patientEntity.questionnaire, drug, drugName, glycemicAC, glycemicPC, smokeNumberADay };
    const json = {
      blood,
      phone,
      address,
      lineId,
      fbId,
      emergencyName,
      emergencyPhone,
      tags,
      questionnaire
    };

    // TODO: sava imgURL to server
    const imgDataURL = this.state.imgDataURL;

    this.setState({ showDino: true });
    // TODO: api and loading progress and success/error page
    let deepCopyPatientEntity = JSON.parse(JSON.stringify(this.props.patientEntity));
    deepCopyPatientEntity = { ...deepCopyPatientEntity, ...json };
    this.props.updatePatient(deepCopyPatientEntity);
  };

  onChange = event => {
    switch (event.target.name) {
      case 'drug':
        if (event.target.value === 'yes1') {
          this.setState({ drug1: true, drug2: false, drugNameDisabled: true, drugName: '' });
        } else if (event.target.value === 'yes2') {
          this.setState({ drug1: false, drug2: true, drugNameDisabled: false });
        } else if (event.target.value === 'no') {
          this.setState({ drug1: false, drug2: false, drugNameDisabled: true, drugName: '' });
        }
        break;
      case 'drugName':
        this.setState({ drugName: event.target.value || '' });
        break;
      case 'smoking':
        if (event.target.value === 'no') {
          this.setState({ smoking: false, smokingDisabled: true, smokeNumberADay: '' });
        } else if (event.target.value === 'yes') {
          this.setState({ smoking: true, smokingDisabled: false });
        }
        break;
      case 'smokeNumberADay':
        if (!isNaN(Number(event.target.value))) {
          this.setState({ smokeNumberADay: event.target.value || '' });
        }
        break;
      case 'pregnant':
        const { pregnant } = this.state;
        this.setState({ pregnant: !pregnant });
        break;
      case 'disease1':
      case 'disease2':
      case 'disease3':
      case 'disease4':
      case 'disease5':
      case 'disease6':
      case 'disease7':
      case 'disease8':
      case 'disease9':
      case 'disease10':
      case 'disease11':
      case 'disease12':
      case 'disease13':
      case 'disease14':
      case 'disease15':
      case 'disease16':
        const { disease } = this.state;
        if (event.target.value === 'AIDS') {
          disease[0] = !disease[0];
        } else if (event.target.value === '高血壓') {
          disease[1] = !disease[1];
        } else if (event.target.value === '低血壓') {
          disease[2] = !disease[2];
        } else if (event.target.value === '氣喘') {
          disease[3] = !disease[3];
        } else if (event.target.value === '心臟雜音') {
          disease[4] = !disease[4];
        } else if (event.target.value === '糖尿病') {
          disease[5] = !disease[5];
        } else if (event.target.value === '肺炎') {
          disease[6] = !disease[6];
        } else if (event.target.value === '肺結核') {
          disease[7] = !disease[7];
        } else if (event.target.value === '肝病') {
          disease[8] = !disease[8];
        } else if (event.target.value === '消化性潰瘍') {
          disease[9] = !disease[9];
        } else if (event.target.value === '癲癇') {
          disease[10] = !disease[10];
        } else if (event.target.value === '暈眩') {
          disease[11] = !disease[11];
        } else if (event.target.value === '中風') {
          disease[12] = !disease[12];
        } else if (event.target.value === '惡性腫瘤(癌症)') {
          disease[13] = !disease[13];
        } else if (event.target.value === '風濕熱') {
          disease[14] = !disease[14];
        } else if (event.target.value === '過敏') {
          disease[15] = !disease[15];
        }
        this.setState({ disease });
        break;
      case 'drugAllergy1':
      case 'drugAllergy2':
      case 'drugAllergy3':
      case 'drugAllergy4':
      case 'drugAllergy5':
      case 'drugAllergy6':
      case 'drugAllergy7':
      case 'drugAllergy8':
        const { drugAllergy } = this.state;
        if (event.target.value === 'Aspirin') {
          drugAllergy[0] = !drugAllergy[0];
        } else if (event.target.value === 'Penicillin') {
          drugAllergy[1] = !drugAllergy[1];
        } else if (event.target.value === '青黴素') {
          drugAllergy[2] = !drugAllergy[2];
        } else if (event.target.value === 'Pyrine') {
          drugAllergy[3] = !drugAllergy[3];
        } else if (event.target.value === 'NSAID') {
          drugAllergy[4] = !drugAllergy[4];
        } else if (event.target.value === '磺胺') {
          drugAllergy[5] = !drugAllergy[5];
        } else if (event.target.value === '消炎藥') {
          drugAllergy[6] = !drugAllergy[6];
        } else if (event.target.value === '骨質疏鬆藥(雙磷酸鹽類藥物)') {
          drugAllergy[7] = !drugAllergy[7];
        }
        this.setState({ drugAllergy });
        break;
      case 'problems1':
      case 'problems2':
      case 'problems3':
      case 'problems4':
      case 'problems5':
      case 'problems6':
        const { problems } = this.state;
        if (event.target.value === '曾經拔牙困難或血流不止') {
          problems[0] = !problems[0];
        } else if (event.target.value === '曾經治療牙齒時昏迷或暈眩') {
          problems[1] = !problems[1];
        } else if (event.target.value === '曾經注射麻藥有不良反應') {
          problems[2] = !problems[2];
        } else if (event.target.value === '曾住院或接受手術') {
          problems[3] = !problems[3];
        } else if (event.target.value === '一年內服長期藥物(含避孕藥)') {
          problems[4] = !problems[4];
        } else if (event.target.value === '曾經接受放射線治療或化學治療') {
          problems[5] = !problems[5];
        }
        this.setState({ problems });
        break;
      default:
        break;
    }
  };

  parseTags = tags => {
    if (!tags) return;

    const state = {
      smoking: false,
      pregnant: false,
      drugAllergy: new Array(8).fill(false),
      disease: new Array(16).fill(false),
      problems: new Array(6).fill(false)
    };
    tags.map(tag => {
      if (tag.name === '抽煙者') {
        state.smoking = true;
      } else if (tag.name === '懷孕') {
        state.pregnant = true;
      } else if (tag.name === 'AIDS') {
        state.disease[0] = true;
      } else if (tag.name === '高血壓') {
        state.disease[1] = true;
      } else if (tag.name === '低血壓') {
        state.disease[2] = true;
      } else if (tag.name === '氣喘') {
        state.disease[3] = true;
      } else if (tag.name === '心臟雜音') {
        state.disease[4] = true;
      } else if (tag.name === '糖尿病') {
        state.disease[5] = true;
      } else if (tag.name === '肺炎') {
        state.disease[6] = true;
      } else if (tag.name === '肺結核') {
        state.disease[7] = true;
      } else if (tag.name === '肝病') {
        state.disease[8] = true;
      } else if (tag.name === '消化性潰瘍') {
        state.disease[9] = true;
      } else if (tag.name === '癲癇') {
        state.disease[10] = true;
      } else if (tag.name === '暈眩') {
        state.disease[11] = true;
      } else if (tag.name === '中風') {
        state.disease[12] = true;
      } else if (tag.name === '惡性腫瘤(癌症)') {
        state.disease[13] = true;
      } else if (tag.name === '風濕熱') {
        state.disease[14] = true;
      } else if (tag.name === '過敏') {
        state.disease[15] = true;
      } else if (tag.name === 'Aspirin') {
        state.drugAllergy[0] = true;
      } else if (tag.name === 'Penicillin') {
        state.drugAllergy[1] = true;
      } else if (tag.name === '青黴素') {
        state.drugAllergy[2] = true;
      } else if (tag.name === 'Pyrine') {
        state.drugAllergy[3] = true;
      } else if (tag.name === 'NSAID') {
        state.drugAllergy[4] = true;
      } else if (tag.name === '磺胺') {
        state.drugAllergy[5] = true;
      } else if (tag.name === '消炎藥') {
        state.drugAllergy[6] = true;
      } else if (tag.name === '骨質疏鬆藥(雙磷酸鹽類藥物)') {
        state.drugAllergy[7] = true;
      } else if (tag.name === '曾經拔牙困難或血流不止') {
        state.problems[0] = true;
      } else if (tag.name === '曾經治療牙齒時昏迷或暈眩') {
        state.problems[1] = true;
      } else if (tag.name === '曾經注射麻藥有不良反應') {
        state.problems[2] = true;
      } else if (tag.name === '曾住院或接受手術') {
        state.problems[3] = true;
      } else if (tag.name === '一年內服長期藥物(含避孕藥)') {
        state.problems[4] = true;
      } else if (tag.name === '曾經接受放射線治療或化學治療') {
        state.problems[5] = true;
      }
    });
    return state;
  };

  renderDino = () => {
    const defaultOptions = {
      loop: true,
      autoplay: true,
      animationData: require('./dino.json'),
      rendererSettings: {
        preserveAspectRatio: 'xMidYMid slice'
      }
    };
    setTimeout(() => {
      this.setState({ showDino: false });
    }, 3000);
    return (
      <div
        style={{
          display: 'flex',
          justifyContent: 'center',
          alignItems: 'center',
          flexDirection: 'column',
          position: 'fixed',
          top: 0,
          left: 0,
          right: 0,
          bottom: 0,
          background: 'rgba(255,255,255,0.5)'
        }}
      >
        <Lottie options={defaultOptions} height={400} width={400} isStopped={false} isPaused={false} />
        <h3>存檔中...</h3>
      </div>
    );
  };

  renderProfile = gender => {
    const wh = 88;
    const profileWH = 55;
    if (gender === Gender.MALE) {
      return <img src="content/images/man@2x.png" width={wh} height={wh} />;
    } else if (gender === Gender.FEMALE) {
      return <img src="content/images/woman@2x.png" width={wh} height={wh} />;
    } else {
      return (
        <div
          style={{
            display: 'flex',
            justifyContent: 'center',
            alignItems: 'center',
            borderRadius: profileWH + 'px',
            width: wh + 'px',
            height: wh + 'px',
            backgroundColor: '#1768ac'
          }}
        >
          <img src="content/images/logo.png" width={profileWH} height={profileWH} />
        </div>
      );
    }
  };

  renderGenderText = gender => {
    if (gender === 'OTHER') return '無';
    if (gender === 'FEMALE') return '女';
    if (gender === 'MALE') return '男';
  };

  getSignatureURL = (url: string) => {
    this.setState({ imgDataURL: url });
  };

  render() {
    const { loaded } = this.state;
    if (!loaded) {
      return (
        <div>
          <p>Loading...</p>
          <Progress animated value="100" />
        </div>
      );
    }

    const { patientEntity } = this.props;
    const ColoredLine = () => (
      <hr
        style={{
          color: 'lightgray',
          backgroundColor: 'lightgray',
          height: 1
        }}
      />
    );
    const SlashLine = () => (
      <div>
        <img src="content/images/rectangle@2x.png" style={{ width: '100%' }} />
      </div>
    );
    const dt = moment(patientEntity.lastModifiedDate).format('YYYY-MM-DD HH:mm:ss');
    const { smoking, pregnant, drugAllergy, disease, problems, drug1, drug2, drugName, smokeNumberADay } = this.state;
    return (
      <div>
        <Link to={'/'}>
          <Button id="resetButton" className="btn btn-back">
            <FontAwesomeIcon icon={faChevronLeft} />
          </Button>
        </Link>
        <Row>
          <Col>
            <div className="time-rect-container">
              <div className="time-rect" style={{ borderRadius: '11px' }}>
                最後更新: {dt}
              </div>
            </div>
            <div style={{ display: 'flex' }}>
              {this.renderProfile(patientEntity.gender)}
              <div style={{ marginLeft: '5px' }}>
                <h1>{patientEntity.name}</h1>
                <div>
                  <span>病歷編號: </span>
                  <span>{patientEntity.medicalId}</span>
                </div>
              </div>
            </div>
            <ColoredLine />
          </Col>
        </Row>
        <Row>
          <Col>
            <Form onSubmit={this.onFormSubmit}>
              <FormGroup>
                <h4>基本資料</h4>
              </FormGroup>
              <FormGroup row>
                <Col sm={3}>
                  <Label>姓名：</Label>
                  <Label>{patientEntity.name}</Label>
                </Col>
                <Col sm={2}>
                  <Label>性別：</Label>
                  <Label>{this.renderGenderText(patientEntity.gender)}</Label>
                </Col>
                <Col sm={3}>
                  <Label>生日：</Label>
                  <Label>{patientEntity.birth}</Label>
                </Col>
                <Col sm={4}>
                  <Label>身分證字號：</Label>
                  <Label>{patientEntity.nationalId}</Label>
                </Col>
              </FormGroup>
              <FormGroup row>
                <Label for="blood" sm={1}>
                  血型
                </Label>
                <Col sm={2}>
                  <Input
                    type="select"
                    name="blood"
                    id="blood"
                    placeholder="請選擇"
                    defaultValue={patientEntity.blood ? patientEntity.blood : 'select'}
                  >
                    <option disabled hidden value="select">
                      請選擇
                    </option>
                    <option>A</option>
                    <option>AB</option>
                    <option>B</option>
                    <option>O</option>
                    <option>UNKNOWN</option>
                  </Input>
                </Col>
                <Label for="phone" sm={1}>
                  電話
                </Label>
                <Col sm={8}>
                  <Input
                    type="text"
                    name="phone"
                    id="phone"
                    placeholder="請填入手機或市話"
                    maxLength={15}
                    defaultValue={patientEntity.phone}
                  />
                </Col>
              </FormGroup>
              <FormGroup row>
                <Label for="address" sm={1}>
                  地址
                </Label>
                <Col sm={11}>
                  <Input
                    type="text"
                    name="address"
                    id="address"
                    placeholder="請填入聯絡地址"
                    maxLength={255}
                    defaultValue={patientEntity.address}
                  />
                </Col>
              </FormGroup>
              <FormGroup row>
                <Label for="lineId" sm={1}>
                  LINE
                </Label>
                <Col sm={3}>
                  <Input type="text" name="lineId" id="lineId" placeholder="請填入" maxLength={15} defaultValue={patientEntity.lineId} />
                </Col>
                <Label for="fbId" sm={1}>
                  臉書
                </Label>
                <Col sm={3}>
                  <Input type="text" name="fbId" id="fbId" placeholder="請填入" maxLength={15} defaultValue={patientEntity.fbId} />
                </Col>
                <Label for="contactType" sm={1}>
                  通知
                </Label>
                <Col sm={3}>
                  <Input type="select" name="contactType" id="contactType" defaultValue="select">
                    <option disabled hidden value="select">
                      請選擇
                    </option>
                    <option>LINE</option>
                    <option>臉書</option>
                    <option>Email</option>
                    <option>電話</option>
                  </Input>
                </Col>
              </FormGroup>
              <FormGroup row>
                <Label for="jobType" sm={1}>
                  職業
                </Label>
                <Col sm={3}>
                  <Input type="select" name="jobType" id="jobType" defaultValue="select">
                    <option disabled hidden value="select">
                      請選擇
                    </option>
                    <option>農、林、漁、牧</option>
                    <option>軍、公、教</option>
                    <option>專業、科學及技術服務業</option>
                    <option>製造業</option>
                    <option>餐飲業</option>
                    <option>治安人員</option>
                    <option>營造業</option>
                    <option>服務業</option>
                    <option>運輸及倉儲業</option>
                    <option>資訊及通訊傳播業</option>
                    <option>金融及保險</option>
                    <option>職業運動人員</option>
                    <option>家庭管理</option>
                  </Input>
                </Col>
                <Label for="maritalStatus" sm={1}>
                  婚姻
                </Label>
                <Col sm={3}>
                  <Input type="select" name="maritalStatus" id="maritalStatus" defaultValue="select">
                    <option disabled hidden value="select">
                      請選擇
                    </option>
                    <option>未婚</option>
                    <option>已婚</option>
                    <option>離婚</option>
                    <option>配偶死亡</option>
                  </Input>
                </Col>
                <Label for="introducor" sm={1}>
                  介紹
                </Label>
                <Col sm={3}>
                  <Input
                    type="text"
                    name="introducor"
                    id="introducor"
                    placeholder="請填入"
                    maxLength={15}
                    defaultValue={patientEntity.introducer}
                  />
                </Col>
              </FormGroup>
              <FormGroup>
                <SlashLine />
                <h4>緊急聯絡</h4>
              </FormGroup>
              <FormGroup row>
                <Label for="emergencyName" sm={1}>
                  姓名
                </Label>
                <Col sm={3}>
                  <Input
                    type="text"
                    name="emergencyName"
                    id="emergencyName"
                    placeholder="請填入"
                    maxLength={15}
                    defaultValue={patientEntity.emergencyName}
                  />
                </Col>
                <Label for="emergencyPhone" sm={1}>
                  電話
                </Label>
                <Col sm={3}>
                  <Input
                    type="text"
                    name="emergencyPhone"
                    id="emergencyPhone"
                    placeholder="請填入手機或市話"
                    defaultValue={patientEntity.emergencyPhone}
                    maxLength={15}
                  />
                </Col>
                <Label for="emergencyRelationship" sm={1}>
                  關係
                </Label>
                <Col sm={3}>
                  <Input type="select" name="emergencyRelationship" id="emergencyRelationship" defaultValue="select">
                    <option disabled hidden value="select">
                      請選擇
                    </option>
                    <option>配偶</option>
                    <option>父母</option>
                    <option>子女</option>
                  </Input>
                </Col>
              </FormGroup>
              <FormGroup>
                <SlashLine />
                <h4>詢問事項</h4>
              </FormGroup>
              <FormGroup row>
                <Label for="disease" sm={12}>
                  疾病史
                </Label>
                <Col sm={2}>
                  <CustomInput
                    type="checkbox"
                    name="disease1"
                    id="disease1"
                    label="AIDS"
                    value="AIDS"
                    checked={disease[0]}
                    onChange={this.onChange}
                  />
                </Col>
                <Col sm={2}>
                  <CustomInput
                    type="checkbox"
                    name="disease2"
                    id="disease2"
                    label="高血壓"
                    value="高血壓"
                    checked={disease[1]}
                    onChange={this.onChange}
                  />
                </Col>
                <Col sm={2}>
                  <CustomInput
                    type="checkbox"
                    name="disease3"
                    id="disease3"
                    label="低血壓"
                    value="低血壓"
                    checked={disease[2]}
                    onChange={this.onChange}
                  />
                </Col>
                <Col sm={2}>
                  <CustomInput
                    type="checkbox"
                    name="disease4"
                    id="disease4"
                    label="氣喘"
                    value="氣喘"
                    checked={disease[3]}
                    onChange={this.onChange}
                  />
                </Col>
                <Col sm={2}>
                  <CustomInput
                    type="checkbox"
                    name="disease5"
                    id="disease5"
                    label="心臟雜音"
                    value="心臟雜音"
                    checked={disease[4]}
                    onChange={this.onChange}
                  />
                </Col>
                <Col sm={2}>
                  <CustomInput
                    type="checkbox"
                    name="disease6"
                    id="disease6"
                    label="糖尿病"
                    value="糖尿病"
                    checked={disease[5]}
                    onChange={this.onChange}
                  />
                </Col>
                <Col sm={2}>
                  <CustomInput
                    type="checkbox"
                    name="disease7"
                    id="disease7"
                    label="肺炎"
                    value="肺炎"
                    checked={disease[6]}
                    onChange={this.onChange}
                  />
                </Col>
                <Col sm={2}>
                  <CustomInput
                    type="checkbox"
                    name="disease8"
                    id="disease8"
                    label="肺結核"
                    value="肺結核"
                    checked={disease[7]}
                    onChange={this.onChange}
                  />
                </Col>
                <Col sm={2}>
                  <CustomInput
                    type="checkbox"
                    name="disease9"
                    id="disease9"
                    label="肝病"
                    value="肝病"
                    checked={disease[8]}
                    onChange={this.onChange}
                  />
                </Col>
                <Col sm={2}>
                  <CustomInput
                    type="checkbox"
                    name="disease11"
                    id="disease11"
                    label="癲癇"
                    value="癲癇"
                    checked={disease[10]}
                    onChange={this.onChange}
                  />
                </Col>
                <Col sm={2}>
                  <CustomInput
                    type="checkbox"
                    name="disease12"
                    id="disease12"
                    label="暈眩"
                    value="暈眩"
                    checked={disease[11]}
                    onChange={this.onChange}
                  />
                </Col>
                <Col sm={2}>
                  <CustomInput
                    type="checkbox"
                    name="disease13"
                    id="disease13"
                    label="中風"
                    value="中風"
                    checked={disease[12]}
                    onChange={this.onChange}
                  />
                </Col>
                <Col sm={2}>
                  <CustomInput
                    type="checkbox"
                    name="disease16"
                    id="disease16"
                    label="過敏"
                    value="過敏"
                    checked={disease[15]}
                    onChange={this.onChange}
                  />
                </Col>
                <Col sm={2}>
                  <CustomInput
                    type="checkbox"
                    name="disease15"
                    id="disease15"
                    label="風濕熱"
                    value="風濕熱"
                    checked={disease[14]}
                    onChange={this.onChange}
                  />
                </Col>
                <Col sm={2}>
                  <CustomInput
                    type="checkbox"
                    name="disease10"
                    id="disease10"
                    label="消化性潰瘍"
                    value="消化性潰瘍"
                    checked={disease[9]}
                    onChange={this.onChange}
                  />
                </Col>
                <Col sm={2}>
                  <CustomInput
                    type="checkbox"
                    name="disease14"
                    id="disease14"
                    label="惡性腫瘤(癌症)"
                    value="惡性腫瘤(癌症)"
                    checked={disease[13]}
                    onChange={this.onChange}
                  />
                </Col>
              </FormGroup>
              <FormGroup row>
                <Label for="drug" sm={12}>
                  正在服用藥物
                </Label>
                <Col sm={3}>
                  <CustomInput
                    type="radio"
                    id="drug1"
                    name="drug"
                    label="無服用中藥物"
                    value="no"
                    checked={!(drug1 || drug2)}
                    onChange={this.onChange}
                  />
                </Col>
                <Col sm={3}>
                  <CustomInput
                    type="radio"
                    id="drug2"
                    name="drug"
                    label="有服用但不知藥名"
                    value="yes1"
                    checked={drug1}
                    onChange={this.onChange}
                  />
                </Col>
                <Col sm={3}>
                  <CustomInput
                    type="radio"
                    id="drug3"
                    name="drug"
                    label="有服用，藥名為"
                    value="yes2"
                    checked={drug2}
                    onChange={this.onChange}
                  />
                </Col>
                <Col sm={3}>
                  <Input
                    type="text"
                    name="drugName"
                    id="drugName"
                    placeholder="請填入"
                    maxLength={255}
                    value={drugName}
                    disabled={this.state.drugNameDisabled}
                    onChange={this.onChange}
                  />
                </Col>
              </FormGroup>
              <FormGroup row>
                <Label for="drugAllergy" sm={12}>
                  藥物過敏
                </Label>
                <Col sm={2}>
                  <CustomInput
                    type="checkbox"
                    name="drugAllergy1"
                    id="drugAllergy1"
                    label="Aspirin"
                    value="Aspirin"
                    checked={drugAllergy[0]}
                    onChange={this.onChange}
                  />
                </Col>
                <Col sm={2}>
                  <CustomInput
                    type="checkbox"
                    name="drugAllergy2"
                    id="drugAllergy2"
                    label="Penicillin"
                    value="Penicillin"
                    checked={drugAllergy[1]}
                    onChange={this.onChange}
                  />
                </Col>
                <Col sm={2}>
                  <CustomInput
                    type="checkbox"
                    name="drugAllergy4"
                    id="drugAllergy4"
                    label="Pyrine"
                    value="Pyrine"
                    checked={drugAllergy[3]}
                    onChange={this.onChange}
                  />
                </Col>
                <Col sm={2}>
                  <CustomInput
                    type="checkbox"
                    name="drugAllergy5"
                    id="drugAllergy5"
                    label="NSAID"
                    value="NSAID"
                    checked={drugAllergy[4]}
                    onChange={this.onChange}
                  />
                </Col>
                <Col sm={2}>
                  <CustomInput
                    type="checkbox"
                    name="drugAllergy6"
                    id="drugAllergy6"
                    label="磺胺"
                    value="磺胺"
                    checked={drugAllergy[5]}
                    onChange={this.onChange}
                  />
                </Col>
                <Col sm={2}>
                  <CustomInput
                    type="checkbox"
                    name="drugAllergy7"
                    id="drugAllergy7"
                    label="消炎藥"
                    value="消炎藥"
                    checked={drugAllergy[6]}
                    onChange={this.onChange}
                  />
                </Col>
                <Col sm={5}>
                  <CustomInput
                    type="checkbox"
                    name="drugAllergy8"
                    id="drugAllergy8"
                    label="骨質疏鬆藥(雙磷酸鹽類藥物)"
                    value="骨質疏鬆藥(雙磷酸鹽類藥物)"
                    checked={drugAllergy[7]}
                    onChange={this.onChange}
                  />
                </Col>
              </FormGroup>
              <FormGroup row>
                <Label for="smoking" sm={12}>
                  吸菸
                </Label>
                <Col sm={1}>
                  <CustomInput
                    type="radio"
                    id="smoking1"
                    name="smoking"
                    label="無"
                    value="no"
                    checked={!smoking}
                    onChange={this.onChange}
                  />
                </Col>
                <Col sm={3}>
                  <CustomInput
                    type="radio"
                    id="smoking2"
                    name="smoking"
                    label="有吸菸，一天(支)"
                    value="yes"
                    checked={smoking}
                    onChange={this.onChange}
                  />
                </Col>
                <Col sm={3}>
                  <Input
                    type="text"
                    name="smokeNumberADay"
                    id="smokeNumberADay"
                    placeholder="請填入"
                    maxLength={255}
                    value={smokeNumberADay}
                    disabled={this.state.smokingDisabled}
                    onChange={this.onChange}
                  />
                </Col>
              </FormGroup>
              <FormGroup row>
                <Label for="pregnant" sm={12}>
                  懷孕
                </Label>
                <Col sm={1}>
                  <CustomInput
                    type="radio"
                    id="pregnant1"
                    name="pregnant"
                    label="無"
                    value="no"
                    checked={!pregnant}
                    onChange={this.onChange}
                  />
                </Col>
                <Col sm={1}>
                  <CustomInput
                    type="radio"
                    id="pregnant2"
                    name="pregnant"
                    label="有"
                    value="yes"
                    checked={pregnant}
                    onChange={this.onChange}
                  />
                </Col>
              </FormGroup>
              <FormGroup row>
                <Label for="glycemic" sm={12}>
                  血糖
                </Label>
                <Label for="glycemicAC" sm={1}>
                  飯前
                </Label>
                <Col sm={3}>
                  <Input
                    type="text"
                    name="glycemicAC"
                    id="glycemicAC"
                    placeholder="請填入"
                    maxLength={3}
                    defaultValue={patientEntity.questionnaire ? patientEntity.questionnaire.glycemicAC : ''}
                  />
                </Col>
                <Label for="glycemicPC" sm={1}>
                  飯後
                </Label>
                <Col sm={3}>
                  <Input
                    type="text"
                    name="glycemicPC"
                    id="glycemicPC"
                    placeholder="請填入"
                    maxLength={3}
                    defaultValue={patientEntity.questionnaire ? patientEntity.questionnaire.glycemicPC : ''}
                  />
                </Col>
              </FormGroup>
              <FormGroup row>
                <Label for="problems" sm={12}>
                  牙科治療中曾遇到的問題
                </Label>
                <Col sm={4}>
                  <CustomInput
                    type="checkbox"
                    name="problems1"
                    id="problems1"
                    label="曾經拔牙困難或血流不止"
                    value="曾經拔牙困難或血流不止"
                    checked={problems[0]}
                    onChange={this.onChange}
                  />
                </Col>
                <Col sm={4}>
                  <CustomInput
                    type="checkbox"
                    name="problems2"
                    id="problems2"
                    label="曾經治療牙齒時昏迷或暈眩"
                    value="曾經治療牙齒時昏迷或暈眩"
                    checked={problems[1]}
                    onChange={this.onChange}
                  />
                </Col>
                <Col sm={4}>
                  <CustomInput
                    type="checkbox"
                    name="problems3"
                    id="problems3"
                    label="曾經注射麻藥有不良反應"
                    value="曾經注射麻藥有不良反應"
                    checked={problems[2]}
                    onChange={this.onChange}
                  />
                </Col>
                <Col sm={4}>
                  <CustomInput
                    type="checkbox"
                    name="problems4"
                    id="problems4"
                    label="曾住院或接受手術"
                    value="曾住院或接受手術"
                    checked={problems[3]}
                    onChange={this.onChange}
                  />
                </Col>
                <Col sm={4}>
                  <CustomInput
                    type="checkbox"
                    name="problems5"
                    id="problems5"
                    label="一年內服長期藥物(含避孕藥)"
                    value="一年內服長期藥物(含避孕藥)"
                    checked={problems[4]}
                    onChange={this.onChange}
                  />
                </Col>
                <Col sm={4}>
                  <CustomInput
                    type="checkbox"
                    name="problems6"
                    id="problems6"
                    label="曾經接受放射線治療或化學治療"
                    value="曾經接受放射線治療或化學治療"
                    checked={problems[5]}
                    onChange={this.onChange}
                  />
                </Col>
              </FormGroup>
              <FormGroup>
                <Signature getSignatureURL={this.getSignatureURL} />
              </FormGroup>
              <FormGroup>
                <Col sm={12} style={{ display: 'flex', justifyContent: 'center' }}>
                  <Button type="submit" id="submit" style={{ width: '200px' }} className="btn-dentall btn btn-primary">
                    存檔
                  </Button>
                </Col>
              </FormGroup>
            </Form>
          </Col>
        </Row>
        {this.state.showDino && this.renderDino()}
      </div>
    );
  }
}

const mapStateToProps = ({ patient, tag }: IRootState) => ({
  patientEntity: patient.entity,
  tagList: tag.entities
});

const mapDispatchToProps = {
  createEntity,
  getEntity,
  updateEntity,
  reset,
  getPatient,
  updatePatient,
  getTags
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(Survey);
