import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Col, Row, Table } from 'reactstrap';
// tslint:disable-next-line:no-unused-variable
import { Translate, ICrudGetAllAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntities } from './nhi-icd-10-cm.reducer';
import { INhiIcd10Cm } from 'app/shared/model/nhi-icd-10-cm.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface INhiIcd10CmProps extends StateProps, DispatchProps, RouteComponentProps<{ url: string }> {}

export class NhiIcd10Cm extends React.Component<INhiIcd10CmProps> {
  componentDidMount() {
    this.props.getEntities();
  }

  render() {
    const { nhiIcd10CmList, match } = this.props;
    return (
      <div>
        <h2 id="nhi-icd-10-cm-heading">
          <Translate contentKey="totoroApp.nhiIcd10Cm.home.title">Nhi Icd 10 Cms</Translate>
          <Link to={`${match.url}/new`} className="btn btn-primary float-right jh-create-entity" id="jh-create-entity">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="totoroApp.nhiIcd10Cm.home.createLabel">Create new Nhi Icd 10 Cm</Translate>
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
                  <Translate contentKey="totoroApp.nhiIcd10Cm.code">Code</Translate>
                </th>
                <th>
                  <Translate contentKey="totoroApp.nhiIcd10Cm.name">Name</Translate>
                </th>
                <th>
                  <Translate contentKey="totoroApp.nhiIcd10Cm.englishName">English Name</Translate>
                </th>
                <th>
                  <Translate contentKey="totoroApp.nhiIcd10Cm.nhiIcd9Cm">Nhi Icd 9 Cm</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {nhiIcd10CmList.map((nhiIcd10Cm, i) => (
                <tr key={`entity-${i}`}>
                  <td>
                    <Button tag={Link} to={`${match.url}/${nhiIcd10Cm.id}`} color="link" size="sm">
                      {nhiIcd10Cm.id}
                    </Button>
                  </td>
                  <td>{nhiIcd10Cm.code}</td>
                  <td>{nhiIcd10Cm.name}</td>
                  <td>{nhiIcd10Cm.englishName}</td>
                  <td>
                    {nhiIcd10Cm.nhiIcd9Cm ? <Link to={`nhi-icd-9-cm/${nhiIcd10Cm.nhiIcd9Cm.id}`}>{nhiIcd10Cm.nhiIcd9Cm.id}</Link> : ''}
                  </td>
                  <td className="text-right">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`${match.url}/${nhiIcd10Cm.id}`} color="info" size="sm">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${nhiIcd10Cm.id}/edit`} color="primary" size="sm">
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${nhiIcd10Cm.id}/delete`} color="danger" size="sm">
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

const mapStateToProps = ({ nhiIcd10Cm }: IRootState) => ({
  nhiIcd10CmList: nhiIcd10Cm.entities
});

const mapDispatchToProps = {
  getEntities
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(NhiIcd10Cm);
