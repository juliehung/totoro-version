import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
// tslint:disable-next-line:no-unused-variable
import { Translate, ICrudGetAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './nhi-medical-record.reducer';
import { INhiMedicalRecord } from 'app/shared/model/nhi-medical-record.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface INhiMedicalRecordDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export class NhiMedicalRecordDetail extends React.Component<INhiMedicalRecordDetailProps> {
  componentDidMount() {
    this.props.getEntity(this.props.match.params.id);
  }

  render() {
    const { nhiMedicalRecordEntity } = this.props;
    return (
      <Row>
        <Col md="8">
          <h2>
            <Translate contentKey="totoroApp.nhiMedicalRecord.detail.title">NhiMedicalRecord</Translate> [<b>{nhiMedicalRecordEntity.id}</b>
            ]
          </h2>
          <dl className="jh-entity-details">
            <dt>
              <span id="date">
                <Translate contentKey="totoroApp.nhiMedicalRecord.date">Date</Translate>
              </span>
            </dt>
            <dd>{nhiMedicalRecordEntity.date}</dd>
            <dt>
              <span id="nhiCategory">
                <Translate contentKey="totoroApp.nhiMedicalRecord.nhiCategory">Nhi Category</Translate>
              </span>
            </dt>
            <dd>{nhiMedicalRecordEntity.nhiCategory}</dd>
            <dt>
              <span id="nhiCode">
                <Translate contentKey="totoroApp.nhiMedicalRecord.nhiCode">Nhi Code</Translate>
              </span>
            </dt>
            <dd>{nhiMedicalRecordEntity.nhiCode}</dd>
            <dt>
              <span id="part">
                <Translate contentKey="totoroApp.nhiMedicalRecord.part">Part</Translate>
              </span>
            </dt>
            <dd>{nhiMedicalRecordEntity.part}</dd>
            <dt>
              <span id="usage">
                <Translate contentKey="totoroApp.nhiMedicalRecord.usage">Usage</Translate>
              </span>
            </dt>
            <dd>{nhiMedicalRecordEntity.usage}</dd>
            <dt>
              <span id="total">
                <Translate contentKey="totoroApp.nhiMedicalRecord.total">Total</Translate>
              </span>
            </dt>
            <dd>{nhiMedicalRecordEntity.total}</dd>
            <dt>
              <span id="note">
                <Translate contentKey="totoroApp.nhiMedicalRecord.note">Note</Translate>
              </span>
            </dt>
            <dd>{nhiMedicalRecordEntity.note}</dd>
            <dt>
              <span id="days">
                <Translate contentKey="totoroApp.nhiMedicalRecord.days">Days</Translate>
              </span>
            </dt>
            <dd>{nhiMedicalRecordEntity.days}</dd>
            <dt>
              <Translate contentKey="totoroApp.nhiMedicalRecord.nhiExtendPatient">Nhi Extend Patient</Translate>
            </dt>
            <dd>{nhiMedicalRecordEntity.nhiExtendPatient ? nhiMedicalRecordEntity.nhiExtendPatient.id : ''}</dd>
          </dl>
          <Button tag={Link} to="/entity/nhi-medical-record" replace color="info">
            <FontAwesomeIcon icon="arrow-left" />{' '}
            <span className="d-none d-md-inline">
              <Translate contentKey="entity.action.back">Back</Translate>
            </span>
          </Button>
          &nbsp;
          <Button tag={Link} to={`/entity/nhi-medical-record/${nhiMedicalRecordEntity.id}/edit`} replace color="primary">
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

const mapStateToProps = ({ nhiMedicalRecord }: IRootState) => ({
  nhiMedicalRecordEntity: nhiMedicalRecord.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(NhiMedicalRecordDetail);
