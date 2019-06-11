import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
// tslint:disable-next-line:no-unused-variable
import { Translate, translate, ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { getEntity, updateEntity, createEntity, reset } from './registration-del.reducer';
import { IRegistrationDel } from 'app/shared/model/registration-del.model';
// tslint:disable-next-line:no-unused-variable
import { convertDateTimeFromServer } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IRegistrationDelUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export interface IRegistrationDelUpdateState {
  isNew: boolean;
}

export class RegistrationDelUpdate extends React.Component<IRegistrationDelUpdateProps, IRegistrationDelUpdateState> {
  constructor(props) {
    super(props);
    this.state = {
      isNew: !this.props.match.params || !this.props.match.params.id
    };
  }

  componentWillUpdate(nextProps, nextState) {
    if (nextProps.updateSuccess !== this.props.updateSuccess && nextProps.updateSuccess) {
      this.handleClose();
    }
  }

  componentDidMount() {
    if (this.state.isNew) {
      this.props.reset();
    } else {
      this.props.getEntity(this.props.match.params.id);
    }
  }

  saveEntity = (event, errors, values) => {
    values.arrivalTime = new Date(values.arrivalTime);

    if (errors.length === 0) {
      const { registrationDelEntity } = this.props;
      const entity = {
        ...registrationDelEntity,
        ...values
      };

      if (this.state.isNew) {
        this.props.createEntity(entity);
      } else {
        this.props.updateEntity(entity);
      }
    }
  };

  handleClose = () => {
    this.props.history.push('/entity/registration-del');
  };

  render() {
    const { registrationDelEntity, loading, updating } = this.props;
    const { isNew } = this.state;

    return (
      <div>
        <Row className="justify-content-center">
          <Col md="8">
            <h2 id="totoroApp.registrationDel.home.createOrEditLabel">
              <Translate contentKey="totoroApp.registrationDel.home.createOrEditLabel">Create or edit a RegistrationDel</Translate>
            </h2>
          </Col>
        </Row>
        <Row className="justify-content-center">
          <Col md="8">
            {loading ? (
              <p>Loading...</p>
            ) : (
              <AvForm model={isNew ? {} : registrationDelEntity} onSubmit={this.saveEntity}>
                {!isNew ? (
                  <AvGroup>
                    <Label for="id">
                      <Translate contentKey="global.field.id">ID</Translate>
                    </Label>
                    <AvInput id="registration-del-id" type="text" className="form-control" name="id" required readOnly />
                  </AvGroup>
                ) : null}
                <AvGroup>
                  <Label id="statusLabel">
                    <Translate contentKey="totoroApp.registrationDel.status">Status</Translate>
                  </Label>
                  <AvInput
                    id="registration-del-status"
                    type="select"
                    className="form-control"
                    name="status"
                    value={(!isNew && registrationDelEntity.status) || 'PENDING'}
                  >
                    <option value="PENDING">
                      <Translate contentKey="totoroApp.RegistrationStatus.PENDING" />
                    </option>
                    <option value="FINISHED">
                      <Translate contentKey="totoroApp.RegistrationStatus.FINISHED" />
                    </option>
                    <option value="IN_PROGRESS">
                      <Translate contentKey="totoroApp.RegistrationStatus.IN_PROGRESS" />
                    </option>
                  </AvInput>
                </AvGroup>
                <AvGroup>
                  <Label id="arrivalTimeLabel" for="arrivalTime">
                    <Translate contentKey="totoroApp.registrationDel.arrivalTime">Arrival Time</Translate>
                  </Label>
                  <AvInput
                    id="registration-del-arrivalTime"
                    type="datetime-local"
                    className="form-control"
                    name="arrivalTime"
                    value={isNew ? null : convertDateTimeFromServer(this.props.registrationDelEntity.arrivalTime)}
                  />
                </AvGroup>
                <AvGroup>
                  <Label id="typeLabel" for="type">
                    <Translate contentKey="totoroApp.registrationDel.type">Type</Translate>
                  </Label>
                  <AvField id="registration-del-type" type="text" name="type" />
                </AvGroup>
                <AvGroup>
                  <Label id="onSiteLabel" check>
                    <AvInput id="registration-del-onSite" type="checkbox" className="form-control" name="onSite" />
                    <Translate contentKey="totoroApp.registrationDel.onSite">On Site</Translate>
                  </Label>
                </AvGroup>
                <AvGroup>
                  <Label id="noCardLabel" check>
                    <AvInput id="registration-del-noCard" type="checkbox" className="form-control" name="noCard" />
                    <Translate contentKey="totoroApp.registrationDel.noCard">No Card</Translate>
                  </Label>
                </AvGroup>
                <AvGroup>
                  <Label id="appointmentIdLabel" for="appointmentId">
                    <Translate contentKey="totoroApp.registrationDel.appointmentId">Appointment Id</Translate>
                  </Label>
                  <AvField id="registration-del-appointmentId" type="string" className="form-control" name="appointmentId" />
                </AvGroup>
                <AvGroup>
                  <Label id="accountingIdLabel" for="accountingId">
                    <Translate contentKey="totoroApp.registrationDel.accountingId">Accounting Id</Translate>
                  </Label>
                  <AvField id="registration-del-accountingId" type="string" className="form-control" name="accountingId" />
                </AvGroup>
                <Button tag={Link} id="cancel-save" to="/entity/registration-del" replace color="info">
                  <FontAwesomeIcon icon="arrow-left" />
                  &nbsp;
                  <span className="d-none d-md-inline">
                    <Translate contentKey="entity.action.back">Back</Translate>
                  </span>
                </Button>
                &nbsp;
                <Button color="primary" id="save-entity" type="submit" disabled={updating}>
                  <FontAwesomeIcon icon="save" />
                  &nbsp;
                  <Translate contentKey="entity.action.save">Save</Translate>
                </Button>
              </AvForm>
            )}
          </Col>
        </Row>
      </div>
    );
  }
}

const mapStateToProps = (storeState: IRootState) => ({
  registrationDelEntity: storeState.registrationDel.entity,
  loading: storeState.registrationDel.loading,
  updating: storeState.registrationDel.updating,
  updateSuccess: storeState.registrationDel.updateSuccess
});

const mapDispatchToProps = {
  getEntity,
  updateEntity,
  createEntity,
  reset
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(RegistrationDelUpdate);
