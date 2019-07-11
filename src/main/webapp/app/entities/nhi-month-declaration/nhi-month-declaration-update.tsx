import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
// tslint:disable-next-line:no-unused-variable
import { Translate, translate, ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { getEntity, updateEntity, createEntity, reset } from './nhi-month-declaration.reducer';
import { INhiMonthDeclaration } from 'app/shared/model/nhi-month-declaration.model';
// tslint:disable-next-line:no-unused-variable
import { convertDateTimeFromServer } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface INhiMonthDeclarationUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export interface INhiMonthDeclarationUpdateState {
  isNew: boolean;
}

export class NhiMonthDeclarationUpdate extends React.Component<INhiMonthDeclarationUpdateProps, INhiMonthDeclarationUpdateState> {
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
      const { nhiMonthDeclarationEntity } = this.props;
      const entity = {
        ...nhiMonthDeclarationEntity,
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
    this.props.history.push('/entity/nhi-month-declaration');
  };

  render() {
    const { nhiMonthDeclarationEntity, loading, updating } = this.props;
    const { isNew } = this.state;

    return (
      <div>
        <Row className="justify-content-center">
          <Col md="8">
            <h2 id="totoroApp.nhiMonthDeclaration.home.createOrEditLabel">
              <Translate contentKey="totoroApp.nhiMonthDeclaration.home.createOrEditLabel">Create or edit a NhiMonthDeclaration</Translate>
            </h2>
          </Col>
        </Row>
        <Row className="justify-content-center">
          <Col md="8">
            {loading ? (
              <p>Loading...</p>
            ) : (
              <AvForm model={isNew ? {} : nhiMonthDeclarationEntity} onSubmit={this.saveEntity}>
                {!isNew ? (
                  <AvGroup>
                    <Label for="id">
                      <Translate contentKey="global.field.id">ID</Translate>
                    </Label>
                    <AvInput id="nhi-month-declaration-id" type="text" className="form-control" name="id" required readOnly />
                  </AvGroup>
                ) : null}
                <AvGroup>
                  <Label id="yearMonthLabel" for="yearMonth">
                    <Translate contentKey="totoroApp.nhiMonthDeclaration.yearMonth">Year Month</Translate>
                  </Label>
                  <AvField
                    id="nhi-month-declaration-yearMonth"
                    type="text"
                    name="yearMonth"
                    validate={{
                      required: { value: true, errorMessage: translate('entity.validation.required') }
                    }}
                  />
                </AvGroup>
                <AvGroup>
                  <Label id="institutionLabel" for="institution">
                    <Translate contentKey="totoroApp.nhiMonthDeclaration.institution">Institution</Translate>
                  </Label>
                  <AvField id="nhi-month-declaration-institution" type="text" name="institution" />
                </AvGroup>
                <Button tag={Link} id="cancel-save" to="/entity/nhi-month-declaration" replace color="info">
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
  nhiMonthDeclarationEntity: storeState.nhiMonthDeclaration.entity,
  loading: storeState.nhiMonthDeclaration.loading,
  updating: storeState.nhiMonthDeclaration.updating,
  updateSuccess: storeState.nhiMonthDeclaration.updateSuccess
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
)(NhiMonthDeclarationUpdate);
