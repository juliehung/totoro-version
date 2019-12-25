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
import { getEntity, updateEntity, createEntity, reset } from './nhi-accumulated-medical-record.reducer';
import { INhiAccumulatedMedicalRecord } from 'app/shared/model/nhi-accumulated-medical-record.model';
// tslint:disable-next-line:no-unused-variable
import { convertDateTimeFromServer } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface INhiAccumulatedMedicalRecordUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export interface INhiAccumulatedMedicalRecordUpdateState {
  isNew: boolean;
  patientId: string;
}

export class NhiAccumulatedMedicalRecordUpdate extends React.Component<
  INhiAccumulatedMedicalRecordUpdateProps,
  INhiAccumulatedMedicalRecordUpdateState
> {
  constructor(props) {
    super(props);
    this.state = {
      patientId: '0',
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
  }

  saveEntity = (event, errors, values) => {
    values.date = new Date(values.date);

    if (errors.length === 0) {
      const { nhiAccumulatedMedicalRecordEntity } = this.props;
      const entity = {
        ...nhiAccumulatedMedicalRecordEntity,
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
    this.props.history.push('/entity/nhi-accumulated-medical-record');
  };

  render() {
    const { nhiAccumulatedMedicalRecordEntity, patients, loading, updating } = this.props;
    const { isNew } = this.state;

    return (
      <div>
        <Row className="justify-content-center">
          <Col md="8">
            <h2 id="totoroApp.nhiAccumulatedMedicalRecord.home.createOrEditLabel">
              <Translate contentKey="totoroApp.nhiAccumulatedMedicalRecord.home.createOrEditLabel">
                Create or edit a NhiAccumulatedMedicalRecord
              </Translate>
            </h2>
          </Col>
        </Row>
        <Row className="justify-content-center">
          <Col md="8">
            {loading ? (
              <p>Loading...</p>
            ) : (
              <AvForm model={isNew ? {} : nhiAccumulatedMedicalRecordEntity} onSubmit={this.saveEntity}>
                {!isNew ? (
                  <AvGroup>
                    <Label for="id">
                      <Translate contentKey="global.field.id">ID</Translate>
                    </Label>
                    <AvInput id="nhi-accumulated-medical-record-id" type="text" className="form-control" name="id" required readOnly />
                  </AvGroup>
                ) : null}
                <AvGroup>
                  <Label id="medicalCategoryLabel" for="medicalCategory">
                    <Translate contentKey="totoroApp.nhiAccumulatedMedicalRecord.medicalCategory">Medical Category</Translate>
                  </Label>
                  <AvField id="nhi-accumulated-medical-record-medicalCategory" type="text" name="medicalCategory" />
                </AvGroup>
                <AvGroup>
                  <Label id="newbornMedicalTreatmentNoteLabel" for="newbornMedicalTreatmentNote">
                    <Translate contentKey="totoroApp.nhiAccumulatedMedicalRecord.newbornMedicalTreatmentNote">
                      Newborn Medical Treatment Note
                    </Translate>
                  </Label>
                  <AvField id="nhi-accumulated-medical-record-newbornMedicalTreatmentNote" type="text" name="newbornMedicalTreatmentNote" />
                </AvGroup>
                <AvGroup>
                  <Label id="dateLabel" for="date">
                    <Translate contentKey="totoroApp.nhiAccumulatedMedicalRecord.date">Date</Translate>
                  </Label>
                  <AvInput
                    id="nhi-accumulated-medical-record-date"
                    type="datetime-local"
                    className="form-control"
                    name="date"
                    value={isNew ? null : convertDateTimeFromServer(this.props.nhiAccumulatedMedicalRecordEntity.date)}
                  />
                </AvGroup>
                <AvGroup>
                  <Label id="cardFillingNoteLabel" for="cardFillingNote">
                    <Translate contentKey="totoroApp.nhiAccumulatedMedicalRecord.cardFillingNote">Card Filling Note</Translate>
                  </Label>
                  <AvField id="nhi-accumulated-medical-record-cardFillingNote" type="text" name="cardFillingNote" />
                </AvGroup>
                <AvGroup>
                  <Label id="seqNumberLabel" for="seqNumber">
                    <Translate contentKey="totoroApp.nhiAccumulatedMedicalRecord.seqNumber">Seq Number</Translate>
                  </Label>
                  <AvField id="nhi-accumulated-medical-record-seqNumber" type="text" name="seqNumber" />
                </AvGroup>
                <AvGroup>
                  <Label id="medicalInstitutionCodeLabel" for="medicalInstitutionCode">
                    <Translate contentKey="totoroApp.nhiAccumulatedMedicalRecord.medicalInstitutionCode">
                      Medical Institution Code
                    </Translate>
                  </Label>
                  <AvField id="nhi-accumulated-medical-record-medicalInstitutionCode" type="text" name="medicalInstitutionCode" />
                </AvGroup>
                <AvGroup>
                  <Label for="patient.id">
                    <Translate contentKey="totoroApp.nhiAccumulatedMedicalRecord.patient">Patient</Translate>
                  </Label>
                  <AvInput
                    id="nhi-accumulated-medical-record-patient"
                    type="select"
                    className="form-control"
                    name="patient.id"
                    value={isNew ? patients[0] && patients[0].id : nhiAccumulatedMedicalRecordEntity.patient.id}
                  >
                    {patients
                      ? patients.map(otherEntity => (
                          <option value={otherEntity.id} key={otherEntity.id}>
                            {otherEntity.id}
                          </option>
                        ))
                      : null}
                  </AvInput>
                </AvGroup>
                <Button tag={Link} id="cancel-save" to="/entity/nhi-accumulated-medical-record" replace color="info">
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
  nhiAccumulatedMedicalRecordEntity: storeState.nhiAccumulatedMedicalRecord.entity,
  loading: storeState.nhiAccumulatedMedicalRecord.loading,
  updating: storeState.nhiAccumulatedMedicalRecord.updating,
  updateSuccess: storeState.nhiAccumulatedMedicalRecord.updateSuccess
});

const mapDispatchToProps = {
  getPatients,
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
)(NhiAccumulatedMedicalRecordUpdate);
