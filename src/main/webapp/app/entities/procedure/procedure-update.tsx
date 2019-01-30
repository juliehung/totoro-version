import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
// tslint:disable-next-line:no-unused-variable
import { Translate, translate, ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { IProcedureType } from 'app/shared/model/procedure-type.model';
import { getEntities as getProcedureTypes } from 'app/entities/procedure-type/procedure-type.reducer';
import { getEntity, updateEntity, createEntity, reset } from './procedure.reducer';
import { IProcedure } from 'app/shared/model/procedure.model';
// tslint:disable-next-line:no-unused-variable
import { convertDateTimeFromServer } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IProcedureUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export interface IProcedureUpdateState {
  isNew: boolean;
  procedureTypeId: string;
}

export class ProcedureUpdate extends React.Component<IProcedureUpdateProps, IProcedureUpdateState> {
  constructor(props) {
    super(props);
    this.state = {
      procedureTypeId: '0',
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

    this.props.getProcedureTypes();
  }

  saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const { procedureEntity } = this.props;
      const entity = {
        ...procedureEntity,
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
    this.props.history.push('/entity/procedure');
  };

  render() {
    const { procedureEntity, procedureTypes, loading, updating } = this.props;
    const { isNew } = this.state;

    return (
      <div>
        <Row className="justify-content-center">
          <Col md="8">
            <h2 id="totoroApp.procedure.home.createOrEditLabel">
              <Translate contentKey="totoroApp.procedure.home.createOrEditLabel">Create or edit a Procedure</Translate>
            </h2>
          </Col>
        </Row>
        <Row className="justify-content-center">
          <Col md="8">
            {loading ? (
              <p>Loading...</p>
            ) : (
              <AvForm model={isNew ? {} : procedureEntity} onSubmit={this.saveEntity}>
                {!isNew ? (
                  <AvGroup>
                    <Label for="id">
                      <Translate contentKey="global.field.id">ID</Translate>
                    </Label>
                    <AvInput id="procedure-id" type="text" className="form-control" name="id" required readOnly />
                  </AvGroup>
                ) : null}
                <AvGroup>
                  <Label id="contentLabel" for="content">
                    <Translate contentKey="totoroApp.procedure.content">Content</Translate>
                  </Label>
                  <AvField
                    id="procedure-content"
                    type="text"
                    name="content"
                    validate={{
                      required: { value: true, errorMessage: translate('entity.validation.required') }
                    }}
                  />
                </AvGroup>
                <AvGroup>
                  <Label id="priceLabel" for="price">
                    <Translate contentKey="totoroApp.procedure.price">Price</Translate>
                  </Label>
                  <AvField id="procedure-price" type="string" className="form-control" name="price" />
                </AvGroup>
                <AvGroup>
                  <Label for="procedureType.id">
                    <Translate contentKey="totoroApp.procedure.procedureType">Procedure Type</Translate>
                  </Label>
                  <AvInput id="procedure-procedureType" type="select" className="form-control" name="procedureType.id">
                    <option value="" key="0" />
                    {procedureTypes
                      ? procedureTypes.map(otherEntity => (
                          <option value={otherEntity.id} key={otherEntity.id}>
                            {otherEntity.id}
                          </option>
                        ))
                      : null}
                  </AvInput>
                </AvGroup>
                <Button tag={Link} id="cancel-save" to="/entity/procedure" replace color="info">
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
  procedureTypes: storeState.procedureType.entities,
  procedureEntity: storeState.procedure.entity,
  loading: storeState.procedure.loading,
  updating: storeState.procedure.updating,
  updateSuccess: storeState.procedure.updateSuccess
});

const mapDispatchToProps = {
  getProcedureTypes,
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
)(ProcedureUpdate);
