import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvForm, AvGroup, AvInput } from 'availity-reactstrap-validation';
// tslint:disable-next-line:no-unused-variable
import { Translate, translate, ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { INHIUnusalContent } from 'app/shared/model/nhi-unusal-content.model';
import { getEntities as getNHiUnusalContents } from 'app/entities/nhi-unusal-content/nhi-unusal-content.reducer';
import { getEntity, updateEntity, createEntity, reset } from './nhi-unusal-incident.reducer';
import { INHIUnusalIncident } from 'app/shared/model/nhi-unusal-incident.model';
// tslint:disable-next-line:no-unused-variable
import { convertDateTimeFromServer } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface INHIUnusalIncidentUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export interface INHIUnusalIncidentUpdateState {
  isNew: boolean;
  nhiUnusalContentId: string;
}

export class NHIUnusalIncidentUpdate extends React.Component<INHIUnusalIncidentUpdateProps, INHIUnusalIncidentUpdateState> {
  constructor(props) {
    super(props);
    this.state = {
      nhiUnusalContentId: '0',
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

    this.props.getNHiUnusalContents();
  }

  saveEntity = (event, errors, values) => {
    values.start = new Date(values.start);
    values.end = new Date(values.end);

    if (errors.length === 0) {
      const { nHIUnusalIncidentEntity } = this.props;
      const entity = {
        ...nHIUnusalIncidentEntity,
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
    this.props.history.push('/entity/nhi-unusal-incident');
  };

  render() {
    const { nHIUnusalIncidentEntity, nHIUnusalContents, loading, updating } = this.props;
    const { isNew } = this.state;

    return (
      <div>
        <Row className="justify-content-center">
          <Col md="8">
            <h2 id="totoroApp.nHIUnusalIncident.home.createOrEditLabel">
              <Translate contentKey="totoroApp.nHIUnusalIncident.home.createOrEditLabel">Create or edit a NHIUnusalIncident</Translate>
            </h2>
          </Col>
        </Row>
        <Row className="justify-content-center">
          <Col md="8">
            {loading ? (
              <p>Loading...</p>
            ) : (
              <AvForm model={isNew ? {} : nHIUnusalIncidentEntity} onSubmit={this.saveEntity}>
                {!isNew ? (
                  <AvGroup>
                    <Label for="id">
                      <Translate contentKey="global.field.id">ID</Translate>
                    </Label>
                    <AvInput id="nhi-unusal-incident-id" type="text" className="form-control" name="id" required readOnly />
                  </AvGroup>
                ) : null}
                <AvGroup>
                  <Label id="startLabel" for="start">
                    <Translate contentKey="totoroApp.nHIUnusalIncident.start">Start</Translate>
                  </Label>
                  <AvInput
                    id="nhi-unusal-incident-start"
                    type="datetime-local"
                    className="form-control"
                    name="start"
                    value={isNew ? null : convertDateTimeFromServer(this.props.nHIUnusalIncidentEntity.start)}
                    validate={{
                      required: { value: true, errorMessage: translate('entity.validation.required') }
                    }}
                  />
                </AvGroup>
                <AvGroup>
                  <Label id="endLabel" for="end">
                    <Translate contentKey="totoroApp.nHIUnusalIncident.end">End</Translate>
                  </Label>
                  <AvInput
                    id="nhi-unusal-incident-end"
                    type="datetime-local"
                    className="form-control"
                    name="end"
                    value={isNew ? null : convertDateTimeFromServer(this.props.nHIUnusalIncidentEntity.end)}
                  />
                </AvGroup>
                <AvGroup>
                  <Label for="nhiUnusalContent.id">
                    <Translate contentKey="totoroApp.nHIUnusalIncident.nhiUnusalContent">Nhi Unusal Content</Translate>
                  </Label>
                  <AvInput id="nhi-unusal-incident-nhiUnusalContent" type="select" className="form-control" name="nhiUnusalContent.id">
                    <option value="" key="0" />
                    {nHIUnusalContents
                      ? nHIUnusalContents.map(otherEntity => (
                          <option value={otherEntity.id} key={otherEntity.id}>
                            {otherEntity.id}
                          </option>
                        ))
                      : null}
                  </AvInput>
                </AvGroup>
                <Button tag={Link} id="cancel-save" to="/entity/nhi-unusal-incident" replace color="info">
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
  nHIUnusalContents: storeState.nHIUnusalContent.entities,
  nHIUnusalIncidentEntity: storeState.nHIUnusalIncident.entity,
  loading: storeState.nHIUnusalIncident.loading,
  updating: storeState.nHIUnusalIncident.updating,
  updateSuccess: storeState.nHIUnusalIncident.updateSuccess
});

const mapDispatchToProps = {
  getNHiUnusalContents,
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
)(NHIUnusalIncidentUpdate);
