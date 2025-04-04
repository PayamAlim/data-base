package todo.service;

import db.Database;
import db.exception.InvalidEntityException;
import todo.entity.Step;

public class StepService {
    public static void saveStep(int taskRef, String title) throws InvalidEntityException {
        Step step = new Step(taskRef, title);
        step.status = Step.Status.NotStarted;
        Database.add(step);
    }
}