import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
// tslint:disable-next-line:no-unused-variable
import { Translate, ICrudGetAction, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './patient.reducer';
import { IPatient } from 'app/shared/model/patient.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IPatientDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export class PatientDetail extends React.Component<IPatientDetailProps> {
  componentDidMount() {
    this.props.getEntity(this.props.match.params.id);
  }

  render() {
    const { patientEntity } = this.props;
    return (
      <Row>
        <Col md="8">
          <h2>
            <Translate contentKey="totoroApp.patient.detail.title">Patient</Translate> [<b>{patientEntity.id}</b>]
          </h2>
          <dl className="jh-entity-details">
            <dt>
              <span id="name">
                <Translate contentKey="totoroApp.patient.name">Name</Translate>
              </span>
            </dt>
            <dd>{patientEntity.name}</dd>
            <dt>
              <span id="nationalId">
                <Translate contentKey="totoroApp.patient.nationalId">National Id</Translate>
              </span>
            </dt>
            <dd>{patientEntity.nationalId}</dd>
            <dt>
              <span id="gender">
                <Translate contentKey="totoroApp.patient.gender">Gender</Translate>
              </span>
            </dt>
            <dd>{patientEntity.gender}</dd>
            <dt>
              <span id="birth">
                <Translate contentKey="totoroApp.patient.birth">Birth</Translate>
              </span>
            </dt>
            <dd>
              <TextFormat value={patientEntity.birth} type="date" format={APP_LOCAL_DATE_FORMAT} />
            </dd>
            <dt>
              <span id="phone">
                <Translate contentKey="totoroApp.patient.phone">Phone</Translate>
              </span>
            </dt>
            <dd>{patientEntity.phone}</dd>
            <dt>
              <span id="medicalId">
                <Translate contentKey="totoroApp.patient.medicalId">Medical Id</Translate>
              </span>
            </dt>
            <dd>{patientEntity.medicalId}</dd>
            <dt>
              <span id="zip">
                <Translate contentKey="totoroApp.patient.zip">Zip</Translate>
              </span>
            </dt>
            <dd>{patientEntity.zip}</dd>
            <dt>
              <span id="address">
                <Translate contentKey="totoroApp.patient.address">Address</Translate>
              </span>
            </dt>
            <dd>{patientEntity.address}</dd>
            <dt>
              <span id="email">
                <Translate contentKey="totoroApp.patient.email">Email</Translate>
              </span>
            </dt>
            <dd>{patientEntity.email}</dd>
            <dt>
              <span id="photo">
                <Translate contentKey="totoroApp.patient.photo">Photo</Translate>
              </span>
            </dt>
            <dd>{patientEntity.photo}</dd>
            <dt>
              <span id="blood">
                <Translate contentKey="totoroApp.patient.blood">Blood</Translate>
              </span>
            </dt>
            <dd>{patientEntity.blood}</dd>
            <dt>
              <span id="cardId">
                <Translate contentKey="totoroApp.patient.cardId">Card Id</Translate>
              </span>
            </dt>
            <dd>{patientEntity.cardId}</dd>
            <dt>
              <span id="vip">
                <Translate contentKey="totoroApp.patient.vip">Vip</Translate>
              </span>
            </dt>
            <dd>{patientEntity.vip}</dd>
            <dt>
              <span id="emergencyName">
                <Translate contentKey="totoroApp.patient.emergencyName">Emergency Name</Translate>
              </span>
            </dt>
            <dd>{patientEntity.emergencyName}</dd>
            <dt>
              <span id="emergencyPhone">
                <Translate contentKey="totoroApp.patient.emergencyPhone">Emergency Phone</Translate>
              </span>
            </dt>
            <dd>{patientEntity.emergencyPhone}</dd>
            <dt>
              <span id="deleteDate">
                <Translate contentKey="totoroApp.patient.deleteDate">Delete Date</Translate>
              </span>
            </dt>
            <dd>
              <TextFormat value={patientEntity.deleteDate} type="date" format={APP_DATE_FORMAT} />
            </dd>
            <dt>
              <span id="scaling">
                <Translate contentKey="totoroApp.patient.scaling">Scaling</Translate>
              </span>
            </dt>
            <dd>
              <TextFormat value={patientEntity.scaling} type="date" format={APP_LOCAL_DATE_FORMAT} />
            </dd>
            <dt>
              <span id="allergy">
                <Translate contentKey="totoroApp.patient.allergy">Allergy</Translate>
              </span>
            </dt>
            <dd>{patientEntity.allergy ? 'true' : 'false'}</dd>
            <dt>
              <span id="inconvenience">
                <Translate contentKey="totoroApp.patient.inconvenience">Inconvenience</Translate>
              </span>
            </dt>
            <dd>{patientEntity.inconvenience ? 'true' : 'false'}</dd>
            <dt>
              <span id="seriousDisease">
                <Translate contentKey="totoroApp.patient.seriousDisease">Serious Disease</Translate>
              </span>
            </dt>
            <dd>{patientEntity.seriousDisease ? 'true' : 'false'}</dd>
            <dt>
              <span id="lineId">
                <Translate contentKey="totoroApp.patient.lineId">Line Id</Translate>
              </span>
            </dt>
            <dd>{patientEntity.lineId}</dd>
            <dt>
              <span id="fbId">
                <Translate contentKey="totoroApp.patient.fbId">Fb Id</Translate>
              </span>
            </dt>
            <dd>{patientEntity.fbId}</dd>
            <dt>
              <span id="reminder">
                <Translate contentKey="totoroApp.patient.reminder">Reminder</Translate>
              </span>
            </dt>
            <dd>{patientEntity.reminder}</dd>
            <dt>
              <span id="writeIcTime">
                <Translate contentKey="totoroApp.patient.writeIcTime">Write Ic Time</Translate>
              </span>
            </dt>
            <dd>
              <TextFormat value={patientEntity.writeIcTime} type="date" format={APP_DATE_FORMAT} />
            </dd>
            <dt>
              <Translate contentKey="totoroApp.patient.introducer">Introducer</Translate>
            </dt>
            <dd>{patientEntity.introducer ? patientEntity.introducer.id : ''}</dd>
            <dt>
              <Translate contentKey="totoroApp.patient.parent">Parent</Translate>
            </dt>
            <dd>
              {patientEntity.parents
                ? patientEntity.parents.map((val, i) => (
                    <span key={val.id}>
                      <a>{val.id}</a>
                      {i === patientEntity.parents.length - 1 ? '' : ', '}
                    </span>
                  ))
                : null}
            </dd>
          </dl>
          <Button tag={Link} to="/entity/patient" replace color="info">
            <FontAwesomeIcon icon="arrow-left" />{' '}
            <span className="d-none d-md-inline">
              <Translate contentKey="entity.action.back">Back</Translate>
            </span>
          </Button>
          &nbsp;
          <Button tag={Link} to={`/entity/patient/${patientEntity.id}/edit`} replace color="primary">
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

const mapStateToProps = ({ patient }: IRootState) => ({
  patientEntity: patient.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(PatientDetail);
