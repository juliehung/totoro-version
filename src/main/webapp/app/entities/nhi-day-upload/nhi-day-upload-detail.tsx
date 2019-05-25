import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
// tslint:disable-next-line:no-unused-variable
import { Translate, ICrudGetAction, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './nhi-day-upload.reducer';
import { INhiDayUpload } from 'app/shared/model/nhi-day-upload.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface INhiDayUploadDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export class NhiDayUploadDetail extends React.Component<INhiDayUploadDetailProps> {
  componentDidMount() {
    this.props.getEntity(this.props.match.params.id);
  }

  render() {
    const { nhiDayUploadEntity } = this.props;
    return (
      <Row>
        <Col md="8">
          <h2>
            <Translate contentKey="totoroApp.nhiDayUpload.detail.title">NhiDayUpload</Translate> [<b>{nhiDayUploadEntity.id}</b>]
          </h2>
          <dl className="jh-entity-details">
            <dt>
              <span id="date">
                <Translate contentKey="totoroApp.nhiDayUpload.date">Date</Translate>
              </span>
            </dt>
            <dd>
              <TextFormat value={nhiDayUploadEntity.date} type="date" format={APP_LOCAL_DATE_FORMAT} />
            </dd>
          </dl>
          <Button tag={Link} to="/entity/nhi-day-upload" replace color="info">
            <FontAwesomeIcon icon="arrow-left" />{' '}
            <span className="d-none d-md-inline">
              <Translate contentKey="entity.action.back">Back</Translate>
            </span>
          </Button>
          &nbsp;
          <Button tag={Link} to={`/entity/nhi-day-upload/${nhiDayUploadEntity.id}/edit`} replace color="primary">
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

const mapStateToProps = ({ nhiDayUpload }: IRootState) => ({
  nhiDayUploadEntity: nhiDayUpload.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(NhiDayUploadDetail);
