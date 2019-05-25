import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import NhiDayUploadDetails from './nhi-day-upload-details';
import NhiDayUploadDetailsDetail from './nhi-day-upload-details-detail';
import NhiDayUploadDetailsUpdate from './nhi-day-upload-details-update';
import NhiDayUploadDetailsDeleteDialog from './nhi-day-upload-details-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={NhiDayUploadDetailsUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={NhiDayUploadDetailsUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={NhiDayUploadDetailsDetail} />
      <ErrorBoundaryRoute path={match.url} component={NhiDayUploadDetails} />
    </Switch>
    <ErrorBoundaryRoute path={`${match.url}/:id/delete`} component={NhiDayUploadDetailsDeleteDialog} />
  </>
);

export default Routes;
