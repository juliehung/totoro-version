import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
// tslint:disable-next-line:no-unused-variable
import { Translate, ICrudGetAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './patient-identity.reducer';
import { IPatientIdentity } from 'app/shared/model/patient-identity.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IPatientIdentityDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export class PatientIdentityDetail extends React.Component<IPatientIdentityDetailProps> {
  componentDidMount() {
    this.props.getEntity(this.props.match.params.id);
  }

  render() {
    const { patientIdentityEntity } = this.props;
    return (
      <Row>
        <Col md="8">
          <h2>
            <Translate contentKey="totoroApp.patientIdentity.detail.title">PatientIdentity</Translate> [<b>{patientIdentityEntity.id}</b>]
          </h2>
          <dl className="jh-entity-details">
            <dt>
              <span id="code">
                <Translate contentKey="totoroApp.patientIdentity.code">Code</Translate>
              </span>
            </dt>
            <dd>{patientIdentityEntity.code}</dd>
            <dt>
              <span id="name">
                <Translate contentKey="totoroApp.patientIdentity.name">Name</Translate>
              </span>
            </dt>
            <dd>{patientIdentityEntity.name}</dd>
            <dt>
              <span id="freeBurden">
                <Translate contentKey="totoroApp.patientIdentity.freeBurden">Free Burden</Translate>
              </span>
            </dt>
            <dd>{patientIdentityEntity.freeBurden ? 'true' : 'false'}</dd>
          </dl>
          <Button tag={Link} to="/entity/patient-identity" replace color="info">
            <FontAwesomeIcon icon="arrow-left" />{' '}
            <span className="d-none d-md-inline">
              <Translate contentKey="entity.action.back">Back</Translate>
            </span>
          </Button>
          &nbsp;
          <Button tag={Link} to={`/entity/patient-identity/${patientIdentityEntity.id}/edit`} replace color="primary">
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

const mapStateToProps = ({ patientIdentity }: IRootState) => ({
  patientIdentityEntity: patientIdentity.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(PatientIdentityDetail);
