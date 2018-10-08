import React from 'react';
import { NavLink } from 'reactstrap';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { NavLink as Link } from 'react-router-dom';
import { Translate, translate } from 'react-jhipster';

const accountMenuItemsAuthenticated = (
  <>
    <NavLink tag={Link} to="/account/settings">
      <FontAwesomeIcon icon="wrench" /> <Translate contentKey="global.menu.account.settings">Settings</Translate>
    </NavLink>
    <NavLink tag={Link} to="/account/password">
      <FontAwesomeIcon icon="clock" /> <Translate contentKey="global.menu.account.password">Password</Translate>
    </NavLink>
    <NavLink tag={Link} to="/logout">
      <FontAwesomeIcon icon="sign-out-alt" /> <Translate contentKey="global.menu.account.logout">Sign out</Translate>
    </NavLink>
  </>
);

const accountMenuItems = (
  <>
    <NavLink id="login-item" tag={Link} to="/login">
      <FontAwesomeIcon icon="sign-in-alt" /> <Translate contentKey="global.menu.account.login">Sign in</Translate>
    </NavLink>
    <NavLink tag={Link} to="/register">
      <FontAwesomeIcon icon="sign-in-alt" /> <Translate contentKey="global.menu.account.register">Register</Translate>
    </NavLink>
  </>
);

export const AccountMenu = ({ isAuthenticated = false }) => (
  <>
    <p style={{ marginTop: '20px', marginBottom: '-3px' }}>{translate('global.menu.account.main')}</p>
    {isAuthenticated ? accountMenuItemsAuthenticated : accountMenuItems}
  </>
);

export default AccountMenu;
