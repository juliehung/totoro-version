import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
// tslint:disable-next-line:no-unused-variable
import { Translate, ICrudGetAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './procedure.reducer';
import { IProcedure } from 'app/shared/model/procedure.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IProcedureDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export class ProcedureDetail extends React.Component<IProcedureDetailProps> {
  componentDidMount() {
    this.props.getEntity(this.props.match.params.id);
  }

  render() {
    const { procedureEntity } = this.props;
    return (
      <Row>
        <Col md="8">
          <h2>
            <Translate contentKey="totoroApp.procedure.detail.title">Procedure</Translate> [<b>{procedureEntity.id}</b>]
          </h2>
          <dl className="jh-entity-details">
            <dt>
              <span id="content">
                <Translate contentKey="totoroApp.procedure.content">Content</Translate>
              </span>
            </dt>
            <dd>{procedureEntity.content}</dd>
            <dt>
              <span id="price">
                <Translate contentKey="totoroApp.procedure.price">Price</Translate>
              </span>
            </dt>
            <dd>{procedureEntity.price}</dd>
            <dt>
              <Translate contentKey="totoroApp.procedure.procedureType">Procedure Type</Translate>
            </dt>
            <dd>{procedureEntity.procedureType ? procedureEntity.procedureType.id : ''}</dd>
          </dl>
          <Button tag={Link} to="/entity/procedure" replace color="info">
            <FontAwesomeIcon icon="arrow-left" />{' '}
            <span className="d-none d-md-inline">
              <Translate contentKey="entity.action.back">Back</Translate>
            </span>
          </Button>
          &nbsp;
          <Button tag={Link} to={`/entity/procedure/${procedureEntity.id}/edit`} replace color="primary">
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

const mapStateToProps = ({ procedure }: IRootState) => ({
  procedureEntity: procedure.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(ProcedureDetail);
