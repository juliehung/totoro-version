import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
// tslint:disable-next-line:no-unused-variable
import { Translate, ICrudGetAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './marriage-options.reducer';
import { IMarriageOptions } from 'app/shared/model/marriage-options.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IMarriageOptionsDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export class MarriageOptionsDetail extends React.Component<IMarriageOptionsDetailProps> {
  componentDidMount() {
    this.props.getEntity(this.props.match.params.id);
  }

  render() {
    const { marriageOptionsEntity } = this.props;
    return (
      <Row>
        <Col md="8">
          <h2>
            <Translate contentKey="totoroApp.marriageOptions.detail.title">MarriageOptions</Translate> [<b>{marriageOptionsEntity.id}</b>]
          </h2>
          <dl className="jh-entity-details">
            <dt>
              <span id="code">
                <Translate contentKey="totoroApp.marriageOptions.code">Code</Translate>
              </span>
            </dt>
            <dd>{marriageOptionsEntity.code}</dd>
            <dt>
              <span id="name">
                <Translate contentKey="totoroApp.marriageOptions.name">Name</Translate>
              </span>
            </dt>
            <dd>{marriageOptionsEntity.name}</dd>
            <dt>
              <span id="order">
                <Translate contentKey="totoroApp.marriageOptions.order">Order</Translate>
              </span>
            </dt>
            <dd>{marriageOptionsEntity.order}</dd>
          </dl>
          <Button tag={Link} to="/entity/marriage-options" replace color="info">
            <FontAwesomeIcon icon="arrow-left" />{' '}
            <span className="d-none d-md-inline">
              <Translate contentKey="entity.action.back">Back</Translate>
            </span>
          </Button>
          &nbsp;
          <Button tag={Link} to={`/entity/marriage-options/${marriageOptionsEntity.id}/edit`} replace color="primary">
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

const mapStateToProps = ({ marriageOptions }: IRootState) => ({
  marriageOptionsEntity: marriageOptions.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(MarriageOptionsDetail);
