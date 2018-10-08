import 'react-toastify/dist/ReactToastify.css';
import './app.css';

import React from 'react';
import { connect } from 'react-redux';
import { Card, Breadcrumb, BreadcrumbItem } from 'reactstrap';
import { HashRouter as Router } from 'react-router-dom';
import { ToastContainer, ToastPosition, toast } from 'react-toastify';
import BurgerMenu from 'react-burger-menu';

import { IRootState } from 'app/shared/reducers';
import { getSession } from 'app/shared/reducers/authentication';
import { getProfile } from 'app/shared/reducers/application-profile';
import { setLocale } from 'app/shared/reducers/locale';
import Header from 'app/shared/layout/header/header';
import Footer from 'app/shared/layout/footer/footer';
import Sidebar from 'app/shared/layout/sidebar/sidebar';
import { hasAnyAuthority } from 'app/shared/auth/private-route';
import ErrorBoundary from 'app/shared/error/error-boundary';
import { AUTHORITIES } from 'app/config/constants';
import AppRoutes from 'app/routes';

export interface IAppProps extends StateProps, DispatchProps {}

interface IAppState {
  isOpen?: boolean;
}

export class App extends React.Component<IAppProps, IAppState> {
  constructor(props) {
    super(props);

    this.state = {
      isOpen: false
    };
  }

  componentDidMount() {
    this.props.getSession();
    this.props.getProfile();
  }

  isMenuOpen = state => this.setState({ isOpen: state.isOpen });

  render() {
    const paddingTop = '60px';
    const Menu = BurgerMenu['push'];
    const { isOpen } = this.state;
    return (
      <Router>
        <div id="outer-container" style={{ height: '100%' }}>
          <Menu id={'push'} pageWrapId={'page-wrap'} outerContainerId={'outer-container'} isOpen={isOpen} onStateChange={this.isMenuOpen}>
            <Sidebar
              isAuthenticated={this.props.isAuthenticated}
              isAdmin={this.props.isAdmin}
              currentLocale={this.props.currentLocale}
              onLocaleChange={this.props.setLocale}
              ribbonEnv={this.props.ribbonEnv}
              isInProduction={this.props.isInProduction}
              isSwaggerEnabled={this.props.isSwaggerEnabled}
            />
          </Menu>
          <main id="page-wrap">
            <div className="app-container" style={{ paddingTop }}>
              <ToastContainer
                position={toast.POSITION.TOP_LEFT as ToastPosition}
                className="toastify-container"
                toastClassName="toastify-toast"
              />
              <ErrorBoundary>
                <Header
                  isAuthenticated={this.props.isAuthenticated}
                  isAdmin={this.props.isAdmin}
                  currentLocale={this.props.currentLocale}
                  onLocaleChange={this.props.setLocale}
                  ribbonEnv={this.props.ribbonEnv}
                  isInProduction={this.props.isInProduction}
                  isSwaggerEnabled={this.props.isSwaggerEnabled}
                  isMenuOpen={this.state.isOpen}
                />
              </ErrorBoundary>
              <div className="container-fluid view-container" id="app-view-container">
                <Card className="jh-card">
                  <ErrorBoundary>
                    <AppRoutes />
                  </ErrorBoundary>
                </Card>
                <Footer />
              </div>
            </div>
          </main>
        </div>
      </Router>
    );
  }
}

const mapStateToProps = ({ authentication, applicationProfile, locale }: IRootState) => ({
  currentLocale: locale.currentLocale,
  isAuthenticated: authentication.isAuthenticated,
  isAdmin: hasAnyAuthority(authentication.account.authorities, [AUTHORITIES.ADMIN]),
  ribbonEnv: applicationProfile.ribbonEnv,
  isInProduction: applicationProfile.inProduction,
  isSwaggerEnabled: applicationProfile.isSwaggerEnabled
});

const mapDispatchToProps = { setLocale, getSession, getProfile };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(App);
