# Part 1 - Basic Docker Compose Commands

1. `docker compose version`
2. `docker compose up`
3. `docker compose up -d`
4. `docker compose ps`
5. `docker compose down`
6. `docker compose restart`
7. `docker compose logs -f`
8. `docker compose build`
9. `docker compose exec <service_name> <command>`
10. `docker compose down -v`
11. `docker compose run <service_name> <command>`
12. `docker compose stop <service_name>`
13. `docker compose rm <service_name>`
14. `docker compose config`
15. `docker compose up -d --build`

## Suggested Practice Flow

```bash
docker compose config
docker compose up -d --build
docker compose ps
docker compose logs -f
docker compose down -v
```
