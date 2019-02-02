import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Col, Row, Table } from 'reactstrap';
// tslint:disable-next-line:no-unused-variable
import { Translate, ICrudGetAllAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntities } from './treatment-plan.reducer';
import { ITreatmentPlan } from 'app/shared/model/treatment-plan.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface ITreatmentPlanProps extends StateProps, DispatchProps, RouteComponentProps<{ url: string }> {}

export class TreatmentPlan extends React.Component<ITreatmentPlanProps> {
  componentDidMount() {
    this.props.getEntities();
  }

  render() {
    const { treatmentPlanList, match } = this.props;
    return (
      <div>
        <h2 id="treatment-plan-heading">
          <Translate contentKey="totoroApp.treatmentPlan.home.title">Treatment Plans</Translate>
          <Link to={`${match.url}/new`} className="btn btn-primary float-right jh-create-entity" id="jh-create-entity">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="totoroApp.treatmentPlan.home.createLabel">Create new Treatment Plan</Translate>
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
                  <Translate contentKey="totoroApp.treatmentPlan.activated">Activated</Translate>
                </th>
                <th>
                  <Translate contentKey="totoroApp.treatmentPlan.treatment">Treatment</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {treatmentPlanList.map((treatmentPlan, i) => (
                <tr key={`entity-${i}`}>
                  <td>
                    <Button tag={Link} to={`${match.url}/${treatmentPlan.id}`} color="link" size="sm">
                      {treatmentPlan.id}
                    </Button>
                  </td>
                  <td>{treatmentPlan.activated ? 'true' : 'false'}</td>
                  <td>
                    {treatmentPlan.treatment ? (
                      <Link to={`treatment/${treatmentPlan.treatment.id}`}>{treatmentPlan.treatment.id}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td className="text-right">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`${match.url}/${treatmentPlan.id}`} color="info" size="sm">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${treatmentPlan.id}/edit`} color="primary" size="sm">
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${treatmentPlan.id}/delete`} color="danger" size="sm">
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

const mapStateToProps = ({ treatmentPlan }: IRootState) => ({
  treatmentPlanList: treatmentPlan.entities
});

const mapDispatchToProps = {
  getEntities
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(TreatmentPlan);
