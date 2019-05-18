import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
// tslint:disable-next-line:no-unused-variable
import { Translate, ICrudGetAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './nhi-procedure.reducer';
import { INhiProcedure } from 'app/shared/model/nhi-procedure.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface INhiProcedureDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export class NhiProcedureDetail extends React.Component<INhiProcedureDetailProps> {
  componentDidMount() {
    this.props.getEntity(this.props.match.params.id);
  }

  render() {
    const { nhiProcedureEntity } = this.props;
    return (
      <Row>
        <Col md="8">
          <h2>
            <Translate contentKey="totoroApp.nhiProcedure.detail.title">NhiProcedure</Translate> [<b>{nhiProcedureEntity.id}</b>]
          </h2>
          <dl className="jh-entity-details">
            <dt>
              <span id="code">
                <Translate contentKey="totoroApp.nhiProcedure.code">Code</Translate>
              </span>
            </dt>
            <dd>{nhiProcedureEntity.code}</dd>
            <dt>
              <span id="name">
                <Translate contentKey="totoroApp.nhiProcedure.name">Name</Translate>
              </span>
            </dt>
            <dd>{nhiProcedureEntity.name}</dd>
            <dt>
              <span id="point">
                <Translate contentKey="totoroApp.nhiProcedure.point">Point</Translate>
              </span>
            </dt>
            <dd>{nhiProcedureEntity.point}</dd>
            <dt>
              <span id="englishName">
                <Translate contentKey="totoroApp.nhiProcedure.englishName">English Name</Translate>
              </span>
            </dt>
            <dd>{nhiProcedureEntity.englishName}</dd>
            <dt>
              <span id="defaultIcd10CmId">
                <Translate contentKey="totoroApp.nhiProcedure.defaultIcd10CmId">Default Icd 10 Cm Id</Translate>
              </span>
            </dt>
            <dd>{nhiProcedureEntity.defaultIcd10CmId}</dd>
            <dt>
              <span id="description">
                <Translate contentKey="totoroApp.nhiProcedure.description">Description</Translate>
              </span>
            </dt>
            <dd>{nhiProcedureEntity.description}</dd>
            <dt>
              <span id="exclude">
                <Translate contentKey="totoroApp.nhiProcedure.exclude">Exclude</Translate>
              </span>
            </dt>
            <dd>{nhiProcedureEntity.exclude}</dd>
            <dt>
              <Translate contentKey="totoroApp.nhiProcedure.nhiProcedureType">Nhi Procedure Type</Translate>
            </dt>
            <dd>{nhiProcedureEntity.nhiProcedureType ? nhiProcedureEntity.nhiProcedureType.id : ''}</dd>
            <dt>
              <Translate contentKey="totoroApp.nhiProcedure.nhiIcd9Cm">Nhi Icd 9 Cm</Translate>
            </dt>
            <dd>{nhiProcedureEntity.nhiIcd9Cm ? nhiProcedureEntity.nhiIcd9Cm.id : ''}</dd>
          </dl>
          <Button tag={Link} to="/entity/nhi-procedure" replace color="info">
            <FontAwesomeIcon icon="arrow-left" />{' '}
            <span className="d-none d-md-inline">
              <Translate contentKey="entity.action.back">Back</Translate>
            </span>
          </Button>
          &nbsp;
          <Button tag={Link} to={`/entity/nhi-procedure/${nhiProcedureEntity.id}/edit`} replace color="primary">
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

const mapStateToProps = ({ nhiProcedure }: IRootState) => ({
  nhiProcedureEntity: nhiProcedure.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(NhiProcedureDetail);
