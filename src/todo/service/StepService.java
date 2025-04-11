package todo.service;

import db.Database;
import db.Entity;
import db.exception.EntityNotFoundException;
import db.exception.InvalidEntityException;
import todo.entity.Step;
import todo.entity.Task;

public class StepService {
    public static void saveStep(int taskRef, String title) throws InvalidEntityException {
        Step newStep = new Step(taskRef, title);
        newStep.status = Step.Status.NotStarted;

        try {
            Database.add(newStep);
        }
        catch (IllegalArgumentException | InvalidEntityException | EntityNotFoundException e) {
            System.out.println("Cannot save step\nError: " + e.getMessage());
            return;
        }
        System.out.println("Step saved successfully\nID: " + newStep.id + "\nCreation Date: " + newStep.getCreationDate());
    }

    public static void removeStep(int id) {
        Database.delete(id);

        System.out.println("Step with ID = " + id + " removed successfully");
    }

    public static void setAsCompleted(int id) throws InvalidEntityException {
        Entity entity = null;
        try {
            entity = Database.get(id);
        }
        catch (EntityNotFoundException e) {
            System.out.println("Cannot update step with ID = " + id + ".\n" +
                    "Error: " + e.getMessage());
            return;
        }

        if (entity instanceof Step) {
            Step step = (Step) entity;
            String oldStatus = String.valueOf(step.status);
            step.status = Step.Status.Completed;

            try {
                Database.update(step);
            } catch (InvalidEntityException | EntityNotFoundException | IllegalArgumentException e) {
                System.out.println("Cannot update step with ID = " + id + ".\n" +
                        "Error: " + e.getMessage());
                return;
            }

            System.out.println("* Successfully updated the step.\n" +
                    "Field: status\n" +
                    "Old Value: " + oldStatus + "\n" +
                    "New Value: Completed" + "\n" +
                    "Modification Date: " + step.getLastModificationDate() + "\n");

            Task taskRef = (Task) Database.get(step.taskRef);

            if (TaskService.isCompleted(taskRef.id))
                TaskService.setAsCompleted(taskRef.id);
            else if (taskRef.status == Task.Status.NotStarted)
                TaskService.setAsInProgress(taskRef.id);
        }
        else
            System.out.println("Cannot update step with ID = " + id + ".\n" +
                    "Error: This id is not a step");
    }

    public static void setAsNotStarted(int id) throws InvalidEntityException {
        Entity entity = null;
        try {
            entity = Database.get(id);
        }
        catch (EntityNotFoundException e) {
            System.out.println("Cannot update step with ID = " + id + ".\n" +
                    "Error: " + e.getMessage());
            return;
        }

        if (entity instanceof Step) {
            Step step = (Step) entity;
            String oldStatus = String.valueOf(step.status);
            step.status = Step.Status.NotStarted;

            try {
                Database.update(step);
            } catch (InvalidEntityException | EntityNotFoundException | IllegalArgumentException e) {
                System.out.println("Cannot update step with ID = " + id + ".\n" +
                        "Error: " + e.getMessage());
                return;
            }

            System.out.println("* Successfully updated the step.\n" +
                    "Field: status\n" +
                    "Old Value: " + oldStatus + "\n" +
                    "New Value: NotStarted" + "\n" +
                    "Modification Date: " + step.getLastModificationDate() + "\n");

            Task taskRef = (Task) Database.get(step.taskRef);

            if (TaskService.isNotStarted(taskRef.id))
                TaskService.setAsNotStarted(taskRef.id);
            else if (taskRef.status == Task.Status.Completed)
                TaskService.setAsInProgress(taskRef.id);
        }
        else
            System.out.println("Cannot update step with ID = " + id + ".\n" +
                    "Error: This id is not a step");

    }

    public static void setTitle(int id, String newTitle) throws InvalidEntityException {
        Entity entity = null;

        try {
            entity = Database.get(id);
        }
        catch (EntityNotFoundException e) {
            System.out.println("Cannot update step with ID = " + id + ".\n" +
                    "Error: " + e.getMessage());
            return;
        }

        if (entity instanceof Step) {
            Step step = (Step) entity;

            String oldTitle = step.title;
            step.title = newTitle;

            try {
                Database.update(step);
            } catch (InvalidEntityException | EntityNotFoundException | IllegalArgumentException e) {
                System.out.println("Cannot update step with ID = " + id + ".\n" +
                        "Error: " + e.getMessage());
                return;
            }

            System.out.println("* Successfully updated the step.\n" +
                    "Field: title\n" +
                    "Old Value: " + oldTitle + "\n" +
                    "New Value: " + newTitle + "\n" +
                    "Modification Date: " + step.getLastModificationDate() + "\n");
        }
        else
            System.out.println("Cannot update step with ID = " + id + ".\n" +
                    "Error: This id is not a step");
    }

    public static void printStep(Step step) {
        System.out.println("+ " + step.title + ":\n" +
                "        ID: " + step.id + "\n" +
                "        Status: " + step.status);
    }
}