import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
// tslint:disable-next-line:no-unused-variable
import { Translate, translate, ICrudGetAction, ICrudGetAllAction, setFileData, openFile, byteSize, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { getEntity, updateEntity, createEntity, setBlob, reset } from './esign.reducer';
import { IEsign } from 'app/shared/model/esign.model';
// tslint:disable-next-line:no-unused-variable
import { convertDateTimeFromServer } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IEsignUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export interface IEsignUpdateState {
  isNew: boolean;
}

export class EsignUpdate extends React.Component<IEsignUpdateProps, IEsignUpdateState> {
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

  onBlobChange = (isAnImage, name) => event => {
    setFileData(event, (contentType, data) => this.props.setBlob(name, data, contentType), isAnImage);
  };

  clearBlob = name => () => {
    this.props.setBlob(name, undefined, undefined);
  };

  saveEntity = (event, errors, values) => {
    values.createTime = new Date(values.createTime);
    values.updateTime = new Date(values.updateTime);

    if (errors.length === 0) {
      const { esignEntity } = this.props;
      const entity = {
        ...esignEntity,
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
    this.props.history.push('/entity/esign');
  };

  render() {
    const { esignEntity, loading, updating } = this.props;
    const { isNew } = this.state;

    const { lob, lobContentType } = esignEntity;

    return (
      <div>
        <Row className="justify-content-center">
          <Col md="8">
            <h2 id="totoroApp.esign.home.createOrEditLabel">
              <Translate contentKey="totoroApp.esign.home.createOrEditLabel">Create or edit a Esign</Translate>
            </h2>
          </Col>
        </Row>
        <Row className="justify-content-center">
          <Col md="8">
            {loading ? (
              <p>Loading...</p>
            ) : (
              <AvForm model={isNew ? {} : esignEntity} onSubmit={this.saveEntity}>
                {!isNew ? (
                  <AvGroup>
                    <Label for="id">
                      <Translate contentKey="global.field.id">ID</Translate>
                    </Label>
                    <AvInput id="esign-id" type="text" className="form-control" name="id" required readOnly />
                  </AvGroup>
                ) : null}
                <AvGroup>
                  <Label id="patientIdLabel" for="patientId">
                    <Translate contentKey="totoroApp.esign.patientId">Patient Id</Translate>
                  </Label>
                  <AvField
                    id="esign-patientId"
                    type="string"
                    className="form-control"
                    name="patientId"
                    validate={{
                      required: { value: true, errorMessage: translate('entity.validation.required') },
                      number: { value: true, errorMessage: translate('entity.validation.number') }
                    }}
                  />
                </AvGroup>
                <AvGroup>
                  <AvGroup>
                    <Label id="lobLabel" for="lob">
                      <Translate contentKey="totoroApp.esign.lob">Lob</Translate>
                    </Label>
                    <br />
                    {lob ? (
                      <div>
                        <a onClick={openFile(lobContentType, lob)}>
                          <img src={`data:${lobContentType};base64,${lob}`} style={{ maxHeight: '100px' }} />
                        </a>
                        <br />
                        <Row>
                          <Col md="11">
                            <span>
                              {lobContentType}, {byteSize(lob)}
                            </span>
                          </Col>
                          <Col md="1">
                            <Button color="danger" onClick={this.clearBlob('lob')}>
                              <FontAwesomeIcon icon="times-circle" />
                            </Button>
                          </Col>
                        </Row>
                      </div>
                    ) : null}
                    <input id="file_lob" type="file" onChange={this.onBlobChange(true, 'lob')} accept="image/*" />
                    <AvInput type="hidden" name="lob" value={lob} validate={{}} />
                  </AvGroup>
                </AvGroup>
                <AvGroup>
                  <Label id="createTimeLabel" for="createTime">
                    <Translate contentKey="totoroApp.esign.createTime">Create Time</Translate>
                  </Label>
                  <AvInput
                    id="esign-createTime"
                    type="datetime-local"
                    className="form-control"
                    name="createTime"
                    value={isNew ? null : convertDateTimeFromServer(this.props.esignEntity.createTime)}
                  />
                </AvGroup>
                <AvGroup>
                  <Label id="updateTimeLabel" for="updateTime">
                    <Translate contentKey="totoroApp.esign.updateTime">Update Time</Translate>
                  </Label>
                  <AvInput
                    id="esign-updateTime"
                    type="datetime-local"
                    className="form-control"
                    name="updateTime"
                    value={isNew ? null : convertDateTimeFromServer(this.props.esignEntity.updateTime)}
                  />
                </AvGroup>
                <AvGroup>
                  <Label id="sourceTypeLabel">
                    <Translate contentKey="totoroApp.esign.sourceType">Source Type</Translate>
                  </Label>
                  <AvInput
                    id="esign-sourceType"
                    type="select"
                    className="form-control"
                    name="sourceType"
                    value={(!isNew && esignEntity.sourceType) || 'BY_STRING64'}
                  >
                    <option value="BY_STRING64">
                      <Translate contentKey="totoroApp.SourceType.BY_STRING64" />
                    </option>
                    <option value="BY_FILE">
                      <Translate contentKey="totoroApp.SourceType.BY_FILE" />
                    </option>
                  </AvInput>
                </AvGroup>
                <Button tag={Link} id="cancel-save" to="/entity/esign" replace color="info">
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
  esignEntity: storeState.esign.entity,
  loading: storeState.esign.loading,
  updating: storeState.esign.updating,
  updateSuccess: storeState.esign.updateSuccess
});

const mapDispatchToProps = {
  getEntity,
  updateEntity,
  setBlob,
  createEntity,
  reset
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(EsignUpdate);
