import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
// tslint:disable-next-line:no-unused-variable
import { Translate, ICrudGetAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './procedure-type.reducer';
import { IProcedureType } from 'app/shared/model/procedure-type.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IProcedureTypeDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export class ProcedureTypeDetail extends React.Component<IProcedureTypeDetailProps> {
  componentDidMount() {
    this.props.getEntity(this.props.match.params.id);
  }

  render() {
    const { procedureTypeEntity } = this.props;
    return (
      <Row>
        <Col md="8">
          <h2>
            <Translate contentKey="totoroApp.procedureType.detail.title">ProcedureType</Translate> [<b>{procedureTypeEntity.id}</b>]
          </h2>
          <dl className="jh-entity-details">
            <dt>
              <span id="major">
                <Translate contentKey="totoroApp.procedureType.major">Major</Translate>
              </span>
            </dt>
            <dd>{procedureTypeEntity.major}</dd>
            <dt>
              <span id="minor">
                <Translate contentKey="totoroApp.procedureType.minor">Minor</Translate>
              </span>
            </dt>
            <dd>{procedureTypeEntity.minor}</dd>
          </dl>
          <Button tag={Link} to="/entity/procedure-type" replace color="info">
            <FontAwesomeIcon icon="arrow-left" />{' '}
            <span className="d-none d-md-inline">
              <Translate contentKey="entity.action.back">Back</Translate>
            </span>
          </Button>
          &nbsp;
          <Button tag={Link} to={`/entity/procedure-type/${procedureTypeEntity.id}/edit`} replace color="primary">
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

const mapStateToProps = ({ procedureType }: IRootState) => ({
  procedureTypeEntity: procedureType.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(ProcedureTypeDetail);
