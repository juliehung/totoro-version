import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
// tslint:disable-next-line:no-unused-variable
import { Translate, translate, ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { INhiProcedure } from 'app/shared/model/nhi-procedure.model';
import { getEntities as getNhiProcedures } from 'app/entities/nhi-procedure/nhi-procedure.reducer';
import { getEntity, updateEntity, createEntity, reset } from './nhi-icd-10-pcs.reducer';
import { INhiIcd10Pcs } from 'app/shared/model/nhi-icd-10-pcs.model';
// tslint:disable-next-line:no-unused-variable
import { convertDateTimeFromServer } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface INhiIcd10PcsUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export interface INhiIcd10PcsUpdateState {
  isNew: boolean;
  nhiProcedureId: string;
}

export class NhiIcd10PcsUpdate extends React.Component<INhiIcd10PcsUpdateProps, INhiIcd10PcsUpdateState> {
  constructor(props) {
    super(props);
    this.state = {
      nhiProcedureId: '0',
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

    this.props.getNhiProcedures();
  }

  saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const { nhiIcd10PcsEntity } = this.props;
      const entity = {
        ...nhiIcd10PcsEntity,
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
    this.props.history.push('/entity/nhi-icd-10-pcs');
  };

  render() {
    const { nhiIcd10PcsEntity, nhiProcedures, loading, updating } = this.props;
    const { isNew } = this.state;

    return (
      <div>
        <Row className="justify-content-center">
          <Col md="8">
            <h2 id="totoroApp.nhiIcd10Pcs.home.createOrEditLabel">
              <Translate contentKey="totoroApp.nhiIcd10Pcs.home.createOrEditLabel">Create or edit a NhiIcd10Pcs</Translate>
            </h2>
          </Col>
        </Row>
        <Row className="justify-content-center">
          <Col md="8">
            {loading ? (
              <p>Loading...</p>
            ) : (
              <AvForm model={isNew ? {} : nhiIcd10PcsEntity} onSubmit={this.saveEntity}>
                {!isNew ? (
                  <AvGroup>
                    <Label for="id">
                      <Translate contentKey="global.field.id">ID</Translate>
                    </Label>
                    <AvInput id="nhi-icd-10-pcs-id" type="text" className="form-control" name="id" required readOnly />
                  </AvGroup>
                ) : null}
                <AvGroup>
                  <Label id="codeLabel" for="code">
                    <Translate contentKey="totoroApp.nhiIcd10Pcs.code">Code</Translate>
                  </Label>
                  <AvField
                    id="nhi-icd-10-pcs-code"
                    type="text"
                    name="code"
                    validate={{
                      required: { value: true, errorMessage: translate('entity.validation.required') }
                    }}
                  />
                </AvGroup>
                <AvGroup>
                  <Label id="nhiNameLabel" for="nhiName">
                    <Translate contentKey="totoroApp.nhiIcd10Pcs.nhiName">Nhi Name</Translate>
                  </Label>
                  <AvField id="nhi-icd-10-pcs-nhiName" type="text" name="nhiName" />
                </AvGroup>
                <AvGroup>
                  <Label id="englishNameLabel" for="englishName">
                    <Translate contentKey="totoroApp.nhiIcd10Pcs.englishName">English Name</Translate>
                  </Label>
                  <AvField id="nhi-icd-10-pcs-englishName" type="text" name="englishName" />
                </AvGroup>
                <AvGroup>
                  <Label id="chineseNameLabel" for="chineseName">
                    <Translate contentKey="totoroApp.nhiIcd10Pcs.chineseName">Chinese Name</Translate>
                  </Label>
                  <AvField id="nhi-icd-10-pcs-chineseName" type="text" name="chineseName" />
                </AvGroup>
                <AvGroup>
                  <Label for="nhiProcedure.id">
                    <Translate contentKey="totoroApp.nhiIcd10Pcs.nhiProcedure">Nhi Procedure</Translate>
                  </Label>
                  <AvInput id="nhi-icd-10-pcs-nhiProcedure" type="select" className="form-control" name="nhiProcedure.id">
                    <option value="" key="0" />
                    {nhiProcedures
                      ? nhiProcedures.map(otherEntity => (
                          <option value={otherEntity.id} key={otherEntity.id}>
                            {otherEntity.id}
                          </option>
                        ))
                      : null}
                  </AvInput>
                </AvGroup>
                <Button tag={Link} id="cancel-save" to="/entity/nhi-icd-10-pcs" replace color="info">
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
  nhiProcedures: storeState.nhiProcedure.entities,
  nhiIcd10PcsEntity: storeState.nhiIcd10Pcs.entity,
  loading: storeState.nhiIcd10Pcs.loading,
  updating: storeState.nhiIcd10Pcs.updating,
  updateSuccess: storeState.nhiIcd10Pcs.updateSuccess
});

const mapDispatchToProps = {
  getNhiProcedures,
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
)(NhiIcd10PcsUpdate);
