import { createEntity, getEntity, reset, updateEntity } from 'app/entities/questionnaire/questionnaire.reducer';
import { getEntity as getPatient } from 'app/entities/patient/patient.reducer';
import { IRootState } from 'app/shared/reducers';

import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Alert, Button, Col, CustomInput, Form, FormGroup, FormText, Input, Label, Row } from 'reactstrap';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faChevronLeft } from '@fortawesome/free-solid-svg-icons';
import Lottie from 'react-lottie';
import './survey.css';

export interface ISurveyProps extends StateProps, DispatchProps, RouteComponentProps<{}> {}

export interface ISurveyState {
  drugtextDisabled: boolean;
  problems4textDisabled: boolean;
  showDino: boolean;
}

export class Survey extends React.Component<ISurveyProps, ISurveyState> {
  state: ISurveyState = {
    drugtextDisabled: true,
    problems4textDisabled: true,
    showDino: false
  };

  componentDidMount() {
    const params = new URLSearchParams(this.props.location.search);
    const pid = params.get('pid');
    if (pid) {
      this.props.getPatient(pid);
    }
  }

  onFormSubmit = event => {
    event.preventDefault();
    const data = new FormData(event.target);
    const blood = data.get('blood');
    const phoneNumber = data.get('phoneNumber');
    const addr = data.get('addr');
    const line = data.get('line');
    const fb = data.get('fb');
    const contactType = data.get('contactType');
    const jobType = data.get('jobType');
    const maritalStatus = data.get('maritalStatus');
    const introducor = data.get('introducor');
    const emergencyContact = data.get('emergencyContact');
    const contactPhoneNumber = data.get('contactPhoneNumber');
    const contactRelationship = data.get('contactRelationship');
    const disease = [];
    for (let i = 1; i <= 15; i++) {
      const d = data.get('disease' + i);
      if (d) {
        disease.push(d);
      }
    }
    const drug = data.get('drug');
    const drugtext = data.get('drugtext');
    const drugAllergy = [];
    for (let i = 1; i <= 8; i++) {
      const d = data.get('drugAllergy' + i);
      if (d) {
        drugAllergy.push(d);
      }
    }
    const smoking = data.get('smoking');
    const pregnant = data.get('pregnant');
    const ac = data.get('ac');
    const pc = data.get('pc');
    const problems = [];
    for (let i = 1; i <= 4; i++) {
      const d = data.get('problems' + i);
      if (d) {
        problems.push(d);
      }
    }
    const problems4text = data.get('problems4text');
    const json = {
      blood,
      phoneNumber,
      addr,
      line,
      fb,
      contactType,
      jobType,
      emergencyContact,
      contactPhoneNumber,
      contactRelationship,
      maritalStatus,
      introducor,
      disease,
      drug,
      drugtext,
      drugAllergy,
      smoking,
      pregnant,
      ac,
      pc,
      problems,
      problems4text
    };

    this.setState({ showDino: true });
    // TODO: api and loading progress and success/error page
  };

  onChange = event => {
    switch (event.target.name) {
      case 'drug':
        if (event.target.value === 'yes2') {
          this.setState({ drugtextDisabled: false });
        } else {
          this.setState({ drugtextDisabled: true });
        }
        break;
      case 'problems4':
        this.setState({ problems4textDisabled: !this.state.problems4textDisabled });
        break;
      default:
        break;
    }
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

  render() {
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

    // TODO: replace with real last updated time
    const today = new Date();
    const date = today.getFullYear() + '-' + (today.getMonth() + 1) + '-' + today.getDate();
    const time = today.getHours() + ':' + today.getMinutes() + ':' + today.getSeconds();
    const dateTime = date + ' ' + time;

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
              <div className="time-rect">最後更新: {dateTime}</div>
            </div>
            <h2>{patientEntity.name}</h2>
            <div>
              <span>病歷編號: </span>
              <span>{patientEntity.medicalId}</span>
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
                <Col sm={3}>
                  <Label>性別：</Label>
                  <Label>{patientEntity.gender}</Label>
                </Col>
                <Col sm={3}>
                  <Label>生日：</Label>
                  <Label>{patientEntity.birth}</Label>
                </Col>
              </FormGroup>
              <FormGroup row>
                <Label for="blood" sm={1}>
                  血型
                </Label>
                <Col sm={2}>
                  <Input type="select" name="blood" id="blood">
                    <option>O</option>
                    <option>A</option>
                    <option>B</option>
                    <option>AB</option>
                  </Input>
                </Col>
                <Label for="phoneNumber" sm={1}>
                  電話
                </Label>
                <Col sm={8}>
                  <Input type="text" name="phoneNumber" id="phoneNumber" placeholder="請填入手機或市話 (必填)" maxLength={15} />
                </Col>
              </FormGroup>
              <FormGroup row>
                <Label for="addr" sm={1}>
                  地址
                </Label>
                <Col sm={11}>
                  <Input type="text" name="addr" id="addr" placeholder="請填入聯絡地址 (必填)" maxLength={255} />
                </Col>
              </FormGroup>
              <FormGroup row>
                <Label for="line" sm={1}>
                  LINE
                </Label>
                <Col sm={3}>
                  <Input type="text" name="line" id="line" placeholder="請填入LINE ID" maxLength={15} />
                </Col>
                <Label for="fb" sm={1}>
                  臉書
                </Label>
                <Col sm={3}>
                  <Input type="text" name="fb" id="fb" placeholder="請填入臉書帳號" maxLength={15} />
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
                  <Input type="text" name="introducor" id="introducor" placeholder="請填入介紹人" maxLength={15} />
                </Col>
              </FormGroup>
              <FormGroup>
                <SlashLine />
                <h4>緊急聯絡</h4>
              </FormGroup>
              <FormGroup row>
                <Label for="emergencyContact" sm={2}>
                  聯絡人
                </Label>
                <Col sm={4}>
                  <Input type="text" name="emergencyContact" id="emergencyContact" placeholder="請填入姓名 (必填)" maxLength={15} />
                </Col>
                <Label for="contactPhoneNumber" sm={2}>
                  聯絡人電話
                </Label>
                <Col sm={4}>
                  <Input
                    type="text"
                    name="contactPhoneNumber"
                    id="contactPhoneNumber"
                    placeholder="請填入手機或市話 (必填)"
                    maxLength={15}
                  />
                </Col>
              </FormGroup>
              <FormGroup row>
                <Label for="contactRelationship" sm={2}>
                  聯絡人關係
                </Label>
                <Col sm={2}>
                  <Input type="select" name="contactRelationship" id="contactRelationship" defaultValue="select">
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
                  <CustomInput type="checkbox" name="disease1" id="disease1" label="AIDS" value="AIDS" />
                </Col>
                <Col sm={2}>
                  <CustomInput type="checkbox" name="disease2" id="disease2" label="高血壓" value="高血壓" />
                </Col>
                <Col sm={2}>
                  <CustomInput type="checkbox" name="disease3" id="disease3" label="氣喘" value="氣喘" />
                </Col>
                <Col sm={2}>
                  <CustomInput type="checkbox" name="disease4" id="disease4" label="心臟雜音" value="心臟雜音" />
                </Col>
                <Col sm={2}>
                  <CustomInput type="checkbox" name="disease5" id="disease5" label="糖尿病" value="糖尿病" />
                </Col>
                <Col sm={2}>
                  <CustomInput type="checkbox" name="disease6" id="disease6" label="肺炎" value="肺炎" />
                </Col>
                <Col sm={2}>
                  <CustomInput type="checkbox" name="disease7" id="disease7" label="肝病" value="肝病" />
                </Col>
                <Col sm={2}>
                  <CustomInput type="checkbox" name="disease8" id="disease8" label="肺結核" value="肺結核" />
                </Col>
                <Col sm={2}>
                  <CustomInput type="checkbox" name="disease9" id="disease9" label="過敏" value="過敏" />
                </Col>
                <Col sm={2}>
                  <CustomInput type="checkbox" name="disease10" id="disease10" label="癲癇" value="癲癇" />
                </Col>
                <Col sm={2}>
                  <CustomInput type="checkbox" name="disease11" id="disease11" label="暈眩" value="暈眩" />
                </Col>
                <Col sm={2}>
                  <CustomInput type="checkbox" name="disease12" id="disease12" label="中風" value="中風" />
                </Col>
                <Col sm={2}>
                  <CustomInput type="checkbox" name="disease13" id="disease13" label="風溼熱" value="風溼熱" />
                </Col>
                <Col sm={2}>
                  <CustomInput type="checkbox" name="disease14" id="disease14" label="消化性潰瘍" value="消化性潰瘍" />
                </Col>
                <Col sm={3}>
                  <CustomInput type="checkbox" name="disease15" id="disease15" label="惡性腫瘤(癌症)" value="惡性腫瘤(癌症)" />
                </Col>
              </FormGroup>
              <FormGroup row>
                <Label for="drug" sm={12}>
                  正在服用藥物
                </Label>
                <Col sm={3}>
                  <CustomInput type="radio" id="drug1" name="drug" label="無服用中藥物" value="no" onChange={this.onChange} />
                </Col>
                <Col sm={4}>
                  <CustomInput type="radio" id="drug2" name="drug" label="有服用，但不知藥名" value="yes1" onChange={this.onChange} />
                </Col>
                <Col sm={3} />
                <Col sm={3}>
                  <CustomInput type="radio" id="drug3" name="drug" label="有服用，藥名為" value="yes2" onChange={this.onChange} />
                </Col>
                <Col sm={12}>
                  <Input
                    type="text"
                    name="drugtext"
                    id="drugtext"
                    placeholder="請填入藥物名稱"
                    maxLength={255}
                    disabled={this.state.drugtextDisabled}
                  />
                </Col>
              </FormGroup>
              <FormGroup row>
                <Label for="drugAllergy" sm={12}>
                  藥物過敏
                </Label>
                <Col sm={2}>
                  <CustomInput type="checkbox" name="drugAllergy1" id="drugAllergy1" label="Aspirin" value="Aspirin" />
                </Col>
                <Col sm={2}>
                  <CustomInput type="checkbox" name="drugAllergy2" id="drugAllergy2" label="Penicillin" value="Penicillin" />
                </Col>
                <Col sm={2}>
                  <CustomInput type="checkbox" name="drugAllergy3" id="drugAllergy3" label="青黴素" value="青黴素" />
                </Col>
                <Col sm={2}>
                  <CustomInput type="checkbox" name="drugAllergy4" id="drugAllergy4" label="Pyrine" value="Pyrine" />
                </Col>
                <Col sm={2}>
                  <CustomInput type="checkbox" name="drugAllergy5" id="drugAllergy5" label="NSAID" value="NSAID" />
                </Col>
                <Col sm={2}>
                  <CustomInput type="checkbox" name="drugAllergy6" id="drugAllergy6" label="磺胺" value="磺胺" />
                </Col>
                <Col sm={2}>
                  <CustomInput type="checkbox" name="drugAllergy7" id="drugAllergy7" label="消炎藥" value="消炎藥" />
                </Col>
                <Col sm={5}>
                  <CustomInput
                    type="checkbox"
                    name="drugAllergy8"
                    id="drugAllergy8"
                    label="骨質疏鬆藥(雙磷酸鹽類藥物)"
                    value="骨質疏鬆藥(雙磷酸鹽類藥物)"
                  />
                </Col>
              </FormGroup>
              <FormGroup row>
                <Label for="smoking" sm={12}>
                  吸菸
                </Label>
                <Col sm={1}>
                  <CustomInput type="radio" id="smoking1" name="smoking" label="無" value="no" />
                </Col>
                <Col sm={1}>
                  <CustomInput type="radio" id="smoking2" name="smoking" label="有" value="yes" />
                </Col>
              </FormGroup>
              <FormGroup row>
                <Label for="pregnant" sm={12}>
                  懷孕
                </Label>
                <Col sm={1}>
                  <CustomInput type="radio" id="pregnant1" name="pregnant" label="無" value="no" />
                </Col>
                <Col sm={1}>
                  <CustomInput type="radio" id="pregnant2" name="pregnant" label="有" value="yes" />
                </Col>
              </FormGroup>
              <FormGroup row>
                <Label for="glycemic" sm={12}>
                  血糖
                </Label>
                <Label for="ac" sm={1}>
                  飯前
                </Label>
                <Col sm={3}>
                  <Input type="text" name="ac" id="ac" placeholder="請填入" maxLength={3} />
                </Col>
                <Label for="pc" sm={1}>
                  飯後
                </Label>
                <Col sm={3}>
                  <Input type="text" name="pc" id="pc" placeholder="請填入" maxLength={3} />
                </Col>
              </FormGroup>
              <FormGroup row>
                <Label for="problems" sm={12}>
                  牙科治療中曾遇到的問題
                </Label>
                <Col sm={4}>
                  <CustomInput type="checkbox" name="problems1" id="problems1" label="拔牙困難或血流不止" value="拔牙困難或血流不止" />
                </Col>
                <Col sm={4}>
                  <CustomInput type="checkbox" name="problems2" id="problems2" label="治療牙齒時昏倒或暈眩" value="治療牙齒時昏倒或暈眩" />
                </Col>
                <Col sm={4}>
                  <CustomInput type="checkbox" name="problems3" id="problems3" label="注射麻藥有不良反應" value="注射麻藥有不良反應" />
                </Col>
                <Col sm={2}>
                  <CustomInput type="checkbox" name="problems4" id="problems4" label="其他" value="其他" onChange={this.onChange} />
                </Col>
                <Col sm={12}>
                  <Input
                    type="text"
                    name="problems4text"
                    id="problems4text"
                    placeholder="請填入"
                    maxLength={255}
                    disabled={this.state.problems4textDisabled}
                  />
                </Col>
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

const mapStateToProps = ({ patient }: IRootState) => ({
  patientEntity: patient.entity
});

const mapDispatchToProps = {
  createEntity,
  getEntity,
  updateEntity,
  reset,
  getPatient
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(Survey);
