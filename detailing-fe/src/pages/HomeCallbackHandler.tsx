import React, { useEffect, useState } from "react";
import { useLocation, useNavigate } from "react-router-dom";
import { useAuth0 } from "@auth0/auth0-react";
import Home from "./Home";


export default function HomeCallbackHandler() {
    const { handleRedirectCallback } = useAuth0();
    const location = useLocation();
    const navigate = useNavigate();

    // We'll only run handleRedirectCallback once
    const [callbackDone, setCallbackDone] = useState(false);

    useEffect(() => {
        async function doCallbackIfNeeded() {
            // If the URL has ?code=, we do the code exchange
            if (location.search.includes("code=")) {
                try {
                    const { appState } = await handleRedirectCallback();
                    // If Auth0 was told "appState.returnTo = '/employee-invite-success'", go there
                    if (appState?.returnTo) {
                        navigate(appState.returnTo);
                        return;
                    }
                } catch (err) {
                    console.error("Error in handleRedirectCallback:", err);
                }
            }
            setCallbackDone(true);
        }

        // Only attempt once
        if (!callbackDone) {
            doCallbackIfNeeded();
        }
    }, [callbackDone, location.search, handleRedirectCallback, navigate]);

    // If we haven't done the callback check, don't show real Home yet
    if (!callbackDone && location.search.includes("code=")) {
        return <div>Completing login...</div>;
    }

    // Otherwise, show the real Home page
    return <Home />;
}