package org.levelup.streams;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import static org.junit.jupiter.api.Assertions.*;

class CollectionUtilsTest {

  @Test
  public void testRemoveLongStrings_whenCollectionIsEmpty_returnEmptyCollectionCopy() {
    Collection<String> originalCollection = new ArrayList<>();

    Collection<String> actualResult = CollectionUtils.removeLongStrings(originalCollection, 10);
    assertTrue(originalCollection.isEmpty());
    assertTrue(actualResult.isEmpty());
    assertNotSame(originalCollection, actualResult);
  }

  @Test
  public void testRemoveLongStrings_whenCollectionIsNull_throwNullPointerException() {
    assertThrows(
        NullPointerException.class, () -> CollectionUtils.removeLongStrings(null, 10)
    );
  }

  @Test
  public void testRemoveLongStrings_whenCollectionContainsNull_throwNullPointerException() {
    Collection<String> originalCollection = new ArrayList<>(Arrays.asList(null, "Long string"));
    assertThrows(
        NullPointerException.class, () -> CollectionUtils.removeLongStrings(originalCollection, 10)
    );
  }

  @Test
  public void testRemoveLongStrings_whenNegativeMaxLength_returnEmptyCollection() {
    Collection<String> originalCollection = new ArrayList<>(Arrays.asList("String1", "Long string"));
    Collection<String> originalCollectionCopy = new ArrayList<>(originalCollection);

    Collection<String> actualResult = CollectionUtils.removeLongStrings(originalCollection, -10);
    assertEquals(originalCollectionCopy, originalCollection);
    assertTrue(actualResult.isEmpty());
  }

  @Test
  public void testRemoveLongStrings_whenZeroMaxLength_returnFilteredCollection() {
    Collection<String> originalCollection = new ArrayList<>(Arrays.asList("", "1", "12345678"));
    Collection<String> originalCollectionCopy = new ArrayList<>(originalCollection);
    Collection<String> expectedResult = new ArrayList<>(Arrays.asList(""));

    Collection<String> actualResult = CollectionUtils.removeLongStrings(originalCollection, 0);
    assertEquals(originalCollectionCopy, originalCollection);
    assertEquals(expectedResult, actualResult);
  }

  @Test
  public void testRemoveLongStrings_whenMaxLengthEqualToOne_returnFilteredCollection() {
    Collection<String> originalCollection = new ArrayList<>(Arrays.asList("", "1", "12", "12345678"));
    Collection<String> originalCollectionCopy = new ArrayList<>(originalCollection);
    Collection<String> expectedResult = new ArrayList<>(Arrays.asList("", "1"));

    Collection<String> actualResult = CollectionUtils.removeLongStrings(originalCollection, 1);
    assertEquals(originalCollectionCopy, originalCollection);
    assertEquals(expectedResult, actualResult);
  }

  @Test
  public void testRemoveLongStrings_whenMaxLengthMoreThanOne_returnFilteredCollection() {
    Collection<String> originalCollection = new ArrayList<>(
        Arrays.asList("12", "123456789", "1234567890", "12345678910", "123456789101112131415")
    );
    Collection<String> originalCollectionCopy = new ArrayList<>(originalCollection);
    Collection<String> expectedResult = new ArrayList<>(Arrays.asList("12", "123456789", "1234567890"));

    Collection<String> actualResult = CollectionUtils.removeLongStrings(originalCollection, 10);
    assertEquals(originalCollectionCopy, originalCollection);
    assertEquals(expectedResult, actualResult);
  }

}