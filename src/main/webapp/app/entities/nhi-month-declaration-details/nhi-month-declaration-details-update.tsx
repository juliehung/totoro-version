import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
// tslint:disable-next-line:no-unused-variable
import { Translate, translate, ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { INhiMonthDeclaration } from 'app/shared/model/nhi-month-declaration.model';
import { getEntities as getNhiMonthDeclarations } from 'app/entities/nhi-month-declaration/nhi-month-declaration.reducer';
import { getEntity, updateEntity, createEntity, reset } from './nhi-month-declaration-details.reducer';
import { INhiMonthDeclarationDetails } from 'app/shared/model/nhi-month-declaration-details.model';
// tslint:disable-next-line:no-unused-variable
import { convertDateTimeFromServer } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface INhiMonthDeclarationDetailsUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export interface INhiMonthDeclarationDetailsUpdateState {
  isNew: boolean;
  nhiMonthDeclarationId: string;
}

export class NhiMonthDeclarationDetailsUpdate extends React.Component<
  INhiMonthDeclarationDetailsUpdateProps,
  INhiMonthDeclarationDetailsUpdateState
> {
  constructor(props) {
    super(props);
    this.state = {
      nhiMonthDeclarationId: '0',
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

    this.props.getNhiMonthDeclarations();
  }

  saveEntity = (event, errors, values) => {
    values.uploadTime = new Date(values.uploadTime);

    if (errors.length === 0) {
      const { nhiMonthDeclarationDetailsEntity } = this.props;
      const entity = {
        ...nhiMonthDeclarationDetailsEntity,
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
    this.props.history.push('/entity/nhi-month-declaration-details');
  };

  render() {
    const { nhiMonthDeclarationDetailsEntity, nhiMonthDeclarations, loading, updating } = this.props;
    const { isNew } = this.state;

    return (
      <div>
        <Row className="justify-content-center">
          <Col md="8">
            <h2 id="totoroApp.nhiMonthDeclarationDetails.home.createOrEditLabel">
              <Translate contentKey="totoroApp.nhiMonthDeclarationDetails.home.createOrEditLabel">
                Create or edit a NhiMonthDeclarationDetails
              </Translate>
            </h2>
          </Col>
        </Row>
        <Row className="justify-content-center">
          <Col md="8">
            {loading ? (
              <p>Loading...</p>
            ) : (
              <AvForm model={isNew ? {} : nhiMonthDeclarationDetailsEntity} onSubmit={this.saveEntity}>
                {!isNew ? (
                  <AvGroup>
                    <Label for="id">
                      <Translate contentKey="global.field.id">ID</Translate>
                    </Label>
                    <AvInput id="nhi-month-declaration-details-id" type="text" className="form-control" name="id" required readOnly />
                  </AvGroup>
                ) : null}
                <AvGroup>
                  <Label id="typeLabel">
                    <Translate contentKey="totoroApp.nhiMonthDeclarationDetails.type">Type</Translate>
                  </Label>
                  <AvInput
                    id="nhi-month-declaration-details-type"
                    type="select"
                    className="form-control"
                    name="type"
                    value={(!isNew && nhiMonthDeclarationDetailsEntity.type) || 'SUBMISSION'}
                  >
                    <option value="SUBMISSION">
                      <Translate contentKey="totoroApp.NhiMonthDeclarationType.SUBMISSION" />
                    </option>
                    <option value="SUPPLEMENT">
                      <Translate contentKey="totoroApp.NhiMonthDeclarationType.SUPPLEMENT" />
                    </option>
                  </AvInput>
                </AvGroup>
                <AvGroup>
                  <Label id="wayLabel" for="way">
                    <Translate contentKey="totoroApp.nhiMonthDeclarationDetails.way">Way</Translate>
                  </Label>
                  <AvField id="nhi-month-declaration-details-way" type="text" name="way" />
                </AvGroup>
                <AvGroup>
                  <Label id="caseTotalLabel" for="caseTotal">
                    <Translate contentKey="totoroApp.nhiMonthDeclarationDetails.caseTotal">Case Total</Translate>
                  </Label>
                  <AvField id="nhi-month-declaration-details-caseTotal" type="string" className="form-control" name="caseTotal" />
                </AvGroup>
                <AvGroup>
                  <Label id="pointTotalLabel" for="pointTotal">
                    <Translate contentKey="totoroApp.nhiMonthDeclarationDetails.pointTotal">Point Total</Translate>
                  </Label>
                  <AvField id="nhi-month-declaration-details-pointTotal" type="string" className="form-control" name="pointTotal" />
                </AvGroup>
                <AvGroup>
                  <Label id="outPatientPointLabel" for="outPatientPoint">
                    <Translate contentKey="totoroApp.nhiMonthDeclarationDetails.outPatientPoint">Out Patient Point</Translate>
                  </Label>
                  <AvField
                    id="nhi-month-declaration-details-outPatientPoint"
                    type="string"
                    className="form-control"
                    name="outPatientPoint"
                  />
                </AvGroup>
                <AvGroup>
                  <Label id="preventiveCaseTotalLabel" for="preventiveCaseTotal">
                    <Translate contentKey="totoroApp.nhiMonthDeclarationDetails.preventiveCaseTotal">Preventive Case Total</Translate>
                  </Label>
                  <AvField
                    id="nhi-month-declaration-details-preventiveCaseTotal"
                    type="string"
                    className="form-control"
                    name="preventiveCaseTotal"
                  />
                </AvGroup>
                <AvGroup>
                  <Label id="preventivePointTotalLabel" for="preventivePointTotal">
                    <Translate contentKey="totoroApp.nhiMonthDeclarationDetails.preventivePointTotal">Preventive Point Total</Translate>
                  </Label>
                  <AvField
                    id="nhi-month-declaration-details-preventivePointTotal"
                    type="string"
                    className="form-control"
                    name="preventivePointTotal"
                  />
                </AvGroup>
                <AvGroup>
                  <Label id="generalCaseTotalLabel" for="generalCaseTotal">
                    <Translate contentKey="totoroApp.nhiMonthDeclarationDetails.generalCaseTotal">General Case Total</Translate>
                  </Label>
                  <AvField
                    id="nhi-month-declaration-details-generalCaseTotal"
                    type="string"
                    className="form-control"
                    name="generalCaseTotal"
                  />
                </AvGroup>
                <AvGroup>
                  <Label id="generalPointTotalLabel" for="generalPointTotal">
                    <Translate contentKey="totoroApp.nhiMonthDeclarationDetails.generalPointTotal">General Point Total</Translate>
                  </Label>
                  <AvField
                    id="nhi-month-declaration-details-generalPointTotal"
                    type="string"
                    className="form-control"
                    name="generalPointTotal"
                  />
                </AvGroup>
                <AvGroup>
                  <Label id="professionalCaseTotalLabel" for="professionalCaseTotal">
                    <Translate contentKey="totoroApp.nhiMonthDeclarationDetails.professionalCaseTotal">Professional Case Total</Translate>
                  </Label>
                  <AvField
                    id="nhi-month-declaration-details-professionalCaseTotal"
                    type="string"
                    className="form-control"
                    name="professionalCaseTotal"
                  />
                </AvGroup>
                <AvGroup>
                  <Label id="professionalPointTotalLabel" for="professionalPointTotal">
                    <Translate contentKey="totoroApp.nhiMonthDeclarationDetails.professionalPointTotal">Professional Point Total</Translate>
                  </Label>
                  <AvField
                    id="nhi-month-declaration-details-professionalPointTotal"
                    type="string"
                    className="form-control"
                    name="professionalPointTotal"
                  />
                </AvGroup>
                <AvGroup>
                  <Label id="partialCaseTotalLabel" for="partialCaseTotal">
                    <Translate contentKey="totoroApp.nhiMonthDeclarationDetails.partialCaseTotal">Partial Case Total</Translate>
                  </Label>
                  <AvField
                    id="nhi-month-declaration-details-partialCaseTotal"
                    type="string"
                    className="form-control"
                    name="partialCaseTotal"
                  />
                </AvGroup>
                <AvGroup>
                  <Label id="partialPointTotalLabel" for="partialPointTotal">
                    <Translate contentKey="totoroApp.nhiMonthDeclarationDetails.partialPointTotal">Partial Point Total</Translate>
                  </Label>
                  <AvField
                    id="nhi-month-declaration-details-partialPointTotal"
                    type="string"
                    className="form-control"
                    name="partialPointTotal"
                  />
                </AvGroup>
                <AvGroup>
                  <Label id="fileLabel" for="file">
                    <Translate contentKey="totoroApp.nhiMonthDeclarationDetails.file">File</Translate>
                  </Label>
                  <AvField id="nhi-month-declaration-details-file" type="text" name="file" />
                </AvGroup>
                <AvGroup>
                  <Label id="uploadTimeLabel" for="uploadTime">
                    <Translate contentKey="totoroApp.nhiMonthDeclarationDetails.uploadTime">Upload Time</Translate>
                  </Label>
                  <AvInput
                    id="nhi-month-declaration-details-uploadTime"
                    type="datetime-local"
                    className="form-control"
                    name="uploadTime"
                    value={isNew ? null : convertDateTimeFromServer(this.props.nhiMonthDeclarationDetailsEntity.uploadTime)}
                  />
                </AvGroup>
                <AvGroup>
                  <Label for="nhiMonthDeclaration.id">
                    <Translate contentKey="totoroApp.nhiMonthDeclarationDetails.nhiMonthDeclaration">Nhi Month Declaration</Translate>
                  </Label>
                  <AvInput
                    id="nhi-month-declaration-details-nhiMonthDeclaration"
                    type="select"
                    className="form-control"
                    name="nhiMonthDeclaration.id"
                  >
                    <option value="" key="0" />
                    {nhiMonthDeclarations
                      ? nhiMonthDeclarations.map(otherEntity => (
                          <option value={otherEntity.id} key={otherEntity.id}>
                            {otherEntity.id}
                          </option>
                        ))
                      : null}
                  </AvInput>
                </AvGroup>
                <Button tag={Link} id="cancel-save" to="/entity/nhi-month-declaration-details" replace color="info">
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
  nhiMonthDeclarations: storeState.nhiMonthDeclaration.entities,
  nhiMonthDeclarationDetailsEntity: storeState.nhiMonthDeclarationDetails.entity,
  loading: storeState.nhiMonthDeclarationDetails.loading,
  updating: storeState.nhiMonthDeclarationDetails.updating,
  updateSuccess: storeState.nhiMonthDeclarationDetails.updateSuccess
});

const mapDispatchToProps = {
  getNhiMonthDeclarations,
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
)(NhiMonthDeclarationDetailsUpdate);
