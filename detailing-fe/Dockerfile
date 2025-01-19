# Use a lightweight Node.js image
FROM node:20-alpine

# Set working directory
WORKDIR /app

# Copy dependency files
COPY package*.json ./

# Install only production dependencies
RUN npm install

# Copy the rest of the application
COPY . .

# Build the React app for production
RUN npm run build

# Add runtime environment injection script
RUN echo 'window.__RUNTIME_CONFIG__ = {' > /app/build/env-config.js && \
    echo '  REACT_APP_AUTH0_DOMAIN: "'${REACT_APP_AUTH0_DOMAIN}'",' >> /app/build/env-config.js && \
    echo '  REACT_APP_AUTH0_CLIENT_ID: "'${REACT_APP_AUTH0_CLIENT_ID}'",' >> /app/build/env-config.js && \
    echo '  REACT_APP_AUTH0_AUDIENCE: "'${REACT_APP_AUTH0_AUDIENCE}'",' >> /app/build/env-config.js && \
    echo '  REACT_APP_AUTH0_CALLBACK_URL: "'${REACT_APP_AUTH0_CALLBACK_URL}'"' >> /app/build/env-config.js && \
    echo '};' >> /app/build/env-config.js

# Install `serve` to serve the static files
RUN npm install -g serve

# Expose the port the app runs on
EXPOSE 3000

# Serve the built app with React Router support
CMD ["serve", "-s", "build", "-l", "3000", "--single"]
