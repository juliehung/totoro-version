import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import CareerOptions from './career-options';
import CareerOptionsDetail from './career-options-detail';
import CareerOptionsUpdate from './career-options-update';
import CareerOptionsDeleteDialog from './career-options-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={CareerOptionsUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={CareerOptionsUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={CareerOptionsDetail} />
      <ErrorBoundaryRoute path={match.url} component={CareerOptions} />
    </Switch>
    <ErrorBoundaryRoute path={`${match.url}/:id/delete`} component={CareerOptionsDeleteDialog} />
  </>
);

export default Routes;
