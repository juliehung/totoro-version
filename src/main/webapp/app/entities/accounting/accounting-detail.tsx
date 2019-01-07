import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
// tslint:disable-next-line:no-unused-variable
import { Translate, ICrudGetAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './accounting.reducer';
import { IAccounting } from 'app/shared/model/accounting.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IAccountingDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export class AccountingDetail extends React.Component<IAccountingDetailProps> {
  componentDidMount() {
    this.props.getEntity(this.props.match.params.id);
  }

  render() {
    const { accountingEntity } = this.props;
    return (
      <Row>
        <Col md="8">
          <h2>
            <Translate contentKey="totoroApp.accounting.detail.title">Accounting</Translate> [<b>{accountingEntity.id}</b>]
          </h2>
          <dl className="jh-entity-details">
            <dt>
              <span id="registrationFee">
                <Translate contentKey="totoroApp.accounting.registrationFee">Registration Fee</Translate>
              </span>
            </dt>
            <dd>{accountingEntity.registrationFee}</dd>
            <dt>
              <span id="partialBurden">
                <Translate contentKey="totoroApp.accounting.partialBurden">Partial Burden</Translate>
              </span>
            </dt>
            <dd>{accountingEntity.partialBurden}</dd>
            <dt>
              <span id="burdenCost">
                <Translate contentKey="totoroApp.accounting.burdenCost">Burden Cost</Translate>
              </span>
            </dt>
            <dd>{accountingEntity.burdenCost}</dd>
            <dt>
              <span id="deposit">
                <Translate contentKey="totoroApp.accounting.deposit">Deposit</Translate>
              </span>
            </dt>
            <dd>{accountingEntity.deposit}</dd>
            <dt>
              <span id="ownExpense">
                <Translate contentKey="totoroApp.accounting.ownExpense">Own Expense</Translate>
              </span>
            </dt>
            <dd>{accountingEntity.ownExpense}</dd>
            <dt>
              <span id="other">
                <Translate contentKey="totoroApp.accounting.other">Other</Translate>
              </span>
            </dt>
            <dd>{accountingEntity.other}</dd>
            <dt>
              <span id="patientIdentity">
                <Translate contentKey="totoroApp.accounting.patientIdentity">Patient Identity</Translate>
              </span>
            </dt>
            <dd>{accountingEntity.patientIdentity}</dd>
            <dt>
              <span id="discountReason">
                <Translate contentKey="totoroApp.accounting.discountReason">Discount Reason</Translate>
              </span>
            </dt>
            <dd>{accountingEntity.discountReason}</dd>
            <dt>
              <span id="discount">
                <Translate contentKey="totoroApp.accounting.discount">Discount</Translate>
              </span>
            </dt>
            <dd>{accountingEntity.discount}</dd>
            <dt>
              <Translate contentKey="totoroApp.accounting.hospital">Hospital</Translate>
            </dt>
            <dd>{accountingEntity.hospital ? accountingEntity.hospital.id : ''}</dd>
          </dl>
          <Button tag={Link} to="/entity/accounting" replace color="info">
            <FontAwesomeIcon icon="arrow-left" />{' '}
            <span className="d-none d-md-inline">
              <Translate contentKey="entity.action.back">Back</Translate>
            </span>
          </Button>
          &nbsp;
          <Button tag={Link} to={`/entity/accounting/${accountingEntity.id}/edit`} replace color="primary">
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

const mapStateToProps = ({ accounting }: IRootState) => ({
  accountingEntity: accounting.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(AccountingDetail);
