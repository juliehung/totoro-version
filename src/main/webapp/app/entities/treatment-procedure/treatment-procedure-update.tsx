import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
// tslint:disable-next-line:no-unused-variable
import { Translate, translate, ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { INhiProcedure } from 'app/shared/model/nhi-procedure.model';
import { getEntities as getNhiProcedures } from 'app/entities/nhi-procedure/nhi-procedure.reducer';
import { ITreatmentTask } from 'app/shared/model/treatment-task.model';
import { getEntities as getTreatmentTasks } from 'app/entities/treatment-task/treatment-task.reducer';
import { IProcedure } from 'app/shared/model/procedure.model';
import { getEntities as getProcedures } from 'app/entities/procedure/procedure.reducer';
import { IAppointment } from 'app/shared/model/appointment.model';
import { getEntities as getAppointments } from 'app/entities/appointment/appointment.reducer';
import { ITodo } from 'app/shared/model/todo.model';
import { getEntities as getTodos } from 'app/entities/todo/todo.reducer';
import { IDisposal } from 'app/shared/model/disposal.model';
import { getEntities as getDisposals } from 'app/entities/disposal/disposal.reducer';
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
  procedureId: string;
  appointmentId: string;
  todoId: string;
  disposalId: string;
}

export class TreatmentProcedureUpdate extends React.Component<ITreatmentProcedureUpdateProps, ITreatmentProcedureUpdateState> {
  constructor(props) {
    super(props);
    this.state = {
      nhiProcedureId: '0',
      treatmentTaskId: '0',
      procedureId: '0',
      appointmentId: '0',
      todoId: '0',
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

    this.props.getNhiProcedures();
    this.props.getTreatmentTasks();
    this.props.getProcedures();
    this.props.getAppointments();
    this.props.getTodos();
    this.props.getDisposals();
  }

  saveEntity = (event, errors, values) => {
    values.completedDate = new Date(values.completedDate);

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
    const {
      treatmentProcedureEntity,
      nhiProcedures,
      treatmentTasks,
      procedures,
      appointments,
      todos,
      disposals,
      loading,
      updating
    } = this.props;
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
                  <Label id="statusLabel">
                    <Translate contentKey="totoroApp.treatmentProcedure.status">Status</Translate>
                  </Label>
                  <AvInput
                    id="treatment-procedure-status"
                    type="select"
                    className="form-control"
                    name="status"
                    value={(!isNew && treatmentProcedureEntity.status) || 'PLANNED'}
                  >
                    <option value="PLANNED">
                      <Translate contentKey="totoroApp.TreatmentProcedureStatus.PLANNED" />
                    </option>
                    <option value="IN_PROGRESS">
                      <Translate contentKey="totoroApp.TreatmentProcedureStatus.IN_PROGRESS" />
                    </option>
                    <option value="COMPLETED">
                      <Translate contentKey="totoroApp.TreatmentProcedureStatus.COMPLETED" />
                    </option>
                    <option value="CANCEL">
                      <Translate contentKey="totoroApp.TreatmentProcedureStatus.CANCEL" />
                    </option>
                    <option value="HIDE">
                      <Translate contentKey="totoroApp.TreatmentProcedureStatus.HIDE" />
                    </option>
                    <option value="TEMPORARY">
                      <Translate contentKey="totoroApp.TreatmentProcedureStatus.TEMPORARY" />
                    </option>
                  </AvInput>
                </AvGroup>
                <AvGroup>
                  <Label id="quantityLabel" for="quantity">
                    <Translate contentKey="totoroApp.treatmentProcedure.quantity">Quantity</Translate>
                  </Label>
                  <AvField id="treatment-procedure-quantity" type="string" className="form-control" name="quantity" />
                </AvGroup>
                <AvGroup>
                  <Label id="totalLabel" for="total">
                    <Translate contentKey="totoroApp.treatmentProcedure.total">Total</Translate>
                  </Label>
                  <AvField id="treatment-procedure-total" type="string" className="form-control" name="total" />
                </AvGroup>
                <AvGroup>
                  <Label id="noteLabel" for="note">
                    <Translate contentKey="totoroApp.treatmentProcedure.note">Note</Translate>
                  </Label>
                  <AvField id="treatment-procedure-note" type="text" name="note" />
                </AvGroup>
                <AvGroup>
                  <Label id="completedDateLabel" for="completedDate">
                    <Translate contentKey="totoroApp.treatmentProcedure.completedDate">Completed Date</Translate>
                  </Label>
                  <AvInput
                    id="treatment-procedure-completedDate"
                    type="datetime-local"
                    className="form-control"
                    name="completedDate"
                    value={isNew ? null : convertDateTimeFromServer(this.props.treatmentProcedureEntity.completedDate)}
                  />
                </AvGroup>
                <AvGroup>
                  <Label id="priceLabel" for="price">
                    <Translate contentKey="totoroApp.treatmentProcedure.price">Price</Translate>
                  </Label>
                  <AvField id="treatment-procedure-price" type="string" className="form-control" name="price" />
                </AvGroup>
                <AvGroup>
                  <Label id="nhiCategoryLabel" for="nhiCategory">
                    <Translate contentKey="totoroApp.treatmentProcedure.nhiCategory">Nhi Category</Translate>
                  </Label>
                  <AvField id="treatment-procedure-nhiCategory" type="text" name="nhiCategory" />
                </AvGroup>
                <AvGroup>
                  <Label for="nhiProcedure.id">
                    <Translate contentKey="totoroApp.treatmentProcedure.nhiProcedure">Nhi Procedure</Translate>
                  </Label>
                  <AvInput id="treatment-procedure-nhiProcedure" type="select" className="form-control" name="nhiProcedure.id">
                    <option value="" key="0" />
                    {nhiProcedures
                      ? nhiProcedures.map(otherEntity => (
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
                <AvGroup>
                  <Label for="procedure.id">
                    <Translate contentKey="totoroApp.treatmentProcedure.procedure">Procedure</Translate>
                  </Label>
                  <AvInput id="treatment-procedure-procedure" type="select" className="form-control" name="procedure.id">
                    <option value="" key="0" />
                    {procedures
                      ? procedures.map(otherEntity => (
                          <option value={otherEntity.id} key={otherEntity.id}>
                            {otherEntity.id}
                          </option>
                        ))
                      : null}
                  </AvInput>
                </AvGroup>
                <AvGroup>
                  <Label for="appointment.id">
                    <Translate contentKey="totoroApp.treatmentProcedure.appointment">Appointment</Translate>
                  </Label>
                  <AvInput id="treatment-procedure-appointment" type="select" className="form-control" name="appointment.id">
                    <option value="" key="0" />
                    {appointments
                      ? appointments.map(otherEntity => (
                          <option value={otherEntity.id} key={otherEntity.id}>
                            {otherEntity.id}
                          </option>
                        ))
                      : null}
                  </AvInput>
                </AvGroup>
                <AvGroup>
                  <Label for="todo.id">
                    <Translate contentKey="totoroApp.treatmentProcedure.todo">Todo</Translate>
                  </Label>
                  <AvInput id="treatment-procedure-todo" type="select" className="form-control" name="todo.id">
                    <option value="" key="0" />
                    {todos
                      ? todos.map(otherEntity => (
                          <option value={otherEntity.id} key={otherEntity.id}>
                            {otherEntity.id}
                          </option>
                        ))
                      : null}
                  </AvInput>
                </AvGroup>
                <AvGroup>
                  <Label for="disposal.id">
                    <Translate contentKey="totoroApp.treatmentProcedure.disposal">Disposal</Translate>
                  </Label>
                  <AvInput id="treatment-procedure-disposal" type="select" className="form-control" name="disposal.id">
                    <option value="" key="0" />
                    {disposals
                      ? disposals.map(otherEntity => (
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
  nhiProcedures: storeState.nhiProcedure.entities,
  treatmentTasks: storeState.treatmentTask.entities,
  procedures: storeState.procedure.entities,
  appointments: storeState.appointment.entities,
  todos: storeState.todo.entities,
  disposals: storeState.disposal.entities,
  treatmentProcedureEntity: storeState.treatmentProcedure.entity,
  loading: storeState.treatmentProcedure.loading,
  updating: storeState.treatmentProcedure.updating,
  updateSuccess: storeState.treatmentProcedure.updateSuccess
});

const mapDispatchToProps = {
  getNhiProcedures,
  getTreatmentTasks,
  getProcedures,
  getAppointments,
  getTodos,
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
)(TreatmentProcedureUpdate);
