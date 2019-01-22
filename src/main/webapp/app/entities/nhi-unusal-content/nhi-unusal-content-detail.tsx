import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
// tslint:disable-next-line:no-unused-variable
import { Translate, ICrudGetAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './nhi-unusal-content.reducer';
import { INHIUnusalContent } from 'app/shared/model/nhi-unusal-content.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface INHIUnusalContentDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export class NHIUnusalContentDetail extends React.Component<INHIUnusalContentDetailProps> {
  componentDidMount() {
    this.props.getEntity(this.props.match.params.id);
  }

  render() {
    const { nHIUnusalContentEntity } = this.props;
    return (
      <Row>
        <Col md="8">
          <h2>
            <Translate contentKey="totoroApp.nHIUnusalContent.detail.title">NHIUnusalContent</Translate> [<b>{nHIUnusalContentEntity.id}</b>
            ]
          </h2>
          <dl className="jh-entity-details">
            <dt>
              <span id="content">
                <Translate contentKey="totoroApp.nHIUnusalContent.content">Content</Translate>
              </span>
            </dt>
            <dd>{nHIUnusalContentEntity.content}</dd>
            <dt>
              <span id="noSeqNumber">
                <Translate contentKey="totoroApp.nHIUnusalContent.noSeqNumber">No Seq Number</Translate>
              </span>
            </dt>
            <dd>{nHIUnusalContentEntity.noSeqNumber}</dd>
            <dt>
              <span id="gotSeqNumber">
                <Translate contentKey="totoroApp.nHIUnusalContent.gotSeqNumber">Got Seq Number</Translate>
              </span>
            </dt>
            <dd>{nHIUnusalContentEntity.gotSeqNumber}</dd>
          </dl>
          <Button tag={Link} to="/entity/nhi-unusal-content" replace color="info">
            <FontAwesomeIcon icon="arrow-left" />{' '}
            <span className="d-none d-md-inline">
              <Translate contentKey="entity.action.back">Back</Translate>
            </span>
          </Button>
          &nbsp;
          <Button tag={Link} to={`/entity/nhi-unusal-content/${nHIUnusalContentEntity.id}/edit`} replace color="primary">
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

const mapStateToProps = ({ nHIUnusalContent }: IRootState) => ({
  nHIUnusalContentEntity: nHIUnusalContent.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(NHIUnusalContentDetail);
