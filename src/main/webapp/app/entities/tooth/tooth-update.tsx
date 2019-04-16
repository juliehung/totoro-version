import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
// tslint:disable-next-line:no-unused-variable
import { Translate, translate, ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { ITreatmentProcedure } from 'app/shared/model/treatment-procedure.model';
import { getEntities as getTreatmentProcedures } from 'app/entities/treatment-procedure/treatment-procedure.reducer';
import { IDisposal } from 'app/shared/model/disposal.model';
import { getEntities as getDisposals } from 'app/entities/disposal/disposal.reducer';
import { IPatient } from 'app/shared/model/patient.model';
import { getEntities as getPatients } from 'app/entities/patient/patient.reducer';
import { getEntity, updateEntity, createEntity, reset } from './tooth.reducer';
import { ITooth } from 'app/shared/model/tooth.model';
// tslint:disable-next-line:no-unused-variable
import { convertDateTimeFromServer } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IToothUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export interface IToothUpdateState {
  isNew: boolean;
  treatmentProcedureId: string;
  disposalId: string;
  patientId: string;
}

export class ToothUpdate extends React.Component<IToothUpdateProps, IToothUpdateState> {
  constructor(props) {
    super(props);
    this.state = {
      treatmentProcedureId: '0',
      disposalId: '0',
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

    this.props.getTreatmentProcedures();
    this.props.getDisposals();
    this.props.getPatients();
  }

  saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const { toothEntity } = this.props;
      const entity = {
        ...toothEntity,
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
    this.props.history.push('/entity/tooth');
  };

  render() {
    const { toothEntity, treatmentProcedures, disposals, patients, loading, updating } = this.props;
    const { isNew } = this.state;

    return (
      <div>
        <Row className="justify-content-center">
          <Col md="8">
            <h2 id="totoroApp.tooth.home.createOrEditLabel">
              <Translate contentKey="totoroApp.tooth.home.createOrEditLabel">Create or edit a Tooth</Translate>
            </h2>
          </Col>
        </Row>
        <Row className="justify-content-center">
          <Col md="8">
            {loading ? (
              <p>Loading...</p>
            ) : (
              <AvForm model={isNew ? {} : toothEntity} onSubmit={this.saveEntity}>
                {!isNew ? (
                  <AvGroup>
                    <Label for="id">
                      <Translate contentKey="global.field.id">ID</Translate>
                    </Label>
                    <AvInput id="tooth-id" type="text" className="form-control" name="id" required readOnly />
                  </AvGroup>
                ) : null}
                <AvGroup>
                  <Label id="positionLabel" for="position">
                    <Translate contentKey="totoroApp.tooth.position">Position</Translate>
                  </Label>
                  <AvField
                    id="tooth-position"
                    type="text"
                    name="position"
                    validate={{
                      required: { value: true, errorMessage: translate('entity.validation.required') }
                    }}
                  />
                </AvGroup>
                <AvGroup>
                  <Label id="surfaceLabel" for="surface">
                    <Translate contentKey="totoroApp.tooth.surface">Surface</Translate>
                  </Label>
                  <AvField id="tooth-surface" type="text" name="surface" />
                </AvGroup>
                <AvGroup>
                  <Label id="statusLabel" for="status">
                    <Translate contentKey="totoroApp.tooth.status">Status</Translate>
                  </Label>
                  <AvField id="tooth-status" type="text" name="status" />
                </AvGroup>
                <AvGroup>
                  <Label for="treatmentProcedure.id">
                    <Translate contentKey="totoroApp.tooth.treatmentProcedure">Treatment Procedure</Translate>
                  </Label>
                  <AvInput id="tooth-treatmentProcedure" type="select" className="form-control" name="treatmentProcedure.id">
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
                <AvGroup>
                  <Label for="disposal.id">
                    <Translate contentKey="totoroApp.tooth.disposal">Disposal</Translate>
                  </Label>
                  <AvInput id="tooth-disposal" type="select" className="form-control" name="disposal.id">
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
                <AvGroup>
                  <Label for="patient.id">
                    <Translate contentKey="totoroApp.tooth.patient">Patient</Translate>
                  </Label>
                  <AvInput id="tooth-patient" type="select" className="form-control" name="patient.id">
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
                <Button tag={Link} id="cancel-save" to="/entity/tooth" replace color="info">
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
  treatmentProcedures: storeState.treatmentProcedure.entities,
  disposals: storeState.disposal.entities,
  patients: storeState.patient.entities,
  toothEntity: storeState.tooth.entity,
  loading: storeState.tooth.loading,
  updating: storeState.tooth.updating,
  updateSuccess: storeState.tooth.updateSuccess
});

const mapDispatchToProps = {
  getTreatmentProcedures,
  getDisposals,
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
)(ToothUpdate);
