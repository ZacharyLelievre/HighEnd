import './App.css';

import { RouterProvider } from 'react-router-dom'
import router from './routes/router';
import { JSX } from 'react/jsx-runtime';

function App(): JSX.Element {
  return <RouterProvider router={router} />;
}

export default App;