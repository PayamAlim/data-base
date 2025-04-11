package todo.entity;

import db.Entity;
import db.Trackable;

import java.util.Date;

public class Step extends Entity implements Trackable {
    public static final int STEP_ENTITY_CODE = 60;

    public enum Status {
        NotStarted,
        Completed;
    }

    public String title;

    public int taskRef;

    public Status status;

    private Date creationDate, lastModificationDate;

    public Step(int taskRef, String title) {
        this.taskRef = taskRef;
        this.title = title;
    }

    @Override
    public Step copy() {
        Step copyStep = new Step(taskRef, title);
        copyStep.id = id;
        copyStep.status = status;
        copyStep.creationDate = new Date(creationDate.getTime());
        copyStep.lastModificationDate = new Date(lastModificationDate.getTime());

        return copyStep;
    }

    @Override
    public int getEntityCode() {
        return STEP_ENTITY_CODE;
    }

    @Override
    public void setCreationDate(Date date) {
        creationDate = date;
    }

    @Override
    public Date getCreationDate() {
        return creationDate;
    }

    @Override
    public void setLastModificationDate(Date date) {
        lastModificationDate = date;
    }

    @Override
    public Date getLastModificationDate() {
        return lastModificationDate;
    }
}
