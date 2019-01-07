import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Accounting from './accounting';
import AccountingDetail from './accounting-detail';
import AccountingUpdate from './accounting-update';
import AccountingDeleteDialog from './accounting-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={AccountingUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={AccountingUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={AccountingDetail} />
      <ErrorBoundaryRoute path={match.url} component={Accounting} />
    </Switch>
    <ErrorBoundaryRoute path={`${match.url}/:id/delete`} component={AccountingDeleteDialog} />
  </>
);

export default Routes;
