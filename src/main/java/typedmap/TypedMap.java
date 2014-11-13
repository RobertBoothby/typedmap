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

import java.util.Map;

/**
 * <p>
 * Simple interface for a 'Typed Map' which allows the use of a special type of key that uses Generics to strengthen the
 * typing of values added and retrieved to it. As long as the compile time typing is honored you can only store values
 * whose type matches the key used to store them and can when values are retrieved they are retrieved as the type used
 * at storage.
 * </p>
 * <p>&#169; 2014 Robert Boothby.</p>
 *
 * @author Robert Boothby.
 */
public interface TypedMap extends Map<TypedKey<?>, Object> {

    /**
     * Store the value in the TypedMap against the TypedKey used.
     * @param key The TypedKey to use for storage.
     * @param value The value whose type must match the type of the TypedKey
     * @param <T> The type of the value associated with the TypedKey.
     * @return The value that was previously stored against the key or null if a new value.
     */
    <T> T putTyped(TypedKey<T> key, T value);

    /**
     * Get the value from the map that is stored against the TypedKey. The value will be cast correctly as long as the
     * TypedKey's type is honored at compile time.
     * @param key The TypedKey to use.
     * @param <T> The type of the value associated with the TypedKey.
     * @return The value stored against the key or null if not present.
     */
    <T> T getTyped(TypedKey<T> key);

    /**
     * Remove a key and value from the TypedMap.
     * @param key The TypedKey whose value is to be removed from the map.
     * @param <T> The type of the value associated with the TypedKey.
     * @return The value that was stored against the key or null if no value was stored.
     */
    <T> T removeTyped(TypedKey<T> key);

}
