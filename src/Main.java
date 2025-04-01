import db .*;
import db.exception.*;
import example.*;

public class Main {
    public static void main(String[] args) throws InvalidEntityException {
        //test
        Database.registerValidator(Human.HUMAN_ENTITY_CODE, new AnimalValidator());

        Human ali = new Human(null, -10);
        Database.add(ali);
    }
}