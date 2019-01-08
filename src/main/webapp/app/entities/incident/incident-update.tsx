import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
// tslint:disable-next-line:no-unused-variable
import { Translate, translate, ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { getEntity, updateEntity, createEntity, reset } from './incident.reducer';
import { IIncident } from 'app/shared/model/incident.model';
// tslint:disable-next-line:no-unused-variable
import { convertDateTimeFromServer } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IIncidentUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export interface IIncidentUpdateState {
  isNew: boolean;
}

export class IncidentUpdate extends React.Component<IIncidentUpdateProps, IIncidentUpdateState> {
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
    values.start = new Date(values.start);
    values.end = new Date(values.end);

    if (errors.length === 0) {
      const { incidentEntity } = this.props;
      const entity = {
        ...incidentEntity,
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
    this.props.history.push('/entity/incident');
  };

  render() {
    const { incidentEntity, loading, updating } = this.props;
    const { isNew } = this.state;

    return (
      <div>
        <Row className="justify-content-center">
          <Col md="8">
            <h2 id="totoroApp.incident.home.createOrEditLabel">
              <Translate contentKey="totoroApp.incident.home.createOrEditLabel">Create or edit a Incident</Translate>
            </h2>
          </Col>
        </Row>
        <Row className="justify-content-center">
          <Col md="8">
            {loading ? (
              <p>Loading...</p>
            ) : (
              <AvForm model={isNew ? {} : incidentEntity} onSubmit={this.saveEntity}>
                {!isNew ? (
                  <AvGroup>
                    <Label for="id">
                      <Translate contentKey="global.field.id">ID</Translate>
                    </Label>
                    <AvInput id="incident-id" type="text" className="form-control" name="id" required readOnly />
                  </AvGroup>
                ) : null}
                <AvGroup>
                  <Label id="typeLabel">
                    <Translate contentKey="totoroApp.incident.type">Type</Translate>
                  </Label>
                  <AvInput
                    id="incident-type"
                    type="select"
                    className="form-control"
                    name="type"
                    value={(!isNew && incidentEntity.type) || 'UNUSUAL'}
                  >
                    <option value="UNUSUAL">
                      <Translate contentKey="totoroApp.IncidentType.UNUSUAL" />
                    </option>
                  </AvInput>
                </AvGroup>
                <AvGroup>
                  <Label id="startLabel" for="start">
                    <Translate contentKey="totoroApp.incident.start">Start</Translate>
                  </Label>
                  <AvInput
                    id="incident-start"
                    type="datetime-local"
                    className="form-control"
                    name="start"
                    value={isNew ? null : convertDateTimeFromServer(this.props.incidentEntity.start)}
                    validate={{
                      required: { value: true, errorMessage: translate('entity.validation.required') }
                    }}
                  />
                </AvGroup>
                <AvGroup>
                  <Label id="endLabel" for="end">
                    <Translate contentKey="totoroApp.incident.end">End</Translate>
                  </Label>
                  <AvInput
                    id="incident-end"
                    type="datetime-local"
                    className="form-control"
                    name="end"
                    value={isNew ? null : convertDateTimeFromServer(this.props.incidentEntity.end)}
                  />
                </AvGroup>
                <AvGroup>
                  <Label id="contentLabel" for="content">
                    <Translate contentKey="totoroApp.incident.content">Content</Translate>
                  </Label>
                  <AvField
                    id="incident-content"
                    type="text"
                    name="content"
                    validate={{
                      required: { value: true, errorMessage: translate('entity.validation.required') }
                    }}
                  />
                </AvGroup>
                <Button tag={Link} id="cancel-save" to="/entity/incident" replace color="info">
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
  incidentEntity: storeState.incident.entity,
  loading: storeState.incident.loading,
  updating: storeState.incident.updating,
  updateSuccess: storeState.incident.updateSuccess
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
)(IncidentUpdate);
