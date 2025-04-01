package db;

import db.exception.EntityNotFoundException;
import db.exception.InvalidEntityException;

import java.util.ArrayList;
import java.util.HashMap;

public class Database {
    private static HashMap<Integer, Validator> validators = new HashMap<>();

    private static ArrayList<Entity> entities = new ArrayList<>();

    private static int lastId = 1;

    private Database() {};

    public static void registerValidator(int entityCode, Validator validator) {
        if (validators.containsKey(entityCode))
            throw new IllegalArgumentException("The validator with entity code: " + entityCode + " already exists");
        validators.put(entityCode, validator);
    }

    public static void add(Entity e) throws InvalidEntityException {
        Validator validator = validators.get(e.getEntityCode());
        validator.validate(e);
        e.id = lastId;
        lastId ++;
        entities.add(e.copy());
    }

    public static Entity get(int id) {
        for (Entity e: entities)
            if (e.id == id)
                return e.copy();
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

    public static void update(Entity e) throws InvalidEntityException {
        Validator validator = validators.get(e.getEntityCode());
        validator.validate(e);
        boolean contain = false;
        for (int i = 0; i < entities.size(); i ++)
            if (entities.get(i).id == e.id) {
                entities.remove(i);
                entities.add(e.copy());
                contain = true;
            }
        if (!contain)
            throw new EntityNotFoundException(e.id);
    }
}