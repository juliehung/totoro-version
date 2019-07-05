import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Esign from './esign';
import EsignDetail from './esign-detail';
import EsignUpdate from './esign-update';
import EsignDeleteDialog from './esign-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={EsignUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={EsignUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={EsignDetail} />
      <ErrorBoundaryRoute path={match.url} component={Esign} />
    </Switch>
    <ErrorBoundaryRoute path={`${match.url}/:id/delete`} component={EsignDeleteDialog} />
  </>
);

export default Routes;
