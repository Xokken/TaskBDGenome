import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class MyReaderGenome {
    private final String filePath;

    public MyReaderGenome(String filePath) {
        this.filePath = filePath;
    }

    public List<String> take(int count) {
        List<String> list = new LinkedList<>();
        Queue<Character> queue = new LinkedList<>();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(filePath)))) {
            String line;
            while((line = reader.readLine()) != null) {
                for (int i = 0; i < line.length(); i++) {
                    if (queue.size() == count) {
                        list.add(buildPortion(queue));
                        queue.poll();
                    }
                    queue.add(line.charAt(i));
                }
            }
        } catch (IOException e) {
            throw new IllegalArgumentException(e);
        }
        return list;
    }

    private String buildPortion(Queue<Character> characters) {
        StringBuilder builder = new StringBuilder();
        for (Character character: characters) {
            builder.append(character);
        }
        return builder.toString();
    }
}
