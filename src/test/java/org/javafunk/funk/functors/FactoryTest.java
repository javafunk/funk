package org.javafunk.funk.functors;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class FactoryTest {
    @Mock Factory<String> factory;

    @Test
    public void shouldDelegateCallToCreate() throws Exception {
        // Given
        String expected = "something created";
        given(factory.call()).willCallRealMethod();
        given(factory.create()).willReturn(expected);

        // When
        String actual = factory.call();

        // Then
        verify(factory).create();
        assertThat(actual, is(expected));
    }
}
