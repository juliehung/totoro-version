import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
// tslint:disable-next-line:no-unused-variable
import { Translate, ICrudGetAction, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './nhi-accumulated-medical-record.reducer';
import { INhiAccumulatedMedicalRecord } from 'app/shared/model/nhi-accumulated-medical-record.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface INhiAccumulatedMedicalRecordDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export class NhiAccumulatedMedicalRecordDetail extends React.Component<INhiAccumulatedMedicalRecordDetailProps> {
  componentDidMount() {
    this.props.getEntity(this.props.match.params.id);
  }

  render() {
    const { nhiAccumulatedMedicalRecordEntity } = this.props;
    return (
      <Row>
        <Col md="8">
          <h2>
            <Translate contentKey="totoroApp.nhiAccumulatedMedicalRecord.detail.title">NhiAccumulatedMedicalRecord</Translate> [
            <b>{nhiAccumulatedMedicalRecordEntity.id}</b>]
          </h2>
          <dl className="jh-entity-details">
            <dt>
              <span id="medicalCategory">
                <Translate contentKey="totoroApp.nhiAccumulatedMedicalRecord.medicalCategory">Medical Category</Translate>
              </span>
            </dt>
            <dd>{nhiAccumulatedMedicalRecordEntity.medicalCategory}</dd>
            <dt>
              <span id="newbornMedicalTreatmentNote">
                <Translate contentKey="totoroApp.nhiAccumulatedMedicalRecord.newbornMedicalTreatmentNote">
                  Newborn Medical Treatment Note
                </Translate>
              </span>
            </dt>
            <dd>{nhiAccumulatedMedicalRecordEntity.newbornMedicalTreatmentNote}</dd>
            <dt>
              <span id="date">
                <Translate contentKey="totoroApp.nhiAccumulatedMedicalRecord.date">Date</Translate>
              </span>
            </dt>
            <dd>
              <TextFormat value={nhiAccumulatedMedicalRecordEntity.date} type="date" format={APP_DATE_FORMAT} />
            </dd>
            <dt>
              <span id="cardFillingNote">
                <Translate contentKey="totoroApp.nhiAccumulatedMedicalRecord.cardFillingNote">Card Filling Note</Translate>
              </span>
            </dt>
            <dd>{nhiAccumulatedMedicalRecordEntity.cardFillingNote}</dd>
            <dt>
              <span id="seqNumber">
                <Translate contentKey="totoroApp.nhiAccumulatedMedicalRecord.seqNumber">Seq Number</Translate>
              </span>
            </dt>
            <dd>{nhiAccumulatedMedicalRecordEntity.seqNumber}</dd>
            <dt>
              <span id="medicalInstitutionCode">
                <Translate contentKey="totoroApp.nhiAccumulatedMedicalRecord.medicalInstitutionCode">Medical Institution Code</Translate>
              </span>
            </dt>
            <dd>{nhiAccumulatedMedicalRecordEntity.medicalInstitutionCode}</dd>
            <dt>
              <Translate contentKey="totoroApp.nhiAccumulatedMedicalRecord.patient">Patient</Translate>
            </dt>
            <dd>{nhiAccumulatedMedicalRecordEntity.patient ? nhiAccumulatedMedicalRecordEntity.patient.id : ''}</dd>
          </dl>
          <Button tag={Link} to="/entity/nhi-accumulated-medical-record" replace color="info">
            <FontAwesomeIcon icon="arrow-left" />{' '}
            <span className="d-none d-md-inline">
              <Translate contentKey="entity.action.back">Back</Translate>
            </span>
          </Button>
          &nbsp;
          <Button
            tag={Link}
            to={`/entity/nhi-accumulated-medical-record/${nhiAccumulatedMedicalRecordEntity.id}/edit`}
            replace
            color="primary"
          >
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

const mapStateToProps = ({ nhiAccumulatedMedicalRecord }: IRootState) => ({
  nhiAccumulatedMedicalRecordEntity: nhiAccumulatedMedicalRecord.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(NhiAccumulatedMedicalRecordDetail);
