import { createEntity } from 'app/entities/questionnaire/questionnaire.reducer';

import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { CustomInput, Form, FormGroup, Input, Label, FormText, Button, Alert, Col, Row } from 'reactstrap';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faChevronLeft } from '@fortawesome/free-solid-svg-icons';
import './survey.css';

export interface ISurveyProps extends StateProps, DispatchProps, RouteComponentProps<{}> {}

export interface ISurveyState {
  medicalId: string;
  name: string;
}

export class Survey extends React.Component<ISurveyProps, ISurveyState> {
  state: ISurveyState = {
    medicalId: '',
    name: ''
  };

  componentDidMount() {
    const params = new URLSearchParams(this.props.location.search);
    const medicalId = params.get('medicalId');
    const name = params.get('name');
    this.setState({ medicalId, name });
  }

  render() {
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
    return (
      <div>
        <Link to={'/'}>
          <Button id="resetButton" className="btn btn-back">
            <FontAwesomeIcon icon={faChevronLeft} />
          </Button>
        </Link>
        <Row>
          <Col>
            <h2>{this.state.name}</h2>
            <div>
              <span>病歷編號: </span>
              <span>{this.state.medicalId}</span>
            </div>
            <ColoredLine />
          </Col>
        </Row>
        <Row>
          <Col>
            <Form>
              <FormGroup>
                <h4>基本資料</h4>
              </FormGroup>
              <FormGroup row>
                <Col sm={3}>
                  <Label>姓名：</Label>
                  <Label>{this.state.name}</Label>
                </Col>
                <Col sm={2}>
                  <Label>性別：</Label>
                  <Label>女</Label>
                </Col>
                <Col sm={3}>
                  <Label>生日：</Label>
                  <Label>1929/11/21</Label>
                </Col>
                <Col sm={4}>
                  <Label>身分證字號：</Label>
                  <Label>F32123232</Label>
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
                  <Input type="text" name="phoneNumber" id="phoneNumber" placeholder="請填入手機或市話 (必填)" />
                </Col>
              </FormGroup>
              <FormGroup row>
                <Label for="addr" sm={1}>
                  地址
                </Label>
                <Col sm={11}>
                  <Input type="text" name="addr" id="addr" placeholder="請填入聯絡地址 (必填)" />
                </Col>
              </FormGroup>
              <FormGroup row>
                <Label for="line" sm={1}>
                  LINE
                </Label>
                <Col sm={3}>
                  <Input type="text" name="line" id="line" placeholder="請填入LINE ID" />
                </Col>
                <Label for="fb" sm={1}>
                  臉書
                </Label>
                <Col sm={3}>
                  <Input type="text" name="fb" id="fb" placeholder="請填入臉書帳號" />
                </Col>
                <Label for="contactType" sm={1}>
                  通知
                </Label>
                <Col sm={3}>
                  <Input type="select" name="contactType" id="contactType">
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
                  <Input type="select" name="jobType" id="jobType">
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
                  <Input type="select" name="maritalStatus" id="maritalStatus">
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
                  <Input type="text" name="introducor" id="introducor" placeholder="請填入介紹人" />
                </Col>
              </FormGroup>
              <FormGroup>
                <SlashLine />
                <h4>緊急聯絡</h4>
              </FormGroup>
              <FormGroup row>
                <Label for="contact" sm={2}>
                  聯絡人
                </Label>
                <Col sm={4}>
                  <Input type="text" name="contact" id="contact" placeholder="請填入姓名 (必填)" />
                </Col>
                <Label for="contactPhoneNumber" sm={2}>
                  聯絡人電話
                </Label>
                <Col sm={4}>
                  <Input type="text" name="contactPhoneNumber" id="contactPhoneNumber" placeholder="請填入手機或市話 (必填)" />
                </Col>
              </FormGroup>
              <FormGroup row>
                <Label for="contactRelationship" sm={2}>
                  聯絡人關係
                </Label>
                <Col sm={2}>
                  <Input type="select" name="contactRelationship" id="contactRelationship">
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
                  <CustomInput type="checkbox" id="disease1" label="AIDS" />
                </Col>
                <Col sm={2}>
                  <CustomInput type="checkbox" id="disease2" label="高血壓" />
                </Col>
                <Col sm={2}>
                  <CustomInput type="checkbox" id="disease3" label="氣喘" />
                </Col>
                <Col sm={2}>
                  <CustomInput type="checkbox" id="disease4" label="心臟雜音" />
                </Col>
                <Col sm={2}>
                  <CustomInput type="checkbox" id="disease5" label="糖尿病" />
                </Col>
                <Col sm={2}>
                  <CustomInput type="checkbox" id="disease6" label="肺炎" />
                </Col>
              </FormGroup>
              <FormGroup row>
                <Col sm={2}>
                  <CustomInput type="checkbox" id="disease7" label="肝病" />
                </Col>
                <Col sm={2}>
                  <CustomInput type="checkbox" id="disease8" label="肺結核" />
                </Col>
                <Col sm={2}>
                  <CustomInput type="checkbox" id="disease14" label="過敏" />
                </Col>
                <Col sm={2}>
                  <CustomInput type="checkbox" id="disease10" label="癲癇" />
                </Col>
                <Col sm={2}>
                  <CustomInput type="checkbox" id="disease11" label="暈眩" />
                </Col>
                <Col sm={2}>
                  <CustomInput type="checkbox" id="disease12" label="中風" />
                </Col>
              </FormGroup>
              <FormGroup row>
                <Col sm={2}>
                  <CustomInput type="checkbox" id="disease13" label="風溼熱" />
                </Col>
                <Col sm={2}>
                  <CustomInput type="checkbox" id="disease9" label="消化性潰瘍" />
                </Col>
                <Col sm={3}>
                  <CustomInput type="checkbox" id="disease15" label="惡性腫瘤 (癌症)" />
                </Col>
              </FormGroup>
              <FormGroup row>
                <Label for="drug" sm={12}>
                  正在服用藥物
                </Label>
                <Col sm={3}>
                  <CustomInput type="radio" id="drug1" name="drug" label="無服用中藥物" />
                </Col>
                <Col sm={4}>
                  <CustomInput type="radio" id="drug2" name="drug" label="有服用，但不知藥名" />
                </Col>
              </FormGroup>
              <FormGroup row>
                <Col sm={3}>
                  <CustomInput type="radio" id="drug3" name="drug" label="有服用，藥名為" />
                </Col>
                <Col sm={7}>
                  <Input type="text" name="drugtext" id="drugtext" placeholder="請填入藥物名稱" />
                </Col>
              </FormGroup>
              <FormGroup row>
                <Label for="drugAllergy" sm={12}>
                  藥物過敏
                </Label>
                <Col sm={2}>
                  <CustomInput type="checkbox" id="drugAllergy1" label="Aspirin" />
                </Col>
                <Col sm={2}>
                  <CustomInput type="checkbox" id="drugAllergy2" label="Penicillin" />
                </Col>
                <Col sm={2}>
                  <CustomInput type="checkbox" id="drugAllergy3" label="青黴素" />
                </Col>
                <Col sm={2}>
                  <CustomInput type="checkbox" id="drugAllergy4" label="Pyrine" />
                </Col>
                <Col sm={2}>
                  <CustomInput type="checkbox" id="drugAllergy5" label="NSAID" />
                </Col>
                <Col sm={2}>
                  <CustomInput type="checkbox" id="drugAllergy6" label="磺胺" />
                </Col>
              </FormGroup>
              <FormGroup row>
                <Col sm={2}>
                  <CustomInput type="checkbox" id="drugAllergy7" label="消炎藥" />
                </Col>
                <Col sm={5}>
                  <CustomInput type="checkbox" id="drugAllergy8" label="骨質疏鬆藥(雙磷酸鹽類藥物)" />
                </Col>
              </FormGroup>
              <FormGroup row>
                <Label for="smoking" sm={12}>
                  吸菸
                </Label>
                <Col sm={1}>
                  <CustomInput type="radio" id="smoking1" name="smoking" label="無" />
                </Col>
                <Col sm={1}>
                  <CustomInput type="radio" id="smoking2" name="smoking" label="有" />
                </Col>
              </FormGroup>
              <FormGroup row>
                <Label for="pregnant" sm={12}>
                  懷孕
                </Label>
                <Col sm={1}>
                  <CustomInput type="radio" id="pregnant1" name="pregnant" label="無" />
                </Col>
                <Col sm={1}>
                  <CustomInput type="radio" id="pregnant2" name="pregnant" label="有" />
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
                  <Input type="text" name="ac" id="ac" placeholder="請填入" />
                </Col>
                <Label for="pc" sm={1}>
                  飯後
                </Label>
                <Col sm={3}>
                  <Input type="text" name="pc" id="pc" placeholder="請填入" />
                </Col>
              </FormGroup>
              <FormGroup row>
                <Label for="problems" sm={12}>
                  藥物過敏
                </Label>
                <Col sm={4}>
                  <CustomInput type="checkbox" id="problems1" label="拔牙困難或血流不止" />
                </Col>
                <Col sm={4}>
                  <CustomInput type="checkbox" id="problems2" label="治療牙齒時昏倒或暈眩" />
                </Col>
                <Col sm={4}>
                  <CustomInput type="checkbox" id="problems3" label="注射麻藥有不良反應" />
                </Col>
                <Col sm={4}>
                  <CustomInput type="checkbox" id="problems4" label="其他" />
                </Col>
              </FormGroup>
              <FormGroup row>
                <Col sm={12} style={{ display: 'flex', justifyContent: 'center' }}>
                  <Button id="resetButton" style={{ width: '200px' }} className="btn-dentall btn btn-primary">
                    送出
                  </Button>
                </Col>
              </FormGroup>
            </Form>
          </Col>
        </Row>
      </div>
    );
  }
}

const mapStateToProps = storeState => ({
  account: storeState.authentication.account
});

const mapDispatchToProps = {
  createEntity
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(Survey);
