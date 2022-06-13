import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.RecursiveTask;

public class ChildLinkCollector extends RecursiveTask<Set<String>> {

  Link link;
  static AllReference allReference = new AllReference();

  public ChildLinkCollector(Link link) {
    this.link = link;
  }

  @Override
  protected Set<String> compute() {
    Set<String> set = new TreeSet<>();
    Collections.synchronizedSet(set);
    try {
      Thread.sleep(130);

      if (link.getIsExist()) {
        set.add(link.getValue());
        Set<String> refToChilds = link.getRefToChilds();
        List<ChildLinkCollector> taskList = new ArrayList<>();

        if (refToChilds.size() > 0) {
          for (String reference : refToChilds) {

            if (allReference.getAllReference().contains(reference)) {
              continue;
            }
            //TODO строка проверки
            System.out.println(reference);

            allReference.setAllReference(reference);
            ChildLinkCollector task = new ChildLinkCollector(new Link(reference));
            task.fork();
            taskList.add(task);
          }

          for (ChildLinkCollector task : taskList) {
            set.addAll(task.join());
          }
        }
      }
    } catch (InterruptedException | IOException e) {
    }
    return set;
  }
}


