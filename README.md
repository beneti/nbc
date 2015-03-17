# nubank challenge #

## sbt ##

install sbt following this [instructions](http://www.scala-sbt.org/0.13/tutorial/Setup.html)

## to run tests ##
```sh
$ cd nubank_challenge
$ ./sbt
> test
```

## Build & Run ##

```sh
$ cd nubank_challenge
$ ./sbt
> container:start
> browse
```

If `browse` doesn't launch your browser, manually open [http://localhost:8080/](http://localhost:8080/) in your browser.

## routes ##
```
GET /api/centrality/ -> to get the ranking of vertices
POST /api/centrality/edge -> body x, y as int to add a edge on graph
PUT /api/centrality/vertex -> body id as int to set the vertex as fraudulent
```