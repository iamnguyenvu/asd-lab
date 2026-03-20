FROM node:20-alpine AS build
WORKDIR /app
COPY package.json .
RUN npm install
COPY . .
ARG VITE_GATEWAY_BASE=http://localhost:8084/api
ENV VITE_GATEWAY_BASE=$VITE_GATEWAY_BASE
RUN npm run build

FROM nginx:1.27-alpine
COPY --from=build /app/dist /usr/share/nginx/html
EXPOSE 80
