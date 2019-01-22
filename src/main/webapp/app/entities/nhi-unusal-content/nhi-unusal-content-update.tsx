import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
// tslint:disable-next-line:no-unused-variable
import { Translate, translate, ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { getEntity, updateEntity, createEntity, reset } from './nhi-unusal-content.reducer';
import { INHIUnusalContent } from 'app/shared/model/nhi-unusal-content.model';
// tslint:disable-next-line:no-unused-variable
import { convertDateTimeFromServer } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface INHIUnusalContentUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export interface INHIUnusalContentUpdateState {
  isNew: boolean;
}

export class NHIUnusalContentUpdate extends React.Component<INHIUnusalContentUpdateProps, INHIUnusalContentUpdateState> {
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
      const { nHIUnusalContentEntity } = this.props;
      const entity = {
        ...nHIUnusalContentEntity,
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
    this.props.history.push('/entity/nhi-unusal-content');
  };

  render() {
    const { nHIUnusalContentEntity, loading, updating } = this.props;
    const { isNew } = this.state;

    return (
      <div>
        <Row className="justify-content-center">
          <Col md="8">
            <h2 id="totoroApp.nHIUnusalContent.home.createOrEditLabel">
              <Translate contentKey="totoroApp.nHIUnusalContent.home.createOrEditLabel">Create or edit a NHIUnusalContent</Translate>
            </h2>
          </Col>
        </Row>
        <Row className="justify-content-center">
          <Col md="8">
            {loading ? (
              <p>Loading...</p>
            ) : (
              <AvForm model={isNew ? {} : nHIUnusalContentEntity} onSubmit={this.saveEntity}>
                {!isNew ? (
                  <AvGroup>
                    <Label for="id">
                      <Translate contentKey="global.field.id">ID</Translate>
                    </Label>
                    <AvInput id="nhi-unusal-content-id" type="text" className="form-control" name="id" required readOnly />
                  </AvGroup>
                ) : null}
                <AvGroup>
                  <Label id="contentLabel" for="content">
                    <Translate contentKey="totoroApp.nHIUnusalContent.content">Content</Translate>
                  </Label>
                  <AvField
                    id="nhi-unusal-content-content"
                    type="text"
                    name="content"
                    validate={{
                      required: { value: true, errorMessage: translate('entity.validation.required') }
                    }}
                  />
                </AvGroup>
                <AvGroup>
                  <Label id="noSeqNumberLabel" for="noSeqNumber">
                    <Translate contentKey="totoroApp.nHIUnusalContent.noSeqNumber">No Seq Number</Translate>
                  </Label>
                  <AvField id="nhi-unusal-content-noSeqNumber" type="text" name="noSeqNumber" />
                </AvGroup>
                <AvGroup>
                  <Label id="gotSeqNumberLabel" for="gotSeqNumber">
                    <Translate contentKey="totoroApp.nHIUnusalContent.gotSeqNumber">Got Seq Number</Translate>
                  </Label>
                  <AvField id="nhi-unusal-content-gotSeqNumber" type="text" name="gotSeqNumber" />
                </AvGroup>
                <Button tag={Link} id="cancel-save" to="/entity/nhi-unusal-content" replace color="info">
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
  nHIUnusalContentEntity: storeState.nHIUnusalContent.entity,
  loading: storeState.nHIUnusalContent.loading,
  updating: storeState.nHIUnusalContent.updating,
  updateSuccess: storeState.nHIUnusalContent.updateSuccess
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
)(NHIUnusalContentUpdate);
