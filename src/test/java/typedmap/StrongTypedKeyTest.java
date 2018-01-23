package typedmap;

import org.junit.Test;
import typedmap.StrongTypedKey;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static typedmap.StrongTypedKey.typedKey;

public class StrongTypedKeyTest {

    @Test
    public void shouldVerifySameType(){
        //Given
        StrongTypedKey<String> key = typedKey(String.class);

        //When
        boolean result = key.verify("TestString");

        //Then
        assertThat(result, is(true));
    }

    @Test
    public void shouldNotVerifyWrongType(){
        //Given
        StrongTypedKey<String> key = typedKey(String.class);

        //When
        boolean result = key.verify(10);

        //Then
        assertThat(result, is(false));
    }

    @Test
    public void shouldVerifyChildType(){
        //Given
        StrongTypedKey<CharSequence> key = typedKey(CharSequence.class);

        //When
        boolean result = key.verify("TestString");

        //Then
        assertThat(result, is(true));
    }
}
