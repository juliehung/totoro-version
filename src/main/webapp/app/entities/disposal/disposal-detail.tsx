import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
// tslint:disable-next-line:no-unused-variable
import { Translate, ICrudGetAction, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './disposal.reducer';
import { IDisposal } from 'app/shared/model/disposal.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IDisposalDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export class DisposalDetail extends React.Component<IDisposalDetailProps> {
  componentDidMount() {
    this.props.getEntity(this.props.match.params.id);
  }

  render() {
    const { disposalEntity } = this.props;
    return (
      <Row>
        <Col md="8">
          <h2>
            <Translate contentKey="totoroApp.disposal.detail.title">Disposal</Translate> [<b>{disposalEntity.id}</b>]
          </h2>
          <dl className="jh-entity-details">
            <dt>
              <span id="status">
                <Translate contentKey="totoroApp.disposal.status">Status</Translate>
              </span>
            </dt>
            <dd>{disposalEntity.status}</dd>
            <dt>
              <span id="total">
                <Translate contentKey="totoroApp.disposal.total">Total</Translate>
              </span>
            </dt>
            <dd>{disposalEntity.total}</dd>
            <dt>
              <span id="dateTime">
                <Translate contentKey="totoroApp.disposal.dateTime">Date Time</Translate>
              </span>
            </dt>
            <dd>
              <TextFormat value={disposalEntity.dateTime} type="date" format={APP_DATE_FORMAT} />
            </dd>
            <dt>
              <Translate contentKey="totoroApp.disposal.prescription">Prescription</Translate>
            </dt>
            <dd>{disposalEntity.prescription ? disposalEntity.prescription.id : ''}</dd>
            <dt>
              <Translate contentKey="totoroApp.disposal.todo">Todo</Translate>
            </dt>
            <dd>{disposalEntity.todo ? disposalEntity.todo.id : ''}</dd>
            <dt>
              <Translate contentKey="totoroApp.disposal.registration">Registration</Translate>
            </dt>
            <dd>{disposalEntity.registration ? disposalEntity.registration.id : ''}</dd>
          </dl>
          <Button tag={Link} to="/entity/disposal" replace color="info">
            <FontAwesomeIcon icon="arrow-left" />{' '}
            <span className="d-none d-md-inline">
              <Translate contentKey="entity.action.back">Back</Translate>
            </span>
          </Button>
          &nbsp;
          <Button tag={Link} to={`/entity/disposal/${disposalEntity.id}/edit`} replace color="primary">
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

const mapStateToProps = ({ disposal }: IRootState) => ({
  disposalEntity: disposal.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(DisposalDetail);
