import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import TreatmentProcedure from './treatment-procedure';
import TreatmentProcedureDetail from './treatment-procedure-detail';
import TreatmentProcedureUpdate from './treatment-procedure-update';
import TreatmentProcedureDeleteDialog from './treatment-procedure-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={TreatmentProcedureUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={TreatmentProcedureUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={TreatmentProcedureDetail} />
      <ErrorBoundaryRoute path={match.url} component={TreatmentProcedure} />
    </Switch>
    <ErrorBoundaryRoute path={`${match.url}/:id/delete`} component={TreatmentProcedureDeleteDialog} />
  </>
);

export default Routes;
