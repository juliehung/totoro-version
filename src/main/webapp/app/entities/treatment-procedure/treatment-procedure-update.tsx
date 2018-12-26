import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
// tslint:disable-next-line:no-unused-variable
import { Translate, translate, ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { INHIProcedure } from 'app/shared/model/nhi-procedure.model';
import { getEntities as getNHiProcedures } from 'app/entities/nhi-procedure/nhi-procedure.reducer';
import { ITreatmentTask } from 'app/shared/model/treatment-task.model';
import { getEntities as getTreatmentTasks } from 'app/entities/treatment-task/treatment-task.reducer';
import { getEntity, updateEntity, createEntity, reset } from './treatment-procedure.reducer';
import { ITreatmentProcedure } from 'app/shared/model/treatment-procedure.model';
// tslint:disable-next-line:no-unused-variable
import { convertDateTimeFromServer } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface ITreatmentProcedureUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export interface ITreatmentProcedureUpdateState {
  isNew: boolean;
  nhiProcedureId: string;
  treatmentTaskId: string;
}

export class TreatmentProcedureUpdate extends React.Component<ITreatmentProcedureUpdateProps, ITreatmentProcedureUpdateState> {
  constructor(props) {
    super(props);
    this.state = {
      nhiProcedureId: '0',
      treatmentTaskId: '0',
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

    this.props.getNHiProcedures();
    this.props.getTreatmentTasks();
  }

  saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const { treatmentProcedureEntity } = this.props;
      const entity = {
        ...treatmentProcedureEntity,
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
    this.props.history.push('/entity/treatment-procedure');
  };

  render() {
    const { treatmentProcedureEntity, nHIProcedures, treatmentTasks, loading, updating } = this.props;
    const { isNew } = this.state;

    return (
      <div>
        <Row className="justify-content-center">
          <Col md="8">
            <h2 id="totoroApp.treatmentProcedure.home.createOrEditLabel">
              <Translate contentKey="totoroApp.treatmentProcedure.home.createOrEditLabel">Create or edit a TreatmentProcedure</Translate>
            </h2>
          </Col>
        </Row>
        <Row className="justify-content-center">
          <Col md="8">
            {loading ? (
              <p>Loading...</p>
            ) : (
              <AvForm model={isNew ? {} : treatmentProcedureEntity} onSubmit={this.saveEntity}>
                {!isNew ? (
                  <AvGroup>
                    <Label for="id">
                      <Translate contentKey="global.field.id">ID</Translate>
                    </Label>
                    <AvInput id="treatment-procedure-id" type="text" className="form-control" name="id" required readOnly />
                  </AvGroup>
                ) : null}
                <AvGroup>
                  <Label id="priceLabel" for="price">
                    <Translate contentKey="totoroApp.treatmentProcedure.price">Price</Translate>
                  </Label>
                  <AvField id="treatment-procedure-price" type="string" className="form-control" name="price" />
                </AvGroup>
                <AvGroup>
                  <Label id="teethLabel" for="teeth">
                    <Translate contentKey="totoroApp.treatmentProcedure.teeth">Teeth</Translate>
                  </Label>
                  <AvField id="treatment-procedure-teeth" type="text" name="teeth" />
                </AvGroup>
                <AvGroup>
                  <Label id="surfacesLabel" for="surfaces">
                    <Translate contentKey="totoroApp.treatmentProcedure.surfaces">Surfaces</Translate>
                  </Label>
                  <AvField id="treatment-procedure-surfaces" type="text" name="surfaces" />
                </AvGroup>
                <AvGroup>
                  <Label id="nhiDeclaredLabel" check>
                    <AvInput id="treatment-procedure-nhiDeclared" type="checkbox" className="form-control" name="nhiDeclared" />
                    <Translate contentKey="totoroApp.treatmentProcedure.nhiDeclared">Nhi Declared</Translate>
                  </Label>
                </AvGroup>
                <AvGroup>
                  <Label for="nhiProcedure.id">
                    <Translate contentKey="totoroApp.treatmentProcedure.nhiProcedure">Nhi Procedure</Translate>
                  </Label>
                  <AvInput id="treatment-procedure-nhiProcedure" type="select" className="form-control" name="nhiProcedure.id">
                    <option value="" key="0" />
                    {nHIProcedures
                      ? nHIProcedures.map(otherEntity => (
                          <option value={otherEntity.id} key={otherEntity.id}>
                            {otherEntity.id}
                          </option>
                        ))
                      : null}
                  </AvInput>
                </AvGroup>
                <AvGroup>
                  <Label for="treatmentTask.id">
                    <Translate contentKey="totoroApp.treatmentProcedure.treatmentTask">Treatment Task</Translate>
                  </Label>
                  <AvInput id="treatment-procedure-treatmentTask" type="select" className="form-control" name="treatmentTask.id">
                    <option value="" key="0" />
                    {treatmentTasks
                      ? treatmentTasks.map(otherEntity => (
                          <option value={otherEntity.id} key={otherEntity.id}>
                            {otherEntity.id}
                          </option>
                        ))
                      : null}
                  </AvInput>
                </AvGroup>
                <Button tag={Link} id="cancel-save" to="/entity/treatment-procedure" replace color="info">
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
  nHIProcedures: storeState.nHIProcedure.entities,
  treatmentTasks: storeState.treatmentTask.entities,
  treatmentProcedureEntity: storeState.treatmentProcedure.entity,
  loading: storeState.treatmentProcedure.loading,
  updating: storeState.treatmentProcedure.updating,
  updateSuccess: storeState.treatmentProcedure.updateSuccess
});

const mapDispatchToProps = {
  getNHiProcedures,
  getTreatmentTasks,
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
)(TreatmentProcedureUpdate);
