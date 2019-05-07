import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
// tslint:disable-next-line:no-unused-variable
import { Translate, ICrudGetAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './nhi-icd-9-cm.reducer';
import { INhiIcd9Cm } from 'app/shared/model/nhi-icd-9-cm.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface INhiIcd9CmDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export class NhiIcd9CmDetail extends React.Component<INhiIcd9CmDetailProps> {
  componentDidMount() {
    this.props.getEntity(this.props.match.params.id);
  }

  render() {
    const { nhiIcd9CmEntity } = this.props;
    return (
      <Row>
        <Col md="8">
          <h2>
            <Translate contentKey="totoroApp.nhiIcd9Cm.detail.title">NhiIcd9Cm</Translate> [<b>{nhiIcd9CmEntity.id}</b>]
          </h2>
          <dl className="jh-entity-details">
            <dt>
              <span id="code">
                <Translate contentKey="totoroApp.nhiIcd9Cm.code">Code</Translate>
              </span>
            </dt>
            <dd>{nhiIcd9CmEntity.code}</dd>
            <dt>
              <span id="name">
                <Translate contentKey="totoroApp.nhiIcd9Cm.name">Name</Translate>
              </span>
            </dt>
            <dd>{nhiIcd9CmEntity.name}</dd>
            <dt>
              <span id="englishName">
                <Translate contentKey="totoroApp.nhiIcd9Cm.englishName">English Name</Translate>
              </span>
            </dt>
            <dd>{nhiIcd9CmEntity.englishName}</dd>
          </dl>
          <Button tag={Link} to="/entity/nhi-icd-9-cm" replace color="info">
            <FontAwesomeIcon icon="arrow-left" />{' '}
            <span className="d-none d-md-inline">
              <Translate contentKey="entity.action.back">Back</Translate>
            </span>
          </Button>
          &nbsp;
          <Button tag={Link} to={`/entity/nhi-icd-9-cm/${nhiIcd9CmEntity.id}/edit`} replace color="primary">
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

const mapStateToProps = ({ nhiIcd9Cm }: IRootState) => ({
  nhiIcd9CmEntity: nhiIcd9Cm.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(NhiIcd9CmDetail);
