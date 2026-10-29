package app.service;

import app.model.Item;
import java.io.*;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;

public class ItemService {
    private ArrayList<Item> items = new ArrayList<>();

    public void add(String name) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Name required");
        }

        items.add(new Item(name));
    }

    public int count() {
        return items.size();
    }

    public ArrayList<Item> all() {
        return new ArrayList<>(items);
    }

    public void save(Path file) throws IOException {
        try (ObjectOutputStream out = new ObjectOutputStream(
                Files.newOutputStream(file))) {
            out.writeObject(items);
        }
    }

    @SuppressWarnings("unchecked")
    public void load(Path file)
            throws IOException, ClassNotFoundException {
        try (ObjectInputStream in = new ObjectInputStream(
                Files.newInputStream(file))) {

            Object data = in.readObject();

            if (!(data instanceof ArrayList<?>)) {
                throw new IOException("Invalid data file");
            }

            ArrayList<?> loaded = (ArrayList<?>) data;

            for (Object obj : loaded) {
                if (!(obj instanceof Item)) {
                    throw new IOException("Invalid item data");
                }
            }

            items = new ArrayList<>((List<Item>) loaded);
        }
    }
}