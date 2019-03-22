import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
// tslint:disable-next-line:no-unused-variable
import { Translate, ICrudGetAction, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './registration-del.reducer';
import { IRegistrationDel } from 'app/shared/model/registration-del.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IRegistrationDelDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export class RegistrationDelDetail extends React.Component<IRegistrationDelDetailProps> {
  componentDidMount() {
    this.props.getEntity(this.props.match.params.id);
  }

  render() {
    const { registrationDelEntity } = this.props;
    return (
      <Row>
        <Col md="8">
          <h2>
            <Translate contentKey="totoroApp.registrationDel.detail.title">RegistrationDel</Translate> [<b>{registrationDelEntity.id}</b>]
          </h2>
          <dl className="jh-entity-details">
            <dt>
              <span id="status">
                <Translate contentKey="totoroApp.registrationDel.status">Status</Translate>
              </span>
            </dt>
            <dd>{registrationDelEntity.status}</dd>
            <dt>
              <span id="arrivalTime">
                <Translate contentKey="totoroApp.registrationDel.arrivalTime">Arrival Time</Translate>
              </span>
            </dt>
            <dd>
              <TextFormat value={registrationDelEntity.arrivalTime} type="date" format={APP_DATE_FORMAT} />
            </dd>
            <dt>
              <span id="type">
                <Translate contentKey="totoroApp.registrationDel.type">Type</Translate>
              </span>
            </dt>
            <dd>{registrationDelEntity.type}</dd>
            <dt>
              <span id="onSite">
                <Translate contentKey="totoroApp.registrationDel.onSite">On Site</Translate>
              </span>
            </dt>
            <dd>{registrationDelEntity.onSite ? 'true' : 'false'}</dd>
            <dt>
              <span id="appointmentId">
                <Translate contentKey="totoroApp.registrationDel.appointmentId">Appointment Id</Translate>
              </span>
            </dt>
            <dd>{registrationDelEntity.appointmentId}</dd>
            <dt>
              <span id="accountingId">
                <Translate contentKey="totoroApp.registrationDel.accountingId">Accounting Id</Translate>
              </span>
            </dt>
            <dd>{registrationDelEntity.accountingId}</dd>
          </dl>
          <Button tag={Link} to="/entity/registration-del" replace color="info">
            <FontAwesomeIcon icon="arrow-left" />{' '}
            <span className="d-none d-md-inline">
              <Translate contentKey="entity.action.back">Back</Translate>
            </span>
          </Button>
          &nbsp;
          <Button tag={Link} to={`/entity/registration-del/${registrationDelEntity.id}/edit`} replace color="primary">
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

const mapStateToProps = ({ registrationDel }: IRootState) => ({
  registrationDelEntity: registrationDel.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(RegistrationDelDetail);
