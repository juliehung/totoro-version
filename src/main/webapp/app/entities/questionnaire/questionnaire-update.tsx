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

  componentWillUpdate(nextProps, nextState) {
    if (nextProps.updateSuccess !== this.props.updateSuccess && nextProps.updateSuccess) {
      this.handleClose();
    }
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
                  <Label id="drugLabel" check>
                    <AvInput id="questionnaire-drug" type="checkbox" className="form-control" name="drug" />
                    <Translate contentKey="totoroApp.questionnaire.drug">Drug</Translate>
                  </Label>
                </AvGroup>
                <AvGroup>
                  <Label id="drugNameLabel" for="drugName">
                    <Translate contentKey="totoroApp.questionnaire.drugName">Drug Name</Translate>
                  </Label>
                  <AvField id="questionnaire-drugName" type="text" name="drugName" />
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
  updating: storeState.questionnaire.updating,
  updateSuccess: storeState.questionnaire.updateSuccess
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
