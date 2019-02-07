import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Col, Row, Table } from 'reactstrap';
// tslint:disable-next-line:no-unused-variable
import { Translate, ICrudGetAllAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntities } from './finding-type.reducer';
import { IFindingType } from 'app/shared/model/finding-type.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IFindingTypeProps extends StateProps, DispatchProps, RouteComponentProps<{ url: string }> {}

export class FindingType extends React.Component<IFindingTypeProps> {
  componentDidMount() {
    this.props.getEntities();
  }

  render() {
    const { findingTypeList, match } = this.props;
    return (
      <div>
        <h2 id="finding-type-heading">
          <Translate contentKey="totoroApp.findingType.home.title">Finding Types</Translate>
          <Link to={`${match.url}/new`} className="btn btn-primary float-right jh-create-entity" id="jh-create-entity">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="totoroApp.findingType.home.createLabel">Create new Finding Type</Translate>
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
                  <Translate contentKey="totoroApp.findingType.major">Major</Translate>
                </th>
                <th>
                  <Translate contentKey="totoroApp.findingType.minor">Minor</Translate>
                </th>
                <th>
                  <Translate contentKey="totoroApp.findingType.display">Display</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {findingTypeList.map((findingType, i) => (
                <tr key={`entity-${i}`}>
                  <td>
                    <Button tag={Link} to={`${match.url}/${findingType.id}`} color="link" size="sm">
                      {findingType.id}
                    </Button>
                  </td>
                  <td>{findingType.major}</td>
                  <td>{findingType.minor}</td>
                  <td>{findingType.display ? 'true' : 'false'}</td>
                  <td className="text-right">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`${match.url}/${findingType.id}`} color="info" size="sm">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${findingType.id}/edit`} color="primary" size="sm">
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${findingType.id}/delete`} color="danger" size="sm">
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

const mapStateToProps = ({ findingType }: IRootState) => ({
  findingTypeList: findingType.entities
});

const mapDispatchToProps = {
  getEntities
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(FindingType);
