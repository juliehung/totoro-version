import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
// tslint:disable-next-line:no-unused-variable
import { Translate, ICrudGetAction, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './appointment.reducer';
import { IAppointment } from 'app/shared/model/appointment.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IAppointmentDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export class AppointmentDetail extends React.Component<IAppointmentDetailProps> {
  componentDidMount() {
    this.props.getEntity(this.props.match.params.id);
  }

  render() {
    const { appointmentEntity } = this.props;
    return (
      <Row>
        <Col md="8">
          <h2>
            <Translate contentKey="totoroApp.appointment.detail.title">Appointment</Translate> [<b>{appointmentEntity.id}</b>]
          </h2>
          <dl className="jh-entity-details">
            <dt>
              <span id="status">
                <Translate contentKey="totoroApp.appointment.status">Status</Translate>
              </span>
            </dt>
            <dd>{appointmentEntity.status}</dd>
            <dt>
              <span id="subject">
                <Translate contentKey="totoroApp.appointment.subject">Subject</Translate>
              </span>
            </dt>
            <dd>{appointmentEntity.subject}</dd>
            <dt>
              <span id="note">
                <Translate contentKey="totoroApp.appointment.note">Note</Translate>
              </span>
            </dt>
            <dd>{appointmentEntity.note}</dd>
            <dt>
              <span id="expectedArrivalTime">
                <Translate contentKey="totoroApp.appointment.expectedArrivalTime">Expected Arrival Time</Translate>
              </span>
            </dt>
            <dd>
              <TextFormat value={appointmentEntity.expectedArrivalTime} type="date" format={APP_DATE_FORMAT} />
            </dd>
            <dt>
              <span id="requiredTreatmentTime">
                <Translate contentKey="totoroApp.appointment.requiredTreatmentTime">Required Treatment Time</Translate>
              </span>
            </dt>
            <dd>{appointmentEntity.requiredTreatmentTime}</dd>
            <dt>
              <span id="microscope">
                <Translate contentKey="totoroApp.appointment.microscope">Microscope</Translate>
              </span>
            </dt>
            <dd>{appointmentEntity.microscope ? 'true' : 'false'}</dd>
            <dt>
              <span id="baseFloor">
                <Translate contentKey="totoroApp.appointment.baseFloor">Base Floor</Translate>
              </span>
            </dt>
            <dd>{appointmentEntity.baseFloor ? 'true' : 'false'}</dd>
            <dt>
              <span id="colorId">
                <Translate contentKey="totoroApp.appointment.colorId">Color Id</Translate>
              </span>
            </dt>
            <dd>{appointmentEntity.colorId}</dd>
            <dt>
              <span id="archived">
                <Translate contentKey="totoroApp.appointment.archived">Archived</Translate>
              </span>
            </dt>
            <dd>{appointmentEntity.archived ? 'true' : 'false'}</dd>
            <dt>
              <span id="contacted">
                <Translate contentKey="totoroApp.appointment.contacted">Contacted</Translate>
              </span>
            </dt>
            <dd>{appointmentEntity.contacted ? 'true' : 'false'}</dd>
            <dt>
              <Translate contentKey="totoroApp.appointment.patient">Patient</Translate>
            </dt>
            <dd>{appointmentEntity.patient ? appointmentEntity.patient.id : ''}</dd>
            <dt>
              <Translate contentKey="totoroApp.appointment.registration">Registration</Translate>
            </dt>
            <dd>{appointmentEntity.registration ? appointmentEntity.registration.id : ''}</dd>
          </dl>
          <Button tag={Link} to="/entity/appointment" replace color="info">
            <FontAwesomeIcon icon="arrow-left" />{' '}
            <span className="d-none d-md-inline">
              <Translate contentKey="entity.action.back">Back</Translate>
            </span>
          </Button>
          &nbsp;
          <Button tag={Link} to={`/entity/appointment/${appointmentEntity.id}/edit`} replace color="primary">
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

const mapStateToProps = ({ appointment }: IRootState) => ({
  appointmentEntity: appointment.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(AppointmentDetail);
