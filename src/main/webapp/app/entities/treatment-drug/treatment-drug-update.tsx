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
import { IDrug } from 'app/shared/model/drug.model';
import { getEntities as getDrugs } from 'app/entities/drug/drug.reducer';
import { getEntity, updateEntity, createEntity, reset } from './treatment-drug.reducer';
import { ITreatmentDrug } from 'app/shared/model/treatment-drug.model';
// tslint:disable-next-line:no-unused-variable
import { convertDateTimeFromServer } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface ITreatmentDrugUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export interface ITreatmentDrugUpdateState {
  isNew: boolean;
  prescriptionId: string;
  drugId: string;
}

export class TreatmentDrugUpdate extends React.Component<ITreatmentDrugUpdateProps, ITreatmentDrugUpdateState> {
  constructor(props) {
    super(props);
    this.state = {
      prescriptionId: '0',
      drugId: '0',
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
    this.props.getDrugs();
  }

  saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const { treatmentDrugEntity } = this.props;
      const entity = {
        ...treatmentDrugEntity,
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
    this.props.history.push('/entity/treatment-drug');
  };

  render() {
    const { treatmentDrugEntity, prescriptions, drugs, loading, updating } = this.props;
    const { isNew } = this.state;

    return (
      <div>
        <Row className="justify-content-center">
          <Col md="8">
            <h2 id="totoroApp.treatmentDrug.home.createOrEditLabel">
              <Translate contentKey="totoroApp.treatmentDrug.home.createOrEditLabel">Create or edit a TreatmentDrug</Translate>
            </h2>
          </Col>
        </Row>
        <Row className="justify-content-center">
          <Col md="8">
            {loading ? (
              <p>Loading...</p>
            ) : (
              <AvForm model={isNew ? {} : treatmentDrugEntity} onSubmit={this.saveEntity}>
                {!isNew ? (
                  <AvGroup>
                    <Label for="id">
                      <Translate contentKey="global.field.id">ID</Translate>
                    </Label>
                    <AvInput id="treatment-drug-id" type="text" className="form-control" name="id" required readOnly />
                  </AvGroup>
                ) : null}
                <AvGroup>
                  <Label id="dayLabel" for="day">
                    <Translate contentKey="totoroApp.treatmentDrug.day">Day</Translate>
                  </Label>
                  <AvField id="treatment-drug-day" type="string" className="form-control" name="day" />
                </AvGroup>
                <AvGroup>
                  <Label for="prescription.id">
                    <Translate contentKey="totoroApp.treatmentDrug.prescription">Prescription</Translate>
                  </Label>
                  <AvInput id="treatment-drug-prescription" type="select" className="form-control" name="prescription.id">
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
                  <Label for="drug.id">
                    <Translate contentKey="totoroApp.treatmentDrug.drug">Drug</Translate>
                  </Label>
                  <AvInput id="treatment-drug-drug" type="select" className="form-control" name="drug.id">
                    <option value="" key="0" />
                    {drugs
                      ? drugs.map(otherEntity => (
                          <option value={otherEntity.id} key={otherEntity.id}>
                            {otherEntity.id}
                          </option>
                        ))
                      : null}
                  </AvInput>
                </AvGroup>
                <Button tag={Link} id="cancel-save" to="/entity/treatment-drug" replace color="info">
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
  drugs: storeState.drug.entities,
  treatmentDrugEntity: storeState.treatmentDrug.entity,
  loading: storeState.treatmentDrug.loading,
  updating: storeState.treatmentDrug.updating,
  updateSuccess: storeState.treatmentDrug.updateSuccess
});

const mapDispatchToProps = {
  getPrescriptions,
  getDrugs,
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
)(TreatmentDrugUpdate);
