import db .*;
import example.*;

public class Main {
    public static void main(String[] args) {
        //test

        Human ali = new Human("Ali");
        Database.add(ali);

        ali.name = "Ali Hosseini";

        Human aliFromTheDatabase = (Human) Database.get(ali.id);

        System.out.println("ali's name in the database: " + aliFromTheDatabase.name);

        aliFromTheDatabase.name = "Alireza";
        Human aliFromTheDatabaseAfterGet = (Human) Database.get(ali.id);

        System.out.println("ali's name in the database after using get method: " + aliFromTheDatabaseAfterGet.name);
    }
}