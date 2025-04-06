package todo.validator;

import db.*;
import db.exception.EntityNotFoundException;
import db.exception.InvalidEntityException;
import todo.entity.Step;
import todo.entity.Task;

public class StepValidator implements Validator {
    @Override
    public void validate(Entity entity) throws InvalidEntityException {
        if (!(entity instanceof Step))
            throw new IllegalArgumentException("You are validating nonstep entity with step validator!");

        if (((Step) entity).title == null)
            throw new InvalidEntityException("The title of the step cannot be null!");

        if (((Step) entity).title.isEmpty())
            throw new InvalidEntityException("The title of the step cannot be empty!");

        boolean haveException = false;
        Entity taskRef;
        try {
            taskRef = Database.get(((Step) entity).taskRef);
        }
        catch (EntityNotFoundException e) {
            throw new EntityNotFoundException(e.getMessage() + "; So the step's task reference is false!");
        }
        if (!haveException)
            if (!(taskRef instanceof Task))
                throw new IllegalArgumentException("The step's task reference is not a task!");
    }
}