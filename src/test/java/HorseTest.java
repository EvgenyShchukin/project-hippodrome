import lombok.SneakyThrows;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.MockedStatic;

import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mockStatic;

class HorseTest {
    private String name = "Zephyr";
    private double speed = 2.6;
    private double distance = 10;

    @Test
    @DisplayName("Проверка выброса Exception по первому параметру - передан Null")
    public void testConstructShouldThrowExWhenArgsNameIsNull() {
        assertThrows(IllegalArgumentException.class, () ->
                new Horse(null, speed, distance));
    }

    @Test
    @DisplayName("Проверка сообщения в Exception по первому параметру - передан Null")
    public void testConstructShouldMessageExWhenArgsNameIsNull() {
        String expectedResult = "Name cannot be null.";

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                new Horse(null, speed));

        String actualResult = exception.getMessage();

        assertEquals(expectedResult, actualResult);
//        try {
//            new Horse(null, speed, distance);
//        } catch (IllegalArgumentException e) {
//            actualResult = e.getMessage();
//            Assertions.assertEquals(expectedResult, actualResult);
//            return;
//        }
//        assert false : "Исключение не выброшено";
    }

    @ParameterizedTest
    @ValueSource(strings = {"", " ", "\t", "\n"})
    @DisplayName("Проверка выброса Exception по первому параметру - пустая строка, запрещенные символы")
    public void testConstructShouldThrowExWhenArgsNameIsBlank(String firstParameter) {
        assertThrows(IllegalArgumentException.class, () ->
                new Horse(firstParameter, speed, distance));
    }

    @ParameterizedTest
    @ValueSource(strings = {"", " ", "\t", "\n"})
    @DisplayName("Проверка сообщения в Exception по первому параметру - пустая строка, запрещенные символы")
    public void testConstructShouldMessageExWhenArgsNameIsBlank(String firstParameter) {
        String expectedResult = "Name cannot be blank.";

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                new Horse(firstParameter, speed, distance));

        String actualResult = exception.getMessage();

        assertEquals(expectedResult, actualResult);

//        String actualResult = "";
//        try {
//            new Horse(firstParameter, 10, 15);
//        } catch (IllegalArgumentException e) {
//            actualResult = e.getMessage();
//            assertEquals(expectedResult, actualResult);
//            return;
//        }
//        assert false : "Исключение не выброшено";
    }

    @Test
    @DisplayName("Проверка выброса Exception по второму параметру - отрицательное число")
    public void testConstructShouldThrowExWhenArgsSpeedNegative() {
        assertThrows(IllegalArgumentException.class, () ->
                new Horse(name, -speed, distance));
    }

    @Test
    @DisplayName("Проверка сообщения в Exception по второму параметру - отрицательное число")
    public void testConstructShouldMessageExWhenArgsSpeedNegative() {
        String expectedResult = "Speed cannot be negative.";

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                new Horse(name, -speed, distance));

        String actualResult = exception.getMessage();

        assertEquals(expectedResult, actualResult);

//        String actualResult = "";
//        try {
//            new Horse("MyName", -15, 15);
//        } catch (IllegalArgumentException e) {
//            actualResult = e.getMessage();
//            assertEquals(expectedResult, actualResult);
//            return;
//        }
//        assert false : "Исключение не выброшено";
    }

    @Test
    @DisplayName("Проверка выброса Exception по третьему параметру - отрицательное число")
    public void testConstructShouldThrowExWhenArgsDistanceNegative() {
        assertThrows(IllegalArgumentException.class, () ->
                new Horse(name, speed, -distance));
    }

    @Test
    @DisplayName("Проверка сообщения в Exception по третьему параметру - отрицательное число")
    public void testConstructShouldMessageExWhenArgsDistanceNegative() {
        String expectedResult = "Distance cannot be negative.";

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                new Horse(name, speed, -distance));

        String actualResult = exception.getMessage();

        assertEquals(expectedResult, actualResult);

//        String actualResult = "";
//        try {
//            new Horse("MyName", 15, -35);
//        } catch (IllegalArgumentException e) {
//            actualResult = e.getMessage();
//            assertEquals(expectedResult, actualResult);
//            return;
//        }
//        assert false : "Исключение не выброшено";
    }

    @Test
    @DisplayName("Проверка метода getName на возврат заданной строки")
    public void testGetNameValidOutString() {
        String expectedResult = new String("Zephyr");
        Horse horse = new Horse(name, speed, distance);
        String actualResult = horse.getName();

        assertEquals(expectedResult, actualResult);
    }

    @Test
    @DisplayName("Проверка метода getSpeed на возврат заданного числа")
    public void testGetSpeedValidOutString() {
        double expectedResult = 2.6;
        Horse horse = new Horse(name, speed, distance);
        double actualResult = horse.getSpeed();

        assertEquals(expectedResult, actualResult);
    }

    @Test
    @DisplayName("Проверка метода getDistance на возврат заданного числа")
    public void testGetDistanceValidOutString() {
        double expectedResult = 10;
        Horse horse = new Horse(name, speed, distance);
        double actualResult = horse.getDistance();

        assertEquals(expectedResult, actualResult);
    }

    @Test
    @DisplayName("Проверка метода getDistance на возврат числа по умолчанию 0")
    public void testGetDistanceValidOutDefault() {
        double expectedResult = 0;
        Horse horse = new Horse(name, speed);
        double actualResult = horse.getDistance();

        assertEquals(expectedResult, actualResult);
    }

    @SneakyThrows
    @Test
    @DisplayName("Проверка метода getName на возврат заданной строки")
    public void testGetNameValidOutStringReflection() {
        String expectedResult = new String("Zephyr");

        Horse horse = new Horse(name, speed, distance);

        Field field = horse.getClass().getDeclaredField("name");

        field.setAccessible(true);
        String actualResult = (String) field.get(horse);

        assertEquals(expectedResult, actualResult);
    }

    @SneakyThrows
    @Test
    @DisplayName("Проверка метода getSpeed на возврат заданной строки")
    public void testGetSpeedValidOutStringReflection() {
        double expectedResult = speed;

        Horse horse = new Horse(name, speed, distance);

        Field field = horse.getClass().getDeclaredField("speed");

        field.setAccessible(true);
        double actualResult = (double) field.get(horse);

        assertEquals(expectedResult, actualResult);
    }

    @SneakyThrows
    @Test
    @DisplayName("Проверка метода getDistance на возврат заданной строки")
    public void testGetDistanceValidOutStringReflection() {
        double expectedResult = distance;

        Horse horse = new Horse(name, speed, distance);

        Field field = horse.getClass().getDeclaredField("distance");

        field.setAccessible(true);
        double actualResult = (double) field.get(horse);

        assertEquals(expectedResult, actualResult);
    }

    @Test
    void testMoveCallsGetRandomDoubleWithCorrectParameters() {
        try (MockedStatic<Horse> mockedStatic = mockStatic(Horse.class)) {

            Horse horse = new Horse(name, speed, distance);

            horse.move();

            mockedStatic.verify(() -> Horse.getRandomDouble(0.2, 0.9));
        }
    }

    @ParameterizedTest
    @ValueSource(doubles = {0, 0.2, 0, 5, 1, 10, 100})
    public void testMoveCallsDistanceCorrectOutParameter(double fakeDouble) {
        Horse horse = new Horse(name, speed, distance);

        double expectedDistance = horse.getDistance() + horse.getSpeed() * fakeDouble;

        try (MockedStatic<Horse> mockedStatic = mockStatic(Horse.class)) {
            mockedStatic.when(() -> Horse.getRandomDouble(0.2, 0.9)).thenReturn(fakeDouble);

            horse.move();

            double actualDistance = horse.getDistance();

            assertEquals(expectedDistance, actualDistance);
        }

    }
}
