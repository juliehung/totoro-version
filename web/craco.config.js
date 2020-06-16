const CracoLessPlugin = require('craco-less');

module.exports = {
  plugins: [
    {
      plugin: CracoLessPlugin,
      options: {
        lessLoaderOptions: {
          lessOptions: {
            modifyVars: { '@primary-color': '#3266FF', 'link-color': '#3366ff' },
            javascriptEnabled: true,
          },
        },
      },
    },
  ],
};
