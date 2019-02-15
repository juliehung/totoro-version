import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Col, Row, Table } from 'reactstrap';
// tslint:disable-next-line:no-unused-variable
import {
  Translate,
  ICrudGetAllAction,
  TextFormat,
  getSortState,
  IPaginationBaseState,
  getPaginationItemsNumber,
  JhiPagination
} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntities } from './treatment-procedure.reducer';
import { ITreatmentProcedure } from 'app/shared/model/treatment-procedure.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { ITEMS_PER_PAGE } from 'app/shared/util/pagination.constants';

export interface ITreatmentProcedureProps extends StateProps, DispatchProps, RouteComponentProps<{ url: string }> {}

export type ITreatmentProcedureState = IPaginationBaseState;

export class TreatmentProcedure extends React.Component<ITreatmentProcedureProps, ITreatmentProcedureState> {
  state: ITreatmentProcedureState = {
    ...getSortState(this.props.location, ITEMS_PER_PAGE)
  };

  componentDidMount() {
    this.getEntities();
  }

  sort = prop => () => {
    this.setState(
      {
        order: this.state.order === 'asc' ? 'desc' : 'asc',
        sort: prop
      },
      () => this.sortEntities()
    );
  };

  sortEntities() {
    this.getEntities();
    this.props.history.push(`${this.props.location.pathname}?page=${this.state.activePage}&sort=${this.state.sort},${this.state.order}`);
  }

  handlePagination = activePage => this.setState({ activePage }, () => this.sortEntities());

  getEntities = () => {
    const { activePage, itemsPerPage, sort, order } = this.state;
    this.props.getEntities(activePage - 1, itemsPerPage, `${sort},${order}`);
  };

  render() {
    const { treatmentProcedureList, match, totalItems } = this.props;
    return (
      <div>
        <h2 id="treatment-procedure-heading">
          <Translate contentKey="totoroApp.treatmentProcedure.home.title">Treatment Procedures</Translate>
          <Link to={`${match.url}/new`} className="btn btn-primary float-right jh-create-entity" id="jh-create-entity">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="totoroApp.treatmentProcedure.home.createLabel">Create new Treatment Procedure</Translate>
          </Link>
        </h2>
        <div className="table-responsive">
          <Table responsive>
            <thead>
              <tr>
                <th className="hand" onClick={this.sort('id')}>
                  <Translate contentKey="global.field.id">ID</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={this.sort('status')}>
                  <Translate contentKey="totoroApp.treatmentProcedure.status">Status</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={this.sort('quantity')}>
                  <Translate contentKey="totoroApp.treatmentProcedure.quantity">Quantity</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={this.sort('total')}>
                  <Translate contentKey="totoroApp.treatmentProcedure.total">Total</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={this.sort('note')}>
                  <Translate contentKey="totoroApp.treatmentProcedure.note">Note</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={this.sort('completedDate')}>
                  <Translate contentKey="totoroApp.treatmentProcedure.completedDate">Completed Date</Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  <Translate contentKey="totoroApp.treatmentProcedure.nhiProcedure">Nhi Procedure</Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  <Translate contentKey="totoroApp.treatmentProcedure.treatmentTask">Treatment Task</Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  <Translate contentKey="totoroApp.treatmentProcedure.procedure">Procedure</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  <Translate contentKey="totoroApp.treatmentProcedure.appointment">Appointment</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  <Translate contentKey="totoroApp.treatmentProcedure.registration">Registration</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  <Translate contentKey="totoroApp.treatmentProcedure.todo">Todo</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {treatmentProcedureList.map((treatmentProcedure, i) => (
                <tr key={`entity-${i}`}>
                  <td>
                    <Button tag={Link} to={`${match.url}/${treatmentProcedure.id}`} color="link" size="sm">
                      {treatmentProcedure.id}
                    </Button>
                  </td>
                  <td>
                    <Translate contentKey={`totoroApp.TreatmentProcedureStatus.${treatmentProcedure.status}`} />
                  </td>
                  <td>{treatmentProcedure.quantity}</td>
                  <td>{treatmentProcedure.total}</td>
                  <td>{treatmentProcedure.note}</td>
                  <td>
                    <TextFormat type="date" value={treatmentProcedure.completedDate} format={APP_DATE_FORMAT} />
                  </td>
                  <td>
                    {treatmentProcedure.nhiProcedure ? (
                      <Link to={`nhi-procedure/${treatmentProcedure.nhiProcedure.id}`}>{treatmentProcedure.nhiProcedure.id}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td>
                    {treatmentProcedure.treatmentTask ? (
                      <Link to={`treatment-task/${treatmentProcedure.treatmentTask.id}`}>{treatmentProcedure.treatmentTask.id}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td>
                    {treatmentProcedure.procedure ? (
                      <Link to={`procedure/${treatmentProcedure.procedure.id}`}>{treatmentProcedure.procedure.id}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td>
                    {treatmentProcedure.appointment ? (
                      <Link to={`appointment/${treatmentProcedure.appointment.id}`}>{treatmentProcedure.appointment.id}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td>
                    {treatmentProcedure.registration ? (
                      <Link to={`registration/${treatmentProcedure.registration.id}`}>{treatmentProcedure.registration.id}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td>
                    {treatmentProcedure.todo ? <Link to={`todo/${treatmentProcedure.todo.id}`}>{treatmentProcedure.todo.id}</Link> : ''}
                  </td>
                  <td className="text-right">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`${match.url}/${treatmentProcedure.id}`} color="info" size="sm">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${treatmentProcedure.id}/edit`} color="primary" size="sm">
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${treatmentProcedure.id}/delete`} color="danger" size="sm">
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
        <Row className="justify-content-center">
          <JhiPagination
            items={getPaginationItemsNumber(totalItems, this.state.itemsPerPage)}
            activePage={this.state.activePage}
            onSelect={this.handlePagination}
            maxButtons={5}
          />
        </Row>
      </div>
    );
  }
}

const mapStateToProps = ({ treatmentProcedure }: IRootState) => ({
  treatmentProcedureList: treatmentProcedure.entities,
  totalItems: treatmentProcedure.totalItems
});

const mapDispatchToProps = {
  getEntities
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(TreatmentProcedure);
