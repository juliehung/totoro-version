import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
// tslint:disable-next-line:no-unused-variable
import { Translate, ICrudGetAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './ledger.reducer';
import { ILedger } from 'app/shared/model/ledger.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface ILedgerDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export class LedgerDetail extends React.Component<ILedgerDetailProps> {
  componentDidMount() {
    this.props.getEntity(this.props.match.params.id);
  }

  render() {
    const { ledgerEntity } = this.props;
    return (
      <Row>
        <Col md="8">
          <h2>
            <Translate contentKey="totoroApp.ledger.detail.title">Ledger</Translate> [<b>{ledgerEntity.id}</b>]
          </h2>
          <dl className="jh-entity-details">
            <dt>
              <span id="amount">
                <Translate contentKey="totoroApp.ledger.amount">Amount</Translate>
              </span>
            </dt>
            <dd>{ledgerEntity.amount}</dd>
            <dt>
              <span id="charge">
                <Translate contentKey="totoroApp.ledger.charge">Charge</Translate>
              </span>
            </dt>
            <dd>{ledgerEntity.charge}</dd>
            <dt>
              <span id="arrears">
                <Translate contentKey="totoroApp.ledger.arrears">Arrears</Translate>
              </span>
            </dt>
            <dd>{ledgerEntity.arrears}</dd>
            <dt>
              <span id="note">
                <Translate contentKey="totoroApp.ledger.note">Note</Translate>
              </span>
            </dt>
            <dd>{ledgerEntity.note}</dd>
            <dt>
              <span id="doctor">
                <Translate contentKey="totoroApp.ledger.doctor">Doctor</Translate>
              </span>
            </dt>
            <dd>{ledgerEntity.doctor}</dd>
            <dt>
              <Translate contentKey="totoroApp.ledger.treatmentPlan">Treatment Plan</Translate>
            </dt>
            <dd>{ledgerEntity.treatmentPlan ? ledgerEntity.treatmentPlan.id : ''}</dd>
          </dl>
          <Button tag={Link} to="/entity/ledger" replace color="info">
            <FontAwesomeIcon icon="arrow-left" />{' '}
            <span className="d-none d-md-inline">
              <Translate contentKey="entity.action.back">Back</Translate>
            </span>
          </Button>
          &nbsp;
          <Button tag={Link} to={`/entity/ledger/${ledgerEntity.id}/edit`} replace color="primary">
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

const mapStateToProps = ({ ledger }: IRootState) => ({
  ledgerEntity: ledger.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(LedgerDetail);
