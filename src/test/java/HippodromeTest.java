import lombok.SneakyThrows;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mockStatic;

class HippodromeTest {
    @Test
    @DisplayName("Проверка выброса Exception - передан Null")
    public void testConstructShouldThrowExWhenListIsNull() {
        Assertions.assertThrows(IllegalArgumentException.class, () ->
                new Hippodrome(null));
    }

    @Test
    @DisplayName("Проверка сообщения в Exception - передан Null")
    public void testConstructShouldMessageExWhenListIsNull() {
        String expectedResult = "Horses cannot be null.";

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                new Hippodrome(null));

        String actualResult = exception.getMessage();

        assertEquals(expectedResult, actualResult);
    }

    @Test
    @DisplayName("Проверка выброса Exception - передан пустой список")
    public void testConstructShouldThrowExWhenListIsEmpty() {
        Assertions.assertThrows(IllegalArgumentException.class, () ->
                new Hippodrome(new ArrayList<>()));
    }

    @Test
    @DisplayName("Проверка сообщения в Exception - передан пустой список")
    public void testConstructShouldMessageExWhenListIsEmpty() {
        String expectedResult = "Horses cannot be empty.";

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                new Hippodrome(new ArrayList<>()));

        String actualResult = exception.getMessage();

        assertEquals(expectedResult, actualResult);
    }

    @SneakyThrows
    @Test
    @DisplayName("Проверка метода getHorses на возврат списка")
    public void testGetHorsesValidOutString() {
        List<Horse> expectedResult = new ArrayList<>();
        for (int i = 0; i < 30; i++) {
            expectedResult.add(new Horse("Пони" + i, 2.4 + i, 3.5 + i));
        }

        Hippodrome hippodrome = new Hippodrome(expectedResult);

        List<Horse> actualResult = hippodrome.getHorses();

        assertEquals(expectedResult, actualResult);
    }

    @SneakyThrows
    @Test
    @DisplayName("Проверка метода getHorses на возврат списка")
    public void testGetHorsesValidOutStringReflection() {
        List<Horse> expectedResult = new ArrayList<>();
        for (int i = 0; i < 30; i++) {
            expectedResult.add(new Horse("Пони" + i, 2.4 + i, 3.5 + i));
        }

        Hippodrome hippodrome = new Hippodrome(expectedResult);

        Field field = hippodrome.getClass().getDeclaredField("horses");

        field.setAccessible(true);

        List<Horse> actualResult = (List<Horse>) field.get(hippodrome);

        assertEquals(expectedResult, actualResult);
    }

    @Test
    void testMoveInputHorses() {
//        List<Horse> horses = new ArrayList<>();
//        for (int i = 0; i < 50; i++) {
//            Horse horse = Mockito.mock(Horse.class);
//            horses.add(horse);
//        }

        List<Horse> horses = IntStream.range(0, 50).mapToObj(i -> Mockito.mock(Horse.class))
                .collect(Collectors.toList());

        Hippodrome hippodrome = new Hippodrome(horses);

        hippodrome.move();

        horses.forEach(horse -> Mockito.verify(horse, Mockito.times(1)).move());
    }

    @Test
    void testGetWinnerOutMaxDistance() {

        Horse horse1 = new Horse("nameOne", 1, 10);
        Horse horse2 = new Horse("nameTwo", 2, 20);
        Horse expectedResult = new Horse("nameFree", 3, 30);

        List<Horse> horses = List.of(horse1, horse2, expectedResult);

        Hippodrome hippodrome = new Hippodrome(horses);

        Horse actualResult = hippodrome.getWinner();

        assertSame(expectedResult, actualResult);
    }
}