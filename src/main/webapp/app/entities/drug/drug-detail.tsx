import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
// tslint:disable-next-line:no-unused-variable
import { Translate, ICrudGetAction, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './drug.reducer';
import { IDrug } from 'app/shared/model/drug.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IDrugDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export class DrugDetail extends React.Component<IDrugDetailProps> {
  componentDidMount() {
    this.props.getEntity(this.props.match.params.id);
  }

  render() {
    const { drugEntity } = this.props;
    return (
      <Row>
        <Col md="8">
          <h2>
            <Translate contentKey="totoroApp.drug.detail.title">Drug</Translate> [<b>{drugEntity.id}</b>]
          </h2>
          <dl className="jh-entity-details">
            <dt>
              <span id="name">
                <Translate contentKey="totoroApp.drug.name">Name</Translate>
              </span>
            </dt>
            <dd>{drugEntity.name}</dd>
            <dt>
              <span id="chineseName">
                <Translate contentKey="totoroApp.drug.chineseName">Chinese Name</Translate>
              </span>
            </dt>
            <dd>{drugEntity.chineseName}</dd>
            <dt>
              <span id="unit">
                <Translate contentKey="totoroApp.drug.unit">Unit</Translate>
              </span>
            </dt>
            <dd>{drugEntity.unit}</dd>
            <dt>
              <span id="price">
                <Translate contentKey="totoroApp.drug.price">Price</Translate>
              </span>
            </dt>
            <dd>{drugEntity.price}</dd>
            <dt>
              <span id="quantity">
                <Translate contentKey="totoroApp.drug.quantity">Quantity</Translate>
              </span>
            </dt>
            <dd>{drugEntity.quantity}</dd>
            <dt>
              <span id="frequency">
                <Translate contentKey="totoroApp.drug.frequency">Frequency</Translate>
              </span>
            </dt>
            <dd>{drugEntity.frequency}</dd>
            <dt>
              <span id="way">
                <Translate contentKey="totoroApp.drug.way">Way</Translate>
              </span>
            </dt>
            <dd>{drugEntity.way}</dd>
            <dt>
              <span id="nhiCode">
                <Translate contentKey="totoroApp.drug.nhiCode">Nhi Code</Translate>
              </span>
            </dt>
            <dd>{drugEntity.nhiCode}</dd>
            <dt>
              <span id="warning">
                <Translate contentKey="totoroApp.drug.warning">Warning</Translate>
              </span>
            </dt>
            <dd>{drugEntity.warning}</dd>
            <dt>
              <span id="days">
                <Translate contentKey="totoroApp.drug.days">Days</Translate>
              </span>
            </dt>
            <dd>{drugEntity.days}</dd>
            <dt>
              <span id="order">
                <Translate contentKey="totoroApp.drug.order">Order</Translate>
              </span>
            </dt>
            <dd>{drugEntity.order}</dd>
            <dt>
              <span id="createdDate">
                <Translate contentKey="totoroApp.drug.createdDate">Created Date</Translate>
              </span>
            </dt>
            <dd>
              <TextFormat value={drugEntity.createdDate} type="date" format={APP_DATE_FORMAT} />
            </dd>
            <dt>
              <span id="createdBy">
                <Translate contentKey="totoroApp.drug.createdBy">Created By</Translate>
              </span>
            </dt>
            <dd>{drugEntity.createdBy}</dd>
            <dt>
              <span id="lastModifiedDate">
                <Translate contentKey="totoroApp.drug.lastModifiedDate">Last Modified Date</Translate>
              </span>
            </dt>
            <dd>
              <TextFormat value={drugEntity.lastModifiedDate} type="date" format={APP_DATE_FORMAT} />
            </dd>
            <dt>
              <span id="LastModifiedBy">
                <Translate contentKey="totoroApp.drug.LastModifiedBy">Last Modified By</Translate>
              </span>
            </dt>
            <dd>{drugEntity.LastModifiedBy}</dd>
          </dl>
          <Button tag={Link} to="/entity/drug" replace color="info">
            <FontAwesomeIcon icon="arrow-left" />{' '}
            <span className="d-none d-md-inline">
              <Translate contentKey="entity.action.back">Back</Translate>
            </span>
          </Button>
          &nbsp;
          <Button tag={Link} to={`/entity/drug/${drugEntity.id}/edit`} replace color="primary">
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

const mapStateToProps = ({ drug }: IRootState) => ({
  drugEntity: drug.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(DrugDetail);
