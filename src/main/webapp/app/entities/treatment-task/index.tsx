import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import TreatmentTask from './treatment-task';
import TreatmentTaskDetail from './treatment-task-detail';
import TreatmentTaskUpdate from './treatment-task-update';
import TreatmentTaskDeleteDialog from './treatment-task-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={TreatmentTaskUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={TreatmentTaskUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={TreatmentTaskDetail} />
      <ErrorBoundaryRoute path={match.url} component={TreatmentTask} />
    </Switch>
    <ErrorBoundaryRoute path={`${match.url}/:id/delete`} component={TreatmentTaskDeleteDialog} />
  </>
);

export default Routes;
