import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Prescription from './prescription';
import PrescriptionDetail from './prescription-detail';
import PrescriptionUpdate from './prescription-update';
import PrescriptionDeleteDialog from './prescription-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={PrescriptionUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={PrescriptionUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={PrescriptionDetail} />
      <ErrorBoundaryRoute path={match.url} component={Prescription} />
    </Switch>
    <ErrorBoundaryRoute path={`${match.url}/:id/delete`} component={PrescriptionDeleteDialog} />
  </>
);

export default Routes;
