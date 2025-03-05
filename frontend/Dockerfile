# Estágio de build: Compila o Angular
FROM node:16-alpine as build
WORKDIR /app

# Copia os arquivos de dependências e instala
COPY package.json package-lock.json ./
RUN npm install

# Copia o restante do código e faz o build
COPY . .
RUN npm run build -- --configuration=production

# Estágio de produção: Serve os arquivos compilados com Nginx
FROM nginx:alpine

# Copia os arquivos compilados do estágio de build para o Nginx
COPY --from=build /app/dist/my-angular-app /usr/share/nginx/html

# Expõe a porta 80 (HTTP)
EXPOSE 80

# Comando para rodar o Nginx
CMD ["nginx", "-g", "daemon off;"]