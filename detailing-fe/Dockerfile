# Stage 1: Build the React app
FROM node:18-alpine AS build

# Set working directory
WORKDIR /workspace

# Copy dependency files
COPY package*.json ./

# Install all dependencies (including devDependencies for build)
RUN npm install

# Copy the rest of the application
COPY . .

# Build the React app for production
RUN npm run build

# Stage 2: Serve the React app using `serve`
FROM node:18-alpine

# Install `serve` globally
RUN npm install -g serve

# Set working directory
WORKDIR /workspace

# Copy the build from the previous stage
COPY --from=build /workspace/build ./build

# Expose the port the app runs on
EXPOSE 3000

# Serve the built app with React Router support
CMD ["serve", "-s", "build", "-l", "3000", "--single"]