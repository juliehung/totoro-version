import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
// tslint:disable-next-line:no-unused-variable
import { Translate, ICrudGetAction, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './incident.reducer';
import { IIncident } from 'app/shared/model/incident.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IIncidentDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export class IncidentDetail extends React.Component<IIncidentDetailProps> {
  componentDidMount() {
    this.props.getEntity(this.props.match.params.id);
  }

  render() {
    const { incidentEntity } = this.props;
    return (
      <Row>
        <Col md="8">
          <h2>
            <Translate contentKey="totoroApp.incident.detail.title">Incident</Translate> [<b>{incidentEntity.id}</b>]
          </h2>
          <dl className="jh-entity-details">
            <dt>
              <span id="type">
                <Translate contentKey="totoroApp.incident.type">Type</Translate>
              </span>
            </dt>
            <dd>{incidentEntity.type}</dd>
            <dt>
              <span id="start">
                <Translate contentKey="totoroApp.incident.start">Start</Translate>
              </span>
            </dt>
            <dd>
              <TextFormat value={incidentEntity.start} type="date" format={APP_DATE_FORMAT} />
            </dd>
            <dt>
              <span id="end">
                <Translate contentKey="totoroApp.incident.end">End</Translate>
              </span>
            </dt>
            <dd>
              <TextFormat value={incidentEntity.end} type="date" format={APP_DATE_FORMAT} />
            </dd>
            <dt>
              <span id="content">
                <Translate contentKey="totoroApp.incident.content">Content</Translate>
              </span>
            </dt>
            <dd>{incidentEntity.content}</dd>
          </dl>
          <Button tag={Link} to="/entity/incident" replace color="info">
            <FontAwesomeIcon icon="arrow-left" />{' '}
            <span className="d-none d-md-inline">
              <Translate contentKey="entity.action.back">Back</Translate>
            </span>
          </Button>
          &nbsp;
          <Button tag={Link} to={`/entity/incident/${incidentEntity.id}/edit`} replace color="primary">
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

const mapStateToProps = ({ incident }: IRootState) => ({
  incidentEntity: incident.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(IncidentDetail);
