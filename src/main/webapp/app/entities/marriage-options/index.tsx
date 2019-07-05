import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import MarriageOptions from './marriage-options';
import MarriageOptionsDetail from './marriage-options-detail';
import MarriageOptionsUpdate from './marriage-options-update';
import MarriageOptionsDeleteDialog from './marriage-options-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={MarriageOptionsUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={MarriageOptionsUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={MarriageOptionsDetail} />
      <ErrorBoundaryRoute path={match.url} component={MarriageOptions} />
    </Switch>
    <ErrorBoundaryRoute path={`${match.url}/:id/delete`} component={MarriageOptionsDeleteDialog} />
  </>
);

export default Routes;
