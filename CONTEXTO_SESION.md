# Contexto de sesión — retomar en Claude Code

> Pega este contenido como primer mensaje al abrir Claude Code en esta carpeta,
> o consúltalo tú mismo antes de empezar. No es una convención de equipo (eso
> vive en `CLAUDE.md`), es la foto de dónde va el proyecto formativo.

## De qué va esto

José está preparando el contenido de un curso corporativo de 27 módulos,
**"Claude Code y Alternativas IA para Desarrolladores Java"**, encargado por
Imagina Formación. El curso reaprovecha material de dos cursos propios de
Udemy ("Claude Code y Apps con IA" y "SDD con Claude Code y Codex"), cruzado
con otros cursos de Udemy y libros/vídeos de O'Reilly, en una matriz de
trabajo (Excel) que marca % de aprovechamiento por módulo y qué falta crear.

## Dónde estamos ahora

- La matriz de los 27 módulos ya está hecha: la mayoría de módulos están al
  70-95% de aprovechamiento; solo quedan dos huecos reales sin apenas fuente
  reutilizable (documentación técnica/ADR, y la ejecución práctica de
  migraciones de dependencias con IA).
- Antes de redactar el curso capítulo a capítulo, José necesita hacer una
  **demo de validación con el cliente** para confirmar el enfoque.
- Se eligió el **módulo 13 (Revisión de código, Pull Requests y análisis de
  cambios)** como demo: es rápido de montar, tiene alto aprovechamiento y
  conecta directamente con un dolor de negocio (revisiones lentas, bugs que
  se cuelan) que resuena tanto con perfiles técnicos como con quien decide la
  compra.

## Qué es este repositorio

Es el material de apoyo de esa demo, no el curso en sí:

- Rama `main`: CRUD limpio de `Cliente` (Spring Boot + JPA + H2), con su test.
- Rama `feature/demo-pr`: añade la gestión de `Pedido`s con **4 problemas
  plantados a propósito** para que la revisión de Claude Code tenga algo real
  que encontrar — inyección SQL, N+1 de Hibernate, una excepción silenciada
  que viola `CLAUDE.md`, y un controller nuevo sin ningún test.
- `CLAUDE.md`: las convenciones del equipo ficticio, que es lo que permite que
  Claude detecte 2 de los 4 problemas por contradecirlas explícitamente.
- `.github/workflows/claude-review.yml`: workflow de ejemplo para disparar la
  revisión automática comentando `@claude` en el PR (pendiente de configurar
  el secret `ANTHROPIC_API_KEY` antes de la demo real).
- Existe también un guion de demo aparte (`Guion_Demo_Modulo13_Revision_PRs.md`,
  no incluido en este repo) con el paso a paso, tiempos, plan B sin conexión y
  preguntas frecuentes del cliente con respuestas sugeridas.

**Aviso:** el código se revisó a mano línea por línea pero no se ha podido
compilar todavía (sin Maven/JDK completo en el entorno donde se generó).
Antes de la demo real: `mvn spring-boot:run` y confirmar que arranca.

## Qué tiene sentido hacer ahora en esta sesión de Claude Code

Dependiendo de en qué punto retomes:

1. **Si es la primera vez que abres el repo aquí**: compílalo y arráncalo
   (`mvn spring-boot:run`), confirma que los 4 endpoints responden, y haz un
   `git checkout feature/demo-pr` para tener el PR listo para subir a GitHub.
2. **Si vas a ensayar la demo**: sube ambas ramas a un repo de GitHub real,
   abre el Pull Request de `feature/demo-pr` contra `main`, y prueba tanto la
   Opción A (GitHub Actions con `@claude`) como la Opción B (revisión local
   con `claude -p "..."`, el prompt exacto está en el guion de la demo).
3. **Si la demo ya se validó con el cliente**: el siguiente paso natural es
   convertir este mismo repo y sus 4 problemas plantados en el material
   didáctico completo del módulo 13 (documentar cada paso para que lo pueda
   seguir un alumno, no solo mostrarlo en vivo).
4. **Si quieres adelantar otros módulos** mientras se confirma la demo: los
   candidatos con mayor aprovechamiento y menos trabajo pendiente son los
   módulos 17 (Skills), 18 (Subagentes) y 19 (MCP) — están al 90-95% en la
   matriz.
