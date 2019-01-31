import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
// tslint:disable-next-line:no-unused-variable
import { Translate, ICrudGetAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './treatment.reducer';
import { ITreatment } from 'app/shared/model/treatment.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface ITreatmentDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export class TreatmentDetail extends React.Component<ITreatmentDetailProps> {
  componentDidMount() {
    this.props.getEntity(this.props.match.params.id);
  }

  render() {
    const { treatmentEntity } = this.props;
    return (
      <Row>
        <Col md="8">
          <h2>
            <Translate contentKey="totoroApp.treatment.detail.title">Treatment</Translate> [<b>{treatmentEntity.id}</b>]
          </h2>
          <dl className="jh-entity-details">
            <dt>
              <span id="name">
                <Translate contentKey="totoroApp.treatment.name">Name</Translate>
              </span>
            </dt>
            <dd>{treatmentEntity.name}</dd>
            <dt>
              <span id="chiefComplaint">
                <Translate contentKey="totoroApp.treatment.chiefComplaint">Chief Complaint</Translate>
              </span>
            </dt>
            <dd>{treatmentEntity.chiefComplaint}</dd>
            <dt>
              <span id="goal">
                <Translate contentKey="totoroApp.treatment.goal">Goal</Translate>
              </span>
            </dt>
            <dd>{treatmentEntity.goal}</dd>
            <dt>
              <span id="note">
                <Translate contentKey="totoroApp.treatment.note">Note</Translate>
              </span>
            </dt>
            <dd>{treatmentEntity.note}</dd>
            <dt>
              <span id="finding">
                <Translate contentKey="totoroApp.treatment.finding">Finding</Translate>
              </span>
            </dt>
            <dd>{treatmentEntity.finding}</dd>
            <dt>
              <Translate contentKey="totoroApp.treatment.patient">Patient</Translate>
            </dt>
            <dd>{treatmentEntity.patient ? treatmentEntity.patient.id : ''}</dd>
          </dl>
          <Button tag={Link} to="/entity/treatment" replace color="info">
            <FontAwesomeIcon icon="arrow-left" />{' '}
            <span className="d-none d-md-inline">
              <Translate contentKey="entity.action.back">Back</Translate>
            </span>
          </Button>
          &nbsp;
          <Button tag={Link} to={`/entity/treatment/${treatmentEntity.id}/edit`} replace color="primary">
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

const mapStateToProps = ({ treatment }: IRootState) => ({
  treatmentEntity: treatment.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(TreatmentDetail);
