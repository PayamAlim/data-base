package db;

import db.exception.EntityNotFoundException;
import java.util.ArrayList;

public class Database {
    private static ArrayList<Entity> entities = new ArrayList<>();
    private static int lastId = 1;

    private Database() {};
    public static void add(Entity e) {
        e.id = lastId;
        lastId ++;
        entities.add(e);
    }
    public static Entity get(int id) {
        for (Entity e: entities)
            if (e.id == id)
                return e;
        throw new EntityNotFoundException(id);
    }
    public static void delete(int id) {
        boolean contain = false;
        for (int i = 0; i < entities.size(); i ++)
            if (entities.get(i).id == id) {
                entities.remove(i);
                contain = true;
            }
        if (!contain)
            throw new EntityNotFoundException(id);
    }
    public static void update(Entity e) {
        boolean contain = false;
        for (int i = 0; i < entities.size(); i ++)
            if (entities.get(i).id == e.id) {
                entities.remove(i);
                entities.add(e);
                contain = true;
            }
        if (!contain)
            throw new EntityNotFoundException(e.id);
    }
}