import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
// tslint:disable-next-line:no-unused-variable
import { Translate, translate, ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { IDisposal } from 'app/shared/model/disposal.model';
import { getEntities as getDisposals } from 'app/entities/disposal/disposal.reducer';
import { getEntity, updateEntity, createEntity, reset } from './prescription.reducer';
import { IPrescription } from 'app/shared/model/prescription.model';
// tslint:disable-next-line:no-unused-variable
import { convertDateTimeFromServer } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IPrescriptionUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export interface IPrescriptionUpdateState {
  isNew: boolean;
  disposalId: string;
}

export class PrescriptionUpdate extends React.Component<IPrescriptionUpdateProps, IPrescriptionUpdateState> {
  constructor(props) {
    super(props);
    this.state = {
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

    this.props.getDisposals();
  }

  saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const { prescriptionEntity } = this.props;
      const entity = {
        ...prescriptionEntity,
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
    this.props.history.push('/entity/prescription');
  };

  render() {
    const { prescriptionEntity, disposals, loading, updating } = this.props;
    const { isNew } = this.state;

    return (
      <div>
        <Row className="justify-content-center">
          <Col md="8">
            <h2 id="totoroApp.prescription.home.createOrEditLabel">
              <Translate contentKey="totoroApp.prescription.home.createOrEditLabel">Create or edit a Prescription</Translate>
            </h2>
          </Col>
        </Row>
        <Row className="justify-content-center">
          <Col md="8">
            {loading ? (
              <p>Loading...</p>
            ) : (
              <AvForm model={isNew ? {} : prescriptionEntity} onSubmit={this.saveEntity}>
                {!isNew ? (
                  <AvGroup>
                    <Label for="id">
                      <Translate contentKey="global.field.id">ID</Translate>
                    </Label>
                    <AvInput id="prescription-id" type="text" className="form-control" name="id" required readOnly />
                  </AvGroup>
                ) : null}
                <AvGroup>
                  <Label id="clinicAdministrationLabel" check>
                    <AvInput id="prescription-clinicAdministration" type="checkbox" className="form-control" name="clinicAdministration" />
                    <Translate contentKey="totoroApp.prescription.clinicAdministration">Clinic Administration</Translate>
                  </Label>
                </AvGroup>
                <AvGroup>
                  <Label id="antiInflammatoryDrugLabel" check>
                    <AvInput id="prescription-antiInflammatoryDrug" type="checkbox" className="form-control" name="antiInflammatoryDrug" />
                    <Translate contentKey="totoroApp.prescription.antiInflammatoryDrug">Anti Inflammatory Drug</Translate>
                  </Label>
                </AvGroup>
                <AvGroup>
                  <Label id="painLabel" check>
                    <AvInput id="prescription-pain" type="checkbox" className="form-control" name="pain" />
                    <Translate contentKey="totoroApp.prescription.pain">Pain</Translate>
                  </Label>
                </AvGroup>
                <AvGroup>
                  <Label id="takenAllLabel" check>
                    <AvInput id="prescription-takenAll" type="checkbox" className="form-control" name="takenAll" />
                    <Translate contentKey="totoroApp.prescription.takenAll">Taken All</Translate>
                  </Label>
                </AvGroup>
                <AvGroup>
                  <Label id="statusLabel">
                    <Translate contentKey="totoroApp.prescription.status">Status</Translate>
                  </Label>
                  <AvInput
                    id="prescription-status"
                    type="select"
                    className="form-control"
                    name="status"
                    value={(!isNew && prescriptionEntity.status) || 'TEMPORARY'}
                  >
                    <option value="TEMPORARY">
                      <Translate contentKey="totoroApp.PrescriptionStatus.TEMPORARY" />
                    </option>
                    <option value="PERMANENT">
                      <Translate contentKey="totoroApp.PrescriptionStatus.PERMANENT" />
                    </option>
                  </AvInput>
                </AvGroup>
                <Button tag={Link} id="cancel-save" to="/entity/prescription" replace color="info">
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
  disposals: storeState.disposal.entities,
  prescriptionEntity: storeState.prescription.entity,
  loading: storeState.prescription.loading,
  updating: storeState.prescription.updating,
  updateSuccess: storeState.prescription.updateSuccess
});

const mapDispatchToProps = {
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
)(PrescriptionUpdate);
