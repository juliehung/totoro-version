import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
// tslint:disable-next-line:no-unused-variable
import { Translate, ICrudGetAction, TextFormat } from 'react-jhipster';
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
              <span id="status">
                <Translate contentKey="totoroApp.treatmentProcedure.status">Status</Translate>
              </span>
            </dt>
            <dd>{treatmentProcedureEntity.status}</dd>
            <dt>
              <span id="quantity">
                <Translate contentKey="totoroApp.treatmentProcedure.quantity">Quantity</Translate>
              </span>
            </dt>
            <dd>{treatmentProcedureEntity.quantity}</dd>
            <dt>
              <span id="total">
                <Translate contentKey="totoroApp.treatmentProcedure.total">Total</Translate>
              </span>
            </dt>
            <dd>{treatmentProcedureEntity.total}</dd>
            <dt>
              <span id="note">
                <Translate contentKey="totoroApp.treatmentProcedure.note">Note</Translate>
              </span>
            </dt>
            <dd>{treatmentProcedureEntity.note}</dd>
            <dt>
              <span id="completedDate">
                <Translate contentKey="totoroApp.treatmentProcedure.completedDate">Completed Date</Translate>
              </span>
            </dt>
            <dd>
              <TextFormat value={treatmentProcedureEntity.completedDate} type="date" format={APP_DATE_FORMAT} />
            </dd>
            <dt>
              <Translate contentKey="totoroApp.treatmentProcedure.nhiProcedure">Nhi Procedure</Translate>
            </dt>
            <dd>{treatmentProcedureEntity.nhiProcedure ? treatmentProcedureEntity.nhiProcedure.id : ''}</dd>
            <dt>
              <Translate contentKey="totoroApp.treatmentProcedure.treatmentTask">Treatment Task</Translate>
            </dt>
            <dd>{treatmentProcedureEntity.treatmentTask ? treatmentProcedureEntity.treatmentTask.id : ''}</dd>
            <dt>
              <Translate contentKey="totoroApp.treatmentProcedure.procedure">Procedure</Translate>
            </dt>
            <dd>{treatmentProcedureEntity.procedure ? treatmentProcedureEntity.procedure.id : ''}</dd>
            <dt>
              <Translate contentKey="totoroApp.treatmentProcedure.appointment">Appointment</Translate>
            </dt>
            <dd>{treatmentProcedureEntity.appointment ? treatmentProcedureEntity.appointment.id : ''}</dd>
            <dt>
              <Translate contentKey="totoroApp.treatmentProcedure.registration">Registration</Translate>
            </dt>
            <dd>{treatmentProcedureEntity.registration ? treatmentProcedureEntity.registration.id : ''}</dd>
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
