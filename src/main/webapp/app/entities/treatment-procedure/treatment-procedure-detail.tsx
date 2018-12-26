import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
// tslint:disable-next-line:no-unused-variable
import { Translate, ICrudGetAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './treatment-procedure.reducer';
import { ITreatmentProcedure } from 'app/shared/model/treatment-procedure.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface ITreatmentProcedureDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export class TreatmentProcedureDetail extends React.Component<ITreatmentProcedureDetailProps> {
  componentDidMount() {
    this.props.getEntity(this.props.match.params.id);
  }

  render() {
    const { treatmentProcedureEntity } = this.props;
    return (
      <Row>
        <Col md="8">
          <h2>
            <Translate contentKey="totoroApp.treatmentProcedure.detail.title">TreatmentProcedure</Translate> [
            <b>{treatmentProcedureEntity.id}</b>]
          </h2>
          <dl className="jh-entity-details">
            <dt>
              <span id="price">
                <Translate contentKey="totoroApp.treatmentProcedure.price">Price</Translate>
              </span>
            </dt>
            <dd>{treatmentProcedureEntity.price}</dd>
            <dt>
              <span id="teeth">
                <Translate contentKey="totoroApp.treatmentProcedure.teeth">Teeth</Translate>
              </span>
            </dt>
            <dd>{treatmentProcedureEntity.teeth}</dd>
            <dt>
              <span id="surfaces">
                <Translate contentKey="totoroApp.treatmentProcedure.surfaces">Surfaces</Translate>
              </span>
            </dt>
            <dd>{treatmentProcedureEntity.surfaces}</dd>
            <dt>
              <span id="nhiDeclared">
                <Translate contentKey="totoroApp.treatmentProcedure.nhiDeclared">Nhi Declared</Translate>
              </span>
            </dt>
            <dd>{treatmentProcedureEntity.nhiDeclared ? 'true' : 'false'}</dd>
            <dt>
              <Translate contentKey="totoroApp.treatmentProcedure.nhiProcedure">Nhi Procedure</Translate>
            </dt>
            <dd>{treatmentProcedureEntity.nhiProcedure ? treatmentProcedureEntity.nhiProcedure.id : ''}</dd>
            <dt>
              <Translate contentKey="totoroApp.treatmentProcedure.treatmentTask">Treatment Task</Translate>
            </dt>
            <dd>{treatmentProcedureEntity.treatmentTask ? treatmentProcedureEntity.treatmentTask.id : ''}</dd>
          </dl>
          <Button tag={Link} to="/entity/treatment-procedure" replace color="info">
            <FontAwesomeIcon icon="arrow-left" />{' '}
            <span className="d-none d-md-inline">
              <Translate contentKey="entity.action.back">Back</Translate>
            </span>
          </Button>
          &nbsp;
          <Button tag={Link} to={`/entity/treatment-procedure/${treatmentProcedureEntity.id}/edit`} replace color="primary">
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

const mapStateToProps = ({ treatmentProcedure }: IRootState) => ({
  treatmentProcedureEntity: treatmentProcedure.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(TreatmentProcedureDetail);
