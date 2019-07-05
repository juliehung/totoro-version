import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
// tslint:disable-next-line:no-unused-variable
import { Translate, translate, ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { getEntity, updateEntity, createEntity, reset } from './relationship-options.reducer';
import { IRelationshipOptions } from 'app/shared/model/relationship-options.model';
// tslint:disable-next-line:no-unused-variable
import { convertDateTimeFromServer } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IRelationshipOptionsUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export interface IRelationshipOptionsUpdateState {
  isNew: boolean;
}

export class RelationshipOptionsUpdate extends React.Component<IRelationshipOptionsUpdateProps, IRelationshipOptionsUpdateState> {
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
    if (errors.length === 0) {
      const { relationshipOptionsEntity } = this.props;
      const entity = {
        ...relationshipOptionsEntity,
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
    this.props.history.push('/entity/relationship-options');
  };

  render() {
    const { relationshipOptionsEntity, loading, updating } = this.props;
    const { isNew } = this.state;

    return (
      <div>
        <Row className="justify-content-center">
          <Col md="8">
            <h2 id="totoroApp.relationshipOptions.home.createOrEditLabel">
              <Translate contentKey="totoroApp.relationshipOptions.home.createOrEditLabel">Create or edit a RelationshipOptions</Translate>
            </h2>
          </Col>
        </Row>
        <Row className="justify-content-center">
          <Col md="8">
            {loading ? (
              <p>Loading...</p>
            ) : (
              <AvForm model={isNew ? {} : relationshipOptionsEntity} onSubmit={this.saveEntity}>
                {!isNew ? (
                  <AvGroup>
                    <Label for="id">
                      <Translate contentKey="global.field.id">ID</Translate>
                    </Label>
                    <AvInput id="relationship-options-id" type="text" className="form-control" name="id" required readOnly />
                  </AvGroup>
                ) : null}
                <AvGroup>
                  <Label id="codeLabel" for="code">
                    <Translate contentKey="totoroApp.relationshipOptions.code">Code</Translate>
                  </Label>
                  <AvField id="relationship-options-code" type="text" name="code" />
                </AvGroup>
                <AvGroup>
                  <Label id="nameLabel" for="name">
                    <Translate contentKey="totoroApp.relationshipOptions.name">Name</Translate>
                  </Label>
                  <AvField id="relationship-options-name" type="text" name="name" />
                </AvGroup>
                <AvGroup>
                  <Label id="orderLabel" for="order">
                    <Translate contentKey="totoroApp.relationshipOptions.order">Order</Translate>
                  </Label>
                  <AvField id="relationship-options-order" type="string" className="form-control" name="order" />
                </AvGroup>
                <Button tag={Link} id="cancel-save" to="/entity/relationship-options" replace color="info">
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
  relationshipOptionsEntity: storeState.relationshipOptions.entity,
  loading: storeState.relationshipOptions.loading,
  updating: storeState.relationshipOptions.updating,
  updateSuccess: storeState.relationshipOptions.updateSuccess
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
)(RelationshipOptionsUpdate);
