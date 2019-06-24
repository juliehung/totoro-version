import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
// tslint:disable-next-line:no-unused-variable
import { Translate, translate, ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { IHospital } from 'app/shared/model/hospital.model';
import { getEntities as getHospitals } from 'app/entities/hospital/hospital.reducer';
import { IRegistration } from 'app/shared/model/registration.model';
import { getEntities as getRegistrations } from 'app/entities/registration/registration.reducer';
import { getEntity, updateEntity, createEntity, reset } from './accounting.reducer';
import { IAccounting } from 'app/shared/model/accounting.model';
// tslint:disable-next-line:no-unused-variable
import { convertDateTimeFromServer } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IAccountingUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export interface IAccountingUpdateState {
  isNew: boolean;
  hospitalId: string;
  registrationId: string;
}

export class AccountingUpdate extends React.Component<IAccountingUpdateProps, IAccountingUpdateState> {
  constructor(props) {
    super(props);
    this.state = {
      hospitalId: '0',
      registrationId: '0',
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

    this.props.getHospitals();
    this.props.getRegistrations();
  }

  saveEntity = (event, errors, values) => {
    values.transactionTime = new Date(values.transactionTime);

    if (errors.length === 0) {
      const { accountingEntity } = this.props;
      const entity = {
        ...accountingEntity,
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
    this.props.history.push('/entity/accounting');
  };

  render() {
    const { accountingEntity, hospitals, registrations, loading, updating } = this.props;
    const { isNew } = this.state;

    return (
      <div>
        <Row className="justify-content-center">
          <Col md="8">
            <h2 id="totoroApp.accounting.home.createOrEditLabel">
              <Translate contentKey="totoroApp.accounting.home.createOrEditLabel">Create or edit a Accounting</Translate>
            </h2>
          </Col>
        </Row>
        <Row className="justify-content-center">
          <Col md="8">
            {loading ? (
              <p>Loading...</p>
            ) : (
              <AvForm model={isNew ? {} : accountingEntity} onSubmit={this.saveEntity}>
                {!isNew ? (
                  <AvGroup>
                    <Label for="id">
                      <Translate contentKey="global.field.id">ID</Translate>
                    </Label>
                    <AvInput id="accounting-id" type="text" className="form-control" name="id" required readOnly />
                  </AvGroup>
                ) : null}
                <AvGroup>
                  <Label id="registrationFeeLabel" for="registrationFee">
                    <Translate contentKey="totoroApp.accounting.registrationFee">Registration Fee</Translate>
                  </Label>
                  <AvField
                    id="accounting-registrationFee"
                    type="string"
                    className="form-control"
                    name="registrationFee"
                    validate={{
                      required: { value: true, errorMessage: translate('entity.validation.required') },
                      number: { value: true, errorMessage: translate('entity.validation.number') }
                    }}
                  />
                </AvGroup>
                <AvGroup>
                  <Label id="partialBurdenLabel" for="partialBurden">
                    <Translate contentKey="totoroApp.accounting.partialBurden">Partial Burden</Translate>
                  </Label>
                  <AvField id="accounting-partialBurden" type="string" className="form-control" name="partialBurden" />
                </AvGroup>
                <AvGroup>
                  <Label id="depositLabel" for="deposit">
                    <Translate contentKey="totoroApp.accounting.deposit">Deposit</Translate>
                  </Label>
                  <AvField id="accounting-deposit" type="string" className="form-control" name="deposit" />
                </AvGroup>
                <AvGroup>
                  <Label id="ownExpenseLabel" for="ownExpense">
                    <Translate contentKey="totoroApp.accounting.ownExpense">Own Expense</Translate>
                  </Label>
                  <AvField id="accounting-ownExpense" type="string" className="form-control" name="ownExpense" />
                </AvGroup>
                <AvGroup>
                  <Label id="otherLabel" for="other">
                    <Translate contentKey="totoroApp.accounting.other">Other</Translate>
                  </Label>
                  <AvField id="accounting-other" type="string" className="form-control" name="other" />
                </AvGroup>
                <AvGroup>
                  <Label id="patientIdentityLabel" for="patientIdentity">
                    <Translate contentKey="totoroApp.accounting.patientIdentity">Patient Identity</Translate>
                  </Label>
                  <AvField id="accounting-patientIdentity" type="text" name="patientIdentity" />
                </AvGroup>
                <AvGroup>
                  <Label id="discountReasonLabel" for="discountReason">
                    <Translate contentKey="totoroApp.accounting.discountReason">Discount Reason</Translate>
                  </Label>
                  <AvField id="accounting-discountReason" type="text" name="discountReason" />
                </AvGroup>
                <AvGroup>
                  <Label id="discountLabel" for="discount">
                    <Translate contentKey="totoroApp.accounting.discount">Discount</Translate>
                  </Label>
                  <AvField id="accounting-discount" type="string" className="form-control" name="discount" />
                </AvGroup>
                <AvGroup>
                  <Label id="withdrawalLabel" for="withdrawal">
                    <Translate contentKey="totoroApp.accounting.withdrawal">Withdrawal</Translate>
                  </Label>
                  <AvField id="accounting-withdrawal" type="string" className="form-control" name="withdrawal" />
                </AvGroup>
                <AvGroup>
                  <Label id="transactionTimeLabel" for="transactionTime">
                    <Translate contentKey="totoroApp.accounting.transactionTime">Transaction Time</Translate>
                  </Label>
                  <AvInput
                    id="accounting-transactionTime"
                    type="datetime-local"
                    className="form-control"
                    name="transactionTime"
                    value={isNew ? null : convertDateTimeFromServer(this.props.accountingEntity.transactionTime)}
                  />
                </AvGroup>
                <AvGroup>
                  <Label id="staffLabel" for="staff">
                    <Translate contentKey="totoroApp.accounting.staff">Staff</Translate>
                  </Label>
                  <AvField id="accounting-staff" type="text" name="staff" />
                </AvGroup>
                <AvGroup>
                  <Label for="hospital.id">
                    <Translate contentKey="totoroApp.accounting.hospital">Hospital</Translate>
                  </Label>
                  <AvInput id="accounting-hospital" type="select" className="form-control" name="hospital.id">
                    <option value="" key="0" />
                    {hospitals
                      ? hospitals.map(otherEntity => (
                          <option value={otherEntity.id} key={otherEntity.id}>
                            {otherEntity.id}
                          </option>
                        ))
                      : null}
                  </AvInput>
                </AvGroup>
                <Button tag={Link} id="cancel-save" to="/entity/accounting" replace color="info">
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
  hospitals: storeState.hospital.entities,
  registrations: storeState.registration.entities,
  accountingEntity: storeState.accounting.entity,
  loading: storeState.accounting.loading,
  updating: storeState.accounting.updating,
  updateSuccess: storeState.accounting.updateSuccess
});

const mapDispatchToProps = {
  getHospitals,
  getRegistrations,
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
)(AccountingUpdate);
