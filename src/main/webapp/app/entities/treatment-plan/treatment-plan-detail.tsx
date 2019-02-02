import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
// tslint:disable-next-line:no-unused-variable
import { Translate, ICrudGetAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './treatment-plan.reducer';
import { ITreatmentPlan } from 'app/shared/model/treatment-plan.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface ITreatmentPlanDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export class TreatmentPlanDetail extends React.Component<ITreatmentPlanDetailProps> {
  componentDidMount() {
    this.props.getEntity(this.props.match.params.id);
  }

  render() {
    const { treatmentPlanEntity } = this.props;
    return (
      <Row>
        <Col md="8">
          <h2>
            <Translate contentKey="totoroApp.treatmentPlan.detail.title">TreatmentPlan</Translate> [<b>{treatmentPlanEntity.id}</b>]
          </h2>
          <dl className="jh-entity-details">
            <dt>
              <span id="activated">
                <Translate contentKey="totoroApp.treatmentPlan.activated">Activated</Translate>
              </span>
            </dt>
            <dd>{treatmentPlanEntity.activated ? 'true' : 'false'}</dd>
            <dt>
              <Translate contentKey="totoroApp.treatmentPlan.treatment">Treatment</Translate>
            </dt>
            <dd>{treatmentPlanEntity.treatment ? treatmentPlanEntity.treatment.id : ''}</dd>
          </dl>
          <Button tag={Link} to="/entity/treatment-plan" replace color="info">
            <FontAwesomeIcon icon="arrow-left" />{' '}
            <span className="d-none d-md-inline">
              <Translate contentKey="entity.action.back">Back</Translate>
            </span>
          </Button>
          &nbsp;
          <Button tag={Link} to={`/entity/treatment-plan/${treatmentPlanEntity.id}/edit`} replace color="primary">
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

const mapStateToProps = ({ treatmentPlan }: IRootState) => ({
  treatmentPlanEntity: treatmentPlan.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(TreatmentPlanDetail);
