import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
// tslint:disable-next-line:no-unused-variable
import { Translate, ICrudGetAction, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './nhi-unusal-incident.reducer';
import { INHIUnusalIncident } from 'app/shared/model/nhi-unusal-incident.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface INHIUnusalIncidentDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export class NHIUnusalIncidentDetail extends React.Component<INHIUnusalIncidentDetailProps> {
  componentDidMount() {
    this.props.getEntity(this.props.match.params.id);
  }

  render() {
    const { nHIUnusalIncidentEntity } = this.props;
    return (
      <Row>
        <Col md="8">
          <h2>
            <Translate contentKey="totoroApp.nHIUnusalIncident.detail.title">NHIUnusalIncident</Translate> [
            <b>{nHIUnusalIncidentEntity.id}</b>]
          </h2>
          <dl className="jh-entity-details">
            <dt>
              <span id="start">
                <Translate contentKey="totoroApp.nHIUnusalIncident.start">Start</Translate>
              </span>
            </dt>
            <dd>
              <TextFormat value={nHIUnusalIncidentEntity.start} type="date" format={APP_DATE_FORMAT} />
            </dd>
            <dt>
              <span id="end">
                <Translate contentKey="totoroApp.nHIUnusalIncident.end">End</Translate>
              </span>
            </dt>
            <dd>
              <TextFormat value={nHIUnusalIncidentEntity.end} type="date" format={APP_DATE_FORMAT} />
            </dd>
            <dt>
              <Translate contentKey="totoroApp.nHIUnusalIncident.nhiUnusalContent">Nhi Unusal Content</Translate>
            </dt>
            <dd>{nHIUnusalIncidentEntity.nhiUnusalContent ? nHIUnusalIncidentEntity.nhiUnusalContent.id : ''}</dd>
          </dl>
          <Button tag={Link} to="/entity/nhi-unusal-incident" replace color="info">
            <FontAwesomeIcon icon="arrow-left" />{' '}
            <span className="d-none d-md-inline">
              <Translate contentKey="entity.action.back">Back</Translate>
            </span>
          </Button>
          &nbsp;
          <Button tag={Link} to={`/entity/nhi-unusal-incident/${nHIUnusalIncidentEntity.id}/edit`} replace color="primary">
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

const mapStateToProps = ({ nHIUnusalIncident }: IRootState) => ({
  nHIUnusalIncidentEntity: nHIUnusalIncident.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(NHIUnusalIncidentDetail);
