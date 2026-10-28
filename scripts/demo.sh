#!/usr/bin/env bash
set -euo pipefail
root="$(cd "$(dirname "$0")/.." && pwd)"
command -v docker >/dev/null || { echo 'Instala Docker con Compose antes de iniciar la demo.' >&2; exit 1; }
docker info >/dev/null 2>&1 || { echo 'Inicia Docker y vuelve a ejecutar este comando.' >&2; exit 1; }
if [ ! -f "$root/../inventory-frontend/package.json" ]; then
  git clone https://github.com/dan1el64/Inventario_frontend.git "$root/../inventory-frontend"
fi
if [ ! -f "$root/.env" ]; then
  umask 077
  printf 'DB_PASSWORD=%s\n' "$(openssl rand -hex 24)" > "$root/.env"
fi
docker compose --project-directory "$root" --profile demo up --build --wait
printf '\nInterfaz: http://localhost:4200\nAPI: http://localhost:8080/api/v1/products\nSwagger: http://localhost:8080/swagger-ui/index.html\n'
