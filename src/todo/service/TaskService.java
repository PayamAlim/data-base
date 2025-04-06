package todo.service;

import db.Database;
import db.Entity;
import db.exception.EntityNotFoundException;
import db.exception.InvalidEntityException;
import todo.entity.Step;
import todo.entity.Task;

import java.util.ArrayList;
import java.util.Date;

public class TaskService {
    public static void saveTask(String title, String description, Date dueDate) throws InvalidEntityException {
        Task newTask = new Task(title, description, dueDate);
        newTask.status = Task.Status.NotStarted;

        boolean haveException = false;
        try {
            Database.add(newTask);
        }
        catch (IllegalArgumentException | InvalidEntityException e) {
            System.out.println("Cannot save task\nError: " + e.getMessage());
            haveException = true;
        }
        if (!haveException)
            System.out.println("Task saved successfully\nID: " + newTask.id);
    }

    public static void removeTask(int id) {
        Database.delete(id);
        ArrayList<Entity> steps = Database.getAll(Step.STEP_ENTITY_CODE);
        for (Entity step: steps)
            if (step.id == id)
                Database.delete(id);
    }

    public static void setAsCompleted(int taskId) throws InvalidEntityException {
        Task task = (Task) Database.get(taskId);
        task.status = Task.Status.Completed;
        Database.update(task);
    }
}
