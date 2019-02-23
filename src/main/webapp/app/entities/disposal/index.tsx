import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Disposal from './disposal';
import DisposalDetail from './disposal-detail';
import DisposalUpdate from './disposal-update';
import DisposalDeleteDialog from './disposal-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={DisposalUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={DisposalUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={DisposalDetail} />
      <ErrorBoundaryRoute path={match.url} component={Disposal} />
    </Switch>
    <ErrorBoundaryRoute path={`${match.url}/:id/delete`} component={DisposalDeleteDialog} />
  </>
);

export default Routes;
