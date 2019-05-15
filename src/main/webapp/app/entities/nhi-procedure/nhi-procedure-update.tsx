import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
// tslint:disable-next-line:no-unused-variable
import { Translate, translate, ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { INhiProcedureType } from 'app/shared/model/nhi-procedure-type.model';
import { getEntities as getNhiProcedureTypes } from 'app/entities/nhi-procedure-type/nhi-procedure-type.reducer';
import { INhiIcd9Cm } from 'app/shared/model/nhi-icd-9-cm.model';
import { getEntities as getNhiIcd9Cms } from 'app/entities/nhi-icd-9-cm/nhi-icd-9-cm.reducer';
import { getEntity, updateEntity, createEntity, reset } from './nhi-procedure.reducer';
import { INhiProcedure } from 'app/shared/model/nhi-procedure.model';
// tslint:disable-next-line:no-unused-variable
import { convertDateTimeFromServer } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface INhiProcedureUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export interface INhiProcedureUpdateState {
  isNew: boolean;
  nhiProcedureTypeId: string;
  nhiIcd9CmId: string;
}

export class NhiProcedureUpdate extends React.Component<INhiProcedureUpdateProps, INhiProcedureUpdateState> {
  constructor(props) {
    super(props);
    this.state = {
      nhiProcedureTypeId: '0',
      nhiIcd9CmId: '0',
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

    this.props.getNhiProcedureTypes();
    this.props.getNhiIcd9Cms();
  }

  saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const { nhiProcedureEntity } = this.props;
      const entity = {
        ...nhiProcedureEntity,
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
    this.props.history.push('/entity/nhi-procedure');
  };

  render() {
    const { nhiProcedureEntity, nhiProcedureTypes, nhiIcd9Cms, loading, updating } = this.props;
    const { isNew } = this.state;

    return (
      <div>
        <Row className="justify-content-center">
          <Col md="8">
            <h2 id="totoroApp.nhiProcedure.home.createOrEditLabel">
              <Translate contentKey="totoroApp.nhiProcedure.home.createOrEditLabel">Create or edit a NhiProcedure</Translate>
            </h2>
          </Col>
        </Row>
        <Row className="justify-content-center">
          <Col md="8">
            {loading ? (
              <p>Loading...</p>
            ) : (
              <AvForm model={isNew ? {} : nhiProcedureEntity} onSubmit={this.saveEntity}>
                {!isNew ? (
                  <AvGroup>
                    <Label for="id">
                      <Translate contentKey="global.field.id">ID</Translate>
                    </Label>
                    <AvInput id="nhi-procedure-id" type="text" className="form-control" name="id" required readOnly />
                  </AvGroup>
                ) : null}
                <AvGroup>
                  <Label id="codeLabel" for="code">
                    <Translate contentKey="totoroApp.nhiProcedure.code">Code</Translate>
                  </Label>
                  <AvField id="nhi-procedure-code" type="text" name="code" />
                </AvGroup>
                <AvGroup>
                  <Label id="nameLabel" for="name">
                    <Translate contentKey="totoroApp.nhiProcedure.name">Name</Translate>
                  </Label>
                  <AvField id="nhi-procedure-name" type="text" name="name" />
                </AvGroup>
                <AvGroup>
                  <Label id="pointLabel" for="point">
                    <Translate contentKey="totoroApp.nhiProcedure.point">Point</Translate>
                  </Label>
                  <AvField id="nhi-procedure-point" type="string" className="form-control" name="point" />
                </AvGroup>
                <AvGroup>
                  <Label id="englishNameLabel" for="englishName">
                    <Translate contentKey="totoroApp.nhiProcedure.englishName">English Name</Translate>
                  </Label>
                  <AvField id="nhi-procedure-englishName" type="text" name="englishName" />
                </AvGroup>
                <AvGroup>
                  <Label id="defaultIcd10CmIdLabel" for="defaultIcd10CmId">
                    <Translate contentKey="totoroApp.nhiProcedure.defaultIcd10CmId">Default Icd 10 Cm Id</Translate>
                  </Label>
                  <AvField id="nhi-procedure-defaultIcd10CmId" type="string" className="form-control" name="defaultIcd10CmId" />
                </AvGroup>
                <AvGroup>
                  <Label id="descriptionLabel" for="description">
                    <Translate contentKey="totoroApp.nhiProcedure.description">Description</Translate>
                  </Label>
                  <AvField
                    id="nhi-procedure-description"
                    type="text"
                    name="description"
                    validate={{
                      maxLength: { value: 5100, errorMessage: translate('entity.validation.maxlength', { max: 5100 }) }
                    }}
                  />
                </AvGroup>
                <AvGroup>
                  <Label for="nhiProcedureType.id">
                    <Translate contentKey="totoroApp.nhiProcedure.nhiProcedureType">Nhi Procedure Type</Translate>
                  </Label>
                  <AvInput id="nhi-procedure-nhiProcedureType" type="select" className="form-control" name="nhiProcedureType.id">
                    <option value="" key="0" />
                    {nhiProcedureTypes
                      ? nhiProcedureTypes.map(otherEntity => (
                          <option value={otherEntity.id} key={otherEntity.id}>
                            {otherEntity.id}
                          </option>
                        ))
                      : null}
                  </AvInput>
                </AvGroup>
                <AvGroup>
                  <Label for="nhiIcd9Cm.id">
                    <Translate contentKey="totoroApp.nhiProcedure.nhiIcd9Cm">Nhi Icd 9 Cm</Translate>
                  </Label>
                  <AvInput id="nhi-procedure-nhiIcd9Cm" type="select" className="form-control" name="nhiIcd9Cm.id">
                    <option value="" key="0" />
                    {nhiIcd9Cms
                      ? nhiIcd9Cms.map(otherEntity => (
                          <option value={otherEntity.id} key={otherEntity.id}>
                            {otherEntity.id}
                          </option>
                        ))
                      : null}
                  </AvInput>
                </AvGroup>
                <Button tag={Link} id="cancel-save" to="/entity/nhi-procedure" replace color="info">
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
  nhiProcedureTypes: storeState.nhiProcedureType.entities,
  nhiIcd9Cms: storeState.nhiIcd9Cm.entities,
  nhiProcedureEntity: storeState.nhiProcedure.entity,
  loading: storeState.nhiProcedure.loading,
  updating: storeState.nhiProcedure.updating,
  updateSuccess: storeState.nhiProcedure.updateSuccess
});

const mapDispatchToProps = {
  getNhiProcedureTypes,
  getNhiIcd9Cms,
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
)(NhiProcedureUpdate);
