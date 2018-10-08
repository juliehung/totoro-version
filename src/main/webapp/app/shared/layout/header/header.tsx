import './header.css';

import React from 'react';
import { Translate } from 'react-jhipster';
import { Navbar, Nav, NavbarToggler, NavbarBrand, Collapse } from 'reactstrap';
import LoadingBar from 'react-redux-loading-bar';

import { Brand, Version } from './header-components';
import { LocaleMenu } from './menus';

export interface IHeaderProps {
  isAuthenticated: boolean;
  isAdmin: boolean;
  ribbonEnv: string;
  isInProduction: boolean;
  isSwaggerEnabled: boolean;
  currentLocale: string;
  onLocaleChange: Function;
  isMenuOpen: boolean;
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

  renderBrand = open => {
    if (!open) {
      return (
        <div className="left-shift">
          <Brand />
        </div>
      );
    }
    return <Brand />;
  };

  toggleMenu = () => {
    this.setState({ menuOpen: !this.state.menuOpen });
  };

  render() {
    const { currentLocale, isMenuOpen } = this.props;
    return (
      <div id="app-header">
        <LoadingBar className="loading-bar" />
        <Navbar dark expand="sm" className="jh-navbar">
          <NavbarToggler aria-label="Menu" onClick={this.toggleMenu} />
          {this.renderBrand(isMenuOpen)}
          <Collapse isOpen={this.state.menuOpen} navbar>
            <Nav id="header-tabs" className="ml-auto" navbar>
              <LocaleMenu currentLocale={currentLocale} onClick={this.handleLocaleChange} />
            </Nav>
          </Collapse>
        </Navbar>
      </div>
    );
  }
}
