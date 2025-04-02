package example;

import db.Entity;

public class Animal extends Entity {
    public static final int ANIMAL_ENTITY_CODE = 50;

    public int age;

    public Animal(int age) {
        this.age = age;
    }

    @Override
    public Animal copy() {
        Animal copyAnimal = new Animal(age);
        copyAnimal.id = id;

        return copyAnimal;
    }

    @Override
    public int getEntityCode() {
        return ANIMAL_ENTITY_CODE;
    }
}
