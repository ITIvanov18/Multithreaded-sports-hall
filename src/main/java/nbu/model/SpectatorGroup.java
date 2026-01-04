package nbu.model;

public class SpectatorGroup {

    private final int id;
    private final String category;
    private final int peopleCount;

    public SpectatorGroup(int id, String category, int peopleCount) {
        this.id = id;
        this.category = category;
        this.peopleCount = peopleCount;
    }

    public String getCategory() {
        return category;
    }

    public int getPeopleCount() {
        return peopleCount;
    }

    @Override
    public String toString() {
        return String.format("Група #%d (%s, %d души)", id, category, peopleCount);
    }
}