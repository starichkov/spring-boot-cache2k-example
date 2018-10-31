Cache2k with Spring Boot Example
=
This example is a Spring Boot application with enabled Actuator.

Application demonstrates 2 possible modes:
* Mode 1 - No cache auto refresh
* Mode 2 - With cache auto refresh

## Mode 1

Cache expires in 15 seconds, so be fast!

```http request
GET http://localhost:8080/cache2k
```

If you were fast, first result will be:

```
a:d
b:e
c:f
```

After expiring, you will see following results:

```
a:null
b:null
c:null
```

Configuration parameter for expire time: `cache.expire.at` 

## Mode 2

Cache expires every 3 seconds, but automatically refreshes.

```http request
GET http://localhost:8080/cache2kRefresh
```

Results always will be the same:

```
a:d
b:e
c:f
```

And you'll see following messages in log each time cache refreshing itself:

```
o.s.java.spring.cache2k.CacheConfig      : Loading value for key 'a'...
o.s.java.spring.cache2k.CacheConfig      : Loading value for key 'b'...
o.s.java.spring.cache2k.CacheConfig      : Loading value for key 'c'...
```

See `org.starichkov.java.spring.cache2k.CacheConfig.cacheRefreshLoader` for the details.

Configuration parameter for expire time: `cache.refresh.expire.at`
