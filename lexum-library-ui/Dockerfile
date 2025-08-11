# lexum-library-ui/Dockerfile
# ---------------------------
# Multi-stage build:
# 1) builder (Node) : installe deps et build l'app Angular
# 2) runtime (nginx) : sert le dossier dist

#####################################
# Stage 1 - Build with Node
#####################################
FROM node:20.19-alpine AS builder

ARG APP_NAME=lexum-library-ui
ARG NG_BUILD_CONFIG=production

WORKDIR /app

# Copier package.json et package-lock (si présent) en premier pour profiter du cache Docker
COPY package.json package-lock.json* ./

# Installer les dépendances (npm ci si lockfile présent)
RUN if [ -f package-lock.json ]; then npm ci --legacy-peer-deps; else npm install --legacy-peer-deps; fi

# Copier tout le projet
COPY . .

RUN npx ng build ${APP_NAME} --configuration ${NG_BUILD_CONFIG}

#####################################
# Stage 2 - Serve with nginx
#####################################
FROM nginx:1.25-alpine

RUN rm -rf /usr/share/nginx/html/*

# Copier le build depuis le builder
COPY --from=builder /app/dist/lexum-library-ui/browser /usr/share/nginx/html

EXPOSE 80

# Lancement de nginx
CMD ["nginx", "-g", "daemon off;"]
