# demo-clientes — Repo mínimo para la demo del Módulo 13

API REST muy simple de gestión de clientes (Spring Boot + JPA + H2 en memoria),
pensada para la demo de validación con el cliente del módulo **"Revisión de código,
Pull Requests y análisis de cambios"** del curso *Claude Code y Alternativas IA
para Desarrolladores Java*.

## Cómo está organizado

- **Rama `main`**: estado "limpio" del proyecto — CRUD básico de `Cliente`, con su
  test correspondiente. Esto es lo que el equipo ya tenía en producción.
- **Rama `feature/demo-pr`**: añade una funcionalidad nueva (gestión de `Pedido`s
  asociados a un `Cliente`) tal y como la habría entregado un desarrollador del
  equipo. Contiene **4 problemas plantados deliberadamente** para que la revisión
  de Claude Code tenga algo real que encontrar:

  1. **Inyección SQL** — `ClienteRepository.buscarPorNombreInseguro(String)` construye
     la consulta concatenando el parámetro de entrada.
  2. **N+1 de Hibernate** — el endpoint `GET /clientes/con-pedidos` itera clientes y
     accede a `cliente.getPedidos()` (relación `LAZY`) dentro del bucle, sin `JOIN FETCH`.
  3. **Excepción silenciada** — `ClienteService.eliminarPedido(...)` captura la excepción
     y no hace nada con ella, violando la convención de `CLAUDE.md`.
  4. **Endpoint sin test** — los dos endpoints nuevos de `PedidoController` no tienen
     ningún test asociado.

- **`CLAUDE.md`**: las convenciones del equipo. Claude Code las lee antes de revisar,
  así que el punto 2 y el punto 3 de la lista de arriba se detectan precisamente
  porque contradicen lo que dice este fichero.

- **`.github/workflows/claude-review.yml`**: workflow de ejemplo para disparar la
  revisión automática de Claude Code al abrir el PR o al comentar `@claude` en él.
  Necesita el secret `ANTHROPIC_API_KEY` configurado en el repositorio antes de la demo.

## Cómo montar el PR de la demo

```bash
git checkout main
git checkout -b feature/demo-pr   # ya existe en este repo empaquetado, solo hace falta el push
git push -u origin main
git push -u origin feature/demo-pr
```

Luego, desde GitHub, abre un Pull Request de `feature/demo-pr` contra `main`.
Ese PR es el que se usa en el paso 2 del guion de la demo.

## Cómo correr la Opción B (revisión local, plan B del guion)

Con el repo en la rama `feature/demo-pr` y Claude Code instalado y autenticado:

```bash
claude -p "Actúa como revisor senior de un equipo Java/Spring. Revisa el diff de la rama feature/demo-pr contra main. Señala por separado: vulnerabilidades de seguridad, problemas de rendimiento con JPA/Hibernate, incumplimientos de las convenciones definidas en CLAUDE.md, y tests que falten para el código nuevo."
```

## Cómo arrancar la app (opcional, si quieres enseñar que compila y funciona)

```bash
mvn spring-boot:run
```

La API queda en `http://localhost:8080/clientes`. La consola de H2 está en
`http://localhost:8080/h2-console` (JDBC URL: `jdbc:h2:mem:demodb`).
