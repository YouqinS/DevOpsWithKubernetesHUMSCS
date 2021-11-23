#FROM node:latest
FROM node:14-alpine

COPY .  .

RUN node -v && npm -v

RUN npm install

RUN npm run build

RUN npm install -g serve


EXPOSE 3000

CMD ["npm", "start"]