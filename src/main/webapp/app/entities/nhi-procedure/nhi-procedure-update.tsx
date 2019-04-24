import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
// tslint:disable-next-line:no-unused-variable
import { Translate, translate, ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { INHIProcedureType } from 'app/shared/model/nhi-procedure-type.model';
import { getEntities as getNHiProcedureTypes } from 'app/entities/nhi-procedure-type/nhi-procedure-type.reducer';
import { getEntity, updateEntity, createEntity, reset } from './nhi-procedure.reducer';
import { INHIProcedure } from 'app/shared/model/nhi-procedure.model';
// tslint:disable-next-line:no-unused-variable
import { convertDateTimeFromServer } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface INHIProcedureUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export interface INHIProcedureUpdateState {
  isNew: boolean;
  nHIProcedureTypeId: string;
}

export class NHIProcedureUpdate extends React.Component<INHIProcedureUpdateProps, INHIProcedureUpdateState> {
  constructor(props) {
    super(props);
    this.state = {
      nHIProcedureTypeId: '0',
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

    this.props.getNHiProcedureTypes();
  }

  saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const { nHIProcedureEntity } = this.props;
      const entity = {
        ...nHIProcedureEntity,
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
    const { nHIProcedureEntity, nHIProcedureTypes, loading, updating } = this.props;
    const { isNew } = this.state;

    return (
      <div>
        <Row className="justify-content-center">
          <Col md="8">
            <h2 id="totoroApp.nHIProcedure.home.createOrEditLabel">
              <Translate contentKey="totoroApp.nHIProcedure.home.createOrEditLabel">Create or edit a NHIProcedure</Translate>
            </h2>
          </Col>
        </Row>
        <Row className="justify-content-center">
          <Col md="8">
            {loading ? (
              <p>Loading...</p>
            ) : (
              <AvForm model={isNew ? {} : nHIProcedureEntity} onSubmit={this.saveEntity}>
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
                    <Translate contentKey="totoroApp.nHIProcedure.code">Code</Translate>
                  </Label>
                  <AvField
                    id="nhi-procedure-code"
                    type="text"
                    name="code"
                    validate={{
                      required: { value: true, errorMessage: translate('entity.validation.required') }
                    }}
                  />
                </AvGroup>
                <AvGroup>
                  <Label id="nameLabel" for="name">
                    <Translate contentKey="totoroApp.nHIProcedure.name">Name</Translate>
                  </Label>
                  <AvField
                    id="nhi-procedure-name"
                    type="text"
                    name="name"
                    validate={{
                      required: { value: true, errorMessage: translate('entity.validation.required') }
                    }}
                  />
                </AvGroup>
                <AvGroup>
                  <Label id="pointLabel" for="point">
                    <Translate contentKey="totoroApp.nHIProcedure.point">Point</Translate>
                  </Label>
                  <AvField
                    id="nhi-procedure-point"
                    type="string"
                    className="form-control"
                    name="point"
                    validate={{
                      required: { value: true, errorMessage: translate('entity.validation.required') },
                      number: { value: true, errorMessage: translate('entity.validation.number') }
                    }}
                  />
                </AvGroup>
                <AvGroup>
                  <Label id="englishNameLabel" for="englishName">
                    <Translate contentKey="totoroApp.nHIProcedure.englishName">English Name</Translate>
                  </Label>
                  <AvField id="nhi-procedure-englishName" type="text" name="englishName" />
                </AvGroup>
                <AvGroup>
                  <Label for="nHIProcedureType.id">
                    <Translate contentKey="totoroApp.nHIProcedure.nHIProcedureType">N HI Procedure Type</Translate>
                  </Label>
                  <AvInput id="nhi-procedure-nHIProcedureType" type="select" className="form-control" name="nHIProcedureType.id">
                    <option value="" key="0" />
                    {nHIProcedureTypes
                      ? nHIProcedureTypes.map(otherEntity => (
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
  nHIProcedureTypes: storeState.nHIProcedureType.entities,
  nHIProcedureEntity: storeState.nHIProcedure.entity,
  loading: storeState.nHIProcedure.loading,
  updating: storeState.nHIProcedure.updating,
  updateSuccess: storeState.nHIProcedure.updateSuccess
});

const mapDispatchToProps = {
  getNHiProcedureTypes,
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
)(NHIProcedureUpdate);
