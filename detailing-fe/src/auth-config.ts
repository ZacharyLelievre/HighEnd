export const auth0Config = {
  domain: process.env.REACT_APP_AUTH0_DOMAIN || "",
  clientId: process.env.REACT_APP_AUTH0_CLIENT_ID || "",
  callback: process.env.REACT_APP_AUTH0_CALLBACK_URL || "",
  audience: process.env.REACT_APP_AUTH0_AUDIENCE || "",
};
