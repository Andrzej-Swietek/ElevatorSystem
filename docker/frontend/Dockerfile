FROM node:18.3.0-alpine

WORKDIR /usr/src/app

ENV PORT 3000
RUN apk add --update python3 make g++ && rm -rf /var/cache/apk/*

COPY . .

RUN yarn install

#RUN yarn run build

EXPOSE 3000

CMD ["yarn", "dev"]