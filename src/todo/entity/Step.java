package todo.entity;

import db.Entity;

public class Step extends Entity {
    public static final int STEP_ENTITY_CODE = 60;

    public enum Status {
        NotStarted,
        Completed;
    }

    public String title;
    public int taskRef;
    public Status status;

    @Override
    public Entity copy() {
        return null;
    }

    @Override
    public int getEntityCode() {
        return STEP_ENTITY_CODE;
    }
}
