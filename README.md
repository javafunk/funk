Funk
====

Simplifying functional programming in Java.

Description
-----------

Funk is a library of functional utilities to aid in writing Java in a functional style.
It currently supports the following main features:

  * Lazy and eager higher order functions for manipulating collections such as `map`, `reduce`, `zip`, `filter`, `take` and `drop`.
  * A full suite of function interfaces for representing different arities of functions, predicates and procedures.
  * Collection literals and builders for assembling collections from various sources.
  * Monadic types such as `Option` and `Either`.
  * Generically typed tuples of different lengths for representing arbitrary records in a type safe manner.
  * Generators for generating potentially infinite streams of objects satisfying particular semantics.

Examples
--------

One of the most commonly used features of Funk is the collection manipulation functions. As an example, consider
an online catalogue that contains a collection of `Product` instances. Each product instance has a number of
associated `Variant` instances representing different colours or sizes. To obtain a collection containing all
possible variants, we first need to map each `Product` to its collection of `Variant`s:

```java
Iterable<Product> products = database.getAllProducts();
Iterable<Iterable<Variant>> productVariants = Lazily.map(products, new Mapper<Product, Iterable<Variant>>() {
  @Override public Iterable<Variant> map(Product product) {
    return product.getVariants();
  }
});
```

Once we have the `Variant` collections, to obtain a flattened collection of those variants, we need to concatenate:

```java
Iterable<Variant> allVariants = Iterables.concat(productVariants);
```

And finally, to get the set of all unique variants, we can convert the `Iterable` to a `Set`:

```java
Set<Variant> uniqueVariants = Literals.setFrom(allVariants);
```

Whilst this example is slightly contrived, this manner of collection manipulation is common in many different
use cases. It is important to note:

  * The mapping is performed lazily as is the concatenation of the `Iterable` instances. Thus, no collection
    manipulation is actually performed until the `Iterable` is iterated to construct the final `Set`.
  * All operations leave the input collections unchanged and represent pure functions.

For more information see the project [wiki](https://github.com/javafunk/funk/wiki) or the Javadoc documentation.

Binary Releases
---------------

The latest released version of Funk is [0.1.13](http://search.maven.org/#artifactdetails%7Corg.javafunk.funk%7Cfunk%7C0.1.13%7Cjar)
which can be obtained from [Maven Central](http://search.maven.org/#search%7Cgav%7C1%7Cg%3A%22org.javafunk.funk%22%20AND%20a%3A%22funk%22).

Planned Features
----------------

The highest priority future features are outlined below:

  * Full Javadoc documentation of the public API (required before 1.0).
  * Extended collection interfaces and implementations to allow a more fluid approach to performing collection
    manipulations for those projects that prefer not to use static methods so frequently.
  * Immutability as a first class concern.
  * Function base classes and manipulation methods such as composition and partial evaluation.

Contributions
-------------

Funk is always looking for contributors so if you find it useful please consider fixing an issue. The
[issue page](https://github.com/javafunk/funk/issues) is used to track all feature requests and bugs.
To contribute, fork the [main repository](https://github.com/javafunk/funk), make your changes on a
branch and make a pull request from that branch.

Contributors
------------

See the [contributors](https://github.com/javafunk/funk/contributors) page.

License
-------
[The BSD License](http://opensource.org/licenses/bsd-license.php).