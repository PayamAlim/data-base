package todo.entity;

import db.Entity;
import db.Trackable;

import java.util.Date;

public class Task extends Entity implements Trackable {
    public static final int TASK_ENTITY_CODE = 20;

    public enum Status {
        NotStarted,
        InProgress,
        Completed;
    }

    public String title, description;

    public Date dueDate;
    private Date creationDate, lastModificationDate;

    public Status status;

    public Task(String title, String description, Date dueDate) {
        this.title = title;
        this.description = description;
        this.dueDate = new Date(dueDate.getTime());
    }

    @Override
    public Task copy() {
        Task copyTask = new Task(title, description, dueDate);
        copyTask.id = id;
        copyTask.status = status;
        copyTask.creationDate = new Date(creationDate.getTime());
        copyTask.lastModificationDate = new Date(lastModificationDate.getTime());

        return copyTask;
    }

    @Override
    public int getEntityCode() {
        return TASK_ENTITY_CODE;
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
