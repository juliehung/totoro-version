import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Col, Row, Table } from 'reactstrap';
// tslint:disable-next-line:no-unused-variable
import { Translate, ICrudGetAllAction, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntities } from './nhi-unusal-incident.reducer';
import { INHIUnusalIncident } from 'app/shared/model/nhi-unusal-incident.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface INHIUnusalIncidentProps extends StateProps, DispatchProps, RouteComponentProps<{ url: string }> {}

export class NHIUnusalIncident extends React.Component<INHIUnusalIncidentProps> {
  componentDidMount() {
    this.props.getEntities();
  }

  render() {
    const { nHIUnusalIncidentList, match } = this.props;
    return (
      <div>
        <h2 id="nhi-unusal-incident-heading">
          <Translate contentKey="totoroApp.nHIUnusalIncident.home.title">NHI Unusal Incidents</Translate>
          <Link to={`${match.url}/new`} className="btn btn-primary float-right jh-create-entity" id="jh-create-entity">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="totoroApp.nHIUnusalIncident.home.createLabel">Create new NHI Unusal Incident</Translate>
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
                  <Translate contentKey="totoroApp.nHIUnusalIncident.start">Start</Translate>
                </th>
                <th>
                  <Translate contentKey="totoroApp.nHIUnusalIncident.end">End</Translate>
                </th>
                <th>
                  <Translate contentKey="totoroApp.nHIUnusalIncident.nhiUnusalContent">Nhi Unusal Content</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {nHIUnusalIncidentList.map((nHIUnusalIncident, i) => (
                <tr key={`entity-${i}`}>
                  <td>
                    <Button tag={Link} to={`${match.url}/${nHIUnusalIncident.id}`} color="link" size="sm">
                      {nHIUnusalIncident.id}
                    </Button>
                  </td>
                  <td>
                    <TextFormat type="date" value={nHIUnusalIncident.start} format={APP_DATE_FORMAT} />
                  </td>
                  <td>
                    <TextFormat type="date" value={nHIUnusalIncident.end} format={APP_DATE_FORMAT} />
                  </td>
                  <td>
                    {nHIUnusalIncident.nhiUnusalContent ? (
                      <Link to={`nhi-unusal-content/${nHIUnusalIncident.nhiUnusalContent.id}`}>
                        {nHIUnusalIncident.nhiUnusalContent.id}
                      </Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td className="text-right">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`${match.url}/${nHIUnusalIncident.id}`} color="info" size="sm">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${nHIUnusalIncident.id}/edit`} color="primary" size="sm">
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${nHIUnusalIncident.id}/delete`} color="danger" size="sm">
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

const mapStateToProps = ({ nHIUnusalIncident }: IRootState) => ({
  nHIUnusalIncidentList: nHIUnusalIncident.entities
});

const mapDispatchToProps = {
  getEntities
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(NHIUnusalIncident);
