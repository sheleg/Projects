/**
 * Created by vladasheleg on 19.02.17.
 */
public class SearchInfo {
    public String path;
    public String template;
    public String lineForSearch;

    public boolean isSubdirectory = false;
    public boolean isTemplate = false;
    public boolean isLineForSearch = false;

    public SearchInfo(String path, String template, String lineForSearch, boolean isSubdirectory, boolean isTemplate, boolean isLineForSearch) {
        this.path = path;
        this.template = template;
        this.lineForSearch = lineForSearch;
        this.isSubdirectory = isSubdirectory;
        this.isTemplate = isTemplate;
        this.isLineForSearch = isLineForSearch;
    }
}
