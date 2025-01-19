import React from "react";
import ReactDOM from "react-dom/client";
import App from "./App";
import { Auth0Provider } from "@auth0/auth0-react";
import { auth0Config } from "./auth-config";

// Log all the variables used
console.log("Auth0 Config:");
console.log("Domain:", auth0Config.domain);
console.log("Client ID:", auth0Config.clientId);
console.log("Audience:", auth0Config.audience);
console.log("Redirect URI:", `${window.location.origin}/home`);

ReactDOM.createRoot(document.getElementById("root")!).render(
    <React.StrictMode>
        <Auth0Provider
            domain={auth0Config.domain}
            clientId={auth0Config.clientId}
            authorizationParams={{
                redirect_uri: `${window.location.origin}/home`,
                audience: auth0Config.audience,
                scope: "openid profile email",
            }}
        >
            <App />
        </Auth0Provider>
    </React.StrictMode>,
);