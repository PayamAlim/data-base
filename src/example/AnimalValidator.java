package example;

import db.*;
import db.exception.InvalidEntityException;

public class AnimalValidator implements Validator {
    @Override
    public void validate(Entity entity) throws InvalidEntityException {
        if (!(entity instanceof Animal))
            throw new IllegalArgumentException("You are validating nonanimal entity with animal validator!");
        if (((Animal) entity).age < 0)
            throw new InvalidEntityException("The animal cannot have negative age!");
    }
}
