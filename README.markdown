# Scala Template for Projects

A slightly opinionated [Giter8][g8] template for a scala project. By default, it sets up a project with the following features:
 - Uses [`sbt`](https://www.scala-sbt.org/) for builds, tests, and running the server
 - [`tapir`](https://tapir-scala.readthedocs.io/en/latest/) API backed by [`http4s`](https://http4s.org) and [`doobie`](https://tpolecat.github.io/doobie/docs/01-Introduction.html)
 - Easy integration with [`geotrellis-server`](https://github.com/geotrellis/geotrellis-server)

## Usage

1. Ensure `java` (>= 8) is installed, if you have trouble with this try using [`jabba`](https://github.com/shyiko/jabba#jabba--)
2. Install [`sbt`](https://www.scala-sbt.org/download.html) (version >= 1.x)
3. Install `docker`
4. From a terminal shell Run `sbt new azavea/azavea.g8`
5. Change directory into new project, run `./scripts/setup && ./scripts/server`
6. `curl http://localhost:8080/api/users/`


[g8]: http://www.foundweekends.org/giter8/
