import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import NhiDayUpload from './nhi-day-upload';
import NhiDayUploadDetail from './nhi-day-upload-detail';
import NhiDayUploadUpdate from './nhi-day-upload-update';
import NhiDayUploadDeleteDialog from './nhi-day-upload-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={NhiDayUploadUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={NhiDayUploadUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={NhiDayUploadDetail} />
      <ErrorBoundaryRoute path={match.url} component={NhiDayUpload} />
    </Switch>
    <ErrorBoundaryRoute path={`${match.url}/:id/delete`} component={NhiDayUploadDeleteDialog} />
  </>
);

export default Routes;
