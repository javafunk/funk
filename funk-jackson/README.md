Funk Jackson
============

Simplifying functional programming in Java.

Description
-----------

Project to build Jackson module to support JSON serialization and deserialization of Funk types.

This project is basically a port of the [Guava Jackson module](https://github.com/FasterXML/jackson-datatype-guava) for Funk.

Registering module
------------------

Like all standard Jackson modules (libraries that implement Module interface), registration is done as follows:

```java
ObjectMapper mapper = new ObjectMapper();
mapper.registerModule(new FunkModule());
```

after which functionality is available for all normal Jackson operations.
