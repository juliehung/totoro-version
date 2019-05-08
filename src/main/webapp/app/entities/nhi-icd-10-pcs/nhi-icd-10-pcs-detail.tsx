import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
// tslint:disable-next-line:no-unused-variable
import { Translate, ICrudGetAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './nhi-icd-10-pcs.reducer';
import { INhiIcd10Pcs } from 'app/shared/model/nhi-icd-10-pcs.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface INhiIcd10PcsDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export class NhiIcd10PcsDetail extends React.Component<INhiIcd10PcsDetailProps> {
  componentDidMount() {
    this.props.getEntity(this.props.match.params.id);
  }

  render() {
    const { nhiIcd10PcsEntity } = this.props;
    return (
      <Row>
        <Col md="8">
          <h2>
            <Translate contentKey="totoroApp.nhiIcd10Pcs.detail.title">NhiIcd10Pcs</Translate> [<b>{nhiIcd10PcsEntity.id}</b>]
          </h2>
          <dl className="jh-entity-details">
            <dt>
              <span id="code">
                <Translate contentKey="totoroApp.nhiIcd10Pcs.code">Code</Translate>
              </span>
            </dt>
            <dd>{nhiIcd10PcsEntity.code}</dd>
            <dt>
              <span id="nhiName">
                <Translate contentKey="totoroApp.nhiIcd10Pcs.nhiName">Nhi Name</Translate>
              </span>
            </dt>
            <dd>{nhiIcd10PcsEntity.nhiName}</dd>
            <dt>
              <span id="englishName">
                <Translate contentKey="totoroApp.nhiIcd10Pcs.englishName">English Name</Translate>
              </span>
            </dt>
            <dd>{nhiIcd10PcsEntity.englishName}</dd>
            <dt>
              <span id="chineseName">
                <Translate contentKey="totoroApp.nhiIcd10Pcs.chineseName">Chinese Name</Translate>
              </span>
            </dt>
            <dd>{nhiIcd10PcsEntity.chineseName}</dd>
            <dt>
              <Translate contentKey="totoroApp.nhiIcd10Pcs.nhiProcedure">Nhi Procedure</Translate>
            </dt>
            <dd>{nhiIcd10PcsEntity.nhiProcedure ? nhiIcd10PcsEntity.nhiProcedure.id : ''}</dd>
          </dl>
          <Button tag={Link} to="/entity/nhi-icd-10-pcs" replace color="info">
            <FontAwesomeIcon icon="arrow-left" />{' '}
            <span className="d-none d-md-inline">
              <Translate contentKey="entity.action.back">Back</Translate>
            </span>
          </Button>
          &nbsp;
          <Button tag={Link} to={`/entity/nhi-icd-10-pcs/${nhiIcd10PcsEntity.id}/edit`} replace color="primary">
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

const mapStateToProps = ({ nhiIcd10Pcs }: IRootState) => ({
  nhiIcd10PcsEntity: nhiIcd10Pcs.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(NhiIcd10PcsDetail);
