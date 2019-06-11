import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
// tslint:disable-next-line:no-unused-variable
import { Translate, translate, ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { IAppointment } from 'app/shared/model/appointment.model';
import { getEntities as getAppointments } from 'app/entities/appointment/appointment.reducer';
import { IAccounting } from 'app/shared/model/accounting.model';
import { getEntities as getAccountings } from 'app/entities/accounting/accounting.reducer';
import { IDisposal } from 'app/shared/model/disposal.model';
import { getEntities as getDisposals } from 'app/entities/disposal/disposal.reducer';
import { getEntity, updateEntity, createEntity, reset } from './registration.reducer';
import { IRegistration } from 'app/shared/model/registration.model';
// tslint:disable-next-line:no-unused-variable
import { convertDateTimeFromServer } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IRegistrationUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export interface IRegistrationUpdateState {
  isNew: boolean;
  appointmentId: string;
  accountingId: string;
  disposalId: string;
}

export class RegistrationUpdate extends React.Component<IRegistrationUpdateProps, IRegistrationUpdateState> {
  constructor(props) {
    super(props);
    this.state = {
      appointmentId: '0',
      accountingId: '0',
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

    this.props.getAppointments();
    this.props.getAccountings();
    this.props.getDisposals();
  }

  saveEntity = (event, errors, values) => {
    values.arrivalTime = new Date(values.arrivalTime);

    if (errors.length === 0) {
      const { registrationEntity } = this.props;
      const entity = {
        ...registrationEntity,
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
    this.props.history.push('/entity/registration');
  };

  render() {
    const { registrationEntity, appointments, accountings, disposals, loading, updating } = this.props;
    const { isNew } = this.state;

    return (
      <div>
        <Row className="justify-content-center">
          <Col md="8">
            <h2 id="totoroApp.registration.home.createOrEditLabel">
              <Translate contentKey="totoroApp.registration.home.createOrEditLabel">Create or edit a Registration</Translate>
            </h2>
          </Col>
        </Row>
        <Row className="justify-content-center">
          <Col md="8">
            {loading ? (
              <p>Loading...</p>
            ) : (
              <AvForm model={isNew ? {} : registrationEntity} onSubmit={this.saveEntity}>
                {!isNew ? (
                  <AvGroup>
                    <Label for="id">
                      <Translate contentKey="global.field.id">ID</Translate>
                    </Label>
                    <AvInput id="registration-id" type="text" className="form-control" name="id" required readOnly />
                  </AvGroup>
                ) : null}
                <AvGroup>
                  <Label id="statusLabel">
                    <Translate contentKey="totoroApp.registration.status">Status</Translate>
                  </Label>
                  <AvInput
                    id="registration-status"
                    type="select"
                    className="form-control"
                    name="status"
                    value={(!isNew && registrationEntity.status) || 'PENDING'}
                  >
                    <option value="PENDING">
                      <Translate contentKey="totoroApp.RegistrationStatus.PENDING" />
                    </option>
                    <option value="FINISHED">
                      <Translate contentKey="totoroApp.RegistrationStatus.FINISHED" />
                    </option>
                    <option value="IN_PROGRESS">
                      <Translate contentKey="totoroApp.RegistrationStatus.IN_PROGRESS" />
                    </option>
                  </AvInput>
                </AvGroup>
                <AvGroup>
                  <Label id="arrivalTimeLabel" for="arrivalTime">
                    <Translate contentKey="totoroApp.registration.arrivalTime">Arrival Time</Translate>
                  </Label>
                  <AvInput
                    id="registration-arrivalTime"
                    type="datetime-local"
                    className="form-control"
                    name="arrivalTime"
                    value={isNew ? null : convertDateTimeFromServer(this.props.registrationEntity.arrivalTime)}
                  />
                </AvGroup>
                <AvGroup>
                  <Label id="typeLabel" for="type">
                    <Translate contentKey="totoroApp.registration.type">Type</Translate>
                  </Label>
                  <AvField id="registration-type" type="text" name="type" />
                </AvGroup>
                <AvGroup>
                  <Label id="onSiteLabel" check>
                    <AvInput id="registration-onSite" type="checkbox" className="form-control" name="onSite" />
                    <Translate contentKey="totoroApp.registration.onSite">On Site</Translate>
                  </Label>
                </AvGroup>
                <AvGroup>
                  <Label id="noCardLabel" check>
                    <AvInput id="registration-noCard" type="checkbox" className="form-control" name="noCard" />
                    <Translate contentKey="totoroApp.registration.noCard">No Card</Translate>
                  </Label>
                </AvGroup>
                <AvGroup>
                  <Label for="accounting.id">
                    <Translate contentKey="totoroApp.registration.accounting">Accounting</Translate>
                  </Label>
                  <AvInput id="registration-accounting" type="select" className="form-control" name="accounting.id">
                    <option value="" key="0" />
                    {accountings
                      ? accountings.map(otherEntity => (
                          <option value={otherEntity.id} key={otherEntity.id}>
                            {otherEntity.id}
                          </option>
                        ))
                      : null}
                  </AvInput>
                </AvGroup>
                <Button tag={Link} id="cancel-save" to="/entity/registration" replace color="info">
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
  appointments: storeState.appointment.entities,
  accountings: storeState.accounting.entities,
  disposals: storeState.disposal.entities,
  registrationEntity: storeState.registration.entity,
  loading: storeState.registration.loading,
  updating: storeState.registration.updating,
  updateSuccess: storeState.registration.updateSuccess
});

const mapDispatchToProps = {
  getAppointments,
  getAccountings,
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
)(RegistrationUpdate);
