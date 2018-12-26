import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
// tslint:disable-next-line:no-unused-variable
import { Translate, translate, ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { getEntity, updateEntity, createEntity, reset } from './treatment-task.reducer';
import { ITreatmentTask } from 'app/shared/model/treatment-task.model';
// tslint:disable-next-line:no-unused-variable
import { convertDateTimeFromServer } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface ITreatmentTaskUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export interface ITreatmentTaskUpdateState {
  isNew: boolean;
}

export class TreatmentTaskUpdate extends React.Component<ITreatmentTaskUpdateProps, ITreatmentTaskUpdateState> {
  constructor(props) {
    super(props);
    this.state = {
      isNew: !this.props.match.params || !this.props.match.params.id
    };
  }

  componentDidMount() {
    if (this.state.isNew) {
      this.props.reset();
    } else {
      this.props.getEntity(this.props.match.params.id);
    }
  }

  saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const { treatmentTaskEntity } = this.props;
      const entity = {
        ...treatmentTaskEntity,
        ...values
      };

      if (this.state.isNew) {
        this.props.createEntity(entity);
      } else {
        this.props.updateEntity(entity);
      }
      this.handleClose();
    }
  };

  handleClose = () => {
    this.props.history.push('/entity/treatment-task');
  };

  render() {
    const { treatmentTaskEntity, loading, updating } = this.props;
    const { isNew } = this.state;

    return (
      <div>
        <Row className="justify-content-center">
          <Col md="8">
            <h2 id="totoroApp.treatmentTask.home.createOrEditLabel">
              <Translate contentKey="totoroApp.treatmentTask.home.createOrEditLabel">Create or edit a TreatmentTask</Translate>
            </h2>
          </Col>
        </Row>
        <Row className="justify-content-center">
          <Col md="8">
            {loading ? (
              <p>Loading...</p>
            ) : (
              <AvForm model={isNew ? {} : treatmentTaskEntity} onSubmit={this.saveEntity}>
                {!isNew ? (
                  <AvGroup>
                    <Label for="id">
                      <Translate contentKey="global.field.id">ID</Translate>
                    </Label>
                    <AvInput id="treatment-task-id" type="text" className="form-control" name="id" required readOnly />
                  </AvGroup>
                ) : null}
                <AvGroup>
                  <Label id="statusLabel">
                    <Translate contentKey="totoroApp.treatmentTask.status">Status</Translate>
                  </Label>
                  <AvInput
                    id="treatment-task-status"
                    type="select"
                    className="form-control"
                    name="status"
                    value={(!isNew && treatmentTaskEntity.status) || 'PLANNED'}
                  >
                    <option value="PLANNED">
                      <Translate contentKey="totoroApp.TreatmentTaskStatus.PLANNED" />
                    </option>
                    <option value="IN_PROGRESS">
                      <Translate contentKey="totoroApp.TreatmentTaskStatus.IN_PROGRESS" />
                    </option>
                    <option value="COMPLETED">
                      <Translate contentKey="totoroApp.TreatmentTaskStatus.COMPLETED" />
                    </option>
                    <option value="CANCEL">
                      <Translate contentKey="totoroApp.TreatmentTaskStatus.CANCEL" />
                    </option>
                  </AvInput>
                </AvGroup>
                <AvGroup>
                  <Label id="descriptionLabel" for="description">
                    <Translate contentKey="totoroApp.treatmentTask.description">Description</Translate>
                  </Label>
                  <AvField id="treatment-task-description" type="text" name="description" />
                </AvGroup>
                <AvGroup>
                  <Label id="teethLabel" for="teeth">
                    <Translate contentKey="totoroApp.treatmentTask.teeth">Teeth</Translate>
                  </Label>
                  <AvField id="treatment-task-teeth" type="text" name="teeth" />
                </AvGroup>
                <AvGroup>
                  <Label id="surfacesLabel" for="surfaces">
                    <Translate contentKey="totoroApp.treatmentTask.surfaces">Surfaces</Translate>
                  </Label>
                  <AvField id="treatment-task-surfaces" type="text" name="surfaces" />
                </AvGroup>
                <AvGroup>
                  <Label id="noteLabel" for="note">
                    <Translate contentKey="totoroApp.treatmentTask.note">Note</Translate>
                  </Label>
                  <AvField id="treatment-task-note" type="text" name="note" />
                </AvGroup>
                <Button tag={Link} id="cancel-save" to="/entity/treatment-task" replace color="info">
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
  treatmentTaskEntity: storeState.treatmentTask.entity,
  loading: storeState.treatmentTask.loading,
  updating: storeState.treatmentTask.updating
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
)(TreatmentTaskUpdate);
