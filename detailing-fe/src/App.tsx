import "./App.css";

import { RouterProvider } from "react-router-dom";
import router from "./routes/router";
import "./pages/i18n"; // Import translation setup

function App(): JSX.Element {
  return <RouterProvider router={router} />;
}

export default App;
