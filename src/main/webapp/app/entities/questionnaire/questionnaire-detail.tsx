import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
// tslint:disable-next-line:no-unused-variable
import { Translate, ICrudGetAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './questionnaire.reducer';
import { IQuestionnaire } from 'app/shared/model/questionnaire.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IQuestionnaireDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export class QuestionnaireDetail extends React.Component<IQuestionnaireDetailProps> {
  componentDidMount() {
    this.props.getEntity(this.props.match.params.id);
  }

  render() {
    const { questionnaireEntity } = this.props;
    return (
      <Row>
        <Col md="8">
          <h2>
            <Translate contentKey="totoroApp.questionnaire.detail.title">Questionnaire</Translate> [<b>{questionnaireEntity.id}</b>]
          </h2>
          <dl className="jh-entity-details">
            <dt>
              <span id="hypertension">
                <Translate contentKey="totoroApp.questionnaire.hypertension">Hypertension</Translate>
              </span>
            </dt>
            <dd>{questionnaireEntity.hypertension}</dd>
            <dt>
              <span id="heartDiseases">
                <Translate contentKey="totoroApp.questionnaire.heartDiseases">Heart Diseases</Translate>
              </span>
            </dt>
            <dd>{questionnaireEntity.heartDiseases}</dd>
            <dt>
              <span id="kidneyDiseases">
                <Translate contentKey="totoroApp.questionnaire.kidneyDiseases">Kidney Diseases</Translate>
              </span>
            </dt>
            <dd>{questionnaireEntity.kidneyDiseases}</dd>
            <dt>
              <span id="bloodDiseases">
                <Translate contentKey="totoroApp.questionnaire.bloodDiseases">Blood Diseases</Translate>
              </span>
            </dt>
            <dd>{questionnaireEntity.bloodDiseases}</dd>
            <dt>
              <span id="liverDiseases">
                <Translate contentKey="totoroApp.questionnaire.liverDiseases">Liver Diseases</Translate>
              </span>
            </dt>
            <dd>{questionnaireEntity.liverDiseases}</dd>
            <dt>
              <span id="hepatitisType">
                <Translate contentKey="totoroApp.questionnaire.hepatitisType">Hepatitis Type</Translate>
              </span>
            </dt>
            <dd>{questionnaireEntity.hepatitisType}</dd>
            <dt>
              <span id="gastrointestinalDiseases">
                <Translate contentKey="totoroApp.questionnaire.gastrointestinalDiseases">Gastrointestinal Diseases</Translate>
              </span>
            </dt>
            <dd>{questionnaireEntity.gastrointestinalDiseases}</dd>
            <dt>
              <span id="receivingMedication">
                <Translate contentKey="totoroApp.questionnaire.receivingMedication">Receiving Medication</Translate>
              </span>
            </dt>
            <dd>{questionnaireEntity.receivingMedication}</dd>
            <dt>
              <span id="anyAllergySensitivity">
                <Translate contentKey="totoroApp.questionnaire.anyAllergySensitivity">Any Allergy Sensitivity</Translate>
              </span>
            </dt>
            <dd>{questionnaireEntity.anyAllergySensitivity}</dd>
            <dt>
              <span id="glycemicAC">
                <Translate contentKey="totoroApp.questionnaire.glycemicAC">Glycemic AC</Translate>
              </span>
            </dt>
            <dd>{questionnaireEntity.glycemicAC}</dd>
            <dt>
              <span id="glycemicPC">
                <Translate contentKey="totoroApp.questionnaire.glycemicPC">Glycemic PC</Translate>
              </span>
            </dt>
            <dd>{questionnaireEntity.glycemicPC}</dd>
            <dt>
              <span id="smokeNumberADay">
                <Translate contentKey="totoroApp.questionnaire.smokeNumberADay">Smoke Number A Day</Translate>
              </span>
            </dt>
            <dd>{questionnaireEntity.smokeNumberADay}</dd>
            <dt>
              <span id="productionYear">
                <Translate contentKey="totoroApp.questionnaire.productionYear">Production Year</Translate>
              </span>
            </dt>
            <dd>{questionnaireEntity.productionYear}</dd>
            <dt>
              <span id="productionMonth">
                <Translate contentKey="totoroApp.questionnaire.productionMonth">Production Month</Translate>
              </span>
            </dt>
            <dd>{questionnaireEntity.productionMonth}</dd>
            <dt>
              <span id="other">
                <Translate contentKey="totoroApp.questionnaire.other">Other</Translate>
              </span>
            </dt>
            <dd>{questionnaireEntity.other}</dd>
            <dt>
              <span id="difficultExtractionOrContinuousBleeding">
                <Translate contentKey="totoroApp.questionnaire.difficultExtractionOrContinuousBleeding">
                  Difficult Extraction Or Continuous Bleeding
                </Translate>
              </span>
            </dt>
            <dd>{questionnaireEntity.difficultExtractionOrContinuousBleeding ? 'true' : 'false'}</dd>
            <dt>
              <span id="nauseaOrDizziness">
                <Translate contentKey="totoroApp.questionnaire.nauseaOrDizziness">Nausea Or Dizziness</Translate>
              </span>
            </dt>
            <dd>{questionnaireEntity.nauseaOrDizziness ? 'true' : 'false'}</dd>
            <dt>
              <span id="adverseReactionsToAnestheticInjections">
                <Translate contentKey="totoroApp.questionnaire.adverseReactionsToAnestheticInjections">
                  Adverse Reactions To Anesthetic Injections
                </Translate>
              </span>
            </dt>
            <dd>{questionnaireEntity.adverseReactionsToAnestheticInjections ? 'true' : 'false'}</dd>
            <dt>
              <span id="otherInTreatment">
                <Translate contentKey="totoroApp.questionnaire.otherInTreatment">Other In Treatment</Translate>
              </span>
            </dt>
            <dd>{questionnaireEntity.otherInTreatment}</dd>
          </dl>
          <Button tag={Link} to="/entity/questionnaire" replace color="info">
            <FontAwesomeIcon icon="arrow-left" />{' '}
            <span className="d-none d-md-inline">
              <Translate contentKey="entity.action.back">Back</Translate>
            </span>
          </Button>
          &nbsp;
          <Button tag={Link} to={`/entity/questionnaire/${questionnaireEntity.id}/edit`} replace color="primary">
            <FontAwesomeIcon icon="pencil-alt" />{' '}
            <span className="d-none d-md-inline">
              <Translate contentKey="entity.action.edit">Edit</Translate>
            </span>
          </Button>
        </Col>
      </Row>
    );
  }
}

const mapStateToProps = ({ questionnaire }: IRootState) => ({
  questionnaireEntity: questionnaire.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(QuestionnaireDetail);
