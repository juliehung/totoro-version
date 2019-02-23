import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
// tslint:disable-next-line:no-unused-variable
import { Translate, ICrudGetAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './prescription.reducer';
import { IPrescription } from 'app/shared/model/prescription.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IPrescriptionDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export class PrescriptionDetail extends React.Component<IPrescriptionDetailProps> {
  componentDidMount() {
    this.props.getEntity(this.props.match.params.id);
  }

  render() {
    const { prescriptionEntity } = this.props;
    return (
      <Row>
        <Col md="8">
          <h2>
            <Translate contentKey="totoroApp.prescription.detail.title">Prescription</Translate> [<b>{prescriptionEntity.id}</b>]
          </h2>
          <dl className="jh-entity-details">
            <dt>
              <span id="clinicAdministration">
                <Translate contentKey="totoroApp.prescription.clinicAdministration">Clinic Administration</Translate>
              </span>
            </dt>
            <dd>{prescriptionEntity.clinicAdministration ? 'true' : 'false'}</dd>
            <dt>
              <span id="antiInflammatoryDrug">
                <Translate contentKey="totoroApp.prescription.antiInflammatoryDrug">Anti Inflammatory Drug</Translate>
              </span>
            </dt>
            <dd>{prescriptionEntity.antiInflammatoryDrug ? 'true' : 'false'}</dd>
            <dt>
              <span id="pain">
                <Translate contentKey="totoroApp.prescription.pain">Pain</Translate>
              </span>
            </dt>
            <dd>{prescriptionEntity.pain ? 'true' : 'false'}</dd>
            <dt>
              <span id="takenAll">
                <Translate contentKey="totoroApp.prescription.takenAll">Taken All</Translate>
              </span>
            </dt>
            <dd>{prescriptionEntity.takenAll ? 'true' : 'false'}</dd>
            <dt>
              <span id="status">
                <Translate contentKey="totoroApp.prescription.status">Status</Translate>
              </span>
            </dt>
            <dd>{prescriptionEntity.status}</dd>
          </dl>
          <Button tag={Link} to="/entity/prescription" replace color="info">
            <FontAwesomeIcon icon="arrow-left" />{' '}
            <span className="d-none d-md-inline">
              <Translate contentKey="entity.action.back">Back</Translate>
            </span>
          </Button>
          &nbsp;
          <Button tag={Link} to={`/entity/prescription/${prescriptionEntity.id}/edit`} replace color="primary">
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

const mapStateToProps = ({ prescription }: IRootState) => ({
  prescriptionEntity: prescription.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(PrescriptionDetail);
