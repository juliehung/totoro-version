import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Col, Row, Table } from 'reactstrap';
// tslint:disable-next-line:no-unused-variable
import { Translate, ICrudGetAllAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntities } from './marriage-options.reducer';
import { IMarriageOptions } from 'app/shared/model/marriage-options.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IMarriageOptionsProps extends StateProps, DispatchProps, RouteComponentProps<{ url: string }> {}

export class MarriageOptions extends React.Component<IMarriageOptionsProps> {
  componentDidMount() {
    this.props.getEntities();
  }

  render() {
    const { marriageOptionsList, match } = this.props;
    return (
      <div>
        <h2 id="marriage-options-heading">
          <Translate contentKey="totoroApp.marriageOptions.home.title">Marriage Options</Translate>
          <Link to={`${match.url}/new`} className="btn btn-primary float-right jh-create-entity" id="jh-create-entity">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="totoroApp.marriageOptions.home.createLabel">Create new Marriage Options</Translate>
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
                  <Translate contentKey="totoroApp.marriageOptions.code">Code</Translate>
                </th>
                <th>
                  <Translate contentKey="totoroApp.marriageOptions.name">Name</Translate>
                </th>
                <th>
                  <Translate contentKey="totoroApp.marriageOptions.order">Order</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {marriageOptionsList.map((marriageOptions, i) => (
                <tr key={`entity-${i}`}>
                  <td>
                    <Button tag={Link} to={`${match.url}/${marriageOptions.id}`} color="link" size="sm">
                      {marriageOptions.id}
                    </Button>
                  </td>
                  <td>{marriageOptions.code}</td>
                  <td>{marriageOptions.name}</td>
                  <td>{marriageOptions.order}</td>
                  <td className="text-right">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`${match.url}/${marriageOptions.id}`} color="info" size="sm">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${marriageOptions.id}/edit`} color="primary" size="sm">
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${marriageOptions.id}/delete`} color="danger" size="sm">
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

const mapStateToProps = ({ marriageOptions }: IRootState) => ({
  marriageOptionsList: marriageOptions.entities
});

const mapDispatchToProps = {
  getEntities
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(MarriageOptions);
