import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
// tslint:disable-next-line:no-unused-variable
import { Translate, ICrudGetAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './relationship-options.reducer';
import { IRelationshipOptions } from 'app/shared/model/relationship-options.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IRelationshipOptionsDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export class RelationshipOptionsDetail extends React.Component<IRelationshipOptionsDetailProps> {
  componentDidMount() {
    this.props.getEntity(this.props.match.params.id);
  }

  render() {
    const { relationshipOptionsEntity } = this.props;
    return (
      <Row>
        <Col md="8">
          <h2>
            <Translate contentKey="totoroApp.relationshipOptions.detail.title">RelationshipOptions</Translate> [
            <b>{relationshipOptionsEntity.id}</b>]
          </h2>
          <dl className="jh-entity-details">
            <dt>
              <span id="code">
                <Translate contentKey="totoroApp.relationshipOptions.code">Code</Translate>
              </span>
            </dt>
            <dd>{relationshipOptionsEntity.code}</dd>
            <dt>
              <span id="name">
                <Translate contentKey="totoroApp.relationshipOptions.name">Name</Translate>
              </span>
            </dt>
            <dd>{relationshipOptionsEntity.name}</dd>
            <dt>
              <span id="order">
                <Translate contentKey="totoroApp.relationshipOptions.order">Order</Translate>
              </span>
            </dt>
            <dd>{relationshipOptionsEntity.order}</dd>
          </dl>
          <Button tag={Link} to="/entity/relationship-options" replace color="info">
            <FontAwesomeIcon icon="arrow-left" />{' '}
            <span className="d-none d-md-inline">
              <Translate contentKey="entity.action.back">Back</Translate>
            </span>
          </Button>
          &nbsp;
          <Button tag={Link} to={`/entity/relationship-options/${relationshipOptionsEntity.id}/edit`} replace color="primary">
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

const mapStateToProps = ({ relationshipOptions }: IRootState) => ({
  relationshipOptionsEntity: relationshipOptions.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(RelationshipOptionsDetail);
