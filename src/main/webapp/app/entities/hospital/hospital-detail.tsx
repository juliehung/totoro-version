import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
// tslint:disable-next-line:no-unused-variable
import { Translate, ICrudGetAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './hospital.reducer';
import { IHospital } from 'app/shared/model/hospital.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IHospitalDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export class HospitalDetail extends React.Component<IHospitalDetailProps> {
  componentDidMount() {
    this.props.getEntity(this.props.match.params.id);
  }

  render() {
    const { hospitalEntity } = this.props;
    return (
      <Row>
        <Col md="8">
          <h2>
            <Translate contentKey="totoroApp.hospital.detail.title">Hospital</Translate> [<b>{hospitalEntity.id}</b>]
          </h2>
          <dl className="jh-entity-details">
            <dt>
              <span id="hospitalId">
                <Translate contentKey="totoroApp.hospital.hospitalId">Hospital Id</Translate>
              </span>
            </dt>
            <dd>{hospitalEntity.hospitalId}</dd>
            <dt>
              <span id="name">
                <Translate contentKey="totoroApp.hospital.name">Name</Translate>
              </span>
            </dt>
            <dd>{hospitalEntity.name}</dd>
            <dt>
              <span id="address">
                <Translate contentKey="totoroApp.hospital.address">Address</Translate>
              </span>
            </dt>
            <dd>{hospitalEntity.address}</dd>
            <dt>
              <span id="branch">
                <Translate contentKey="totoroApp.hospital.branch">Branch</Translate>
              </span>
            </dt>
            <dd>{hospitalEntity.branch}</dd>
          </dl>
          <Button tag={Link} to="/entity/hospital" replace color="info">
            <FontAwesomeIcon icon="arrow-left" />{' '}
            <span className="d-none d-md-inline">
              <Translate contentKey="entity.action.back">Back</Translate>
            </span>
          </Button>
          &nbsp;
          <Button tag={Link} to={`/entity/hospital/${hospitalEntity.id}/edit`} replace color="primary">
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

const mapStateToProps = ({ hospital }: IRootState) => ({
  hospitalEntity: hospital.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(HospitalDetail);
