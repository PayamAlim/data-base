package example;

import db.*;
import db.exception.InvalidEntityException;

public class HumanValidator implements Validator {
    @Override
    public void validate(Entity entity) throws InvalidEntityException {
        if (!(entity instanceof Human))
            throw new IllegalArgumentException("The entity is not a human!");
        if (((Human) entity).name == null)
            throw new InvalidEntityException("The human cannot have null name");
        if (((Human) entity).age < 0)
            throw new InvalidEntityException("The human cannot have negative age!");
    }
}
