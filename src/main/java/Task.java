import lombok.*;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@RequiredArgsConstructor
@EqualsAndHashCode(of = {"title", "type"})
@Setter
@Getter
@ToString
public class Task {

  private final String id;
  private final String title;
  private final TaskType type;
  private final LocalDate createdOn;
  private boolean done = false;
  private Set<String> tags = new HashSet<>();
  private LocalDate dueOn;

  public void addTag(String tag) {
    tags.add(tag);
  }

  public void removeTag(String tag) {
    tags.remove(tag);
  }

}
