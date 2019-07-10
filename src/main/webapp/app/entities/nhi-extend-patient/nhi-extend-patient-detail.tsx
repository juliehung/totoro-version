import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
// tslint:disable-next-line:no-unused-variable
import { Translate, ICrudGetAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './nhi-extend-patient.reducer';
import { INhiExtendPatient } from 'app/shared/model/nhi-extend-patient.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface INhiExtendPatientDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export class NhiExtendPatientDetail extends React.Component<INhiExtendPatientDetailProps> {
  componentDidMount() {
    this.props.getEntity(this.props.match.params.id);
  }

  render() {
    const { nhiExtendPatientEntity } = this.props;
    return (
      <Row>
        <Col md="8">
          <h2>
            <Translate contentKey="totoroApp.nhiExtendPatient.detail.title">NhiExtendPatient</Translate> [<b>{nhiExtendPatientEntity.id}</b>
            ]
          </h2>
          <dl className="jh-entity-details">
            <dt>
              <span id="cardNumber">
                <Translate contentKey="totoroApp.nhiExtendPatient.cardNumber">Card Number</Translate>
              </span>
            </dt>
            <dd>{nhiExtendPatientEntity.cardNumber}</dd>
            <dt>
              <span id="cardAnnotation">
                <Translate contentKey="totoroApp.nhiExtendPatient.cardAnnotation">Card Annotation</Translate>
              </span>
            </dt>
            <dd>{nhiExtendPatientEntity.cardAnnotation}</dd>
            <dt>
              <span id="cardValidDate">
                <Translate contentKey="totoroApp.nhiExtendPatient.cardValidDate">Card Valid Date</Translate>
              </span>
            </dt>
            <dd>{nhiExtendPatientEntity.cardValidDate}</dd>
            <dt>
              <span id="cardIssueDate">
                <Translate contentKey="totoroApp.nhiExtendPatient.cardIssueDate">Card Issue Date</Translate>
              </span>
            </dt>
            <dd>{nhiExtendPatientEntity.cardIssueDate}</dd>
            <dt>
              <span id="nhiIdentity">
                <Translate contentKey="totoroApp.nhiExtendPatient.nhiIdentity">Nhi Identity</Translate>
              </span>
            </dt>
            <dd>{nhiExtendPatientEntity.nhiIdentity}</dd>
            <dt>
              <span id="availableTimes">
                <Translate contentKey="totoroApp.nhiExtendPatient.availableTimes">Available Times</Translate>
              </span>
            </dt>
            <dd>{nhiExtendPatientEntity.availableTimes}</dd>
            <dt>
              <span id="scaling">
                <Translate contentKey="totoroApp.nhiExtendPatient.scaling">Scaling</Translate>
              </span>
            </dt>
            <dd>{nhiExtendPatientEntity.scaling}</dd>
            <dt>
              <span id="fluoride">
                <Translate contentKey="totoroApp.nhiExtendPatient.fluoride">Fluoride</Translate>
              </span>
            </dt>
            <dd>{nhiExtendPatientEntity.fluoride}</dd>
            <dt>
              <span id="perio">
                <Translate contentKey="totoroApp.nhiExtendPatient.perio">Perio</Translate>
              </span>
            </dt>
            <dd>{nhiExtendPatientEntity.perio}</dd>
          </dl>
          <Button tag={Link} to="/entity/nhi-extend-patient" replace color="info">
            <FontAwesomeIcon icon="arrow-left" />{' '}
            <span className="d-none d-md-inline">
              <Translate contentKey="entity.action.back">Back</Translate>
            </span>
          </Button>
          &nbsp;
          <Button tag={Link} to={`/entity/nhi-extend-patient/${nhiExtendPatientEntity.id}/edit`} replace color="primary">
            <FontAwesomeIcon icon="pencil-alt" />{' '}
            <span className="d-none d-md-inline">
              <Translate contentKey="entity.action.edit">Edit</Translate>
            </span>
          </Button>
        </Col>
      </Row>
    );
  }
}

const mapStateToProps = ({ nhiExtendPatient }: IRootState) => ({
  nhiExtendPatientEntity: nhiExtendPatient.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(NhiExtendPatientDetail);
