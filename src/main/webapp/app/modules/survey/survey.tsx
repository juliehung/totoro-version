import './survey.css';
import '../../../../../../node_modules/survey-react/survey.css';

import React from 'react';
import { Row, Col, Alert } from 'reactstrap';
import * as SurveyReact from 'survey-react';
import * as widgets from 'surveyjs-widgets';

export class Survey extends React.Component {
  json = {
    questions: [
      {
        type: 'radiogroup',
        name: 'hypertension',
        title: '高血壓 (Hypertension',
        isRequired: true,
        defaultValue: '否',
        choices: ['否', '是']
      },
      {
        type: 'checkbox',
        name: 'car',
        title: '牙科治療時曾經發生以下問題?',
        isRequired: true,
        colCount: 1,
        choices: ['曾經拔牙困難或流血不止', '曾經治療牙齒時昏倒或暈眩', '曾經注射麻藥有不良反應', '其他', '以上皆無']
      }
    ]
  };

  componentDidMount() {}

  onValueChanged = result => {
    // TODO: handle api
  };

  onComplete = result => {
    // TODO: handle api
  };

  render() {
    SurveyReact.Survey.cssType = 'bootstrap';
    widgets.signaturepad(SurveyReact);
    const model = new SurveyReact.ReactSurveyModel(this.json);
    model.onUpdateQuestionCssClasses.add((survey, options) => {
      const classes = options.cssClasses;
      classes.root = 'sq-root';
      classes.title = 'sq-title';
      classes.item = 'sq-item';
    });

    return (
      <Row>
        <Col>
          <SurveyReact.Survey model={model} onComplete={this.onComplete} onValueChanged={this.onValueChanged} />
        </Col>
      </Row>
    );
  }
}

export default Survey;
