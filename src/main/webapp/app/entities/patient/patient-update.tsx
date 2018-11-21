import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
// tslint:disable-next-line:no-unused-variable
import { Translate, translate, ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { getEntities as getPatients } from 'app/entities/patient/patient.reducer';
import { ITag } from 'app/shared/model/tag.model';
import { getEntities as getTags } from 'app/entities/tag/tag.reducer';
import { getEntity, updateEntity, createEntity, reset } from './patient.reducer';
import { IPatient } from 'app/shared/model/patient.model';
// tslint:disable-next-line:no-unused-variable
import { convertDateTimeFromServer } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IPatientUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export interface IPatientUpdateState {
  isNew: boolean;
  idsparent: any[];
  idsspouse1: any[];
  idstag: any[];
  introducerId: string;
  childId: string;
  spouse2Id: string;
}

export class PatientUpdate extends React.Component<IPatientUpdateProps, IPatientUpdateState> {
  constructor(props) {
    super(props);
    this.state = {
      idsparent: [],
      idsspouse1: [],
      idstag: [],
      introducerId: '0',
      childId: '0',
      spouse2Id: '0',
      isNew: !this.props.match.params || !this.props.match.params.id
    };
  }

  componentDidMount() {
    if (this.state.isNew) {
      this.props.reset();
    } else {
      this.props.getEntity(this.props.match.params.id);
    }

    this.props.getPatients();
    this.props.getTags();
  }

  saveEntity = (event, errors, values) => {
    values.deleteDate = new Date(values.deleteDate);
    values.writeIcTime = new Date(values.writeIcTime);

    if (errors.length === 0) {
      const { patientEntity } = this.props;
      const entity = {
        ...patientEntity,
        ...values,
        parents: mapIdList(values.parents),
        spouse1S: mapIdList(values.spouse1S),
        tags: mapIdList(values.tags)
      };

      if (this.state.isNew) {
        this.props.createEntity(entity);
      } else {
        this.props.updateEntity(entity);
      }
      this.handleClose();
    }
  };

  handleClose = () => {
    this.props.history.push('/entity/patient');
  };

  render() {
    const { patientEntity, patients, tags, loading, updating } = this.props;
    const { isNew } = this.state;

    return (
      <div>
        <Row className="justify-content-center">
          <Col md="8">
            <h2 id="totoroApp.patient.home.createOrEditLabel">
              <Translate contentKey="totoroApp.patient.home.createOrEditLabel">Create or edit a Patient</Translate>
            </h2>
          </Col>
        </Row>
        <Row className="justify-content-center">
          <Col md="8">
            {loading ? (
              <p>Loading...</p>
            ) : (
              <AvForm model={isNew ? {} : patientEntity} onSubmit={this.saveEntity}>
                {!isNew ? (
                  <AvGroup>
                    <Label for="id">
                      <Translate contentKey="global.field.id">ID</Translate>
                    </Label>
                    <AvInput id="patient-id" type="text" className="form-control" name="id" required readOnly />
                  </AvGroup>
                ) : null}
                <AvGroup>
                  <Label id="nameLabel" for="name">
                    <Translate contentKey="totoroApp.patient.name">Name</Translate>
                  </Label>
                  <AvField
                    id="patient-name"
                    type="text"
                    name="name"
                    validate={{
                      required: { value: true, errorMessage: translate('entity.validation.required') }
                    }}
                  />
                </AvGroup>
                <AvGroup>
                  <Label id="nationalIdLabel" for="nationalId">
                    <Translate contentKey="totoroApp.patient.nationalId">National Id</Translate>
                  </Label>
                  <AvField
                    id="patient-nationalId"
                    type="text"
                    name="nationalId"
                    validate={{
                      required: { value: true, errorMessage: translate('entity.validation.required') }
                    }}
                  />
                </AvGroup>
                <AvGroup>
                  <Label id="genderLabel">
                    <Translate contentKey="totoroApp.patient.gender">Gender</Translate>
                  </Label>
                  <AvInput
                    id="patient-gender"
                    type="select"
                    className="form-control"
                    name="gender"
                    value={(!isNew && patientEntity.gender) || 'OTHER'}
                  >
                    <option value="OTHER">
                      <Translate contentKey="totoroApp.Gender.OTHER" />
                    </option>
                    <option value="MALE">
                      <Translate contentKey="totoroApp.Gender.MALE" />
                    </option>
                    <option value="FEMALE">
                      <Translate contentKey="totoroApp.Gender.FEMALE" />
                    </option>
                  </AvInput>
                </AvGroup>
                <AvGroup>
                  <Label id="birthLabel" for="birth">
                    <Translate contentKey="totoroApp.patient.birth">Birth</Translate>
                  </Label>
                  <AvField
                    id="patient-birth"
                    type="date"
                    className="form-control"
                    name="birth"
                    validate={{
                      required: { value: true, errorMessage: translate('entity.validation.required') }
                    }}
                  />
                </AvGroup>
                <AvGroup>
                  <Label id="phoneLabel" for="phone">
                    <Translate contentKey="totoroApp.patient.phone">Phone</Translate>
                  </Label>
                  <AvField
                    id="patient-phone"
                    type="text"
                    name="phone"
                    validate={{
                      required: { value: true, errorMessage: translate('entity.validation.required') }
                    }}
                  />
                </AvGroup>
                <AvGroup>
                  <Label id="medicalIdLabel" for="medicalId">
                    <Translate contentKey="totoroApp.patient.medicalId">Medical Id</Translate>
                  </Label>
                  <AvField id="patient-medicalId" type="text" name="medicalId" />
                </AvGroup>
                <AvGroup>
                  <Label id="zipLabel" for="zip">
                    <Translate contentKey="totoroApp.patient.zip">Zip</Translate>
                  </Label>
                  <AvField id="patient-zip" type="text" name="zip" />
                </AvGroup>
                <AvGroup>
                  <Label id="addressLabel" for="address">
                    <Translate contentKey="totoroApp.patient.address">Address</Translate>
                  </Label>
                  <AvField id="patient-address" type="text" name="address" />
                </AvGroup>
                <AvGroup>
                  <Label id="emailLabel" for="email">
                    <Translate contentKey="totoroApp.patient.email">Email</Translate>
                  </Label>
                  <AvField id="patient-email" type="text" name="email" />
                </AvGroup>
                <AvGroup>
                  <Label id="photoLabel" for="photo">
                    <Translate contentKey="totoroApp.patient.photo">Photo</Translate>
                  </Label>
                  <AvField id="patient-photo" type="text" name="photo" />
                </AvGroup>
                <AvGroup>
                  <Label id="bloodLabel">
                    <Translate contentKey="totoroApp.patient.blood">Blood</Translate>
                  </Label>
                  <AvInput
                    id="patient-blood"
                    type="select"
                    className="form-control"
                    name="blood"
                    value={(!isNew && patientEntity.blood) || 'UNKNOWN'}
                  >
                    <option value="UNKNOWN">
                      <Translate contentKey="totoroApp.Blood.UNKNOWN" />
                    </option>
                    <option value="A">
                      <Translate contentKey="totoroApp.Blood.A" />
                    </option>
                    <option value="AB">
                      <Translate contentKey="totoroApp.Blood.AB" />
                    </option>
                    <option value="B">
                      <Translate contentKey="totoroApp.Blood.B" />
                    </option>
                    <option value="O">
                      <Translate contentKey="totoroApp.Blood.O" />
                    </option>
                  </AvInput>
                </AvGroup>
                <AvGroup>
                  <Label id="cardIdLabel" for="cardId">
                    <Translate contentKey="totoroApp.patient.cardId">Card Id</Translate>
                  </Label>
                  <AvField id="patient-cardId" type="text" name="cardId" />
                </AvGroup>
                <AvGroup>
                  <Label id="vipLabel" for="vip">
                    <Translate contentKey="totoroApp.patient.vip">Vip</Translate>
                  </Label>
                  <AvField id="patient-vip" type="text" name="vip" />
                </AvGroup>
                <AvGroup>
                  <Label id="emergencyNameLabel" for="emergencyName">
                    <Translate contentKey="totoroApp.patient.emergencyName">Emergency Name</Translate>
                  </Label>
                  <AvField id="patient-emergencyName" type="text" name="emergencyName" />
                </AvGroup>
                <AvGroup>
                  <Label id="emergencyPhoneLabel" for="emergencyPhone">
                    <Translate contentKey="totoroApp.patient.emergencyPhone">Emergency Phone</Translate>
                  </Label>
                  <AvField id="patient-emergencyPhone" type="text" name="emergencyPhone" />
                </AvGroup>
                <AvGroup>
                  <Label id="deleteDateLabel" for="deleteDate">
                    <Translate contentKey="totoroApp.patient.deleteDate">Delete Date</Translate>
                  </Label>
                  <AvInput
                    id="patient-deleteDate"
                    type="datetime-local"
                    className="form-control"
                    name="deleteDate"
                    value={isNew ? null : convertDateTimeFromServer(this.props.patientEntity.deleteDate)}
                  />
                </AvGroup>
                <AvGroup>
                  <Label id="scalingLabel" for="scaling">
                    <Translate contentKey="totoroApp.patient.scaling">Scaling</Translate>
                  </Label>
                  <AvField id="patient-scaling" type="date" className="form-control" name="scaling" />
                </AvGroup>
                <AvGroup>
                  <Label id="lineIdLabel" for="lineId">
                    <Translate contentKey="totoroApp.patient.lineId">Line Id</Translate>
                  </Label>
                  <AvField id="patient-lineId" type="text" name="lineId" />
                </AvGroup>
                <AvGroup>
                  <Label id="fbIdLabel" for="fbId">
                    <Translate contentKey="totoroApp.patient.fbId">Fb Id</Translate>
                  </Label>
                  <AvField id="patient-fbId" type="text" name="fbId" />
                </AvGroup>
                <AvGroup>
                  <Label id="reminderLabel" for="reminder">
                    <Translate contentKey="totoroApp.patient.reminder">Reminder</Translate>
                  </Label>
                  <AvField id="patient-reminder" type="text" name="reminder" />
                </AvGroup>
                <AvGroup>
                  <Label id="writeIcTimeLabel" for="writeIcTime">
                    <Translate contentKey="totoroApp.patient.writeIcTime">Write Ic Time</Translate>
                  </Label>
                  <AvInput
                    id="patient-writeIcTime"
                    type="datetime-local"
                    className="form-control"
                    name="writeIcTime"
                    value={isNew ? null : convertDateTimeFromServer(this.props.patientEntity.writeIcTime)}
                  />
                </AvGroup>
                <AvGroup>
                  <Label for="introducer.id">
                    <Translate contentKey="totoroApp.patient.introducer">Introducer</Translate>
                  </Label>
                  <AvInput id="patient-introducer" type="select" className="form-control" name="introducer.id">
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
                  <Label for="patients">
                    <Translate contentKey="totoroApp.patient.parent">Parent</Translate>
                  </Label>
                  <AvInput
                    id="patient-parent"
                    type="select"
                    multiple
                    className="form-control"
                    name="parents"
                    value={patientEntity.parents && patientEntity.parents.map(e => e.id)}
                  >
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
                  <Label for="patients">
                    <Translate contentKey="totoroApp.patient.spouse1">Spouse 1</Translate>
                  </Label>
                  <AvInput
                    id="patient-spouse1"
                    type="select"
                    multiple
                    className="form-control"
                    name="spouse1S"
                    value={patientEntity.spouse1S && patientEntity.spouse1S.map(e => e.id)}
                  >
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
                  <Label for="tags">
                    <Translate contentKey="totoroApp.patient.tag">Tag</Translate>
                  </Label>
                  <AvInput
                    id="patient-tag"
                    type="select"
                    multiple
                    className="form-control"
                    name="tags"
                    value={patientEntity.tags && patientEntity.tags.map(e => e.id)}
                  >
                    <option value="" key="0" />
                    {tags
                      ? tags.map(otherEntity => (
                          <option value={otherEntity.id} key={otherEntity.id}>
                            {otherEntity.id}
                          </option>
                        ))
                      : null}
                  </AvInput>
                </AvGroup>
                <Button tag={Link} id="cancel-save" to="/entity/patient" replace color="info">
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
  tags: storeState.tag.entities,
  patientEntity: storeState.patient.entity,
  loading: storeState.patient.loading,
  updating: storeState.patient.updating
});

const mapDispatchToProps = {
  getPatients,
  getTags,
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
)(PatientUpdate);
