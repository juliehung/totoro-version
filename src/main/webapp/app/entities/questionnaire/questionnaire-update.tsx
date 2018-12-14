import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
// tslint:disable-next-line:no-unused-variable
import { Translate, translate, ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { getEntity, updateEntity, createEntity, reset } from './questionnaire.reducer';
import { IQuestionnaire } from 'app/shared/model/questionnaire.model';
// tslint:disable-next-line:no-unused-variable
import { convertDateTimeFromServer } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IQuestionnaireUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export interface IQuestionnaireUpdateState {
  isNew: boolean;
}

export class QuestionnaireUpdate extends React.Component<IQuestionnaireUpdateProps, IQuestionnaireUpdateState> {
  constructor(props) {
    super(props);
    this.state = {
      isNew: !this.props.match.params || !this.props.match.params.id
    };
  }

  componentDidMount() {
    if (this.state.isNew) {
      this.props.reset();
    } else {
      this.props.getEntity(this.props.match.params.id);
    }
  }

  saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const { questionnaireEntity } = this.props;
      const entity = {
        ...questionnaireEntity,
        ...values
      };

      if (this.state.isNew) {
        this.props.createEntity(entity);
      } else {
        this.props.updateEntity(entity);
      }
      this.handleClose();
    }
  };

  handleClose = () => {
    this.props.history.push('/entity/questionnaire');
  };

  render() {
    const { questionnaireEntity, loading, updating } = this.props;
    const { isNew } = this.state;

    return (
      <div>
        <Row className="justify-content-center">
          <Col md="8">
            <h2 id="totoroApp.questionnaire.home.createOrEditLabel">
              <Translate contentKey="totoroApp.questionnaire.home.createOrEditLabel">Create or edit a Questionnaire</Translate>
            </h2>
          </Col>
        </Row>
        <Row className="justify-content-center">
          <Col md="8">
            {loading ? (
              <p>Loading...</p>
            ) : (
              <AvForm model={isNew ? {} : questionnaireEntity} onSubmit={this.saveEntity}>
                {!isNew ? (
                  <AvGroup>
                    <Label for="id">
                      <Translate contentKey="global.field.id">ID</Translate>
                    </Label>
                    <AvInput id="questionnaire-id" type="text" className="form-control" name="id" required readOnly />
                  </AvGroup>
                ) : null}
                <AvGroup>
                  <Label id="hypertensionLabel" for="hypertension">
                    <Translate contentKey="totoroApp.questionnaire.hypertension">Hypertension</Translate>
                  </Label>
                  <AvField id="questionnaire-hypertension" type="text" name="hypertension" />
                </AvGroup>
                <AvGroup>
                  <Label id="heartDiseasesLabel" for="heartDiseases">
                    <Translate contentKey="totoroApp.questionnaire.heartDiseases">Heart Diseases</Translate>
                  </Label>
                  <AvField id="questionnaire-heartDiseases" type="text" name="heartDiseases" />
                </AvGroup>
                <AvGroup>
                  <Label id="kidneyDiseasesLabel" for="kidneyDiseases">
                    <Translate contentKey="totoroApp.questionnaire.kidneyDiseases">Kidney Diseases</Translate>
                  </Label>
                  <AvField id="questionnaire-kidneyDiseases" type="text" name="kidneyDiseases" />
                </AvGroup>
                <AvGroup>
                  <Label id="bloodDiseasesLabel" for="bloodDiseases">
                    <Translate contentKey="totoroApp.questionnaire.bloodDiseases">Blood Diseases</Translate>
                  </Label>
                  <AvField id="questionnaire-bloodDiseases" type="text" name="bloodDiseases" />
                </AvGroup>
                <AvGroup>
                  <Label id="liverDiseasesLabel" for="liverDiseases">
                    <Translate contentKey="totoroApp.questionnaire.liverDiseases">Liver Diseases</Translate>
                  </Label>
                  <AvField id="questionnaire-liverDiseases" type="text" name="liverDiseases" />
                </AvGroup>
                <AvGroup>
                  <Label id="hepatitisTypeLabel">
                    <Translate contentKey="totoroApp.questionnaire.hepatitisType">Hepatitis Type</Translate>
                  </Label>
                  <AvInput
                    id="questionnaire-hepatitisType"
                    type="select"
                    className="form-control"
                    name="hepatitisType"
                    value={(!isNew && questionnaireEntity.hepatitisType) || 'A'}
                  >
                    <option value="A">
                      <Translate contentKey="totoroApp.Hepatitis.A" />
                    </option>
                    <option value="B">
                      <Translate contentKey="totoroApp.Hepatitis.B" />
                    </option>
                    <option value="C">
                      <Translate contentKey="totoroApp.Hepatitis.C" />
                    </option>
                    <option value="D">
                      <Translate contentKey="totoroApp.Hepatitis.D" />
                    </option>
                    <option value="E">
                      <Translate contentKey="totoroApp.Hepatitis.E" />
                    </option>
                  </AvInput>
                </AvGroup>
                <AvGroup>
                  <Label id="gastrointestinalDiseasesLabel" for="gastrointestinalDiseases">
                    <Translate contentKey="totoroApp.questionnaire.gastrointestinalDiseases">Gastrointestinal Diseases</Translate>
                  </Label>
                  <AvField id="questionnaire-gastrointestinalDiseases" type="text" name="gastrointestinalDiseases" />
                </AvGroup>
                <AvGroup>
                  <Label id="receivingMedicationLabel" for="receivingMedication">
                    <Translate contentKey="totoroApp.questionnaire.receivingMedication">Receiving Medication</Translate>
                  </Label>
                  <AvField id="questionnaire-receivingMedication" type="text" name="receivingMedication" />
                </AvGroup>
                <AvGroup>
                  <Label id="anyAllergySensitivityLabel" for="anyAllergySensitivity">
                    <Translate contentKey="totoroApp.questionnaire.anyAllergySensitivity">Any Allergy Sensitivity</Translate>
                  </Label>
                  <AvField id="questionnaire-anyAllergySensitivity" type="text" name="anyAllergySensitivity" />
                </AvGroup>
                <AvGroup>
                  <Label id="glycemicACLabel" for="glycemicAC">
                    <Translate contentKey="totoroApp.questionnaire.glycemicAC">Glycemic AC</Translate>
                  </Label>
                  <AvField id="questionnaire-glycemicAC" type="string" className="form-control" name="glycemicAC" />
                </AvGroup>
                <AvGroup>
                  <Label id="glycemicPCLabel" for="glycemicPC">
                    <Translate contentKey="totoroApp.questionnaire.glycemicPC">Glycemic PC</Translate>
                  </Label>
                  <AvField id="questionnaire-glycemicPC" type="string" className="form-control" name="glycemicPC" />
                </AvGroup>
                <AvGroup>
                  <Label id="smokeNumberADayLabel" for="smokeNumberADay">
                    <Translate contentKey="totoroApp.questionnaire.smokeNumberADay">Smoke Number A Day</Translate>
                  </Label>
                  <AvField id="questionnaire-smokeNumberADay" type="string" className="form-control" name="smokeNumberADay" />
                </AvGroup>
                <AvGroup>
                  <Label id="productionYearLabel" for="productionYear">
                    <Translate contentKey="totoroApp.questionnaire.productionYear">Production Year</Translate>
                  </Label>
                  <AvField id="questionnaire-productionYear" type="string" className="form-control" name="productionYear" />
                </AvGroup>
                <AvGroup>
                  <Label id="productionMonthLabel">
                    <Translate contentKey="totoroApp.questionnaire.productionMonth">Production Month</Translate>
                  </Label>
                  <AvInput
                    id="questionnaire-productionMonth"
                    type="select"
                    className="form-control"
                    name="productionMonth"
                    value={(!isNew && questionnaireEntity.productionMonth) || 'JAN'}
                  >
                    <option value="JAN">
                      <Translate contentKey="totoroApp.Month.JAN" />
                    </option>
                    <option value="FEB">
                      <Translate contentKey="totoroApp.Month.FEB" />
                    </option>
                    <option value="MAR">
                      <Translate contentKey="totoroApp.Month.MAR" />
                    </option>
                    <option value="APR">
                      <Translate contentKey="totoroApp.Month.APR" />
                    </option>
                    <option value="MAY">
                      <Translate contentKey="totoroApp.Month.MAY" />
                    </option>
                    <option value="JUN">
                      <Translate contentKey="totoroApp.Month.JUN" />
                    </option>
                    <option value="JUL">
                      <Translate contentKey="totoroApp.Month.JUL" />
                    </option>
                    <option value="AUG">
                      <Translate contentKey="totoroApp.Month.AUG" />
                    </option>
                    <option value="SEP">
                      <Translate contentKey="totoroApp.Month.SEP" />
                    </option>
                    <option value="OCT">
                      <Translate contentKey="totoroApp.Month.OCT" />
                    </option>
                    <option value="NOV">
                      <Translate contentKey="totoroApp.Month.NOV" />
                    </option>
                    <option value="DEC">
                      <Translate contentKey="totoroApp.Month.DEC" />
                    </option>
                  </AvInput>
                </AvGroup>
                <AvGroup>
                  <Label id="otherLabel" for="other">
                    <Translate contentKey="totoroApp.questionnaire.other">Other</Translate>
                  </Label>
                  <AvField id="questionnaire-other" type="text" name="other" />
                </AvGroup>
                <AvGroup>
                  <Label id="difficultExtractionOrContinuousBleedingLabel" check>
                    <AvInput
                      id="questionnaire-difficultExtractionOrContinuousBleeding"
                      type="checkbox"
                      className="form-control"
                      name="difficultExtractionOrContinuousBleeding"
                    />
                    <Translate contentKey="totoroApp.questionnaire.difficultExtractionOrContinuousBleeding">
                      Difficult Extraction Or Continuous Bleeding
                    </Translate>
                  </Label>
                </AvGroup>
                <AvGroup>
                  <Label id="nauseaOrDizzinessLabel" check>
                    <AvInput id="questionnaire-nauseaOrDizziness" type="checkbox" className="form-control" name="nauseaOrDizziness" />
                    <Translate contentKey="totoroApp.questionnaire.nauseaOrDizziness">Nausea Or Dizziness</Translate>
                  </Label>
                </AvGroup>
                <AvGroup>
                  <Label id="adverseReactionsToAnestheticInjectionsLabel" check>
                    <AvInput
                      id="questionnaire-adverseReactionsToAnestheticInjections"
                      type="checkbox"
                      className="form-control"
                      name="adverseReactionsToAnestheticInjections"
                    />
                    <Translate contentKey="totoroApp.questionnaire.adverseReactionsToAnestheticInjections">
                      Adverse Reactions To Anesthetic Injections
                    </Translate>
                  </Label>
                </AvGroup>
                <AvGroup>
                  <Label id="otherInTreatmentLabel" for="otherInTreatment">
                    <Translate contentKey="totoroApp.questionnaire.otherInTreatment">Other In Treatment</Translate>
                  </Label>
                  <AvField id="questionnaire-otherInTreatment" type="text" name="otherInTreatment" />
                </AvGroup>
                <Button tag={Link} id="cancel-save" to="/entity/questionnaire" replace color="info">
                  <FontAwesomeIcon icon="arrow-left" />
                  &nbsp;
                  <span className="d-none d-md-inline">
                    <Translate contentKey="entity.action.back">Back</Translate>
                  </span>
                </Button>
                &nbsp;
                <Button color="primary" id="save-entity" type="submit" disabled={updating}>
                  <FontAwesomeIcon icon="save" />
                  &nbsp;
                  <Translate contentKey="entity.action.save">Save</Translate>
                </Button>
              </AvForm>
            )}
          </Col>
        </Row>
      </div>
    );
  }
}

const mapStateToProps = (storeState: IRootState) => ({
  questionnaireEntity: storeState.questionnaire.entity,
  loading: storeState.questionnaire.loading,
  updating: storeState.questionnaire.updating
});

const mapDispatchToProps = {
  getEntity,
  updateEntity,
  createEntity,
  reset
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(QuestionnaireUpdate);
