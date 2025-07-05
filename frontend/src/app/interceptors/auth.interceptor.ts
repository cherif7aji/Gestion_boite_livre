import { HttpInterceptorFn } from '@angular/common/http';

export const authInterceptor: HttpInterceptorFn = (request, next) => {
  // Retrieve the token from localStorage
  const token = localStorage.getItem('accessToken');
  const id = localStorage.getItem('userId');
  // If a token is found, clone the request and add the Authorization header
  if (token) {
    const clonedRequest = request.clone({
      setHeaders: {
        Authorization: `Bearer ${JSON.stringify({ accessToken: token })}` // Include Bearer before JSON
      }
    });

    console.log('Authorization header:', `Bearer ${JSON.stringify({ accessToken: token })}`);
    console.log('userId',`${JSON.stringify({ userId: id })}`);

    // Pass the cloned request instead of the original one to the next handler
    return next(clonedRequest);
  }

  // If no token is found, proceed with the original request
  return next(request);
};
