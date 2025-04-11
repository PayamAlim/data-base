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

                    System.out.print("Due date (yyyy-mm-dd): ");
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
                    System.out.println("There is no such object!");
                }
            }

            else if (operation.equals("delete")) {
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

                scn.nextLine();
            }

            else if (operation.equals("update")) {
                String object = scn.nextLine();
                if (object.equals(" task")) {
                    System.out.print("ID: ");
                    int id = scn.nextInt();

                    System.out.print("Field: ");
                    scn.nextLine();
                    String field = scn.nextLine();

                    System.out.print("New Value ((yyyy-mm-dd) format for due date): ");
                    String newValue = scn.nextLine();

                    if (field.equals("title"))
                        TaskService.setTitle(id, newValue);
                    else if (field.equals("description"))
                        TaskService.setDescription(id, newValue);
                    else if (field.equals("status")) {
                        if (newValue.equals("NotStarted")) TaskService.setAsNotStarted(id);
                        if (newValue.equals("InProgress")) TaskService.setAsInProgress(id);
                        if (newValue.equals("Completed")) TaskService.setAsCompleted(id);
                    }
                    else if (field.equals("due date")) {
                        String[] subDate = newValue.split("-");
                        int year = Integer.parseInt(subDate[0]);
                        int month = Integer.parseInt(subDate[1]);
                        int day = Integer.parseInt(subDate[2]);
                        Date newDueDate = new Date(year, month, day);

                        TaskService.setDueDate(id, newDueDate);
                    }
                    else {
                        System.out.println("This field doesn't exist or not accessible");
                    }
                }

                else if (object.equals(" step")) {
                    System.out.print("ID: ");
                    int id = scn.nextInt();
                    scn.nextLine();

                    System.out.print("Field: ");
                    String field = scn.nextLine();

                    System.out.print("New Value ((yyyy-mm-dd) format for due date): ");
                    String newValue = scn.nextLine();

                    if (field.equals("title"))
                        StepService.setTitle(id, newValue);
                    else if (field.equals("status")) {
                        if (newValue.equals("NotStarted")) StepService.setAsNotStarted(id);
                        if (newValue.equals("Completed")) StepService.setAsCompleted(id);
                    }
                    else {
                        System.out.println("This field doesn't exist or not accessible");
                    }
                }

                else
                    System.out.println("There is no such object!");
            }

            else if (operation.equals("get")) {
                String obj = scn.nextLine();

                ArrayList<Entity> tasks = Database.getAll(Task.TASK_ENTITY_CODE);

                if (obj.equals(" task-by-id")) {
                    System.out.print("ID: ");
                    int id = scn.nextInt();
                    scn.nextLine();

                    TaskService.printTask(id);
                }

                else if (obj.equals(" all-tasks")) {
                    for (Entity task: tasks)
                        TaskService.printTask(task.id);
                }

                else if (obj.equals("incomplete-tasks")) {
                    for (Entity task: tasks)
                        if (((Task) task).status != Task.Status.Completed)
                            TaskService.printTask(task.id);
                }

                else
                    System.out.println("There is no such object!");
            }

            else if (operation.equals("exit"))
                System.out.println("Successfully logged out");

            else
                System.out.println("There is no such operation!");
        } while (!operation.equals("exit"));
    }
}