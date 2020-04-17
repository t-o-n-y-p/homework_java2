package org.levelup.streams;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

class CollectionUtilsTest {

  @Test
  public void testRemoveLongStrings_whenCollectionIsEmpty_returnEmptyCollectionCopy() {
    Collection<String> emptyCollection = new ArrayList<>();
    Collection<String> result = CollectionUtils.removeLongStrings(emptyCollection, 10);
    Assertions.assertTrue(result.isEmpty());
    Assertions.assertNotSame(emptyCollection, result);
  }

  @Test
  public void testRemoveLongStrings_whenCollectionIsNull_returnEmptyCollectionCopy() {
    Assertions.assertThrows(
        NullPointerException.class, () -> CollectionUtils.removeLongStrings(null, 10)
    );

  }

  @Test
  public void testRemoveLongStrings_whenCollectionIsValid_returnFilteredCollection() {
    Collection<String> originalCollection = new ArrayList<>(Arrays.asList("String1", "Long string"));
    Collection<String> result = CollectionUtils.removeLongStrings(originalCollection, 10);
    // Assertions.assertNotEquals(originalCollection.size(), result.size());
    Assertions.assertEquals(2, originalCollection.size());
    Assertions.assertEquals(1, result.size());

    boolean isAllStringLengthsLessThan10 = result.stream().allMatch(s -> s.length() <= 10);
    Assertions.assertTrue(isAllStringLengthsLessThan10);
  }

}