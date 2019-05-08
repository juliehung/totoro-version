import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Col, Row, Table } from 'reactstrap';
// tslint:disable-next-line:no-unused-variable
import { Translate, ICrudGetAllAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntities } from './nhi-icd-10-pcs.reducer';
import { INhiIcd10Pcs } from 'app/shared/model/nhi-icd-10-pcs.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface INhiIcd10PcsProps extends StateProps, DispatchProps, RouteComponentProps<{ url: string }> {}

export class NhiIcd10Pcs extends React.Component<INhiIcd10PcsProps> {
  componentDidMount() {
    this.props.getEntities();
  }

  render() {
    const { nhiIcd10PcsList, match } = this.props;
    return (
      <div>
        <h2 id="nhi-icd-10-pcs-heading">
          <Translate contentKey="totoroApp.nhiIcd10Pcs.home.title">Nhi Icd 10 Pcs</Translate>
          <Link to={`${match.url}/new`} className="btn btn-primary float-right jh-create-entity" id="jh-create-entity">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="totoroApp.nhiIcd10Pcs.home.createLabel">Create new Nhi Icd 10 Pcs</Translate>
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
                  <Translate contentKey="totoroApp.nhiIcd10Pcs.code">Code</Translate>
                </th>
                <th>
                  <Translate contentKey="totoroApp.nhiIcd10Pcs.nhiName">Nhi Name</Translate>
                </th>
                <th>
                  <Translate contentKey="totoroApp.nhiIcd10Pcs.englishName">English Name</Translate>
                </th>
                <th>
                  <Translate contentKey="totoroApp.nhiIcd10Pcs.chineseName">Chinese Name</Translate>
                </th>
                <th>
                  <Translate contentKey="totoroApp.nhiIcd10Pcs.nhiProcedure">Nhi Procedure</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {nhiIcd10PcsList.map((nhiIcd10Pcs, i) => (
                <tr key={`entity-${i}`}>
                  <td>
                    <Button tag={Link} to={`${match.url}/${nhiIcd10Pcs.id}`} color="link" size="sm">
                      {nhiIcd10Pcs.id}
                    </Button>
                  </td>
                  <td>{nhiIcd10Pcs.code}</td>
                  <td>{nhiIcd10Pcs.nhiName}</td>
                  <td>{nhiIcd10Pcs.englishName}</td>
                  <td>{nhiIcd10Pcs.chineseName}</td>
                  <td>
                    {nhiIcd10Pcs.nhiProcedure ? (
                      <Link to={`nhi-procedure/${nhiIcd10Pcs.nhiProcedure.id}`}>{nhiIcd10Pcs.nhiProcedure.id}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td className="text-right">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`${match.url}/${nhiIcd10Pcs.id}`} color="info" size="sm">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${nhiIcd10Pcs.id}/edit`} color="primary" size="sm">
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${nhiIcd10Pcs.id}/delete`} color="danger" size="sm">
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

const mapStateToProps = ({ nhiIcd10Pcs }: IRootState) => ({
  nhiIcd10PcsList: nhiIcd10Pcs.entities
});

const mapDispatchToProps = {
  getEntities
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(NhiIcd10Pcs);
