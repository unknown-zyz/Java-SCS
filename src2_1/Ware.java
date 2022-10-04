public class Ware {

    private final String wareId;

    private final String wareName;

    public String getWareId() {
        return wareId;
    }

    public String getWareName() {
        return wareName;
    }

    public Ware(String wareId,String wareName) {
        this.wareId = wareId;
        this.wareName = wareName;
    }
}
