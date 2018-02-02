typedmap
========

This project extends the Java Map collection interface to add support for typed keys that determine  the types of
objects that are stored and retrieved by them. and includes some usable implementations.

To use this one must first define some typed keys. e.g.

    public static final TypedKey<FileSystemContext> FILE_SYSTEM_CONTEXT_KEY= DefaultTypedKey.typedKey();
    private TypedKey<Name> userNameKey = DefaultTypedKey.typedKey();
    private TypedKey<Address> userAddressKey = DefaultTypedKey.typedKey();
    private TypedKey<Email> userEmailKey = StrongTypedKey.typedKey(Email.class);

Once the keys have been defined they can be used to store values in a TypedMap. e.g.

    TypedMap executionContext = TypedMapDecorator.typedMap();
    ...
    executionContext.putTyped(userNameKey, userName);
    executionContext.putTyped(userAddressKey, userAddress);
    executionContext.putTyped(userEmailkey, userEmail);

If you try to store something of the wrong type for a key then it won't compile. e.g.

    //Won't compile
    executionContext.putTyped(userNameKey, userAddress);

Finally the stored values can be retrieved from the map without casting. e.g.

    Name userName = executionContext.getTyped(userNameKey);
    Address userAddress = executionContext.getTyped(userAddressKey);

The DefaultTypedKey is the simplest implementation of TypedKey that will work, relying on the underlying equals and
hashcode implementations from java.lang.Object. This means that the keys will only work in contexts where their
instances are accessible to both the putTyped and getTyped methods; commonly this is useful where the keys are held
as Constants on a common class or interface or where the keys are defined and utilised within the same method
invocation.

This default implementation only provides typing at compile time by using the Generics capabilities. Due to type erasure, it is
feasible to create some interesting runtime exceptions. This should be no more of a problem than with current Generic
collection types but should be considered.

The StrongTypedKey is a more sophisticated implementation that supports runtime type checking. It too relies on the
underlying equals and hashcode implementations from java.lang.Object. In most contexts a ClassCastException will be 
thrown but please refer to the documentation for full details.

The TypedKey interface is designed so that if you wish to move beyond this, you can define TypedKeys yourself and use
them in different contexts and use cases. A note of caution though, this means that you may lose one of the benefits of
using TypedMap and TypedKeys in that you may have to know the type when you create a new copy of the key for retrieval
which is no real saving over casting with a traditional map.

The easiest way to use this project is to import it in Maven.

    <project>
        ...
        <dependencies>
            ...
            <dependency>
                <groupId>com.robertboothby</groupId>
                <artifactId>TypedMap</artifactId>
                <version>0.2.0</version>
            </dependency>
            ...
        </dependencies>
        ...
    </project>
    

