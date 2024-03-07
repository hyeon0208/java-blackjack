package domain;

public enum Result {
    WIN("승"),
    LOSE("패"),
    TIE("무");

    private final String name;

    Result(final String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
