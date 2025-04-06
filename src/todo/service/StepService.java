package todo.service;

import db.Database;
import db.exception.InvalidEntityException;
import todo.entity.Step;

public class StepService {
    public static void saveStep(int taskRef, String title) throws InvalidEntityException {
        Step newStep = new Step(taskRef, title);
        newStep.status = Step.Status.NotStarted;

        boolean haveException = false;
        try {
            Database.add(newStep);
        }
        catch (IllegalArgumentException | InvalidEntityException e) {
            System.out.println("Cannot save step\nError: " + e.getMessage());
            haveException = true;
        }
        if (!haveException)
            System.out.println("Step saved successfully\nID: " + newStep.id + "\nCreation Date: " + newStep.getCreationDate());
    }

    public static void removeStep(int id) {
        Database.delete(id);
    }
}