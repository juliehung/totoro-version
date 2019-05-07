import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
// tslint:disable-next-line:no-unused-variable
import { Translate, ICrudGetAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './treatment-drug.reducer';
import { ITreatmentDrug } from 'app/shared/model/treatment-drug.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface ITreatmentDrugDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export class TreatmentDrugDetail extends React.Component<ITreatmentDrugDetailProps> {
  componentDidMount() {
    this.props.getEntity(this.props.match.params.id);
  }

  render() {
    const { treatmentDrugEntity } = this.props;
    return (
      <Row>
        <Col md="8">
          <h2>
            <Translate contentKey="totoroApp.treatmentDrug.detail.title">TreatmentDrug</Translate> [<b>{treatmentDrugEntity.id}</b>]
          </h2>
          <dl className="jh-entity-details">
            <dt>
              <span id="day">
                <Translate contentKey="totoroApp.treatmentDrug.day">Day</Translate>
              </span>
            </dt>
            <dd>{treatmentDrugEntity.day}</dd>
            <dt>
              <span id="frequency">
                <Translate contentKey="totoroApp.treatmentDrug.frequency">Frequency</Translate>
              </span>
            </dt>
            <dd>{treatmentDrugEntity.frequency}</dd>
            <dt>
              <span id="way">
                <Translate contentKey="totoroApp.treatmentDrug.way">Way</Translate>
              </span>
            </dt>
            <dd>{treatmentDrugEntity.way}</dd>
            <dt>
              <span id="quantity">
                <Translate contentKey="totoroApp.treatmentDrug.quantity">Quantity</Translate>
              </span>
            </dt>
            <dd>{treatmentDrugEntity.quantity}</dd>
            <dt>
              <Translate contentKey="totoroApp.treatmentDrug.prescription">Prescription</Translate>
            </dt>
            <dd>{treatmentDrugEntity.prescription ? treatmentDrugEntity.prescription.id : ''}</dd>
            <dt>
              <Translate contentKey="totoroApp.treatmentDrug.drug">Drug</Translate>
            </dt>
            <dd>{treatmentDrugEntity.drug ? treatmentDrugEntity.drug.id : ''}</dd>
          </dl>
          <Button tag={Link} to="/entity/treatment-drug" replace color="info">
            <FontAwesomeIcon icon="arrow-left" />{' '}
            <span className="d-none d-md-inline">
              <Translate contentKey="entity.action.back">Back</Translate>
            </span>
          </Button>
          &nbsp;
          <Button tag={Link} to={`/entity/treatment-drug/${treatmentDrugEntity.id}/edit`} replace color="primary">
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

const mapStateToProps = ({ treatmentDrug }: IRootState) => ({
  treatmentDrugEntity: treatmentDrug.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(TreatmentDrugDetail);
