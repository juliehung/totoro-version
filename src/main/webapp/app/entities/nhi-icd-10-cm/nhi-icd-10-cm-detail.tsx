import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
// tslint:disable-next-line:no-unused-variable
import { Translate, ICrudGetAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './nhi-icd-10-cm.reducer';
import { INhiIcd10Cm } from 'app/shared/model/nhi-icd-10-cm.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface INhiIcd10CmDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export class NhiIcd10CmDetail extends React.Component<INhiIcd10CmDetailProps> {
  componentDidMount() {
    this.props.getEntity(this.props.match.params.id);
  }

  render() {
    const { nhiIcd10CmEntity } = this.props;
    return (
      <Row>
        <Col md="8">
          <h2>
            <Translate contentKey="totoroApp.nhiIcd10Cm.detail.title">NhiIcd10Cm</Translate> [<b>{nhiIcd10CmEntity.id}</b>]
          </h2>
          <dl className="jh-entity-details">
            <dt>
              <span id="code">
                <Translate contentKey="totoroApp.nhiIcd10Cm.code">Code</Translate>
              </span>
            </dt>
            <dd>{nhiIcd10CmEntity.code}</dd>
            <dt>
              <span id="name">
                <Translate contentKey="totoroApp.nhiIcd10Cm.name">Name</Translate>
              </span>
            </dt>
            <dd>{nhiIcd10CmEntity.name}</dd>
            <dt>
              <span id="englishName">
                <Translate contentKey="totoroApp.nhiIcd10Cm.englishName">English Name</Translate>
              </span>
            </dt>
            <dd>{nhiIcd10CmEntity.englishName}</dd>
            <dt>
              <Translate contentKey="totoroApp.nhiIcd10Cm.nhiIcd9Cm">Nhi Icd 9 Cm</Translate>
            </dt>
            <dd>{nhiIcd10CmEntity.nhiIcd9Cm ? nhiIcd10CmEntity.nhiIcd9Cm.id : ''}</dd>
          </dl>
          <Button tag={Link} to="/entity/nhi-icd-10-cm" replace color="info">
            <FontAwesomeIcon icon="arrow-left" />{' '}
            <span className="d-none d-md-inline">
              <Translate contentKey="entity.action.back">Back</Translate>
            </span>
          </Button>
          &nbsp;
          <Button tag={Link} to={`/entity/nhi-icd-10-cm/${nhiIcd10CmEntity.id}/edit`} replace color="primary">
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

const mapStateToProps = ({ nhiIcd10Cm }: IRootState) => ({
  nhiIcd10CmEntity: nhiIcd10Cm.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(NhiIcd10CmDetail);
