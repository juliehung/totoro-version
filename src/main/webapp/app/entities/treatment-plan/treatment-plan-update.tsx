import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvForm, AvGroup, AvInput } from 'availity-reactstrap-validation';
// tslint:disable-next-line:no-unused-variable
import { Translate, translate, ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { ITreatment } from 'app/shared/model/treatment.model';
import { getEntities as getTreatments } from 'app/entities/treatment/treatment.reducer';
import { getEntity, updateEntity, createEntity, reset } from './treatment-plan.reducer';
import { ITreatmentPlan } from 'app/shared/model/treatment-plan.model';
// tslint:disable-next-line:no-unused-variable
import { convertDateTimeFromServer } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface ITreatmentPlanUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export interface ITreatmentPlanUpdateState {
  isNew: boolean;
  treatmentId: string;
}

export class TreatmentPlanUpdate extends React.Component<ITreatmentPlanUpdateProps, ITreatmentPlanUpdateState> {
  constructor(props) {
    super(props);
    this.state = {
      treatmentId: '0',
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

    this.props.getTreatments();
  }

  saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const { treatmentPlanEntity } = this.props;
      const entity = {
        ...treatmentPlanEntity,
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
    this.props.history.push('/entity/treatment-plan');
  };

  render() {
    const { treatmentPlanEntity, treatments, loading, updating } = this.props;
    const { isNew } = this.state;

    return (
      <div>
        <Row className="justify-content-center">
          <Col md="8">
            <h2 id="totoroApp.treatmentPlan.home.createOrEditLabel">
              <Translate contentKey="totoroApp.treatmentPlan.home.createOrEditLabel">Create or edit a TreatmentPlan</Translate>
            </h2>
          </Col>
        </Row>
        <Row className="justify-content-center">
          <Col md="8">
            {loading ? (
              <p>Loading...</p>
            ) : (
              <AvForm model={isNew ? {} : treatmentPlanEntity} onSubmit={this.saveEntity}>
                {!isNew ? (
                  <AvGroup>
                    <Label for="id">
                      <Translate contentKey="global.field.id">ID</Translate>
                    </Label>
                    <AvInput id="treatment-plan-id" type="text" className="form-control" name="id" required readOnly />
                  </AvGroup>
                ) : null}
                <AvGroup>
                  <Label id="activatedLabel" check>
                    <AvInput id="treatment-plan-activated" type="checkbox" className="form-control" name="activated" />
                    <Translate contentKey="totoroApp.treatmentPlan.activated">Activated</Translate>
                  </Label>
                </AvGroup>
                <AvGroup>
                  <Label for="treatment.id">
                    <Translate contentKey="totoroApp.treatmentPlan.treatment">Treatment</Translate>
                  </Label>
                  <AvInput id="treatment-plan-treatment" type="select" className="form-control" name="treatment.id">
                    <option value="" key="0" />
                    {treatments
                      ? treatments.map(otherEntity => (
                          <option value={otherEntity.id} key={otherEntity.id}>
                            {otherEntity.id}
                          </option>
                        ))
                      : null}
                  </AvInput>
                </AvGroup>
                <Button tag={Link} id="cancel-save" to="/entity/treatment-plan" replace color="info">
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
  treatments: storeState.treatment.entities,
  treatmentPlanEntity: storeState.treatmentPlan.entity,
  loading: storeState.treatmentPlan.loading,
  updating: storeState.treatmentPlan.updating,
  updateSuccess: storeState.treatmentPlan.updateSuccess
});

const mapDispatchToProps = {
  getTreatments,
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
)(TreatmentPlanUpdate);
