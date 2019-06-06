import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
// tslint:disable-next-line:no-unused-variable
import { Translate, translate, ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { getEntity, updateEntity, createEntity, reset } from './nhi-extend-patient.reducer';
import { INhiExtendPatient } from 'app/shared/model/nhi-extend-patient.model';
// tslint:disable-next-line:no-unused-variable
import { convertDateTimeFromServer } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface INhiExtendPatientUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export interface INhiExtendPatientUpdateState {
  isNew: boolean;
}

export class NhiExtendPatientUpdate extends React.Component<INhiExtendPatientUpdateProps, INhiExtendPatientUpdateState> {
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
      const { nhiExtendPatientEntity } = this.props;
      const entity = {
        ...nhiExtendPatientEntity,
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
    this.props.history.push('/entity/nhi-extend-patient');
  };

  render() {
    const { nhiExtendPatientEntity, loading, updating } = this.props;
    const { isNew } = this.state;

    return (
      <div>
        <Row className="justify-content-center">
          <Col md="8">
            <h2 id="totoroApp.nhiExtendPatient.home.createOrEditLabel">
              <Translate contentKey="totoroApp.nhiExtendPatient.home.createOrEditLabel">Create or edit a NhiExtendPatient</Translate>
            </h2>
          </Col>
        </Row>
        <Row className="justify-content-center">
          <Col md="8">
            {loading ? (
              <p>Loading...</p>
            ) : (
              <AvForm model={isNew ? {} : nhiExtendPatientEntity} onSubmit={this.saveEntity}>
                {!isNew ? (
                  <AvGroup>
                    <Label for="id">
                      <Translate contentKey="global.field.id">ID</Translate>
                    </Label>
                    <AvInput id="nhi-extend-patient-id" type="text" className="form-control" name="id" required readOnly />
                  </AvGroup>
                ) : null}
                <AvGroup>
                  <Label id="cardNumberLabel" for="cardNumber">
                    <Translate contentKey="totoroApp.nhiExtendPatient.cardNumber">Card Number</Translate>
                  </Label>
                  <AvField id="nhi-extend-patient-cardNumber" type="text" name="cardNumber" />
                </AvGroup>
                <AvGroup>
                  <Label id="cardAnnotationLabel" for="cardAnnotation">
                    <Translate contentKey="totoroApp.nhiExtendPatient.cardAnnotation">Card Annotation</Translate>
                  </Label>
                  <AvField id="nhi-extend-patient-cardAnnotation" type="text" name="cardAnnotation" />
                </AvGroup>
                <AvGroup>
                  <Label id="cardValidDateLabel" for="cardValidDate">
                    <Translate contentKey="totoroApp.nhiExtendPatient.cardValidDate">Card Valid Date</Translate>
                  </Label>
                  <AvField id="nhi-extend-patient-cardValidDate" type="date" className="form-control" name="cardValidDate" />
                </AvGroup>
                <AvGroup>
                  <Label id="cardIssueDateLabel" for="cardIssueDate">
                    <Translate contentKey="totoroApp.nhiExtendPatient.cardIssueDate">Card Issue Date</Translate>
                  </Label>
                  <AvField id="nhi-extend-patient-cardIssueDate" type="date" className="form-control" name="cardIssueDate" />
                </AvGroup>
                <AvGroup>
                  <Label id="nhiIdentityLabel" for="nhiIdentity">
                    <Translate contentKey="totoroApp.nhiExtendPatient.nhiIdentity">Nhi Identity</Translate>
                  </Label>
                  <AvField id="nhi-extend-patient-nhiIdentity" type="text" name="nhiIdentity" />
                </AvGroup>
                <AvGroup>
                  <Label id="availableTimesLabel" for="availableTimes">
                    <Translate contentKey="totoroApp.nhiExtendPatient.availableTimes">Available Times</Translate>
                  </Label>
                  <AvField id="nhi-extend-patient-availableTimes" type="string" className="form-control" name="availableTimes" />
                </AvGroup>
                <Button tag={Link} id="cancel-save" to="/entity/nhi-extend-patient" replace color="info">
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
  nhiExtendPatientEntity: storeState.nhiExtendPatient.entity,
  loading: storeState.nhiExtendPatient.loading,
  updating: storeState.nhiExtendPatient.updating,
  updateSuccess: storeState.nhiExtendPatient.updateSuccess
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
)(NhiExtendPatientUpdate);
