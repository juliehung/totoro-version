import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Col, Row, Table } from 'reactstrap';
// tslint:disable-next-line:no-unused-variable
import { Translate, ICrudGetAllAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntities } from './nhi-procedure.reducer';
import { INhiProcedure } from 'app/shared/model/nhi-procedure.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface INhiProcedureProps extends StateProps, DispatchProps, RouteComponentProps<{ url: string }> {}

export class NhiProcedure extends React.Component<INhiProcedureProps> {
  componentDidMount() {
    this.props.getEntities();
  }

  render() {
    const { nhiProcedureList, match } = this.props;
    return (
      <div>
        <h2 id="nhi-procedure-heading">
          <Translate contentKey="totoroApp.nhiProcedure.home.title">Nhi Procedures</Translate>
          <Link to={`${match.url}/new`} className="btn btn-primary float-right jh-create-entity" id="jh-create-entity">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="totoroApp.nhiProcedure.home.createLabel">Create new Nhi Procedure</Translate>
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
                  <Translate contentKey="totoroApp.nhiProcedure.code">Code</Translate>
                </th>
                <th>
                  <Translate contentKey="totoroApp.nhiProcedure.name">Name</Translate>
                </th>
                <th>
                  <Translate contentKey="totoroApp.nhiProcedure.point">Point</Translate>
                </th>
                <th>
                  <Translate contentKey="totoroApp.nhiProcedure.englishName">English Name</Translate>
                </th>
                <th>
                  <Translate contentKey="totoroApp.nhiProcedure.defaultIcd10CmId">Default Icd 10 Cm Id</Translate>
                </th>
                <th>
                  <Translate contentKey="totoroApp.nhiProcedure.description">Description</Translate>
                </th>
                <th>
                  <Translate contentKey="totoroApp.nhiProcedure.exclude">Exclude</Translate>
                </th>
                <th>
                  <Translate contentKey="totoroApp.nhiProcedure.fdi">Fdi</Translate>
                </th>
                <th>
                  <Translate contentKey="totoroApp.nhiProcedure.specificCode">Specific Code</Translate>
                </th>
                <th>
                  <Translate contentKey="totoroApp.nhiProcedure.nhiProcedureType">Nhi Procedure Type</Translate>
                </th>
                <th>
                  <Translate contentKey="totoroApp.nhiProcedure.nhiIcd9Cm">Nhi Icd 9 Cm</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {nhiProcedureList.map((nhiProcedure, i) => (
                <tr key={`entity-${i}`}>
                  <td>
                    <Button tag={Link} to={`${match.url}/${nhiProcedure.id}`} color="link" size="sm">
                      {nhiProcedure.id}
                    </Button>
                  </td>
                  <td>{nhiProcedure.code}</td>
                  <td>{nhiProcedure.name}</td>
                  <td>{nhiProcedure.point}</td>
                  <td>{nhiProcedure.englishName}</td>
                  <td>{nhiProcedure.defaultIcd10CmId}</td>
                  <td>{nhiProcedure.description}</td>
                  <td>{nhiProcedure.exclude}</td>
                  <td>{nhiProcedure.fdi}</td>
                  <td>{nhiProcedure.specificCode}</td>
                  <td>
                    {nhiProcedure.nhiProcedureType ? (
                      <Link to={`nhi-procedure-type/${nhiProcedure.nhiProcedureType.id}`}>{nhiProcedure.nhiProcedureType.id}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td>
                    {nhiProcedure.nhiIcd9Cm ? (
                      <Link to={`nhi-icd-9-cm/${nhiProcedure.nhiIcd9Cm.id}`}>{nhiProcedure.nhiIcd9Cm.id}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td className="text-right">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`${match.url}/${nhiProcedure.id}`} color="info" size="sm">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${nhiProcedure.id}/edit`} color="primary" size="sm">
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${nhiProcedure.id}/delete`} color="danger" size="sm">
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

const mapStateToProps = ({ nhiProcedure }: IRootState) => ({
  nhiProcedureList: nhiProcedure.entities
});

const mapDispatchToProps = {
  getEntities
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(NhiProcedure);
