import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Col, Row, Table } from 'reactstrap';
// tslint:disable-next-line:no-unused-variable
import { Translate, ICrudGetAllAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntities } from './nhi-procedure-type.reducer';
import { INHIProcedureType } from 'app/shared/model/nhi-procedure-type.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface INHIProcedureTypeProps extends StateProps, DispatchProps, RouteComponentProps<{ url: string }> {}

export class NHIProcedureType extends React.Component<INHIProcedureTypeProps> {
  componentDidMount() {
    this.props.getEntities();
  }

  render() {
    const { nHIProcedureTypeList, match } = this.props;
    return (
      <div>
        <h2 id="nhi-procedure-type-heading">
          <Translate contentKey="totoroApp.nHIProcedureType.home.title">NHI Procedure Types</Translate>
          <Link to={`${match.url}/new`} className="btn btn-primary float-right jh-create-entity" id="jh-create-entity">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="totoroApp.nHIProcedureType.home.createLabel">Create new NHI Procedure Type</Translate>
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
                  <Translate contentKey="totoroApp.nHIProcedureType.major">Major</Translate>
                </th>
                <th>
                  <Translate contentKey="totoroApp.nHIProcedureType.name">Name</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {nHIProcedureTypeList.map((nHIProcedureType, i) => (
                <tr key={`entity-${i}`}>
                  <td>
                    <Button tag={Link} to={`${match.url}/${nHIProcedureType.id}`} color="link" size="sm">
                      {nHIProcedureType.id}
                    </Button>
                  </td>
                  <td>{nHIProcedureType.major}</td>
                  <td>{nHIProcedureType.name}</td>
                  <td className="text-right">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`${match.url}/${nHIProcedureType.id}`} color="info" size="sm">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${nHIProcedureType.id}/edit`} color="primary" size="sm">
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${nHIProcedureType.id}/delete`} color="danger" size="sm">
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

const mapStateToProps = ({ nHIProcedureType }: IRootState) => ({
  nHIProcedureTypeList: nHIProcedureType.entities
});

const mapDispatchToProps = {
  getEntities
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(NHIProcedureType);
