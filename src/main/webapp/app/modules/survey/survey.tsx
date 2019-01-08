import { createEntity } from 'app/entities/questionnaire/questionnaire.reducer';

import React from 'react';
import { connect } from 'react-redux';
import { RouteComponentProps } from 'react-router-dom';
import { Alert, Col, Row } from 'reactstrap';
import * as SurveyReact from 'survey-react';
import * as widgets from 'surveyjs-widgets';
import '../../../../../../node_modules/survey-react/survey.css';
import './survey.css';
import questions from './survey.json';

export interface ISurveyProps extends StateProps, DispatchProps, RouteComponentProps<{}> {}

export class Survey extends React.Component<ISurveyProps> {
  componentDidMount() {
    widgets.signaturepad(SurveyReact);
  }

  onComplete = result => {
    // TODO: handle api with redux
  };

  reset = surveyModel => {
    const resetSurvey = () => {
      surveyModel.clear(true, true);
      surveyModel.render();
    };
    return resetSurvey;
  };

  render() {
    SurveyReact.Survey.cssType = 'bootstrap';
    const surveyModel = new SurveyReact.ReactSurveyModel(questions);

    return (
      <div>
        <Row>
          <Col>
            <button id="resetButton" className="btn-dentall btn btn-primary float-right" onClick={this.reset(surveyModel)}>
              重新開始
            </button>
          </Col>
        </Row>
        <Row>
          <Col>
            <SurveyReact.Survey model={surveyModel} onComplete={this.onComplete} />
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
