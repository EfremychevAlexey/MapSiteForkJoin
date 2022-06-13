import java.io.IOException;
import java.util.Set;
import java.util.TreeSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Link {

  public final String reference;
  private String parentValue = "";
  private String value = null;
  private Set<String> refToChilds = new TreeSet<>();
  private boolean isExist;

  public Link(String reference) throws IOException {
    this.reference = reference;
    this.parentValue = getParentValue(reference);
    this.isExist = linkInit(reference);
  }

  public String getValue() {
    return value;
  }

  public Set<String> getRefToChilds() {
    return refToChilds;
  }

  public boolean getIsExist() {
    return isExist;
  }

  public boolean linkInit(String reference) {
    try {
      Document document = Jsoup.connect(reference).maxBodySize(0).get();
      this.value = reference;
      setRefToChilds(document);
      return true;
    } catch (IOException e) {
      return false;
    }
  }

  private void setRefToChilds(Document document) {
    Elements elements = document.select("a");

    if (elements.size() > 0) {
      for (Element e : elements) {
        String text = e.absUrl("href");
        Pattern pattern = Pattern.compile("^" + parentValue + "[^#\\?\\.]+/$");
        Matcher matcher = pattern.matcher(text);

        if (matcher.find()) {
          String newReference = text.substring(matcher.start(), matcher.end());
          refToChilds.add(newReference);
        }
      }
    }
  }

  private String getParentValue(String value) {
    Pattern pattern = Pattern.compile("^https?:\\/\\/\\D+\\.[a-z]+");
    Matcher matcher = pattern.matcher(value);
    if (matcher.find()) {
      return value.substring(matcher.start(), matcher.end());
    } else {
      return "";
    }
  }

    public int compareTo (Link toLink){
      return this.getValue().compareTo(toLink.getValue());
    }
  }
