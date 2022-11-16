import java.nio.file.Path;

public class Ware extends Doc{

    private final String wareId;

    private final String wareName;

    private final Path path;
    public String getWareId() {
        return wareId;
    }

    public String getWareName() {
        return wareName;
    }

    public Path getPath() {
        return path;
    }

    public Ware(String wareId, String wareName, Path path) {
        this.wareId = wareId;
        this.wareName = wareName;
        this.path = path;
    }
}
