import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.ForkJoinPool;
import org.apache.commons.lang3.StringUtils;

public class Main {

  static String path = "src/main/resources/SiteMap.txt";


  public static void main(String[] args) throws IOException {

    Link link = new Link("https://skillbox.ru");
    Set set = new ForkJoinPool().invoke(new ChildLinkCollector(link));
    writeToFile(path, set);
  }

  public static void writeToFile(String path, Set<String> set) throws IOException {
    Set<String> newSet = new TreeSet<>((o1, o2) -> o2.compareTo(o1));
    for (String ref : set) {
      StringBuilder sb = new StringBuilder();
      int occurrence = StringUtils.countMatches(ref, "/");
      for (int i = 3; i < occurrence; i++) {
        sb.append("\t");
      }
      sb.append(ref);
      newSet.add(sb.toString());
    }
    Files.write(Path.of(path), newSet);
  }
}