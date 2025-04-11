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

        try {
            Database.add(newTask);
        }
        catch (IllegalArgumentException | InvalidEntityException e) {
            System.out.println("Cannot save task\n" +
                    "Error: " + e.getMessage());
            return;
        }

        System.out.println("Task saved successfully\n" +
                "ID: " + newTask.id);
    }

    public static void removeTask(int id) {
        Database.delete(id);
        ArrayList<Entity> steps = Database.getAll(Step.STEP_ENTITY_CODE);
        for (Entity step: steps)
            if (step.id == id)
                Database.delete(id);
    }

    public static void setAsCompleted(int id) throws InvalidEntityException {
        Entity entity = null;
        try {
            entity = Database.get(id);
        }
        catch (EntityNotFoundException e) {
            System.out.println("Cannot update task with ID = " + id + ".\n" +
                    "Error: " + e.getMessage());
            return;
        }

        if (entity instanceof Task) {
            Task task = (Task) entity;

            String oldStatus = String.valueOf(task.status);
            task.status = Task.Status.Completed;

            ArrayList<Entity> steps = Database.getAll(Step.STEP_ENTITY_CODE);
            for (Entity step: steps)
                if (((Step) step).taskRef == id)
                    ((Step)step).status = Step.Status.Completed;

            try {
                Database.update(task);
            }
            catch (InvalidEntityException | EntityNotFoundException | IllegalArgumentException e) {
                System.out.println("Cannot update task with ID = " + id + ".\n" +
                        "Error: " + e.getMessage());
                return;
            }

            System.out.println("* Successfully updated the task.\n" +
                    "Field: status\n" +
                    "Old Value: " + oldStatus + "\n" +
                    "New Value: Completed" + "\n" +
                    "Modification Date: " + task.getLastModificationDate() + "\n");
        }
        else
            System.out.println("Cannot update task with ID = " + id + ".\n" +
                    "Error: This id is not a task");
    }

    public static void setAsInProgress(int id) throws InvalidEntityException {
        Entity entity = null;
        try {
            entity = Database.get(id);
        }
        catch (EntityNotFoundException e) {
            System.out.println("Cannot update task with ID = " + id + ".\n" +
                    "Error: " + e.getMessage());
            return;
        }

        if (entity instanceof Task) {
            Task task = (Task) entity;

            String oldStatus = String.valueOf(task.status);
            task.status = Task.Status.InProgress;

            try {
                Database.update(task);
            }
            catch (InvalidEntityException | EntityNotFoundException | IllegalArgumentException e) {
                System.out.println("Cannot update task with ID = " + id + ".\n" +
                        "Error: " + e.getMessage());
                return;
            }

            System.out.println("* Successfully updated the task.\n" +
                    "Field: status\n" +
                    "Old Value: " + oldStatus + "\n" +
                    "New Value: InProgress" + "\n" +
                    "Modification Date: " + task.getLastModificationDate() + "\n");
        }
        else
            System.out.println("Cannot update task with ID = " + id + ".\n" +
                    "Error: This id is not a task");
    }

    public static void setAsNotStarted(int id) throws InvalidEntityException {
        Entity entity = null;
        try {
            entity = Database.get(id);
        }
        catch (EntityNotFoundException e) {
            System.out.println("Cannot update task with ID = " + id + ".\n" +
                    "Error: " + e.getMessage());
            return;
        }

        if (entity instanceof Task) {
            Task task = (Task) entity;

            String oldStatus = String.valueOf(task.status);
            task.status = Task.Status.NotStarted;

            try {
                Database.update(task);
            } catch (InvalidEntityException | EntityNotFoundException | IllegalArgumentException e) {
                System.out.println("Cannot update task with ID = " + id + ".\n" +
                        "Error: " + e.getMessage());
                return;
            }

            System.out.println("* Successfully updated the task.\n" +
                    "Field: status\n" +
                    "Old Value: " + oldStatus + "\n" +
                    "New Value: NotStarted" + "\n" +
                    "Modification Date: " + task.getLastModificationDate() + "\n");
        }
        else
            System.out.println("Cannot update task with ID = " + id + ".\n" +
                    "Error: This id is not a task");
    }

    public static void setTitle(int id, String newTitle) throws InvalidEntityException {
        Entity entity = null;
        try {
            entity = Database.get(id);
        }
        catch (EntityNotFoundException e) {
            System.out.println("Cannot update task with ID = " + id + ".\n" +
                    "Error: " + e.getMessage());
            return;
        }

        if (entity instanceof Task) {
            Task task = (Task) entity;

            String oldTitle = task.title;
            task.title = newTitle;

            try {
                Database.update(task);
            } catch (InvalidEntityException | EntityNotFoundException | IllegalArgumentException e) {
                System.out.println("Cannot update task with ID = " + id + ".\n" +
                        "Error: " + e.getMessage());
                return;
            }

            System.out.println("* Successfully updated the task.\n" +
                    "Field: title\n" +
                    "Old Value: " + oldTitle + "\n" +
                    "New Value: " + newTitle + "\n" +
                    "Modification Date: " + task.getLastModificationDate() + "\n");
        }
        else
            System.out.println("Cannot update task with ID = " + id + ".\n" +
                    "Error: This id is not a task");
    }

    public static void setDescription(int id, String newDescription) throws InvalidEntityException {
        Entity entity = null;
        try {
            entity = Database.get(id);
        }
        catch (EntityNotFoundException e) {
            System.out.println("Cannot update task with ID = " + id + ".\n" +
                    "Error: " + e.getMessage());
            return;
        }

        if (entity instanceof Task) {
            Task task = (Task) entity;
            String oldDescription = task.description;
            task.description = newDescription;

            try {
                Database.update(task);
            } catch (InvalidEntityException | EntityNotFoundException | IllegalArgumentException e) {
                System.out.println("Cannot update task with ID = " + id + ".\n" +
                        "Error: " + e.getMessage());
                return;
            }

            System.out.println("* Successfully updated the task.\n" +
                    "Field: description\n" +
                    "Old Value: " + oldDescription + "\n" +
                    "New Value: " + newDescription + "\n" +
                    "Modification Date: " + task.getLastModificationDate() + "\n");
        }
        else
            System.out.println("Cannot update task with ID = " + id + ".\n" +
                    "Error: This id is not a task");
    }

    public static void setDueDate(int id, Date newDueDate) throws InvalidEntityException {
        Entity entity = null;
        try {
            entity = Database.get(id);
        }
        catch (EntityNotFoundException e) {
            System.out.println("Cannot update task with ID = " + id + ".\n" +
                    "Error: " + e.getMessage());
            return;
        }

        if (entity instanceof Task) {
            Task task = (Task) entity;
            Date oldDate = task.dueDate;
            task.dueDate = newDueDate;

            try {
                Database.update(task);
            } catch (InvalidEntityException | EntityNotFoundException | IllegalArgumentException e) {
                System.out.println("Cannot update task with ID = " + id + ".\n" +
                        "Error: " + e.getMessage());
                return;
            }

            System.out.println("* Successfully updated the task.\n" +
                    "Field: dueDate\n" +
                    "Old Value: " + oldDate + "\n" +
                    "New Value: " + newDueDate + "\n" +
                    "Modification Date: " + task.getLastModificationDate() + "\n");
        }
        else
            System.out.println("Cannot update task with ID = " + id + ".\n" +
                    "Error: This id is not a task");
    }

    public static boolean isCompleted(int taskId) {
        ArrayList<Entity> steps = Database.getAll(Step.STEP_ENTITY_CODE);
        for (Entity step: steps)
            if (((Step) step).taskRef == taskId && ((Step) step).status != Step.Status.Completed)
                return false;
        return true;
    }

    public static void printTask(int id) {
        Entity entity = null;
        try {
            entity = Database.get(id);
        }
        catch (EntityNotFoundException e) {
            System.out.println("No entity with ID = " + id);
            return;
        }

        if (entity instanceof Task) {
            Task task = (Task) entity;

            String year = String.valueOf(task.dueDate.getYear());
            String month = String.valueOf(task.dueDate.getMonth());
            String day = String.valueOf(task.dueDate.getDay());

            if (month.length() < 2) month = "0" + month;
            if (day.length() < 2) day = "0" + day;

            System.out.println("ID: " + id + "\n" +
                    "Title: " + task.title + "\n" +
                    "Due Date: " + year + "-" + month + "-" + day + "\n" +
                    "Status: " + task.status);

            ArrayList<Entity> steps = Database.getAll(Step.STEP_ENTITY_CODE);

            for (Entity step: steps)
                if (((Step) step).taskRef == id)
                    StepService.printStep((Step) step);
        }
        else
            System.out.println("ID = " + id + " is not for a task");
    }
}
