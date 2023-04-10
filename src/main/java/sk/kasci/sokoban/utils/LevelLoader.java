package sk.kasci.sokoban.utils;

import sk.kasci.sokoban.objects.Map;
import sk.kasci.sokoban.objects.MapObject;
import sk.kasci.sokoban.objects.mapActors.Player;
import sk.kasci.sokoban.objects.mapActors.Box;
import sk.kasci.sokoban.objects.mapObjects.Empty;

import java.io.*;
import java.net.URL;
import java.util.*;

public class LevelLoader {

    private List<ArrayList<String>> rawLevels;

    public LevelLoader() {
        this.rawLevels = new ArrayList<>();
    }

    public LinkedList<Map> loadLevels(String path) {
        URL url = getClass().getClassLoader().getResource(path);
        if (url == null) throw new RuntimeException("There is no such file");
        try (FileReader fr = new FileReader(url.getPath()); BufferedReader reader = new BufferedReader(fr)) {
            String line = reader.readLine();
            ArrayList<String> level = new ArrayList<>();
            while (line != null) {
                if (line.startsWith(";")) {
                    if (level.size() > 0) {
                        this.rawLevels.add((ArrayList<String>) level.clone());
                    }
                    level.clear();
                } else {
                    if (!line.isEmpty()) level.add(line);
                }
                line = reader.readLine();
            }
            this.rawLevels.add(level);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("There was an error reading a file");
        }

        LinkedList<Map> levels = new LinkedList<>();
        for (ArrayList<String> l: this.rawLevels) {
            levels.add(parseLevel(l));
        }

        return levels;
    }

    private Map parseLevel(ArrayList<String> raw) {
        Optional<String> maxSize = raw.stream().max(Comparator.comparingInt(String::length));
        if (!maxSize.isPresent()) throw new RuntimeException("Unknown max value");
        MapFactory factory = new MapFactory();
        Map map = new Map();

        map.map = new MapObject[maxSize.get().length()][raw.size()];
        map.sizeX = maxSize.get().length();
        map.sizeY = raw.size();
        for (int i = 0; i < raw.size(); i++) {
            for (int j = 0; j < maxSize.get().length(); j++) {
                if (j >= raw.get(i).length()) {
                    map.map[j][i] = new Empty();
                } else {
                    char c = raw.get(i).charAt(j);
                    if (c == '@') {
                        map.player = new Player(j,i);
                    }
                    if (c == '$') {
                        map.boxes.add(new Box(j,i));
                    }
                    map.map[j][i] = factory.get(c);
                }
            }
        }
        return map;
    }
}
