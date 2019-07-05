import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Col, Row, Table } from 'reactstrap';
// tslint:disable-next-line:no-unused-variable
import { Translate, ICrudGetAllAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntities } from './career-options.reducer';
import { ICareerOptions } from 'app/shared/model/career-options.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface ICareerOptionsProps extends StateProps, DispatchProps, RouteComponentProps<{ url: string }> {}

export class CareerOptions extends React.Component<ICareerOptionsProps> {
  componentDidMount() {
    this.props.getEntities();
  }

  render() {
    const { careerOptionsList, match } = this.props;
    return (
      <div>
        <h2 id="career-options-heading">
          <Translate contentKey="totoroApp.careerOptions.home.title">Career Options</Translate>
          <Link to={`${match.url}/new`} className="btn btn-primary float-right jh-create-entity" id="jh-create-entity">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="totoroApp.careerOptions.home.createLabel">Create new Career Options</Translate>
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
                  <Translate contentKey="totoroApp.careerOptions.code">Code</Translate>
                </th>
                <th>
                  <Translate contentKey="totoroApp.careerOptions.name">Name</Translate>
                </th>
                <th>
                  <Translate contentKey="totoroApp.careerOptions.order">Order</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {careerOptionsList.map((careerOptions, i) => (
                <tr key={`entity-${i}`}>
                  <td>
                    <Button tag={Link} to={`${match.url}/${careerOptions.id}`} color="link" size="sm">
                      {careerOptions.id}
                    </Button>
                  </td>
                  <td>{careerOptions.code}</td>
                  <td>{careerOptions.name}</td>
                  <td>{careerOptions.order}</td>
                  <td className="text-right">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`${match.url}/${careerOptions.id}`} color="info" size="sm">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${careerOptions.id}/edit`} color="primary" size="sm">
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${careerOptions.id}/delete`} color="danger" size="sm">
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

const mapStateToProps = ({ careerOptions }: IRootState) => ({
  careerOptionsList: careerOptions.entities
});

const mapDispatchToProps = {
  getEntities
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(CareerOptions);
