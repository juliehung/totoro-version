import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
// tslint:disable-next-line:no-unused-variable
import { Translate, translate, ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { IPatient } from 'app/shared/model/patient.model';
import { getEntities as getPatients } from 'app/entities/patient/patient.reducer';
import { ITreatmentProcedure } from 'app/shared/model/treatment-procedure.model';
import { getEntities as getTreatmentProcedures } from 'app/entities/treatment-procedure/treatment-procedure.reducer';
import { IDisposal } from 'app/shared/model/disposal.model';
import { getEntities as getDisposals } from 'app/entities/disposal/disposal.reducer';
import { getEntity, updateEntity, createEntity, reset } from './todo.reducer';
import { ITodo } from 'app/shared/model/todo.model';
// tslint:disable-next-line:no-unused-variable
import { convertDateTimeFromServer } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface ITodoUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export interface ITodoUpdateState {
  isNew: boolean;
  idstreatmentProcedure: any[];
  patientId: string;
  disposalId: string;
}

export class TodoUpdate extends React.Component<ITodoUpdateProps, ITodoUpdateState> {
  constructor(props) {
    super(props);
    this.state = {
      idstreatmentProcedure: [],
      patientId: '0',
      disposalId: '0',
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

    this.props.getPatients();
    this.props.getTreatmentProcedures();
    this.props.getDisposals();
  }

  saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const { todoEntity } = this.props;
      const entity = {
        ...todoEntity,
        ...values,
        treatmentProcedures: mapIdList(values.treatmentProcedures)
      };

      if (this.state.isNew) {
        this.props.createEntity(entity);
      } else {
        this.props.updateEntity(entity);
      }
    }
  };

  handleClose = () => {
    this.props.history.push('/entity/todo');
  };

  render() {
    const { todoEntity, patients, treatmentProcedures, disposals, loading, updating } = this.props;
    const { isNew } = this.state;

    return (
      <div>
        <Row className="justify-content-center">
          <Col md="8">
            <h2 id="totoroApp.todo.home.createOrEditLabel">
              <Translate contentKey="totoroApp.todo.home.createOrEditLabel">Create or edit a Todo</Translate>
            </h2>
          </Col>
        </Row>
        <Row className="justify-content-center">
          <Col md="8">
            {loading ? (
              <p>Loading...</p>
            ) : (
              <AvForm model={isNew ? {} : todoEntity} onSubmit={this.saveEntity}>
                {!isNew ? (
                  <AvGroup>
                    <Label for="id">
                      <Translate contentKey="global.field.id">ID</Translate>
                    </Label>
                    <AvInput id="todo-id" type="text" className="form-control" name="id" required readOnly />
                  </AvGroup>
                ) : null}
                <AvGroup>
                  <Label id="statusLabel">
                    <Translate contentKey="totoroApp.todo.status">Status</Translate>
                  </Label>
                  <AvInput
                    id="todo-status"
                    type="select"
                    className="form-control"
                    name="status"
                    value={(!isNew && todoEntity.status) || 'TEMPORARY'}
                  >
                    <option value="TEMPORARY">
                      <Translate contentKey="totoroApp.TodoStatus.TEMPORARY" />
                    </option>
                    <option value="PENDING">
                      <Translate contentKey="totoroApp.TodoStatus.PENDING" />
                    </option>
                    <option value="FINISHED">
                      <Translate contentKey="totoroApp.TodoStatus.FINISHED" />
                    </option>
                  </AvInput>
                </AvGroup>
                <AvGroup>
                  <Label id="expectedDateLabel" for="expectedDate">
                    <Translate contentKey="totoroApp.todo.expectedDate">Expected Date</Translate>
                  </Label>
                  <AvField id="todo-expectedDate" type="date" className="form-control" name="expectedDate" />
                </AvGroup>
                <AvGroup>
                  <Label id="requiredTreatmentTimeLabel" for="requiredTreatmentTime">
                    <Translate contentKey="totoroApp.todo.requiredTreatmentTime">Required Treatment Time</Translate>
                  </Label>
                  <AvField id="todo-requiredTreatmentTime" type="string" className="form-control" name="requiredTreatmentTime" />
                </AvGroup>
                <AvGroup>
                  <Label id="noteLabel" for="note">
                    <Translate contentKey="totoroApp.todo.note">Note</Translate>
                  </Label>
                  <AvField
                    id="todo-note"
                    type="text"
                    name="note"
                    validate={{
                      maxLength: { value: 5100, errorMessage: translate('entity.validation.maxlength', { max: 5100 }) }
                    }}
                  />
                </AvGroup>
                <AvGroup>
                  <Label for="patient.id">
                    <Translate contentKey="totoroApp.todo.patient">Patient</Translate>
                  </Label>
                  <AvInput id="todo-patient" type="select" className="form-control" name="patient.id">
                    <option value="" key="0" />
                    {patients
                      ? patients.map(otherEntity => (
                          <option value={otherEntity.id} key={otherEntity.id}>
                            {otherEntity.id}
                          </option>
                        ))
                      : null}
                  </AvInput>
                </AvGroup>
                <AvGroup>
                  <Label for="treatmentProcedures">
                    <Translate contentKey="totoroApp.todo.treatmentProcedure">Treatment Procedure</Translate>
                  </Label>
                  <AvInput
                    id="todo-treatmentProcedure"
                    type="select"
                    multiple
                    className="form-control"
                    name="treatmentProcedures"
                    value={todoEntity.treatmentProcedures && todoEntity.treatmentProcedures.map(e => e.id)}
                  >
                    <option value="" key="0" />
                    {treatmentProcedures
                      ? treatmentProcedures.map(otherEntity => (
                          <option value={otherEntity.id} key={otherEntity.id}>
                            {otherEntity.id}
                          </option>
                        ))
                      : null}
                  </AvInput>
                </AvGroup>
                <Button tag={Link} id="cancel-save" to="/entity/todo" replace color="info">
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
  patients: storeState.patient.entities,
  treatmentProcedures: storeState.treatmentProcedure.entities,
  disposals: storeState.disposal.entities,
  todoEntity: storeState.todo.entity,
  loading: storeState.todo.loading,
  updating: storeState.todo.updating,
  updateSuccess: storeState.todo.updateSuccess
});

const mapDispatchToProps = {
  getPatients,
  getTreatmentProcedures,
  getDisposals,
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
)(TodoUpdate);
