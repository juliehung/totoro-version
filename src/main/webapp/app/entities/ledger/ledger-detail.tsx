import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
// tslint:disable-next-line:no-unused-variable
import { Translate, ICrudGetAction, TextFormat } from 'react-jhipster';
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
              <span id="gid">
                <Translate contentKey="totoroApp.ledger.gid">Gid</Translate>
              </span>
            </dt>
            <dd>{ledgerEntity.gid}</dd>
            <dt>
              <span id="displayName">
                <Translate contentKey="totoroApp.ledger.displayName">Display Name</Translate>
              </span>
            </dt>
            <dd>{ledgerEntity.displayName}</dd>
            <dt>
              <span id="patientId">
                <Translate contentKey="totoroApp.ledger.patientId">Patient Id</Translate>
              </span>
            </dt>
            <dd>{ledgerEntity.patientId}</dd>
            <dt>
              <span id="type">
                <Translate contentKey="totoroApp.ledger.type">Type</Translate>
              </span>
            </dt>
            <dd>{ledgerEntity.type}</dd>
            <dt>
              <span id="project_code">
                <Translate contentKey="totoroApp.ledger.project_code">Project Code</Translate>
              </span>
            </dt>
            <dd>{ledgerEntity.project_code}</dd>
            <dt>
              <span id="date">
                <Translate contentKey="totoroApp.ledger.date">Date</Translate>
              </span>
            </dt>
            <dd>
              <TextFormat value={ledgerEntity.date} type="date" format={APP_DATE_FORMAT} />
            </dd>
            <dt>
              <span id="createdDate">
                <Translate contentKey="totoroApp.ledger.createdDate">Created Date</Translate>
              </span>
            </dt>
            <dd>
              <TextFormat value={ledgerEntity.createdDate} type="date" format={APP_DATE_FORMAT} />
            </dd>
            <dt>
              <span id="createdBy">
                <Translate contentKey="totoroApp.ledger.createdBy">Created By</Translate>
              </span>
            </dt>
            <dd>{ledgerEntity.createdBy}</dd>
            <dt>
              <span id="lastModifiedDate">
                <Translate contentKey="totoroApp.ledger.lastModifiedDate">Last Modified Date</Translate>
              </span>
            </dt>
            <dd>
              <TextFormat value={ledgerEntity.lastModifiedDate} type="date" format={APP_DATE_FORMAT} />
            </dd>
            <dt>
              <span id="lastModifiedBy">
                <Translate contentKey="totoroApp.ledger.lastModifiedBy">Last Modified By</Translate>
              </span>
            </dt>
            <dd>{ledgerEntity.lastModifiedBy}</dd>
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
