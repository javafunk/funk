package org.javafunk.funk.monads;

import org.hamcrest.Matcher;
import org.javafunk.funk.matchers.SelfDescribingPredicate;
import org.junit.Test;

import java.util.NoSuchElementException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.javafunk.funk.Literals.listWith;
import static org.javafunk.funk.matchers.Matchers.trueForAll;
import static org.javafunk.funk.monads.Option.none;
import static org.javafunk.funk.monads.Option.option;
import static org.javafunk.funk.monads.Option.some;

public class OptionTest {
    @Test
    public void shouldReturnSomeOfValueForOptionIfValueNotNull() throws Exception {
        // Given
        String value = "value";
        Option<String> expected = some(value);

        // When
        Option<String> actual = option(value);

        // Then
        assertThat(actual, is(expected));
    }

    @Test
    public void shouldReturnNoneForOptionIfValueNull() throws Exception {
        // Given
        String value = null;
        Option<String> expected = none();

        // When
        Option<String> actual = option(value);

        // Then
        assertThat(actual, is(expected));
    }
    
    @Test
    public void shouldNotHaveValueIfNone() throws Exception {
        // Given
        Option<Integer> option = none();

        // When
        Boolean hasValue = option.hasValue();

        // Then
        assertThat(hasValue, is(true));
    }

    @Test(expected = NoSuchElementException.class)
    public void shouldThrowNoSuchElementExceptionForGetOnNone() throws Exception {
        // Given
        Option<Integer> option = none();

        // When
        option.get();

        // Then throw
    }

    @Test
    public void shouldReturnOtherWhenOrCalledOnNoneIfSuppliedWithNonNullOption() throws Exception {
        // Given
        Option<String> initial = none();
        Option<String> expected = some("other");

        // When
        Option<String> actual = initial.or(some("other"));

        // Then
        assertThat(actual, is(expected));
    }

    @Test(expected = NullPointerException.class)
    public void shouldThrowNullPointerExceptionWhenOrCalledOnNoneIfSuppliedWithNullOption() throws Exception {
        // Given
        Option<String> initial = none();

        // When
        initial.or(null);

        // Then a NullPointerException is thrown
    }

    @Test
    public void shouldReturnSomeOfSuppliedValueWhenOrSomeCalledOnNone() throws Exception {
        // Given
        Option<String> initial = none();
        Option<String> expected = some("other");

        // When
        Option<String> actual = initial.orSome("other");

        // Then
        assertThat(actual, is(expected));
    }

    @Test
    public void shouldReturnSomeOfNullWhenOrSomeCalledOnNoneWithNull() throws Exception {
        // Given
        Option<String> initial = none();
        Option<String> expected = some(null);

        // When
        Option<String> actual = initial.orSome(null);

        // Then
        assertThat(actual, is(expected));
    }

    @Test
    public void shouldReturnOptionOfSuppliedValueWhenOrOptionCalledOnNone() throws Exception {
        // Given
        Option<Integer> initial = none();
        Option<Integer> expected = option(15);

        // When
        Option<Integer> actual = initial.orOption(15);

        // Then
        assertThat(actual, is(expected));
    }

    @Test
    public void shouldReturnOptionOfNullWhenOrOptionCalledOnNoneWithNull() throws Exception {
        // Given
        Option<Integer> initial = none();
        Option<Integer> expected = option(null);

        // When
        Option<Integer> actual = initial.orOption(null);

        // Then
        assertThat(actual, is(expected));
    }

    @Test(expected = Throwable.class)
    public void shouldThrowSuppliedExceptionIfGetOrThrowCalledOnNone() throws Throwable {
        // Given
        Throwable throwable = new Throwable();
        Option<String> option = none();

        // When
        option.getOrThrow(throwable);

        // Then the throwable is thrown
    }

    @Test(expected = NullPointerException.class)
    public void shouldThrowNullPointerExceptionIfGetOrThrowCalledOnNoneWithNull() throws Throwable {
        // Given
        Option<String> option = none();

        // When
        option.getOrThrow(null);

        // Then a NullPointerException is thrown
    }

    @Test
    public void shouldReturnSuppliedValueIfGetOrElseCalledOnNone() throws Exception {
        // Given
        Option<String> option = none();
        String expected = "else";

        // When
        String actual = option.getOrElse("else");

        // Then
        assertThat(actual, is(expected));
    }

    @Test(expected = NullPointerException.class)
    public void shouldThrowNullPointerExceptionIfGetOrElseCalledOnNoneWithNull() throws Exception {
        // Given
        Option<String> option = none();

        // When
        option.getOrElse(null);

        // Then a NullPointerException is thrown
    }

    @Test
    public void shouldReturnNullIfGetOrNullCalledOnNone() throws Exception {
        // Given
        Option<String> option = none();
        String expected = null;

        // When
        String actual = option.getOrNull();

        // Then
        assertThat(actual, is(expected));
    }

    // none.getOrCall(Callable<T> callable) => return callable.call();

    @Test
    public void shouldHaveValueIfSome() throws Exception {
        // Given
        Option<Integer> option = some(15);

        // When
        Boolean hasValue = option.hasValue();

        // Then
        assertThat(hasValue, is(false));
    }

    @Test
    public void shouldReturnValueForSome() throws Exception {
        // Given
        Option<Integer> option = some(1);

        // When
        Integer integer = option.get();

        // Then
        assertThat(integer, equalTo(1));
    }

    @Test
    public void shouldReturnSomeWhenOrCalledOnSome() throws Exception {
        // Given
        Option<String> initial = some("thing");
        Option<String> expected = some("thing");

        // When
        Option<String> actual = initial.or(some("other"));

        // Then
        assertThat(actual, is(expected));
    }

    @Test(expected = NullPointerException.class)
    public void shouldThrowNullPointerExceptionWhenOrCalledOnSomeIfSuppliedWithNullOption() throws Exception {
        // Given
        Option<String> initial = some("thing");

        // When
        initial.or(null);

        // Then a NullPointerException is thrown
    }

    @Test
    public void shouldReturnSelfWhenOrSomeCalledOnSome() throws Exception {
        // Given
        Option<String> initial = some("thing");
        Option<String> expected = some("thing");

        // When
        Option<String> actual = initial.orSome("other");

        // Then
        assertThat(actual, is(expected));
    }

    @Test
    public void shouldReturnSelfWhenOrSomeCalledOnSomeWithNull() throws Exception {
        // Given
        Option<String> initial = some("thing");
        Option<String> expected = some("thing");

        // When
        Option<String> actual = initial.orSome(null);

        // Then
        assertThat(actual, is(expected));
    }

    @Test
    public void shouldReturnSelfWhenOrOptionCalledOnSome() throws Exception {
        // Given
        Option<Integer> initial = some(10);
        Option<Integer> expected = some(10);

        // When
        Option<Integer> actual = initial.orOption(15);

        // Then
        assertThat(actual, is(expected));
    }

    @Test
    public void shouldReturnSelfWhenOrOptionCalledOnSomeWithNull() throws Exception {
        // Given
        Option<Integer> initial = some(10);
        Option<Integer> expected = some(10);

        // When
        Option<Integer> actual = initial.orOption(null);

        // Then
        assertThat(actual, is(expected));
    }

    @Test
    public void shouldReturnValueIfGetOrThrowCalledOnSome() throws Throwable {
        // Given
        Throwable throwable = new Throwable();
        String expected = "thing";
        Option<String> option = some(expected);

        // When
        String actual = option.getOrThrow(throwable);

        // Then
        assertThat(actual, is(expected));
    }

    @Test(expected = NullPointerException.class)
    public void shouldThrowNullPointerExceptionIfGetOrThrowCalledOnSomeWithNull() throws Throwable {
        // Given
        Option<String> option = some("thing");

        // When
        option.getOrThrow(null);

        // Then a NullPointerException is thrown
    }

    @Test
    public void shouldReturnResultOfGetIfGetOrElseCalledOnSome() throws Exception {
        // Given
        Option<String> option = some("thing");
        String expected = "thing";

        // When
        String actual = option.getOrElse("else");

        // Then
        assertThat(actual, is(expected));
    }

    @Test(expected = NullPointerException.class)
    public void shouldThrowNullPointerExceptionIfGetOrElseCalledOnSomeWithNull() throws Exception {
        // Given
        Option<String> option = some("thing");

        // When
        option.getOrElse(null);

        // Then a NullPointerException is thrown
    }

    @Test
    public void shouldReturnResultOfCallingGetIfGetOrNullCalledOnSome() throws Exception {
        // Given
        Option<String> option = some("thing");
        String expected = "thing";

        // When
        String actual = option.getOrNull();

        // Then
        assertThat(actual, is(expected));
    }

    // some.getOrCall(Callable<T> callable) => some's value;

    @Test
    public void shouldBeEqualIfBothNoneOverSameType() throws Exception {
        // Given
        Option<String> firstNone = none();
        Option<String> secondNone = none();

        // When
        Boolean firstEqualsSecond = firstNone.equals(secondNone);
        Boolean secondEqualsFirst = secondNone.equals(firstNone);

        // Then
        assertThat(listWith(firstEqualsSecond, secondEqualsFirst), hasAllElementsEqualTo(true));
    }

    @Test
    public void shouldBeEqualIfBothNoneButOverDifferentTypes() throws Exception {
        // Given
        Option<String> firstNone = none();
        Option<Integer> secondNone = none();

        // When
        Boolean firstEqualsSecond = firstNone.equals(secondNone);
        Boolean secondEqualsFirst = secondNone.equals(firstNone);

        // Then
        assertThat(listWith(firstEqualsSecond, secondEqualsFirst), hasAllElementsEqualTo(true));
    }

    @Test
    public void shouldBeEqualIfEquatingSomesWithTheSameValue() throws Exception {
        // Given
        Option<Integer> firstOption = some(12);
        Option<Integer> secondOption = some(12);

        // When
        Boolean firstEqualsSecond = firstOption.equals(secondOption);
        Boolean secondEqualsFirst = secondOption.equals(firstOption);

        // Then
        assertThat(listWith(firstEqualsSecond, secondEqualsFirst), hasAllElementsEqualTo(true));
    }

    @Test
    public void shouldBeEqualIfEquatingSomesWithNullValue() throws Exception {
        // Given
        Option<Integer> firstOption = some(null);
        Option<Integer> secondOption = some(null);

        // When
        Boolean firstEqualsSecond = firstOption.equals(secondOption);
        Boolean secondEqualsFirst = secondOption.equals(firstOption);

        // Then
        assertThat(listWith(firstEqualsSecond, secondEqualsFirst), hasAllElementsEqualTo(true));
    }

    @Test
    public void shouldNotBeEqualIfEquatingSomeAndNone() throws Exception {
        // Given
        Option<Integer> firstOption = some(12);
        Option<Integer> secondOption = none();

        // When
        Boolean firstEqualsSecond = firstOption.equals(secondOption);
        Boolean secondEqualsFirst = secondOption.equals(firstOption);

        // Then
        assertThat(listWith(firstEqualsSecond, secondEqualsFirst), hasAllElementsEqualTo(false));
    }

    @Test
    public void shouldNotBeEqualIfEquatingSomesWithDifferentValues() throws Exception {
        // Given
        Option<Integer> firstOption = some(12);
        Option<Integer> secondOption = some(15);

        // When
        Boolean firstEqualsSecond = firstOption.equals(secondOption);
        Boolean secondEqualsFirst = secondOption.equals(firstOption);

        // Then
        assertThat(listWith(firstEqualsSecond, secondEqualsFirst), hasAllElementsEqualTo(false));
    }

    @Test
    public void shouldNotBeEqualIfEquatingSomeWithNullValueToNone() throws Exception {
        // Given
        Option<Integer> firstOption = some(null);
        Option<Integer> secondOption = none();

        // When
        Boolean firstEqualsSecond = firstOption.equals(secondOption);
        Boolean secondEqualsFirst = secondOption.equals(firstOption);

        // Then
        assertThat(listWith(firstEqualsSecond, secondEqualsFirst), hasAllElementsEqualTo(false));
    }

    private Matcher<Iterable<Boolean>> hasAllElementsEqualTo(final Boolean booleanValue) {
        return trueForAll(new SelfDescribingPredicate<Boolean>(){
            @Override public String describe() {
                return "equal to " + booleanValue.toString();
            }

            @Override public boolean evaluate(Boolean item) {
                return item.equals(booleanValue);
            }
        });
    }
}
