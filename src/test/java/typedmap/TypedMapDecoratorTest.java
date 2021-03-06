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

import org.junit.Assert;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;
import static typedmap.TypedMapDecorator.typedMap;

/**
 * <p>&#169; 2014 Robert Boothby.</p>
 *
 * @author Robert Boothby.
 */
public class TypedMapDecoratorTest {

    TypedKey<String> stringTypedKey = DefaultTypedKey.typedKey();
    TypedKey<String> otherStringTypedKey = DefaultTypedKey.typedKey();
    TypedKey<Integer> integerTypedKey = DefaultTypedKey.typedKey();
    TypedKey<String> stringStrongTypedKey = StrongTypedKey.typedKey(String.class);


    @Test
    public void shouldStoreAndRetrieveTypedValueWithoutACast() {
        //Given
        TypedMap typedMap = typedMap();

        //When
        typedMap.putTyped(stringTypedKey, "aString!");

        //Then
        String actual = typedMap.getTyped(stringTypedKey);
        assertThat(actual, is(equalTo("aString!")));
    }

    @Test
    public void shouldStoreSameTypesByDifferentKeys() {
        TypedMap typedMap = typedMap();

        //When
        typedMap.putTyped(stringTypedKey, "aString!");
        typedMap.putTyped(otherStringTypedKey, "anotherString!");

        //Then
        assertThat(typedMap.getTyped(stringTypedKey), is(equalTo("aString!")));
        assertThat(typedMap.getTyped(otherStringTypedKey), is(equalTo("anotherString!")));
    }

    @Test
    public void shouldStoreAndRetrieveDifferentTypesInSameMapWithoutCasting() {
        //Given
        TypedMap typedMap = typedMap();

        //When
        typedMap.putTyped(stringTypedKey, "aString!");
        typedMap.putTyped(integerTypedKey, 1);

        //Then
        assertThat(typedMap.getTyped(stringTypedKey), is(equalTo("aString!")));
        assertThat(typedMap.getTyped(integerTypedKey), is(equalTo(1)));
    }

    @Test
    public void shouldRemoveEntryAndReportValue() {
        //Given
        TypedMap typedMap = typedMap();
        typedMap.putTyped(stringTypedKey, "aString!");
        typedMap.putTyped(otherStringTypedKey, "anotherString!");

        //When
        String removedValue = typedMap.removeTyped(stringTypedKey);

        //Then
        assertThat(typedMap.containsKey(stringTypedKey), is(false));
        assertThat(typedMap.containsValue("aString!"), is(false));

        assertThat(typedMap.containsKey(otherStringTypedKey), is(true));
        assertThat(typedMap.containsValue("anotherString!"), is(true));

        assertThat(removedValue, is("aString!"));
    }

    @Test
    public void shouldStoreCorrectStrongTypedValue(){
        //Given
        TypedMap typedMap = typedMap();

        //When
        typedMap.putTyped(stringStrongTypedKey, "ABCDEF");

        //Then
        assertThat(typedMap.containsKey(stringStrongTypedKey), is(true));
        assertThat(typedMap.containsValue("ABCDEF"), is(true));

        String actual = typedMap.getTyped(stringStrongTypedKey);
        assertThat(actual, is("ABCDEF"));

    }

    @Test
    @SuppressWarnings("unchecked")
    public void shouldRefuseToStoreWrongStrongTypeDespiteCasting(){
        //Given
        TypedMap typedMap = typedMap();

        //This is exactly what you should not be doing with typed keys but is useful for the test!!!
        TypedKey castKey = stringStrongTypedKey;
        TypedKey<Object> objectCastKey = (TypedKey<Object>) castKey;

        try {
            //When
            typedMap.putTyped(objectCastKey, new Object());
            //Then
            Assert.fail("Should have thrown a class cast exception");
        } catch (ClassCastException e) {
            assertThat(e.getMessage(), is("The type of the key {class java.lang.String} does not match the type of the object to be stored {class java.lang.Object}."));
        }
    }

    @Test
    public void shouldNotReturnOldValueIfOfWrongStrongTypeForTypedPut(){
        //Given
        Map<TypedKey<?>, Object> map = new HashMap<TypedKey<?>, Object>();
        TypedMapDecorator typedMap = new TypedMapDecorator(map);
        map.put(stringStrongTypedKey, 1);

        //When
        String result = typedMap.putTyped(stringStrongTypedKey, "ABCDEF");

        //Then
        assertThat(result, is(nullValue()));

    }

    @Test
    public void shouldNotReturnOldValueIfOfWrongStrongTypeForTypedRemove(){
        //Given
        Map<TypedKey<?>, Object> map = new HashMap<TypedKey<?>, Object>();
        TypedMapDecorator typedMap = new TypedMapDecorator(map);
        map.put(stringStrongTypedKey, 1);

        //When
        String result = typedMap.removeTyped(stringStrongTypedKey);

        //Then
        assertThat(result, is(nullValue()));
    }

}
