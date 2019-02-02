import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import TreatmentPlan from './treatment-plan';
import TreatmentPlanDetail from './treatment-plan-detail';
import TreatmentPlanUpdate from './treatment-plan-update';
import TreatmentPlanDeleteDialog from './treatment-plan-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={TreatmentPlanUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={TreatmentPlanUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={TreatmentPlanDetail} />
      <ErrorBoundaryRoute path={match.url} component={TreatmentPlan} />
    </Switch>
    <ErrorBoundaryRoute path={`${match.url}/:id/delete`} component={TreatmentPlanDeleteDialog} />
  </>
);

export default Routes;
