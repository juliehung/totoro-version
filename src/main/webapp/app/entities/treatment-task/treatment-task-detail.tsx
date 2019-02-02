import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
// tslint:disable-next-line:no-unused-variable
import { Translate, ICrudGetAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './treatment-task.reducer';
import { ITreatmentTask } from 'app/shared/model/treatment-task.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface ITreatmentTaskDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export class TreatmentTaskDetail extends React.Component<ITreatmentTaskDetailProps> {
  componentDidMount() {
    this.props.getEntity(this.props.match.params.id);
  }

  render() {
    const { treatmentTaskEntity } = this.props;
    return (
      <Row>
        <Col md="8">
          <h2>
            <Translate contentKey="totoroApp.treatmentTask.detail.title">TreatmentTask</Translate> [<b>{treatmentTaskEntity.id}</b>]
          </h2>
          <dl className="jh-entity-details">
            <dt>
              <span id="name">
                <Translate contentKey="totoroApp.treatmentTask.name">Name</Translate>
              </span>
            </dt>
            <dd>{treatmentTaskEntity.name}</dd>
            <dt>
              <span id="note">
                <Translate contentKey="totoroApp.treatmentTask.note">Note</Translate>
              </span>
            </dt>
            <dd>{treatmentTaskEntity.note}</dd>
            <dt>
              <Translate contentKey="totoroApp.treatmentTask.treatmentPlan">Treatment Plan</Translate>
            </dt>
            <dd>{treatmentTaskEntity.treatmentPlan ? treatmentTaskEntity.treatmentPlan.id : ''}</dd>
          </dl>
          <Button tag={Link} to="/entity/treatment-task" replace color="info">
            <FontAwesomeIcon icon="arrow-left" />{' '}
            <span className="d-none d-md-inline">
              <Translate contentKey="entity.action.back">Back</Translate>
            </span>
          </Button>
          &nbsp;
          <Button tag={Link} to={`/entity/treatment-task/${treatmentTaskEntity.id}/edit`} replace color="primary">
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

const mapStateToProps = ({ treatmentTask }: IRootState) => ({
  treatmentTaskEntity: treatmentTask.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(TreatmentTaskDetail);
