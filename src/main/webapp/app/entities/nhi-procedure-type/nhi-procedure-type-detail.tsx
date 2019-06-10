import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
// tslint:disable-next-line:no-unused-variable
import { Translate, ICrudGetAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './nhi-procedure-type.reducer';
import { INhiProcedureType } from 'app/shared/model/nhi-procedure-type.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface INhiProcedureTypeDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export class NhiProcedureTypeDetail extends React.Component<INhiProcedureTypeDetailProps> {
  componentDidMount() {
    this.props.getEntity(this.props.match.params.id);
  }

  render() {
    const { nhiProcedureTypeEntity } = this.props;
    return (
      <Row>
        <Col md="8">
          <h2>
            <Translate contentKey="totoroApp.nhiProcedureType.detail.title">NhiProcedureType</Translate> [<b>{nhiProcedureTypeEntity.id}</b>
            ]
          </h2>
          <dl className="jh-entity-details">
            <dt>
              <span id="major">
                <Translate contentKey="totoroApp.nhiProcedureType.major">Major</Translate>
              </span>
            </dt>
            <dd>{nhiProcedureTypeEntity.major}</dd>
            <dt>
              <span id="minor">
                <Translate contentKey="totoroApp.nhiProcedureType.minor">Minor</Translate>
              </span>
            </dt>
            <dd>{nhiProcedureTypeEntity.minor}</dd>
            <dt>
              <span id="name">
                <Translate contentKey="totoroApp.nhiProcedureType.name">Name</Translate>
              </span>
            </dt>
            <dd>{nhiProcedureTypeEntity.name}</dd>
          </dl>
          <Button tag={Link} to="/entity/nhi-procedure-type" replace color="info">
            <FontAwesomeIcon icon="arrow-left" />{' '}
            <span className="d-none d-md-inline">
              <Translate contentKey="entity.action.back">Back</Translate>
            </span>
          </Button>
          &nbsp;
          <Button tag={Link} to={`/entity/nhi-procedure-type/${nhiProcedureTypeEntity.id}/edit`} replace color="primary">
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

const mapStateToProps = ({ nhiProcedureType }: IRootState) => ({
  nhiProcedureTypeEntity: nhiProcedureType.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(NhiProcedureTypeDetail);
