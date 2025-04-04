package todo.validator;

import db.Entity;
import db.Validator;
import db.exception.InvalidEntityException;
import todo.entity.Task;

public class TaskValidator implements Validator {
    @Override
    public void validate(Entity entity) throws InvalidEntityException {
        if (!(entity instanceof Task))
            throw new IllegalArgumentException("You are validating nontask entity with task validator!");
        if (((Task) entity).title == null)
            throw new InvalidEntityException("The title of the task cannot be null!");
        if (((Task) entity).title.isEmpty())
            throw new InvalidEntityException("The title of the task cannot be empty!");
    }
}
