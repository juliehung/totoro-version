import React from 'react';
import { Navbar, Nav, NavItem, NavLink, NavbarToggler } from 'reactstrap';

import { Home } from './sidebar-components';
import { AdminMenu, EntitiesMenu, AccountMenu } from './menus';

export interface IHeaderProps {
  isAuthenticated: boolean;
  isAdmin: boolean;
  ribbonEnv: string;
  isInProduction: boolean;
  isSwaggerEnabled: boolean;
  currentLocale: string;
  onLocaleChange: Function;
}

export interface IHeaderState {
  menuOpen: boolean;
}

export default class Sidebar extends React.Component<IHeaderProps, IHeaderState> {
  state: IHeaderState = {
    menuOpen: false
  };

  handleLocaleChange = event => {
    this.props.onLocaleChange(event.target.value);
  };

  toggleMenu = () => {
    this.setState({ menuOpen: !this.state.menuOpen });
  };

  render() {
    const { isAuthenticated, isAdmin, isSwaggerEnabled, isInProduction } = this.props;
    return (
      <Navbar dark expand="sm">
        <NavbarToggler aria-label="Menu" onClick={this.toggleMenu} />
        <Nav id="header-tabs" navbar vertical>
          <Home />
          {isAuthenticated && <EntitiesMenu />}
          {isAuthenticated && isAdmin && <AdminMenu showSwagger={isSwaggerEnabled} showDatabase={!isInProduction} />}
          <AccountMenu isAuthenticated={isAuthenticated} />
        </Nav>
      </Navbar>
    );
  }
}
