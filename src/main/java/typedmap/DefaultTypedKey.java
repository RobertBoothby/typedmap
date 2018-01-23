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
 * Default implementation of the TypedKey.
 * <p>&#169; 2014 Robert Boothby.</p>
 *
 * @author Robert Boothby.
 */
public class DefaultTypedKey<T> implements TypedKey<T> {

    /**
     * Create an instance of DefaultTypedKey that can be used with a {@link TypedMap}.
     * @param <T> The type of the object associated with this DefaultTypedKey.
     * @return an instance of DefaultTypedKey.
     */
    public static <T> DefaultTypedKey<T> typedKey(){
        return new DefaultTypedKey<T>();
    }

    @Override
    public boolean verify(Object object) {
        return true;
    }

    @Override
    public Class<T> getType() {
        return null;
    }
}
