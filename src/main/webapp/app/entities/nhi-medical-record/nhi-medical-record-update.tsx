import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
// tslint:disable-next-line:no-unused-variable
import { Translate, translate, ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { INhiExtendPatient } from 'app/shared/model/nhi-extend-patient.model';
import { getEntities as getNhiExtendPatients } from 'app/entities/nhi-extend-patient/nhi-extend-patient.reducer';
import { getEntity, updateEntity, createEntity, reset } from './nhi-medical-record.reducer';
import { INhiMedicalRecord } from 'app/shared/model/nhi-medical-record.model';
// tslint:disable-next-line:no-unused-variable
import { convertDateTimeFromServer } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface INhiMedicalRecordUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export interface INhiMedicalRecordUpdateState {
  isNew: boolean;
  nhiExtendPatientId: string;
}

export class NhiMedicalRecordUpdate extends React.Component<INhiMedicalRecordUpdateProps, INhiMedicalRecordUpdateState> {
  constructor(props) {
    super(props);
    this.state = {
      nhiExtendPatientId: '0',
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

    this.props.getNhiExtendPatients();
  }

  saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const { nhiMedicalRecordEntity } = this.props;
      const entity = {
        ...nhiMedicalRecordEntity,
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
    this.props.history.push('/entity/nhi-medical-record');
  };

  render() {
    const { nhiMedicalRecordEntity, nhiExtendPatients, loading, updating } = this.props;
    const { isNew } = this.state;

    return (
      <div>
        <Row className="justify-content-center">
          <Col md="8">
            <h2 id="totoroApp.nhiMedicalRecord.home.createOrEditLabel">
              <Translate contentKey="totoroApp.nhiMedicalRecord.home.createOrEditLabel">Create or edit a NhiMedicalRecord</Translate>
            </h2>
          </Col>
        </Row>
        <Row className="justify-content-center">
          <Col md="8">
            {loading ? (
              <p>Loading...</p>
            ) : (
              <AvForm model={isNew ? {} : nhiMedicalRecordEntity} onSubmit={this.saveEntity}>
                {!isNew ? (
                  <AvGroup>
                    <Label for="id">
                      <Translate contentKey="global.field.id">ID</Translate>
                    </Label>
                    <AvInput id="nhi-medical-record-id" type="text" className="form-control" name="id" required readOnly />
                  </AvGroup>
                ) : null}
                <AvGroup>
                  <Label id="dateLabel" for="date">
                    <Translate contentKey="totoroApp.nhiMedicalRecord.date">Date</Translate>
                  </Label>
                  <AvField id="nhi-medical-record-date" type="text" name="date" />
                </AvGroup>
                <AvGroup>
                  <Label id="nhiCategoryLabel" for="nhiCategory">
                    <Translate contentKey="totoroApp.nhiMedicalRecord.nhiCategory">Nhi Category</Translate>
                  </Label>
                  <AvField id="nhi-medical-record-nhiCategory" type="text" name="nhiCategory" />
                </AvGroup>
                <AvGroup>
                  <Label id="nhiCodeLabel" for="nhiCode">
                    <Translate contentKey="totoroApp.nhiMedicalRecord.nhiCode">Nhi Code</Translate>
                  </Label>
                  <AvField id="nhi-medical-record-nhiCode" type="text" name="nhiCode" />
                </AvGroup>
                <AvGroup>
                  <Label id="partLabel" for="part">
                    <Translate contentKey="totoroApp.nhiMedicalRecord.part">Part</Translate>
                  </Label>
                  <AvField id="nhi-medical-record-part" type="text" name="part" />
                </AvGroup>
                <AvGroup>
                  <Label id="usageLabel" for="usage">
                    <Translate contentKey="totoroApp.nhiMedicalRecord.usage">Usage</Translate>
                  </Label>
                  <AvField id="nhi-medical-record-usage" type="text" name="usage" />
                </AvGroup>
                <AvGroup>
                  <Label id="totalLabel" for="total">
                    <Translate contentKey="totoroApp.nhiMedicalRecord.total">Total</Translate>
                  </Label>
                  <AvField id="nhi-medical-record-total" type="text" name="total" />
                </AvGroup>
                <AvGroup>
                  <Label id="noteLabel" for="note">
                    <Translate contentKey="totoroApp.nhiMedicalRecord.note">Note</Translate>
                  </Label>
                  <AvField id="nhi-medical-record-note" type="text" name="note" />
                </AvGroup>
                <AvGroup>
                  <Label id="daysLabel" for="days">
                    <Translate contentKey="totoroApp.nhiMedicalRecord.days">Days</Translate>
                  </Label>
                  <AvField id="nhi-medical-record-days" type="text" name="days" />
                </AvGroup>
                <AvGroup>
                  <Label for="nhiExtendPatient.id">
                    <Translate contentKey="totoroApp.nhiMedicalRecord.nhiExtendPatient">Nhi Extend Patient</Translate>
                  </Label>
                  <AvInput id="nhi-medical-record-nhiExtendPatient" type="select" className="form-control" name="nhiExtendPatient.id">
                    <option value="" key="0" />
                    {nhiExtendPatients
                      ? nhiExtendPatients.map(otherEntity => (
                          <option value={otherEntity.id} key={otherEntity.id}>
                            {otherEntity.id}
                          </option>
                        ))
                      : null}
                  </AvInput>
                </AvGroup>
                <Button tag={Link} id="cancel-save" to="/entity/nhi-medical-record" replace color="info">
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
  nhiExtendPatients: storeState.nhiExtendPatient.entities,
  nhiMedicalRecordEntity: storeState.nhiMedicalRecord.entity,
  loading: storeState.nhiMedicalRecord.loading,
  updating: storeState.nhiMedicalRecord.updating,
  updateSuccess: storeState.nhiMedicalRecord.updateSuccess
});

const mapDispatchToProps = {
  getNhiExtendPatients,
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
)(NhiMedicalRecordUpdate);
