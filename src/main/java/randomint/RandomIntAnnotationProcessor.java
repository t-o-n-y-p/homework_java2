package randomint;

import java.lang.reflect.Field;
import java.util.Random;

public class RandomIntAnnotationProcessor {

  public static Object process(Object object) throws IllegalAccessException {
    for (Field field : object.getClass().getDeclaredFields()) {
      RandomInt annotation = field.getAnnotation(RandomInt.class);
      if (annotation != null) {
        if (isNotInteger(field)) {
          continue;
        }
        int min = annotation.min();
        int max = annotation.max();
        if (max < min) {
          throw new IllegalArgumentException("max() value for RandomInt annotation cannot be less than min() value");
        }
        field.setAccessible(true);
        field.set(object, new Random().nextInt(max - min + 1) + min);
      }
    }
    return object;
  }

  private static boolean isInteger(Field field) {
    return field.getType() == int.class || field.getType() == Integer.class;
  }

  private static boolean isNotInteger(Field field) {
    return !isInteger(field);
  }

}
