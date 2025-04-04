package todo.validator;

import db.*;
import db.exception.EntityNotFoundException;
import db.exception.InvalidEntityException;
import todo.entity.Step;

public class StepValidator implements Validator {
    @Override
    public void validate(Entity entity) throws InvalidEntityException {
        if (!(entity instanceof Step))
            throw new IllegalArgumentException("You are validating nonstep entity with step validator!");
        if (((Step) entity).title == null)
            throw new InvalidEntityException("The title of the step cannot be null!");
        if (((Step) entity).title.isEmpty())
            throw new InvalidEntityException("The title of the step cannot be empty!");
        try {
            Database.get(((Step) entity).taskRef);
        }
        catch (EntityNotFoundException e) {
            throw new EntityNotFoundException(e.getMessage() + "; So the step's task reference is false!");
        }
    }
}