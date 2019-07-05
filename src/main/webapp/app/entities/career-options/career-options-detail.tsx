import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
// tslint:disable-next-line:no-unused-variable
import { Translate, ICrudGetAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './career-options.reducer';
import { ICareerOptions } from 'app/shared/model/career-options.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface ICareerOptionsDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export class CareerOptionsDetail extends React.Component<ICareerOptionsDetailProps> {
  componentDidMount() {
    this.props.getEntity(this.props.match.params.id);
  }

  render() {
    const { careerOptionsEntity } = this.props;
    return (
      <Row>
        <Col md="8">
          <h2>
            <Translate contentKey="totoroApp.careerOptions.detail.title">CareerOptions</Translate> [<b>{careerOptionsEntity.id}</b>]
          </h2>
          <dl className="jh-entity-details">
            <dt>
              <span id="code">
                <Translate contentKey="totoroApp.careerOptions.code">Code</Translate>
              </span>
            </dt>
            <dd>{careerOptionsEntity.code}</dd>
            <dt>
              <span id="name">
                <Translate contentKey="totoroApp.careerOptions.name">Name</Translate>
              </span>
            </dt>
            <dd>{careerOptionsEntity.name}</dd>
            <dt>
              <span id="order">
                <Translate contentKey="totoroApp.careerOptions.order">Order</Translate>
              </span>
            </dt>
            <dd>{careerOptionsEntity.order}</dd>
          </dl>
          <Button tag={Link} to="/entity/career-options" replace color="info">
            <FontAwesomeIcon icon="arrow-left" />{' '}
            <span className="d-none d-md-inline">
              <Translate contentKey="entity.action.back">Back</Translate>
            </span>
          </Button>
          &nbsp;
          <Button tag={Link} to={`/entity/career-options/${careerOptionsEntity.id}/edit`} replace color="primary">
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

const mapStateToProps = ({ careerOptions }: IRootState) => ({
  careerOptionsEntity: careerOptions.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(CareerOptionsDetail);
