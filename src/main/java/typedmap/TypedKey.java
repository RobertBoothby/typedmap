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

/**
 * Instances of this interface can be used as TypedKeys with {@link TypedMap}s. When a typed key is used, only object
 * of the correct type can be stored with it and more importantly the compiler will handle casting and run time type
 * checking (using the Generics framework) when retrieving values by the key.
 * @param <T> the type of the object that is stored and retrieved by this key.
 * <p>&#169; 2014 Robert Boothby.</p>
 *
 * @author Robert Boothby.
 */
public interface TypedKey<T> {
}
