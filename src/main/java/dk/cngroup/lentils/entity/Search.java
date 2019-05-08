package dk.cngroup.lentils.entity;

public class Search {
    private String searchString;
    private Long searchCypherId;

    public Search(String searchString, Long searchCypherId) {
        this.searchString = searchString;
        this.searchCypherId = searchCypherId;
    }

    public String getSearchString() {
        return searchString;
    }

    public void setSearchString(String searchString) {
        this.searchString = searchString;
    }

    public Long getSearchCypherId() {
        return searchCypherId;
    }

    public void setSearchCypherId(Long searchCypherId) {
        this.searchCypherId = searchCypherId;
    }

    public Search() {
    }
}
