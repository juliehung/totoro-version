import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
// tslint:disable-next-line:no-unused-variable
import { Translate, translate, ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { getEntity, updateEntity, createEntity, reset } from './doc-np.reducer';
import { IDocNp } from 'app/shared/model/doc-np.model';
// tslint:disable-next-line:no-unused-variable
import { convertDateTimeFromServer } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IDocNpUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export interface IDocNpUpdateState {
  isNew: boolean;
}

export class DocNpUpdate extends React.Component<IDocNpUpdateProps, IDocNpUpdateState> {
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
    values.createdDate = new Date(values.createdDate);

    if (errors.length === 0) {
      const { docNpEntity } = this.props;
      const entity = {
        ...docNpEntity,
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
    this.props.history.push('/entity/doc-np');
  };

  render() {
    const { docNpEntity, loading, updating } = this.props;
    const { isNew } = this.state;

    return (
      <div>
        <Row className="justify-content-center">
          <Col md="8">
            <h2 id="totoroApp.docNp.home.createOrEditLabel">
              <Translate contentKey="totoroApp.docNp.home.createOrEditLabel">Create or edit a DocNp</Translate>
            </h2>
          </Col>
        </Row>
        <Row className="justify-content-center">
          <Col md="8">
            {loading ? (
              <p>Loading...</p>
            ) : (
              <AvForm model={isNew ? {} : docNpEntity} onSubmit={this.saveEntity}>
                {!isNew ? (
                  <AvGroup>
                    <Label for="id">
                      <Translate contentKey="global.field.id">ID</Translate>
                    </Label>
                    <AvInput id="doc-np-id" type="text" className="form-control" name="id" required readOnly />
                  </AvGroup>
                ) : null}
                <AvGroup>
                  <Label id="patientLabel" for="patient">
                    <Translate contentKey="totoroApp.docNp.patient">Patient</Translate>
                  </Label>
                  <AvField id="doc-np-patient" type="text" name="patient" />
                </AvGroup>
                <AvGroup>
                  <Label id="patientIdLabel" for="patientId">
                    <Translate contentKey="totoroApp.docNp.patientId">Patient Id</Translate>
                  </Label>
                  <AvField id="doc-np-patientId" type="string" className="form-control" name="patientId" />
                </AvGroup>
                <AvGroup>
                  <Label id="esignIdLabel" for="esignId">
                    <Translate contentKey="totoroApp.docNp.esignId">Esign Id</Translate>
                  </Label>
                  <AvField id="doc-np-esignId" type="string" className="form-control" name="esignId" />
                </AvGroup>
                <AvGroup>
                  <Label id="createdDateLabel" for="createdDate">
                    <Translate contentKey="totoroApp.docNp.createdDate">Created Date</Translate>
                  </Label>
                  <AvInput
                    id="doc-np-createdDate"
                    type="datetime-local"
                    className="form-control"
                    name="createdDate"
                    value={isNew ? null : convertDateTimeFromServer(this.props.docNpEntity.createdDate)}
                  />
                </AvGroup>
                <AvGroup>
                  <Label id="createdByLabel" for="createdBy">
                    <Translate contentKey="totoroApp.docNp.createdBy">Created By</Translate>
                  </Label>
                  <AvField id="doc-np-createdBy" type="text" name="createdBy" />
                </AvGroup>
                <Button tag={Link} id="cancel-save" to="/entity/doc-np" replace color="info">
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
  docNpEntity: storeState.docNp.entity,
  loading: storeState.docNp.loading,
  updating: storeState.docNp.updating,
  updateSuccess: storeState.docNp.updateSuccess
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
)(DocNpUpdate);
