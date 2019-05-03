import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
// tslint:disable-next-line:no-unused-variable
import { Translate, ICrudGetAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './nhi-procedure.reducer';
import { INHIProcedure } from 'app/shared/model/nhi-procedure.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface INHIProcedureDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export class NHIProcedureDetail extends React.Component<INHIProcedureDetailProps> {
  componentDidMount() {
    this.props.getEntity(this.props.match.params.id);
  }

  render() {
    const { nHIProcedureEntity } = this.props;
    return (
      <Row>
        <Col md="8">
          <h2>
            <Translate contentKey="totoroApp.nHIProcedure.detail.title">NHIProcedure</Translate> [<b>{nHIProcedureEntity.id}</b>]
          </h2>
          <dl className="jh-entity-details">
            <dt>
              <span id="code">
                <Translate contentKey="totoroApp.nHIProcedure.code">Code</Translate>
              </span>
            </dt>
            <dd>{nHIProcedureEntity.code}</dd>
            <dt>
              <span id="name">
                <Translate contentKey="totoroApp.nHIProcedure.name">Name</Translate>
              </span>
            </dt>
            <dd>{nHIProcedureEntity.name}</dd>
            <dt>
              <span id="point">
                <Translate contentKey="totoroApp.nHIProcedure.point">Point</Translate>
              </span>
            </dt>
            <dd>{nHIProcedureEntity.point}</dd>
            <dt>
              <span id="englishName">
                <Translate contentKey="totoroApp.nHIProcedure.englishName">English Name</Translate>
              </span>
            </dt>
            <dd>{nHIProcedureEntity.englishName}</dd>
            <dt>
              <Translate contentKey="totoroApp.nHIProcedure.nhiProcedureType">Nhi Procedure Type</Translate>
            </dt>
            <dd>{nHIProcedureEntity.nhiProcedureType ? nHIProcedureEntity.nhiProcedureType.id : ''}</dd>
          </dl>
          <Button tag={Link} to="/entity/nhi-procedure" replace color="info">
            <FontAwesomeIcon icon="arrow-left" />{' '}
            <span className="d-none d-md-inline">
              <Translate contentKey="entity.action.back">Back</Translate>
            </span>
          </Button>
          &nbsp;
          <Button tag={Link} to={`/entity/nhi-procedure/${nHIProcedureEntity.id}/edit`} replace color="primary">
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

const mapStateToProps = ({ nHIProcedure }: IRootState) => ({
  nHIProcedureEntity: nHIProcedure.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(NHIProcedureDetail);
