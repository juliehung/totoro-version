import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
// tslint:disable-next-line:no-unused-variable
import { Translate, ICrudGetAction, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './registration.reducer';
import { IRegistration } from 'app/shared/model/registration.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IRegistrationDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export class RegistrationDetail extends React.Component<IRegistrationDetailProps> {
  componentDidMount() {
    this.props.getEntity(this.props.match.params.id);
  }

  render() {
    const { registrationEntity } = this.props;
    return (
      <Row>
        <Col md="8">
          <h2>
            <Translate contentKey="totoroApp.registration.detail.title">Registration</Translate> [<b>{registrationEntity.id}</b>]
          </h2>
          <dl className="jh-entity-details">
            <dt>
              <span id="status">
                <Translate contentKey="totoroApp.registration.status">Status</Translate>
              </span>
            </dt>
            <dd>{registrationEntity.status}</dd>
            <dt>
              <span id="arrivalTime">
                <Translate contentKey="totoroApp.registration.arrivalTime">Arrival Time</Translate>
              </span>
            </dt>
            <dd>
              <TextFormat value={registrationEntity.arrivalTime} type="date" format={APP_DATE_FORMAT} />
            </dd>
            <dt>
              <span id="type">
                <Translate contentKey="totoroApp.registration.type">Type</Translate>
              </span>
            </dt>
            <dd>{registrationEntity.type}</dd>
            <dt>
              <span id="onSite">
                <Translate contentKey="totoroApp.registration.onSite">On Site</Translate>
              </span>
            </dt>
            <dd>{registrationEntity.onSite ? 'true' : 'false'}</dd>
            <dt>
              <span id="noCard">
                <Translate contentKey="totoroApp.registration.noCard">No Card</Translate>
              </span>
            </dt>
            <dd>{registrationEntity.noCard ? 'true' : 'false'}</dd>
            <dt>
              <Translate contentKey="totoroApp.registration.accounting">Accounting</Translate>
            </dt>
            <dd>{registrationEntity.accounting ? registrationEntity.accounting.id : ''}</dd>
          </dl>
          <Button tag={Link} to="/entity/registration" replace color="info">
            <FontAwesomeIcon icon="arrow-left" />{' '}
            <span className="d-none d-md-inline">
              <Translate contentKey="entity.action.back">Back</Translate>
            </span>
          </Button>
          &nbsp;
          <Button tag={Link} to={`/entity/registration/${registrationEntity.id}/edit`} replace color="primary">
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

const mapStateToProps = ({ registration }: IRootState) => ({
  registrationEntity: registration.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(RegistrationDetail);
