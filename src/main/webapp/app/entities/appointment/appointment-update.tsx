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
import { IRegistration } from 'app/shared/model/registration.model';
import { getEntities as getRegistrations } from 'app/entities/registration/registration.reducer';
import { getEntity, updateEntity, createEntity, reset } from './appointment.reducer';
import { IAppointment } from 'app/shared/model/appointment.model';
// tslint:disable-next-line:no-unused-variable
import { convertDateTimeFromServer } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IAppointmentUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export interface IAppointmentUpdateState {
  isNew: boolean;
  patientId: string;
  registrationId: string;
}

export class AppointmentUpdate extends React.Component<IAppointmentUpdateProps, IAppointmentUpdateState> {
  constructor(props) {
    super(props);
    this.state = {
      patientId: '0',
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

    this.props.getPatients();
    this.props.getRegistrations();
  }

  saveEntity = (event, errors, values) => {
    values.expectedArrivalTime = new Date(values.expectedArrivalTime);

    if (errors.length === 0) {
      const { appointmentEntity } = this.props;
      const entity = {
        ...appointmentEntity,
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
    this.props.history.push('/entity/appointment');
  };

  render() {
    const { appointmentEntity, patients, registrations, loading, updating } = this.props;
    const { isNew } = this.state;

    return (
      <div>
        <Row className="justify-content-center">
          <Col md="8">
            <h2 id="totoroApp.appointment.home.createOrEditLabel">
              <Translate contentKey="totoroApp.appointment.home.createOrEditLabel">Create or edit a Appointment</Translate>
            </h2>
          </Col>
        </Row>
        <Row className="justify-content-center">
          <Col md="8">
            {loading ? (
              <p>Loading...</p>
            ) : (
              <AvForm model={isNew ? {} : appointmentEntity} onSubmit={this.saveEntity}>
                {!isNew ? (
                  <AvGroup>
                    <Label for="id">
                      <Translate contentKey="global.field.id">ID</Translate>
                    </Label>
                    <AvInput id="appointment-id" type="text" className="form-control" name="id" required readOnly />
                  </AvGroup>
                ) : null}
                <AvGroup>
                  <Label id="statusLabel">
                    <Translate contentKey="totoroApp.appointment.status">Status</Translate>
                  </Label>
                  <AvInput
                    id="appointment-status"
                    type="select"
                    className="form-control"
                    name="status"
                    value={(!isNew && appointmentEntity.status) || 'CONFIRMED'}
                  >
                    <option value="CONFIRMED">
                      <Translate contentKey="totoroApp.AppointmentStatus.CONFIRMED" />
                    </option>
                    <option value="CANCEL">
                      <Translate contentKey="totoroApp.AppointmentStatus.CANCEL" />
                    </option>
                  </AvInput>
                </AvGroup>
                <AvGroup>
                  <Label id="subjectLabel" for="subject">
                    <Translate contentKey="totoroApp.appointment.subject">Subject</Translate>
                  </Label>
                  <AvField id="appointment-subject" type="text" name="subject" />
                </AvGroup>
                <AvGroup>
                  <Label id="noteLabel" for="note">
                    <Translate contentKey="totoroApp.appointment.note">Note</Translate>
                  </Label>
                  <AvField id="appointment-note" type="text" name="note" />
                </AvGroup>
                <AvGroup>
                  <Label id="expectedArrivalTimeLabel" for="expectedArrivalTime">
                    <Translate contentKey="totoroApp.appointment.expectedArrivalTime">Expected Arrival Time</Translate>
                  </Label>
                  <AvInput
                    id="appointment-expectedArrivalTime"
                    type="datetime-local"
                    className="form-control"
                    name="expectedArrivalTime"
                    value={isNew ? null : convertDateTimeFromServer(this.props.appointmentEntity.expectedArrivalTime)}
                  />
                </AvGroup>
                <AvGroup>
                  <Label id="requiredTreatmentTimeLabel" for="requiredTreatmentTime">
                    <Translate contentKey="totoroApp.appointment.requiredTreatmentTime">Required Treatment Time</Translate>
                  </Label>
                  <AvField id="appointment-requiredTreatmentTime" type="string" className="form-control" name="requiredTreatmentTime" />
                </AvGroup>
                <AvGroup>
                  <Label id="microscopeLabel" check>
                    <AvInput id="appointment-microscope" type="checkbox" className="form-control" name="microscope" />
                    <Translate contentKey="totoroApp.appointment.microscope">Microscope</Translate>
                  </Label>
                </AvGroup>
                <AvGroup>
                  <Label id="newPatientLabel" check>
                    <AvInput id="appointment-newPatient" type="checkbox" className="form-control" name="newPatient" />
                    <Translate contentKey="totoroApp.appointment.newPatient">New Patient</Translate>
                  </Label>
                </AvGroup>
                <AvGroup>
                  <Label id="baseFloorLabel" check>
                    <AvInput id="appointment-baseFloor" type="checkbox" className="form-control" name="baseFloor" />
                    <Translate contentKey="totoroApp.appointment.baseFloor">Base Floor</Translate>
                  </Label>
                </AvGroup>
                <AvGroup>
                  <Label id="colorIdLabel" for="colorId">
                    <Translate contentKey="totoroApp.appointment.colorId">Color Id</Translate>
                  </Label>
                  <AvField id="appointment-colorId" type="string" className="form-control" name="colorId" />
                </AvGroup>
                <AvGroup>
                  <Label id="archivedLabel" check>
                    <AvInput id="appointment-archived" type="checkbox" className="form-control" name="archived" />
                    <Translate contentKey="totoroApp.appointment.archived">Archived</Translate>
                  </Label>
                </AvGroup>
                <AvGroup>
                  <Label for="patient.id">
                    <Translate contentKey="totoroApp.appointment.patient">Patient</Translate>
                  </Label>
                  <AvInput id="appointment-patient" type="select" className="form-control" name="patient.id">
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
                  <Label for="registration.id">
                    <Translate contentKey="totoroApp.appointment.registration">Registration</Translate>
                  </Label>
                  <AvInput id="appointment-registration" type="select" className="form-control" name="registration.id">
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
                <Button tag={Link} id="cancel-save" to="/entity/appointment" replace color="info">
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
  registrations: storeState.registration.entities,
  appointmentEntity: storeState.appointment.entity,
  loading: storeState.appointment.loading,
  updating: storeState.appointment.updating,
  updateSuccess: storeState.appointment.updateSuccess
});

const mapDispatchToProps = {
  getPatients,
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
)(AppointmentUpdate);
