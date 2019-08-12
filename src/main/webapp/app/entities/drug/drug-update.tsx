import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
// tslint:disable-next-line:no-unused-variable
import { Translate, translate, ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { getEntity, updateEntity, createEntity, reset } from './drug.reducer';
import { IDrug } from 'app/shared/model/drug.model';
// tslint:disable-next-line:no-unused-variable
import { convertDateTimeFromServer } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IDrugUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export interface IDrugUpdateState {
  isNew: boolean;
}

export class DrugUpdate extends React.Component<IDrugUpdateProps, IDrugUpdateState> {
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
    values.lastModifiedDate = new Date(values.lastModifiedDate);

    if (errors.length === 0) {
      const { drugEntity } = this.props;
      const entity = {
        ...drugEntity,
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
    this.props.history.push('/entity/drug');
  };

  render() {
    const { drugEntity, loading, updating } = this.props;
    const { isNew } = this.state;

    return (
      <div>
        <Row className="justify-content-center">
          <Col md="8">
            <h2 id="totoroApp.drug.home.createOrEditLabel">
              <Translate contentKey="totoroApp.drug.home.createOrEditLabel">Create or edit a Drug</Translate>
            </h2>
          </Col>
        </Row>
        <Row className="justify-content-center">
          <Col md="8">
            {loading ? (
              <p>Loading...</p>
            ) : (
              <AvForm model={isNew ? {} : drugEntity} onSubmit={this.saveEntity}>
                {!isNew ? (
                  <AvGroup>
                    <Label for="id">
                      <Translate contentKey="global.field.id">ID</Translate>
                    </Label>
                    <AvInput id="drug-id" type="text" className="form-control" name="id" required readOnly />
                  </AvGroup>
                ) : null}
                <AvGroup>
                  <Label id="nameLabel" for="name">
                    <Translate contentKey="totoroApp.drug.name">Name</Translate>
                  </Label>
                  <AvField
                    id="drug-name"
                    type="text"
                    name="name"
                    validate={{
                      required: { value: true, errorMessage: translate('entity.validation.required') }
                    }}
                  />
                </AvGroup>
                <AvGroup>
                  <Label id="chineseNameLabel" for="chineseName">
                    <Translate contentKey="totoroApp.drug.chineseName">Chinese Name</Translate>
                  </Label>
                  <AvField id="drug-chineseName" type="text" name="chineseName" />
                </AvGroup>
                <AvGroup>
                  <Label id="unitLabel" for="unit">
                    <Translate contentKey="totoroApp.drug.unit">Unit</Translate>
                  </Label>
                  <AvField id="drug-unit" type="text" name="unit" />
                </AvGroup>
                <AvGroup>
                  <Label id="priceLabel" for="price">
                    <Translate contentKey="totoroApp.drug.price">Price</Translate>
                  </Label>
                  <AvField id="drug-price" type="string" className="form-control" name="price" />
                </AvGroup>
                <AvGroup>
                  <Label id="quantityLabel" for="quantity">
                    <Translate contentKey="totoroApp.drug.quantity">Quantity</Translate>
                  </Label>
                  <AvField id="drug-quantity" type="string" className="form-control" name="quantity" />
                </AvGroup>
                <AvGroup>
                  <Label id="frequencyLabel" for="frequency">
                    <Translate contentKey="totoroApp.drug.frequency">Frequency</Translate>
                  </Label>
                  <AvField id="drug-frequency" type="text" name="frequency" />
                </AvGroup>
                <AvGroup>
                  <Label id="wayLabel" for="way">
                    <Translate contentKey="totoroApp.drug.way">Way</Translate>
                  </Label>
                  <AvField id="drug-way" type="text" name="way" />
                </AvGroup>
                <AvGroup>
                  <Label id="nhiCodeLabel" for="nhiCode">
                    <Translate contentKey="totoroApp.drug.nhiCode">Nhi Code</Translate>
                  </Label>
                  <AvField id="drug-nhiCode" type="text" name="nhiCode" />
                </AvGroup>
                <AvGroup>
                  <Label id="warningLabel" for="warning">
                    <Translate contentKey="totoroApp.drug.warning">Warning</Translate>
                  </Label>
                  <AvField id="drug-warning" type="text" name="warning" />
                </AvGroup>
                <AvGroup>
                  <Label id="daysLabel" for="days">
                    <Translate contentKey="totoroApp.drug.days">Days</Translate>
                  </Label>
                  <AvField id="drug-days" type="string" className="form-control" name="days" />
                </AvGroup>
                <AvGroup>
                  <Label id="orderLabel" for="order">
                    <Translate contentKey="totoroApp.drug.order">Order</Translate>
                  </Label>
                  <AvField id="drug-order" type="string" className="form-control" name="order" />
                </AvGroup>
                <AvGroup>
                  <Label id="createdDateLabel" for="createdDate">
                    <Translate contentKey="totoroApp.drug.createdDate">Created Date</Translate>
                  </Label>
                  <AvInput
                    id="drug-createdDate"
                    type="datetime-local"
                    className="form-control"
                    name="createdDate"
                    value={isNew ? null : convertDateTimeFromServer(this.props.drugEntity.createdDate)}
                  />
                </AvGroup>
                <AvGroup>
                  <Label id="createdByLabel" for="createdBy">
                    <Translate contentKey="totoroApp.drug.createdBy">Created By</Translate>
                  </Label>
                  <AvField id="drug-createdBy" type="text" name="createdBy" />
                </AvGroup>
                <AvGroup>
                  <Label id="lastModifiedDateLabel" for="lastModifiedDate">
                    <Translate contentKey="totoroApp.drug.lastModifiedDate">Last Modified Date</Translate>
                  </Label>
                  <AvInput
                    id="drug-lastModifiedDate"
                    type="datetime-local"
                    className="form-control"
                    name="lastModifiedDate"
                    value={isNew ? null : convertDateTimeFromServer(this.props.drugEntity.lastModifiedDate)}
                  />
                </AvGroup>
                <AvGroup>
                  <Label id="LastModifiedByLabel" for="LastModifiedBy">
                    <Translate contentKey="totoroApp.drug.LastModifiedBy">Last Modified By</Translate>
                  </Label>
                  <AvField id="drug-LastModifiedBy" type="text" name="LastModifiedBy" />
                </AvGroup>
                <Button tag={Link} id="cancel-save" to="/entity/drug" replace color="info">
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
  drugEntity: storeState.drug.entity,
  loading: storeState.drug.loading,
  updating: storeState.drug.updating,
  updateSuccess: storeState.drug.updateSuccess
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
)(DrugUpdate);
