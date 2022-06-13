import java.util.Collections;
import java.util.Set;
import java.util.TreeSet;

public class AllReference {

  public Set<String> getAllReference() {
    return allReference;
  }

  public void setAllReference(String reference) {
    allReference.add(reference);
  }

  private Set<String> allReference = new TreeSet<>();

  public AllReference() {
    Collections.synchronizedSet(allReference);
  }
}
