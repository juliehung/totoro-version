import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Col, Row, Table } from 'reactstrap';
// tslint:disable-next-line:no-unused-variable
import { Translate, ICrudGetAllAction, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntities } from './nhi-day-upload.reducer';
import { INhiDayUpload } from 'app/shared/model/nhi-day-upload.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface INhiDayUploadProps extends StateProps, DispatchProps, RouteComponentProps<{ url: string }> {}

export class NhiDayUpload extends React.Component<INhiDayUploadProps> {
  componentDidMount() {
    this.props.getEntities();
  }

  render() {
    const { nhiDayUploadList, match } = this.props;
    return (
      <div>
        <h2 id="nhi-day-upload-heading">
          <Translate contentKey="totoroApp.nhiDayUpload.home.title">Nhi Day Uploads</Translate>
          <Link to={`${match.url}/new`} className="btn btn-primary float-right jh-create-entity" id="jh-create-entity">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="totoroApp.nhiDayUpload.home.createLabel">Create new Nhi Day Upload</Translate>
          </Link>
        </h2>
        <div className="table-responsive">
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="global.field.id">ID</Translate>
                </th>
                <th>
                  <Translate contentKey="totoroApp.nhiDayUpload.date">Date</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {nhiDayUploadList.map((nhiDayUpload, i) => (
                <tr key={`entity-${i}`}>
                  <td>
                    <Button tag={Link} to={`${match.url}/${nhiDayUpload.id}`} color="link" size="sm">
                      {nhiDayUpload.id}
                    </Button>
                  </td>
                  <td>
                    <TextFormat type="date" value={nhiDayUpload.date} format={APP_LOCAL_DATE_FORMAT} />
                  </td>
                  <td className="text-right">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`${match.url}/${nhiDayUpload.id}`} color="info" size="sm">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${nhiDayUpload.id}/edit`} color="primary" size="sm">
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${nhiDayUpload.id}/delete`} color="danger" size="sm">
                        <FontAwesomeIcon icon="trash" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.delete">Delete</Translate>
                        </span>
                      </Button>
                    </div>
                  </td>
                </tr>
              ))}
            </tbody>
          </Table>
        </div>
      </div>
    );
  }
}

const mapStateToProps = ({ nhiDayUpload }: IRootState) => ({
  nhiDayUploadList: nhiDayUpload.entities
});

const mapDispatchToProps = {
  getEntities
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(NhiDayUpload);
