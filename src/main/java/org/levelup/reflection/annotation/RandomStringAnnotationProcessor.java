package org.levelup.reflection.annotation;

import java.lang.reflect.Field;
import java.util.Random;

public class RandomStringAnnotationProcessor {

  public static Object process(Object object) throws IllegalAccessException {

    Class<?> objClass = object.getClass();
    Field[] fields = objClass.getDeclaredFields();
    for (Field field : fields) {
      RandomString annotation = field.getAnnotation(RandomString.class);
      if (annotation != null) {
        int maxLength = annotation.maxLength();
        Random random = new Random();
        int number = random.nextInt(Brands.values().length);

        String generatedBrand = Brands.values()[number].name().toLowerCase();
        String brand = generatedBrand.substring(0, Math.min(generatedBrand.length(), maxLength));

        field.setAccessible(true);
        field.set(object, brand);
      }
    }
    return object;

  }

}
