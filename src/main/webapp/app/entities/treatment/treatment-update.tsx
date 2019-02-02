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
import { getEntity, updateEntity, createEntity, reset } from './treatment.reducer';
import { ITreatment } from 'app/shared/model/treatment.model';
// tslint:disable-next-line:no-unused-variable
import { convertDateTimeFromServer } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface ITreatmentUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export interface ITreatmentUpdateState {
  isNew: boolean;
  patientId: string;
}

export class TreatmentUpdate extends React.Component<ITreatmentUpdateProps, ITreatmentUpdateState> {
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
    if (errors.length === 0) {
      const { treatmentEntity } = this.props;
      const entity = {
        ...treatmentEntity,
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
    this.props.history.push('/entity/treatment');
  };

  render() {
    const { treatmentEntity, patients, loading, updating } = this.props;
    const { isNew } = this.state;

    return (
      <div>
        <Row className="justify-content-center">
          <Col md="8">
            <h2 id="totoroApp.treatment.home.createOrEditLabel">
              <Translate contentKey="totoroApp.treatment.home.createOrEditLabel">Create or edit a Treatment</Translate>
            </h2>
          </Col>
        </Row>
        <Row className="justify-content-center">
          <Col md="8">
            {loading ? (
              <p>Loading...</p>
            ) : (
              <AvForm model={isNew ? {} : treatmentEntity} onSubmit={this.saveEntity}>
                {!isNew ? (
                  <AvGroup>
                    <Label for="id">
                      <Translate contentKey="global.field.id">ID</Translate>
                    </Label>
                    <AvInput id="treatment-id" type="text" className="form-control" name="id" required readOnly />
                  </AvGroup>
                ) : null}
                <AvGroup>
                  <Label id="nameLabel" for="name">
                    <Translate contentKey="totoroApp.treatment.name">Name</Translate>
                  </Label>
                  <AvField
                    id="treatment-name"
                    type="text"
                    name="name"
                    validate={{
                      required: { value: true, errorMessage: translate('entity.validation.required') }
                    }}
                  />
                </AvGroup>
                <AvGroup>
                  <Label id="chiefComplaintLabel" for="chiefComplaint">
                    <Translate contentKey="totoroApp.treatment.chiefComplaint">Chief Complaint</Translate>
                  </Label>
                  <AvField id="treatment-chiefComplaint" type="text" name="chiefComplaint" />
                </AvGroup>
                <AvGroup>
                  <Label id="goalLabel" for="goal">
                    <Translate contentKey="totoroApp.treatment.goal">Goal</Translate>
                  </Label>
                  <AvField id="treatment-goal" type="text" name="goal" />
                </AvGroup>
                <AvGroup>
                  <Label id="noteLabel" for="note">
                    <Translate contentKey="totoroApp.treatment.note">Note</Translate>
                  </Label>
                  <AvField id="treatment-note" type="text" name="note" />
                </AvGroup>
                <AvGroup>
                  <Label id="findingLabel" for="finding">
                    <Translate contentKey="totoroApp.treatment.finding">Finding</Translate>
                  </Label>
                  <AvField id="treatment-finding" type="text" name="finding" />
                </AvGroup>
                <AvGroup>
                  <Label id="typeLabel">
                    <Translate contentKey="totoroApp.treatment.type">Type</Translate>
                  </Label>
                  <AvInput
                    id="treatment-type"
                    type="select"
                    className="form-control"
                    name="type"
                    value={(!isNew && treatmentEntity.type) || 'GENERAL'}
                  >
                    <option value="GENERAL">
                      <Translate contentKey="totoroApp.TreatmentType.GENERAL" />
                    </option>
                    <option value="PROFESSIONAL">
                      <Translate contentKey="totoroApp.TreatmentType.PROFESSIONAL" />
                    </option>
                  </AvInput>
                </AvGroup>
                <AvGroup>
                  <Label for="patient.id">
                    <Translate contentKey="totoroApp.treatment.patient">Patient</Translate>
                  </Label>
                  <AvInput id="treatment-patient" type="select" className="form-control" name="patient.id">
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
                <Button tag={Link} id="cancel-save" to="/entity/treatment" replace color="info">
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
  treatmentEntity: storeState.treatment.entity,
  loading: storeState.treatment.loading,
  updating: storeState.treatment.updating,
  updateSuccess: storeState.treatment.updateSuccess
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
)(TreatmentUpdate);
