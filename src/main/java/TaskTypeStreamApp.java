import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

public class TaskTypeStreamApp {

  public static void main(String[] args) {

    List<Task> tasks = Arrays.asList(
        new Task("1", "aliuhomhouh", TaskType.CODING, LocalDate.of(2020, 1, 1)),
        new Task("2", "aliuhomhouh", TaskType.READING, LocalDate.of(2020, 6, 23)),
        new Task("3", "cfghyndbrsdtvrf", TaskType.CODING, LocalDate.of(2020, 12, 6)),
        new Task("4", "dgfnhjgfnbtft", TaskType.READING, LocalDate.of(2020, 4, 4)),
        new Task("5", "efgthbdfbtgsdd", TaskType.WRITING, LocalDate.of(2020, 3, 16)),
        new Task("6", "fghjgfnjfbfhb", TaskType.READING, LocalDate.of(2020, 7, 2)),
        new Task("7", "glkjfdgldk jg", TaskType.READING, LocalDate.of(2020, 2, 8)),
        new Task("8", "hlskdjfh", TaskType.READING, LocalDate.of(2020, 8, 19)),
        new Task("9", "idybdtdtdv", TaskType.WRITING, LocalDate.of(2020, 10, 14)),
        new Task("10", "aliuhomhouh", TaskType.READING, LocalDate.of(2020, 5, 9))
    );

    tasks.get(1).addTag("#books");
    tasks.get(3).addTag("#books");
    tasks.get(5).addTag("#books");
    tasks.get(6).addTag("#books");
    tasks.get(7).addTag("#books");
    tasks.get(9).addTag("#books");

    System.out.println("Reading:");
    tasks.stream()
        .filter(t -> t.getType() == TaskType.READING)
        .sorted(Comparator.comparing(Task::getCreatedOn))
        .forEach(System.out::println);

    System.out.println("Unique:");
    tasks.stream()
        .distinct()
        .forEach(System.out::println);

    System.out.println("First 5 reading:");
    tasks.stream()
        .filter(t -> t.getType() == TaskType.READING)
        .sorted(Comparator.comparing(Task::getCreatedOn))
        .limit(5)
        .forEach(System.out::println);

    System.out.println("Number of CODING tasks:");
    System.out.println(
        tasks.stream()
            .filter(t -> t.getType() == TaskType.CODING)
            .count()
    );

    System.out.println("All READING tasks have #books tag:");
    System.out.println(
        tasks.stream().filter(t -> t.getType() == TaskType.READING).count()
            == tasks.stream().filter(t -> t.getType() == TaskType.READING && t.getTags().contains("#books")).count()
    );

    System.out.println("All task titles:");
    System.out.println(
        tasks.stream()
            .map(Task::getTitle)
            .collect(Collectors.joining("***"))
    );

  }

}
