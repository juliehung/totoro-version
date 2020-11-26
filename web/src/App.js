import React from 'react';
import { createStore, applyMiddleware, compose } from 'redux';
import { Provider } from 'react-redux';
import Home from './container/Home';
import rootReducer from './rootReducers';
import createSagaMiddleware from 'redux-saga';
import { rootSaga } from './rootSaga';
import { HashRouter } from 'react-router-dom';
import { CookiesProvider } from 'react-cookie';
import { HelmetProvider } from 'react-helmet-async';
import { ConfigProvider } from 'antd';
import 'antd/dist/antd.less';

const sagaMiddleware = createSagaMiddleware();
const composeEnhancers = window.__REDUX_DEVTOOLS_EXTENSION_COMPOSE__ || compose;
const store = createStore(rootReducer, /* preloadedState, */ composeEnhancers(applyMiddleware(sagaMiddleware)));
sagaMiddleware.run(rootSaga);

function App() {
  return (
    <Provider store={store}>
      <HashRouter>
        <CookiesProvider>
          <HelmetProvider>
            <ConfigProvider autoInsertSpaceInButton={false}>
              <Home />
            </ConfigProvider>
          </HelmetProvider>
        </CookiesProvider>
      </HashRouter>
    </Provider>
  );
}

export default App;
