import './header.css';

import React from 'react';
import { Translate } from 'react-jhipster';
import { Navbar, Nav, NavbarToggler, NavbarBrand, Collapse } from 'reactstrap';
import LoadingBar from 'react-redux-loading-bar';

import { Home, Brand, Version, DentallBrand } from './header-components';
import { AdminMenu, EntitiesMenu, AccountMenu, LocaleMenu } from './menus';

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

export default class Header extends React.Component<IHeaderProps, IHeaderState> {
  state: IHeaderState = {
    menuOpen: false
  };

  handleLocaleChange = event => {
    this.props.onLocaleChange(event.target.value);
  };

  renderDevRibbon = () =>
    this.props.isInProduction === false ? (
      <div className="ribbon dev">
        <a href="">
          <Translate contentKey={`global.ribbon.${this.props.ribbonEnv}`} />
        </a>
      </div>
    ) : null;

  renderVersion = () => !this.props.isInProduction && <Version />;

  renderDevNavbar = () => {
    const { currentLocale, isAuthenticated, isAdmin, isSwaggerEnabled, isInProduction } = this.props;
    return (
      !isInProduction && (
        <Navbar dark expand="sm" fixed="bottom" className="jh-navbar">
          <NavbarToggler aria-label="Menu" onClick={this.toggleMenu} />
          <Brand />
          {this.renderVersion()}
          <Collapse isOpen={this.state.menuOpen} navbar>
            <Nav id="header-tabs" className="ml-auto" navbar>
              <Home />
              {isAuthenticated && isAdmin && <EntitiesMenu />}
              {isAuthenticated && isAdmin && <AdminMenu showSwagger={isSwaggerEnabled} />}
              <LocaleMenu currentLocale={currentLocale} onClick={this.handleLocaleChange} />
              <AccountMenu isAuthenticated={isAuthenticated} />
            </Nav>
          </Collapse>
        </Navbar>
      )
    );
  };

  toggleMenu = () => {
    this.setState({ menuOpen: !this.state.menuOpen });
  };

  render() {
    /* jhipster-needle-add-element-to-menu - JHipster will add new menu items here */

    let title = 'DENTALL';
    if (window && window.location && window.location.href) {
      const idxSlash = window.location.href.lastIndexOf('/');
      const idxQuestionmark = window.location.href.lastIndexOf('?');
      const tmp =
        idxQuestionmark === -1 ? window.location.href.substring(idxSlash) : window.location.href.substring(idxSlash, idxQuestionmark);
      if (tmp === '/') {
        title = '就診列表';
      } else if (tmp === '/survey') {
        title = '門診病歷表';
      }
    }

    return (
      <div id="app-header">
        {this.renderDevRibbon()}
        <LoadingBar className="loading-bar" />
        {this.renderDevNavbar()}
        <Navbar dark expand="sm" fixed="top" className="jh-navbar dentall-bg-blue">
          <DentallBrand title={title} />
        </Navbar>
      </div>
    );
  }
}
