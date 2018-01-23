/*
Copyright 2014 Robert Boothby

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
*/
package typedmap;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * <p>A simple implementation of {@link TypedMap} that acts as a decorator on an instance of Map.</p>
 * <p>&#169; 2014 Robert Boothby.</p>
 *
 * @author Robert Boothby.
 */
public class TypedMapDecorator implements TypedMap {

    /**
     * If you have a use case needing external access to the wrapped map then just subclass to add a getter (preferably
     * copying the map rather than returning it raw) however please remember the Law of Demeter
     * (@link http://en.wikipedia.org/wiki/Law_of_Demeter}.
     */
    protected final Map<TypedKey<?>, Object> wrappedMap;

    /**
     * Simple method creating an instance of TypedMapDecorator with an empty HashMap.
     * @return an instance of TypedMapDecorator wrapping an empty HashMap.
     */
    public static TypedMapDecorator typedMap() {
        return new TypedMapDecorator(new HashMap<TypedKey<?>, Object>());
    }

    /**
     * Construct an instance using the passed in map.
     * @param wrappedMap The map to be used by the new instance.
     */
    public TypedMapDecorator(Map<TypedKey<?>, Object> wrappedMap) {
        this.wrappedMap = wrappedMap;
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> T getTyped(TypedKey<T> key) {
        Object o = this.get(key);

        //This is an interesting one - this check is done here to preserve the map interface semantic where a
        //key supplied for retrieval need only be equal to the key used for storage. Not sure I agree but there it is.
        if(!key.verify(o)){
            throw new ClassCastException("The type of the key {" + key.getType() +
                    "} does not match the type of the stored object {" + o.getClass() + "}.");
        }

        return (T) o;
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> T putTyped(TypedKey<T> key, T value) {
        Object oldValue = this.put(key, value);
        if(key.verify(oldValue)) {
            return (T) oldValue;
        } else {
            //This may never happen but I'm going POLS this and return null, not throwing an exception.
            return null;
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> T removeTyped(TypedKey<T> key) {
        Object oldValue = this.remove(key);
        if(key.verify(oldValue)) {
            return (T) oldValue;
        } else {
            //This may never happen but I'm going POLS this and return null, not throwing an exception.
            return null;
        }
    }

    @Override
    public int size() {
        return wrappedMap.size();
    }

    @Override
    public boolean isEmpty() {
        return wrappedMap.isEmpty();
    }

    @Override
    public boolean containsKey(Object key) {
        return wrappedMap.containsKey(key);
    }

    @Override
    public boolean containsValue(Object value) {
        return wrappedMap.containsValue(value);
    }

    @Override
    public Object get(Object key) {
        return wrappedMap.get(key);
    }

    @Override
    public Object put(TypedKey<?> key, Object value) {
        if(key.verify(value)) {
            return wrappedMap.put(key, value);
        } else {
            throw new ClassCastException("The type of the key {" + key.getType() +
                    "} does not match the type of the object to be stored {" + value.getClass() + "}.");
        }
    }

    @Override
    public Object remove(Object key) {
        return wrappedMap.remove(key);
    }

    @Override
    public void putAll(Map<? extends TypedKey<?>, ?> m) {
        wrappedMap.putAll(m);
    }

    @Override
    public void clear() {
        wrappedMap.clear();
    }

    @Override
    public Set<TypedKey<?>> keySet() {
        return wrappedMap.keySet();
    }

    @Override
    public Collection<Object> values() {
        return wrappedMap.values();
    }

    @Override
    public Set<Entry<TypedKey<?>, Object>> entrySet() {
        return wrappedMap.entrySet();
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof TypedMapDecorator && wrappedMap.equals(((TypedMapDecorator) o).wrappedMap);
    }

    @Override
    public int hashCode() {
        return wrappedMap.hashCode();
    }
}
