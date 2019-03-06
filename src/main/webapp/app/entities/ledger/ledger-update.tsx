import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
// tslint:disable-next-line:no-unused-variable
import { Translate, translate, ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { ITreatmentPlan } from 'app/shared/model/treatment-plan.model';
import { getEntities as getTreatmentPlans } from 'app/entities/treatment-plan/treatment-plan.reducer';
import { getEntity, updateEntity, createEntity, reset } from './ledger.reducer';
import { ILedger } from 'app/shared/model/ledger.model';
// tslint:disable-next-line:no-unused-variable
import { convertDateTimeFromServer } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface ILedgerUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export interface ILedgerUpdateState {
  isNew: boolean;
  treatmentPlanId: string;
}

export class LedgerUpdate extends React.Component<ILedgerUpdateProps, ILedgerUpdateState> {
  constructor(props) {
    super(props);
    this.state = {
      treatmentPlanId: '0',
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

    this.props.getTreatmentPlans();
  }

  saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const { ledgerEntity } = this.props;
      const entity = {
        ...ledgerEntity,
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
    this.props.history.push('/entity/ledger');
  };

  render() {
    const { ledgerEntity, treatmentPlans, loading, updating } = this.props;
    const { isNew } = this.state;

    return (
      <div>
        <Row className="justify-content-center">
          <Col md="8">
            <h2 id="totoroApp.ledger.home.createOrEditLabel">
              <Translate contentKey="totoroApp.ledger.home.createOrEditLabel">Create or edit a Ledger</Translate>
            </h2>
          </Col>
        </Row>
        <Row className="justify-content-center">
          <Col md="8">
            {loading ? (
              <p>Loading...</p>
            ) : (
              <AvForm model={isNew ? {} : ledgerEntity} onSubmit={this.saveEntity}>
                {!isNew ? (
                  <AvGroup>
                    <Label for="id">
                      <Translate contentKey="global.field.id">ID</Translate>
                    </Label>
                    <AvInput id="ledger-id" type="text" className="form-control" name="id" required readOnly />
                  </AvGroup>
                ) : null}
                <AvGroup>
                  <Label id="amountLabel" for="amount">
                    <Translate contentKey="totoroApp.ledger.amount">Amount</Translate>
                  </Label>
                  <AvField
                    id="ledger-amount"
                    type="string"
                    className="form-control"
                    name="amount"
                    validate={{
                      required: { value: true, errorMessage: translate('entity.validation.required') },
                      number: { value: true, errorMessage: translate('entity.validation.number') }
                    }}
                  />
                </AvGroup>
                <AvGroup>
                  <Label id="chargeLabel" for="charge">
                    <Translate contentKey="totoroApp.ledger.charge">Charge</Translate>
                  </Label>
                  <AvField
                    id="ledger-charge"
                    type="string"
                    className="form-control"
                    name="charge"
                    validate={{
                      required: { value: true, errorMessage: translate('entity.validation.required') },
                      number: { value: true, errorMessage: translate('entity.validation.number') }
                    }}
                  />
                </AvGroup>
                <AvGroup>
                  <Label id="arrearsLabel" for="arrears">
                    <Translate contentKey="totoroApp.ledger.arrears">Arrears</Translate>
                  </Label>
                  <AvField
                    id="ledger-arrears"
                    type="string"
                    className="form-control"
                    name="arrears"
                    validate={{
                      required: { value: true, errorMessage: translate('entity.validation.required') },
                      number: { value: true, errorMessage: translate('entity.validation.number') }
                    }}
                  />
                </AvGroup>
                <AvGroup>
                  <Label id="noteLabel" for="note">
                    <Translate contentKey="totoroApp.ledger.note">Note</Translate>
                  </Label>
                  <AvField id="ledger-note" type="text" name="note" />
                </AvGroup>
                <AvGroup>
                  <Label for="treatmentPlan.id">
                    <Translate contentKey="totoroApp.ledger.treatmentPlan">Treatment Plan</Translate>
                  </Label>
                  <AvInput id="ledger-treatmentPlan" type="select" className="form-control" name="treatmentPlan.id">
                    <option value="" key="0" />
                    {treatmentPlans
                      ? treatmentPlans.map(otherEntity => (
                          <option value={otherEntity.id} key={otherEntity.id}>
                            {otherEntity.id}
                          </option>
                        ))
                      : null}
                  </AvInput>
                </AvGroup>
                <Button tag={Link} id="cancel-save" to="/entity/ledger" replace color="info">
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
  treatmentPlans: storeState.treatmentPlan.entities,
  ledgerEntity: storeState.ledger.entity,
  loading: storeState.ledger.loading,
  updating: storeState.ledger.updating,
  updateSuccess: storeState.ledger.updateSuccess
});

const mapDispatchToProps = {
  getTreatmentPlans,
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
)(LedgerUpdate);
