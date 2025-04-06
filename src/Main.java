import db.*;
import db.exception.*;

import todo.entity.*;
import todo.validator.*;
import todo.service.*;

import java.util.*;

public class Main {
    public static void main(String[] args) throws InvalidEntityException {
        Database.registerValidator(Task.TASK_ENTITY_CODE, new TaskValidator());
        Database.registerValidator(Step.STEP_ENTITY_CODE, new StepValidator());

        var scn = new Scanner(System.in);
        String operation;
        do {
            operation = scn.next();
            if (operation.equals("add")) {
                String object = scn.nextLine();
                if (object.equals(" task")) {
                    System.out.print("Title: ");
                    String title = scn.nextLine();

                    System.out.print("Description: ");
                    String description = scn.nextLine();

                    System.out.print("Due date: ");
                    String date = scn.next();
                    String[] subDate = date.split("-");
                    int year = Integer.parseInt(subDate[0]);
                    int month = Integer.parseInt(subDate[1]);
                    int day = Integer.parseInt(subDate[2]);
                    Date dueDate = new Date(year, month, day);

                    TaskService.saveTask(title, description, dueDate);
                }
                else if (object.equals(" step")) {
                    System.out.print("TaskID: ");
                    int taskId = scn.nextInt();

                    scn.nextLine();
                    System.out.print("Title: ");
                    String title = scn.nextLine();

                    StepService.saveStep(taskId, title);
                }
                else {
                    System.out.println("There is no such operation!");
                }
            }

            if (operation.equals("delete")) {
                scn.nextLine();
                System.out.print("ID: ");
                int id = scn.nextInt();

                boolean hasException = false;
                Entity entity = null;
                try {
                    entity = Database.get(id);
                }
                catch (EntityNotFoundException e) {
                    System.out.println("Cannot delete entity with ID = " + id + ".\n Error: " + e.getMessage());
                    hasException = true;
                }
                if (!hasException)
                    if (entity instanceof Task)
                        TaskService.removeTask(id);
                    else
                        StepService.removeStep(id);
            }

            if (operation.equals("exit"))
                System.out.println("Successfully logged out");
        } while (!operation.equals("exit"));
    }
}