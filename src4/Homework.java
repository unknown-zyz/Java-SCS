import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static java.lang.Math.min;

public class Homework {
    private final Path path;
    private double highestScore;

    private double lastScore;
    public Path getPath() {
        return path;
    }

    public double getHighestScore() {
        return highestScore;
    }

    public double getLastScore() {
        return lastScore;
    }

    public Homework(Path path) {
        this.path = path;
        this.highestScore = -1;
        this.lastScore = -1;
    }

    public void judge(Path answer) throws IOException {
        String[] ans1, ans2;
        ans1 = (new String(Files.readAllBytes(answer))).trim().split("\n");
        ans2 = (new String(Files.readAllBytes(path))).trim().split("\n");
        int lines = min(ans1.length, ans2.length);
        int num = ans1.length, cnt = 0;
        for (int i = 0; i < lines; ++ i)
            if (ans1[i].toLowerCase().equals(ans2[i].toLowerCase())) ++ cnt;
        lastScore = (double)cnt * 100 / num;
        if (lastScore > highestScore) highestScore = lastScore;
    }
}
