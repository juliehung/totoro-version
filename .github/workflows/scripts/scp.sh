
which ssh-agent || ( apt-get update -y && apt-get install openssh-client -y )
eval $(ssh-agent -s)

mkdir -p "${GITHUB_WORKSPACE}"/.ssh
chmod 700 "${GITHUB_WORKSPACE}"/.ssh

echo "${SECRET}" > "${GITHUB_WORKSPACE}"/key
chmod 700 "${GITHUB_WORKSPACE}"/key

ssh-keyscan "${TARGET_HOST}" >> "${GITHUB_WORKSPACE}"/.ssh/known_hosts
chmod 644 "${GITHUB_WORKSPACE}"/.ssh/known_hosts

scp -i "${GITHUB_WORKSPACE}"/key -o UserKnownHostsFile="${GITHUB_WORKSPACE}"/.ssh/known_hosts "${SOURCE_PATH}" "${TARGET_USER}"@"${TARGET_HOST}":"${TARGET_PATH}"
