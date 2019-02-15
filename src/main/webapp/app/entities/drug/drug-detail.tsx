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
              <span id="type">
                <Translate contentKey="totoroApp.drug.type">Type</Translate>
              </span>
            </dt>
            <dd>{drugEntity.type}</dd>
            <dt>
              <span id="validDate">
                <Translate contentKey="totoroApp.drug.validDate">Valid Date</Translate>
              </span>
            </dt>
            <dd>
              <TextFormat value={drugEntity.validDate} type="date" format={APP_LOCAL_DATE_FORMAT} />
            </dd>
            <dt>
              <span id="endDate">
                <Translate contentKey="totoroApp.drug.endDate">End Date</Translate>
              </span>
            </dt>
            <dd>
              <TextFormat value={drugEntity.endDate} type="date" format={APP_LOCAL_DATE_FORMAT} />
            </dd>
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
