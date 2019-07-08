const fs = require('fs');
const path = require('path');

module.exports = {
  parseVersion,
  root,
  isExternalLib
};

// Returns the second occurrence of the version number from `build.gradle` file
function parseVersion() {
  const versionKey = /^semver=/gm;
  let versionProps;
  try {
    versionProps = fs.readFileSync('build/generated/semver.properties', 'utf8');
  } catch (ex) {

    return "can-not-find-semver-properties-run-build-for-it";
  }

  const prop = versionProps.split("\n")
    .filter(e => {
      return versionKey.exec(e);
    });

  return prop.toString().split("=")[1];
}

const _root = path.resolve(__dirname, '..');

function root(args) {
  args = Array.prototype.slice.call(arguments, 0);
  return path.join.apply(path, [_root].concat(args));
}

function isExternalLib(module, check = /node_modules/) {
  const req = module.userRequest;
  if (typeof req !== 'string') {
    return false;
  }
  return req.search(check) >= 0;
}
