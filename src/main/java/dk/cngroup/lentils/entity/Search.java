package dk.cngroup.lentils.entity;

public class Search {
    private String searchString;
    private Long searchCypherId;

    public Search(final String searchString, final Long searchCypherId) {
        this.searchString = searchString;
        this.searchCypherId = searchCypherId;
    }

    public Search(Long searchCypherId) {
        this.searchCypherId = searchCypherId;
    }

    public String getSearchString() {
        return searchString;
    }

    public void setSearchString(final String searchString) {
        this.searchString = searchString;
    }

    public Long getSearchCypherId() {
        return searchCypherId;
    }

    public void setSearchCypherId(final Long searchCypherId) {
        this.searchCypherId = searchCypherId;
    }

    public Search() {
    }
}
