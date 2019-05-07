import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
// tslint:disable-next-line:no-unused-variable
import { Translate, translate, ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { INhiIcd9Cm } from 'app/shared/model/nhi-icd-9-cm.model';
import { getEntities as getNhiIcd9Cms } from 'app/entities/nhi-icd-9-cm/nhi-icd-9-cm.reducer';
import { getEntity, updateEntity, createEntity, reset } from './nhi-icd-10-cm.reducer';
import { INhiIcd10Cm } from 'app/shared/model/nhi-icd-10-cm.model';
// tslint:disable-next-line:no-unused-variable
import { convertDateTimeFromServer } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface INhiIcd10CmUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export interface INhiIcd10CmUpdateState {
  isNew: boolean;
  nhiIcd9CmId: string;
}

export class NhiIcd10CmUpdate extends React.Component<INhiIcd10CmUpdateProps, INhiIcd10CmUpdateState> {
  constructor(props) {
    super(props);
    this.state = {
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

    this.props.getNhiIcd9Cms();
  }

  saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const { nhiIcd10CmEntity } = this.props;
      const entity = {
        ...nhiIcd10CmEntity,
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
    this.props.history.push('/entity/nhi-icd-10-cm');
  };

  render() {
    const { nhiIcd10CmEntity, nhiIcd9Cms, loading, updating } = this.props;
    const { isNew } = this.state;

    return (
      <div>
        <Row className="justify-content-center">
          <Col md="8">
            <h2 id="totoroApp.nhiIcd10Cm.home.createOrEditLabel">
              <Translate contentKey="totoroApp.nhiIcd10Cm.home.createOrEditLabel">Create or edit a NhiIcd10Cm</Translate>
            </h2>
          </Col>
        </Row>
        <Row className="justify-content-center">
          <Col md="8">
            {loading ? (
              <p>Loading...</p>
            ) : (
              <AvForm model={isNew ? {} : nhiIcd10CmEntity} onSubmit={this.saveEntity}>
                {!isNew ? (
                  <AvGroup>
                    <Label for="id">
                      <Translate contentKey="global.field.id">ID</Translate>
                    </Label>
                    <AvInput id="nhi-icd-10-cm-id" type="text" className="form-control" name="id" required readOnly />
                  </AvGroup>
                ) : null}
                <AvGroup>
                  <Label id="codeLabel" for="code">
                    <Translate contentKey="totoroApp.nhiIcd10Cm.code">Code</Translate>
                  </Label>
                  <AvField
                    id="nhi-icd-10-cm-code"
                    type="text"
                    name="code"
                    validate={{
                      required: { value: true, errorMessage: translate('entity.validation.required') }
                    }}
                  />
                </AvGroup>
                <AvGroup>
                  <Label id="nameLabel" for="name">
                    <Translate contentKey="totoroApp.nhiIcd10Cm.name">Name</Translate>
                  </Label>
                  <AvField id="nhi-icd-10-cm-name" type="text" name="name" />
                </AvGroup>
                <AvGroup>
                  <Label id="englishNameLabel" for="englishName">
                    <Translate contentKey="totoroApp.nhiIcd10Cm.englishName">English Name</Translate>
                  </Label>
                  <AvField id="nhi-icd-10-cm-englishName" type="text" name="englishName" />
                </AvGroup>
                <AvGroup>
                  <Label for="nhiIcd9Cm.id">
                    <Translate contentKey="totoroApp.nhiIcd10Cm.nhiIcd9Cm">Nhi Icd 9 Cm</Translate>
                  </Label>
                  <AvInput id="nhi-icd-10-cm-nhiIcd9Cm" type="select" className="form-control" name="nhiIcd9Cm.id">
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
                <Button tag={Link} id="cancel-save" to="/entity/nhi-icd-10-cm" replace color="info">
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
  nhiIcd9Cms: storeState.nhiIcd9Cm.entities,
  nhiIcd10CmEntity: storeState.nhiIcd10Cm.entity,
  loading: storeState.nhiIcd10Cm.loading,
  updating: storeState.nhiIcd10Cm.updating,
  updateSuccess: storeState.nhiIcd10Cm.updateSuccess
});

const mapDispatchToProps = {
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
)(NhiIcd10CmUpdate);
