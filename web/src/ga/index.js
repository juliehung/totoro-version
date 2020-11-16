import ReactGA from 'react-ga';

const isProduction = process.env.NODE_ENV === 'production';
const TrackingCode = 'UA-153780028-2';

class GAHelper {
  constructor() {
    ReactGA.initialize(TrackingCode);
  }

  pageView = page => {
    ReactGA.pageview(page ? page : window.location.href);
  };

  modalView = modal => {
    ReactGA.modalview(modal);
  };

  event = (category, action) => {
    ReactGA.event({
      category,
      action,
    });
  };

  timing = (categoryName, variableName, valueNum) => {
    ReactGA.timing({
      category: categoryName,
      variable: variableName,
      value: valueNum,
    });
  };

  exception = detail => {
    ReactGA.exception({
      description: detail,
    });
  };
}

const GAHelperInstance = new GAHelper();
Object.freeze(GAHelperInstance);

export default GAHelperInstance;
