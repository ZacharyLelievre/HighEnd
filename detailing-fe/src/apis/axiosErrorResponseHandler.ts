import { AxiosError } from 'axios';
import router from '../routes/router';

// map status codes to error pages
const errorPageRedirects: Record<number, string> = {
  401: '/error', 
  403: '/error', 
  408: '/error', 
  500: '/error', 
  503: '/error', 
};

// handles error and redirects based on status codes
export default function axiosErrorResponseHandler(
  error: AxiosError,
  statusCode: number
): void {
  const redirectPath = errorPageRedirects[statusCode];

  if (redirectPath) {
    // log for easy debug
    console.error(`Redirecting to ${redirectPath} due to error:`, error);
    router.navigate(redirectPath);
  } else {
    // log whatever that wasn't handled
    console.error('Unhandled error:', error, 'Status code:', statusCode);
  }
}
