FROM node:20.15.0-alpine

WORKDIR /usr/src/app

ENV PORT 4200
RUN apk add --update python3 make g++ && rm -rf /var/cache/apk/*

COPY . .

RUN npm install

# RUN npm run build

EXPOSE 4200

CMD ["npm", "run", "start"]