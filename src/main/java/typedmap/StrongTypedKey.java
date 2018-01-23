package typedmap;

/**
 * <p>
 *     This typed key supports runtime as well as compile time checking at the expense of a performance hit and slightly
 *     more verbose code. This enables a specific exception to be thrown by typed maps that support it as opposed to a
 *     generic {@link ClassCastException} that may be thrown anywhere in the code base.
 * </p>
 * <p>
 *     The type checking permits children of the type specified and they will be cast to the type of the key on
 *     retrieval.
 * </p>
 * @param <T> The type of the key / object.
 */
public class StrongTypedKey<T> implements TypedKey<T> {

    private final Class<T> type;

    /**
     * Construct an instance of the StrongTypedKey that will support runtime verification of the type.
     * @param type The type of the objects that can be stored with this key.
     */
    public StrongTypedKey(Class<T> type) {
        this.type = type;
    }

    /**
     * Determine whether the object is of a compatible type.
     * @param object The object that is being verified for storage (or retrieval)
     * @return true if the type is compatible, false otherwise.
     */
    public boolean verify(Object object) {
        return object == null || type.isAssignableFrom(object.getClass());
    }

    /**
     * Get the type of the objects that can be stored / retrieved using this key.
     * @return the type associate with the key.
     */
    public Class<T> getType() {
        return type;
    }

    /**
     * Syntax sugar method to make it easier to create strong typed keys.
     * @param type the class representing the type of the key.
     * @param <U> the type of the keu.
     * @return a new strong typed key instance.
     */
    public static <U> StrongTypedKey<U> typedKey(Class<U> type){
        return new StrongTypedKey<U>(type);
    }
}
