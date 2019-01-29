import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Ledger from './ledger';
import LedgerDetail from './ledger-detail';
import LedgerUpdate from './ledger-update';
import LedgerDeleteDialog from './ledger-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={LedgerUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={LedgerUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={LedgerDetail} />
      <ErrorBoundaryRoute path={match.url} component={Ledger} />
    </Switch>
    <ErrorBoundaryRoute path={`${match.url}/:id/delete`} component={LedgerDeleteDialog} />
  </>
);

export default Routes;
