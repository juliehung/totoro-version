import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
// tslint:disable-next-line:no-unused-variable
import { Translate, ICrudGetAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './nhi-category.reducer';
import { INHICategory } from 'app/shared/model/nhi-category.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface INHICategoryDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export class NHICategoryDetail extends React.Component<INHICategoryDetailProps> {
  componentDidMount() {
    this.props.getEntity(this.props.match.params.id);
  }

  render() {
    const { nHICategoryEntity } = this.props;
    return (
      <Row>
        <Col md="8">
          <h2>
            <Translate contentKey="totoroApp.nHICategory.detail.title">NHICategory</Translate> [<b>{nHICategoryEntity.id}</b>]
          </h2>
          <dl className="jh-entity-details">
            <dt>
              <span id="name">
                <Translate contentKey="totoroApp.nHICategory.name">Name</Translate>
              </span>
            </dt>
            <dd>{nHICategoryEntity.name}</dd>
            <dt>
              <span id="codes">
                <Translate contentKey="totoroApp.nHICategory.codes">Codes</Translate>
              </span>
            </dt>
            <dd>{nHICategoryEntity.codes}</dd>
          </dl>
          <Button tag={Link} to="/entity/nhi-category" replace color="info">
            <FontAwesomeIcon icon="arrow-left" />{' '}
            <span className="d-none d-md-inline">
              <Translate contentKey="entity.action.back">Back</Translate>
            </span>
          </Button>
          &nbsp;
          <Button tag={Link} to={`/entity/nhi-category/${nHICategoryEntity.id}/edit`} replace color="primary">
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

const mapStateToProps = ({ nHICategory }: IRootState) => ({
  nHICategoryEntity: nHICategory.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(NHICategoryDetail);
