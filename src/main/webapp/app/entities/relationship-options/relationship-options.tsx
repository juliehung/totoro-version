import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Col, Row, Table } from 'reactstrap';
// tslint:disable-next-line:no-unused-variable
import { Translate, ICrudGetAllAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntities } from './relationship-options.reducer';
import { IRelationshipOptions } from 'app/shared/model/relationship-options.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IRelationshipOptionsProps extends StateProps, DispatchProps, RouteComponentProps<{ url: string }> {}

export class RelationshipOptions extends React.Component<IRelationshipOptionsProps> {
  componentDidMount() {
    this.props.getEntities();
  }

  render() {
    const { relationshipOptionsList, match } = this.props;
    return (
      <div>
        <h2 id="relationship-options-heading">
          <Translate contentKey="totoroApp.relationshipOptions.home.title">Relationship Options</Translate>
          <Link to={`${match.url}/new`} className="btn btn-primary float-right jh-create-entity" id="jh-create-entity">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="totoroApp.relationshipOptions.home.createLabel">Create new Relationship Options</Translate>
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
                  <Translate contentKey="totoroApp.relationshipOptions.code">Code</Translate>
                </th>
                <th>
                  <Translate contentKey="totoroApp.relationshipOptions.name">Name</Translate>
                </th>
                <th>
                  <Translate contentKey="totoroApp.relationshipOptions.order">Order</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {relationshipOptionsList.map((relationshipOptions, i) => (
                <tr key={`entity-${i}`}>
                  <td>
                    <Button tag={Link} to={`${match.url}/${relationshipOptions.id}`} color="link" size="sm">
                      {relationshipOptions.id}
                    </Button>
                  </td>
                  <td>{relationshipOptions.code}</td>
                  <td>{relationshipOptions.name}</td>
                  <td>{relationshipOptions.order}</td>
                  <td className="text-right">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`${match.url}/${relationshipOptions.id}`} color="info" size="sm">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${relationshipOptions.id}/edit`} color="primary" size="sm">
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${relationshipOptions.id}/delete`} color="danger" size="sm">
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

const mapStateToProps = ({ relationshipOptions }: IRootState) => ({
  relationshipOptionsList: relationshipOptions.entities
});

const mapDispatchToProps = {
  getEntities
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(RelationshipOptions);
