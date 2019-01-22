import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Col, Row, Table } from 'reactstrap';
// tslint:disable-next-line:no-unused-variable
import { Translate, ICrudGetAllAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntities } from './nhi-unusal-content.reducer';
import { INHIUnusalContent } from 'app/shared/model/nhi-unusal-content.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface INHIUnusalContentProps extends StateProps, DispatchProps, RouteComponentProps<{ url: string }> {}

export class NHIUnusalContent extends React.Component<INHIUnusalContentProps> {
  componentDidMount() {
    this.props.getEntities();
  }

  render() {
    const { nHIUnusalContentList, match } = this.props;
    return (
      <div>
        <h2 id="nhi-unusal-content-heading">
          <Translate contentKey="totoroApp.nHIUnusalContent.home.title">NHI Unusal Contents</Translate>
          <Link to={`${match.url}/new`} className="btn btn-primary float-right jh-create-entity" id="jh-create-entity">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="totoroApp.nHIUnusalContent.home.createLabel">Create new NHI Unusal Content</Translate>
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
                  <Translate contentKey="totoroApp.nHIUnusalContent.content">Content</Translate>
                </th>
                <th>
                  <Translate contentKey="totoroApp.nHIUnusalContent.noSeqNumber">No Seq Number</Translate>
                </th>
                <th>
                  <Translate contentKey="totoroApp.nHIUnusalContent.gotSeqNumber">Got Seq Number</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {nHIUnusalContentList.map((nHIUnusalContent, i) => (
                <tr key={`entity-${i}`}>
                  <td>
                    <Button tag={Link} to={`${match.url}/${nHIUnusalContent.id}`} color="link" size="sm">
                      {nHIUnusalContent.id}
                    </Button>
                  </td>
                  <td>{nHIUnusalContent.content}</td>
                  <td>{nHIUnusalContent.noSeqNumber}</td>
                  <td>{nHIUnusalContent.gotSeqNumber}</td>
                  <td className="text-right">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`${match.url}/${nHIUnusalContent.id}`} color="info" size="sm">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${nHIUnusalContent.id}/edit`} color="primary" size="sm">
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${nHIUnusalContent.id}/delete`} color="danger" size="sm">
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

const mapStateToProps = ({ nHIUnusalContent }: IRootState) => ({
  nHIUnusalContentList: nHIUnusalContent.entities
});

const mapDispatchToProps = {
  getEntities
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(NHIUnusalContent);
