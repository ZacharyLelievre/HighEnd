# Use a lightweight Node.js image
FROM node:18-alpine

# Set working directory to /workspace to match DigitalOcean's expectation
WORKDIR /workspace

# Copy dependency files
COPY package*.json ./

# Install all dependencies (including devDependencies for build)
RUN npm install

# Copy the rest of the application
COPY . .

# Build the React app for production
RUN npm run build

# Install `serve` to serve the static files
RUN npm install -g serve

# Expose the port the app runs on
EXPOSE 3000

# Serve the built app with React Router support
CMD ["serve", "-s", "build", "-l", "3000", "--single"]