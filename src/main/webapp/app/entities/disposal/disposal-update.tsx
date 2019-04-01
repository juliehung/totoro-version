import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
// tslint:disable-next-line:no-unused-variable
import { Translate, translate, ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { IPrescription } from 'app/shared/model/prescription.model';
import { getEntities as getPrescriptions } from 'app/entities/prescription/prescription.reducer';
import { ITodo } from 'app/shared/model/todo.model';
import { getEntities as getTodos } from 'app/entities/todo/todo.reducer';
import { IRegistration } from 'app/shared/model/registration.model';
import { getEntities as getRegistrations } from 'app/entities/registration/registration.reducer';
import { getEntity, updateEntity, createEntity, reset } from './disposal.reducer';
import { IDisposal } from 'app/shared/model/disposal.model';
// tslint:disable-next-line:no-unused-variable
import { convertDateTimeFromServer } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IDisposalUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export interface IDisposalUpdateState {
  isNew: boolean;
  prescriptionId: string;
  todoId: string;
  registrationId: string;
}

export class DisposalUpdate extends React.Component<IDisposalUpdateProps, IDisposalUpdateState> {
  constructor(props) {
    super(props);
    this.state = {
      prescriptionId: '0',
      todoId: '0',
      registrationId: '0',
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

    this.props.getPrescriptions();
    this.props.getTodos();
    this.props.getRegistrations();
  }

  saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const { disposalEntity } = this.props;
      const entity = {
        ...disposalEntity,
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
    this.props.history.push('/entity/disposal');
  };

  render() {
    const { disposalEntity, prescriptions, todos, registrations, loading, updating } = this.props;
    const { isNew } = this.state;

    return (
      <div>
        <Row className="justify-content-center">
          <Col md="8">
            <h2 id="totoroApp.disposal.home.createOrEditLabel">
              <Translate contentKey="totoroApp.disposal.home.createOrEditLabel">Create or edit a Disposal</Translate>
            </h2>
          </Col>
        </Row>
        <Row className="justify-content-center">
          <Col md="8">
            {loading ? (
              <p>Loading...</p>
            ) : (
              <AvForm model={isNew ? {} : disposalEntity} onSubmit={this.saveEntity}>
                {!isNew ? (
                  <AvGroup>
                    <Label for="id">
                      <Translate contentKey="global.field.id">ID</Translate>
                    </Label>
                    <AvInput id="disposal-id" type="text" className="form-control" name="id" required readOnly />
                  </AvGroup>
                ) : null}
                <AvGroup>
                  <Label id="statusLabel">
                    <Translate contentKey="totoroApp.disposal.status">Status</Translate>
                  </Label>
                  <AvInput
                    id="disposal-status"
                    type="select"
                    className="form-control"
                    name="status"
                    value={(!isNew && disposalEntity.status) || 'TEMPORARY'}
                  >
                    <option value="TEMPORARY">
                      <Translate contentKey="totoroApp.DisposalStatus.TEMPORARY" />
                    </option>
                    <option value="PERMANENT">
                      <Translate contentKey="totoroApp.DisposalStatus.PERMANENT" />
                    </option>
                    <option value="MADE_APPT">
                      <Translate contentKey="totoroApp.DisposalStatus.MADE_APPT" />
                    </option>
                  </AvInput>
                </AvGroup>
                <AvGroup>
                  <Label id="totalLabel" for="total">
                    <Translate contentKey="totoroApp.disposal.total">Total</Translate>
                  </Label>
                  <AvField id="disposal-total" type="string" className="form-control" name="total" />
                </AvGroup>
                <AvGroup>
                  <Label for="prescription.id">
                    <Translate contentKey="totoroApp.disposal.prescription">Prescription</Translate>
                  </Label>
                  <AvInput id="disposal-prescription" type="select" className="form-control" name="prescription.id">
                    <option value="" key="0" />
                    {prescriptions
                      ? prescriptions.map(otherEntity => (
                          <option value={otherEntity.id} key={otherEntity.id}>
                            {otherEntity.id}
                          </option>
                        ))
                      : null}
                  </AvInput>
                </AvGroup>
                <AvGroup>
                  <Label for="todo.id">
                    <Translate contentKey="totoroApp.disposal.todo">Todo</Translate>
                  </Label>
                  <AvInput id="disposal-todo" type="select" className="form-control" name="todo.id">
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
                  <Label for="registration.id">
                    <Translate contentKey="totoroApp.disposal.registration">Registration</Translate>
                  </Label>
                  <AvInput id="disposal-registration" type="select" className="form-control" name="registration.id">
                    <option value="" key="0" />
                    {registrations
                      ? registrations.map(otherEntity => (
                          <option value={otherEntity.id} key={otherEntity.id}>
                            {otherEntity.id}
                          </option>
                        ))
                      : null}
                  </AvInput>
                </AvGroup>
                <Button tag={Link} id="cancel-save" to="/entity/disposal" replace color="info">
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
  prescriptions: storeState.prescription.entities,
  todos: storeState.todo.entities,
  registrations: storeState.registration.entities,
  disposalEntity: storeState.disposal.entity,
  loading: storeState.disposal.loading,
  updating: storeState.disposal.updating,
  updateSuccess: storeState.disposal.updateSuccess
});

const mapDispatchToProps = {
  getPrescriptions,
  getTodos,
  getRegistrations,
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
)(DisposalUpdate);
