import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Col, Row, Table } from 'reactstrap';
// tslint:disable-next-line:no-unused-variable
import { Translate, ICrudGetAllAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntities } from './nhi-icd-9-cm.reducer';
import { INhiIcd9Cm } from 'app/shared/model/nhi-icd-9-cm.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface INhiIcd9CmProps extends StateProps, DispatchProps, RouteComponentProps<{ url: string }> {}

export class NhiIcd9Cm extends React.Component<INhiIcd9CmProps> {
  componentDidMount() {
    this.props.getEntities();
  }

  render() {
    const { nhiIcd9CmList, match } = this.props;
    return (
      <div>
        <h2 id="nhi-icd-9-cm-heading">
          <Translate contentKey="totoroApp.nhiIcd9Cm.home.title">Nhi Icd 9 Cms</Translate>
          <Link to={`${match.url}/new`} className="btn btn-primary float-right jh-create-entity" id="jh-create-entity">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="totoroApp.nhiIcd9Cm.home.createLabel">Create new Nhi Icd 9 Cm</Translate>
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
                  <Translate contentKey="totoroApp.nhiIcd9Cm.code">Code</Translate>
                </th>
                <th>
                  <Translate contentKey="totoroApp.nhiIcd9Cm.name">Name</Translate>
                </th>
                <th>
                  <Translate contentKey="totoroApp.nhiIcd9Cm.englishName">English Name</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {nhiIcd9CmList.map((nhiIcd9Cm, i) => (
                <tr key={`entity-${i}`}>
                  <td>
                    <Button tag={Link} to={`${match.url}/${nhiIcd9Cm.id}`} color="link" size="sm">
                      {nhiIcd9Cm.id}
                    </Button>
                  </td>
                  <td>{nhiIcd9Cm.code}</td>
                  <td>{nhiIcd9Cm.name}</td>
                  <td>{nhiIcd9Cm.englishName}</td>
                  <td className="text-right">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`${match.url}/${nhiIcd9Cm.id}`} color="info" size="sm">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${nhiIcd9Cm.id}/edit`} color="primary" size="sm">
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${nhiIcd9Cm.id}/delete`} color="danger" size="sm">
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

const mapStateToProps = ({ nhiIcd9Cm }: IRootState) => ({
  nhiIcd9CmList: nhiIcd9Cm.entities
});

const mapDispatchToProps = {
  getEntities
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(NhiIcd9Cm);
