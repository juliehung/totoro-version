import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
// tslint:disable-next-line:no-unused-variable
import { Translate, translate, ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { INhiDayUpload } from 'app/shared/model/nhi-day-upload.model';
import { getEntities as getNhiDayUploads } from 'app/entities/nhi-day-upload/nhi-day-upload.reducer';
import { getEntity, updateEntity, createEntity, reset } from './nhi-day-upload-details.reducer';
import { INhiDayUploadDetails } from 'app/shared/model/nhi-day-upload-details.model';
// tslint:disable-next-line:no-unused-variable
import { convertDateTimeFromServer } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface INhiDayUploadDetailsUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export interface INhiDayUploadDetailsUpdateState {
  isNew: boolean;
  nhiDayUploadId: string;
}

export class NhiDayUploadDetailsUpdate extends React.Component<INhiDayUploadDetailsUpdateProps, INhiDayUploadDetailsUpdateState> {
  constructor(props) {
    super(props);
    this.state = {
      nhiDayUploadId: '0',
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

    this.props.getNhiDayUploads();
  }

  saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const { nhiDayUploadDetailsEntity } = this.props;
      const entity = {
        ...nhiDayUploadDetailsEntity,
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
    this.props.history.push('/entity/nhi-day-upload-details');
  };

  render() {
    const { nhiDayUploadDetailsEntity, nhiDayUploads, loading, updating } = this.props;
    const { isNew } = this.state;

    return (
      <div>
        <Row className="justify-content-center">
          <Col md="8">
            <h2 id="totoroApp.nhiDayUploadDetails.home.createOrEditLabel">
              <Translate contentKey="totoroApp.nhiDayUploadDetails.home.createOrEditLabel">Create or edit a NhiDayUploadDetails</Translate>
            </h2>
          </Col>
        </Row>
        <Row className="justify-content-center">
          <Col md="8">
            {loading ? (
              <p>Loading...</p>
            ) : (
              <AvForm model={isNew ? {} : nhiDayUploadDetailsEntity} onSubmit={this.saveEntity}>
                {!isNew ? (
                  <AvGroup>
                    <Label for="id">
                      <Translate contentKey="global.field.id">ID</Translate>
                    </Label>
                    <AvInput id="nhi-day-upload-details-id" type="text" className="form-control" name="id" required readOnly />
                  </AvGroup>
                ) : null}
                <AvGroup>
                  <Label id="typeLabel">
                    <Translate contentKey="totoroApp.nhiDayUploadDetails.type">Type</Translate>
                  </Label>
                  <AvInput
                    id="nhi-day-upload-details-type"
                    type="select"
                    className="form-control"
                    name="type"
                    value={(!isNew && nhiDayUploadDetailsEntity.type) || 'NORMAL'}
                  >
                    <option value="NORMAL">
                      <Translate contentKey="totoroApp.NhiDayUploadDetailType.NORMAL" />
                    </option>
                    <option value="CORRECTION">
                      <Translate contentKey="totoroApp.NhiDayUploadDetailType.CORRECTION" />
                    </option>
                  </AvInput>
                </AvGroup>
                <AvGroup>
                  <Label for="nhiDayUpload.id">
                    <Translate contentKey="totoroApp.nhiDayUploadDetails.nhiDayUpload">Nhi Day Upload</Translate>
                  </Label>
                  <AvInput id="nhi-day-upload-details-nhiDayUpload" type="select" className="form-control" name="nhiDayUpload.id">
                    <option value="" key="0" />
                    {nhiDayUploads
                      ? nhiDayUploads.map(otherEntity => (
                          <option value={otherEntity.id} key={otherEntity.id}>
                            {otherEntity.id}
                          </option>
                        ))
                      : null}
                  </AvInput>
                </AvGroup>
                <Button tag={Link} id="cancel-save" to="/entity/nhi-day-upload-details" replace color="info">
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
  nhiDayUploads: storeState.nhiDayUpload.entities,
  nhiDayUploadDetailsEntity: storeState.nhiDayUploadDetails.entity,
  loading: storeState.nhiDayUploadDetails.loading,
  updating: storeState.nhiDayUploadDetails.updating,
  updateSuccess: storeState.nhiDayUploadDetails.updateSuccess
});

const mapDispatchToProps = {
  getNhiDayUploads,
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
)(NhiDayUploadDetailsUpdate);
