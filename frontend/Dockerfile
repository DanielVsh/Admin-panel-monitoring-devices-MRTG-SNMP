FROM node:latest as build
WORKDIR /app
COPY package*.json ./
RUN npm install
COPY . .
RUN npm run build

FROM nginx:latest
COPY nginx.conf /etc/nginx/nginx.conf
#COPY --from=build app/build/* /var/www/html/
COPY --from=build app/build /var/www/html/
EXPOSE 80

CMD ["nginx", "-g", "daemon off;"]