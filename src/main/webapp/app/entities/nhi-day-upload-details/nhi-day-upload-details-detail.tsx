import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
// tslint:disable-next-line:no-unused-variable
import { Translate, ICrudGetAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './nhi-day-upload-details.reducer';
import { INhiDayUploadDetails } from 'app/shared/model/nhi-day-upload-details.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface INhiDayUploadDetailsDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export class NhiDayUploadDetailsDetail extends React.Component<INhiDayUploadDetailsDetailProps> {
  componentDidMount() {
    this.props.getEntity(this.props.match.params.id);
  }

  render() {
    const { nhiDayUploadDetailsEntity } = this.props;
    return (
      <Row>
        <Col md="8">
          <h2>
            <Translate contentKey="totoroApp.nhiDayUploadDetails.detail.title">NhiDayUploadDetails</Translate> [
            <b>{nhiDayUploadDetailsEntity.id}</b>]
          </h2>
          <dl className="jh-entity-details">
            <dt>
              <span id="type">
                <Translate contentKey="totoroApp.nhiDayUploadDetails.type">Type</Translate>
              </span>
            </dt>
            <dd>{nhiDayUploadDetailsEntity.type}</dd>
            <dt>
              <Translate contentKey="totoroApp.nhiDayUploadDetails.nhiDayUpload">Nhi Day Upload</Translate>
            </dt>
            <dd>{nhiDayUploadDetailsEntity.nhiDayUpload ? nhiDayUploadDetailsEntity.nhiDayUpload.id : ''}</dd>
          </dl>
          <Button tag={Link} to="/entity/nhi-day-upload-details" replace color="info">
            <FontAwesomeIcon icon="arrow-left" />{' '}
            <span className="d-none d-md-inline">
              <Translate contentKey="entity.action.back">Back</Translate>
            </span>
          </Button>
          &nbsp;
          <Button tag={Link} to={`/entity/nhi-day-upload-details/${nhiDayUploadDetailsEntity.id}/edit`} replace color="primary">
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

const mapStateToProps = ({ nhiDayUploadDetails }: IRootState) => ({
  nhiDayUploadDetailsEntity: nhiDayUploadDetails.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(NhiDayUploadDetailsDetail);
