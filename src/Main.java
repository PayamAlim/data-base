import db.*;
import db.exception.*;

import todo.entity.*;
import todo.validator.*;
import todo.service.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class Main {
    public static void main(String[] args) throws InvalidEntityException {
        Database.registerValidator(Task.TASK_ENTITY_CODE, new TaskValidator());
        Database.registerValidator(Step.STEP_ENTITY_CODE, new StepValidator());

        var scn = new Scanner(System.in);
        String operation = "";
        do {
            try {
                System.out.print("Operation: ");
                operation = scn.next();
            } catch (InputMismatchException e) {
                System.out.println("Wrong input!");
                scn.nextLine();
                continue;
            }

            if (operation.equals("add")) {
                String object;
                try {
                    object = scn.nextLine();
                } catch (InputMismatchException e) {
                    System.out.println("Wrong input!");
                    scn.nextLine();
                    continue;
                }

                if (object.equals(" task")) {
                    try {
                        System.out.print("Title: ");
                        String title = scn.nextLine();

                        System.out.print("Description: ");
                        String description = scn.nextLine();

                        System.out.print("Due Date (yyyy-mm-dd): ");
                        String date = scn.nextLine();
                        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

                        Date dueDate = formatter.parse(date);

                        TaskService.saveTask(title, description, dueDate);
                    } catch (InputMismatchException e) {
                        System.out.println("Wrong input!");
                        scn.nextLine();
                    } catch (ParseException e) {
                        System.out.println("Invalid date format");
                        return;
                    }
                } else if (object.equals(" step")) {
                    try {
                        System.out.print("TaskID: ");
                        int taskId = scn.nextInt();

                        scn.nextLine();
                        System.out.print("Title: ");
                        String title = scn.nextLine();

                        StepService.saveStep(taskId, title);
                    } catch (InputMismatchException e) {
                        System.out.println("Wrong input!");
                        scn.nextLine();
                    }
                } else {
                    System.out.println("There is no such object!");
                }
            } else if (operation.equals("delete")) {
                try {
                    scn.nextLine();
                    System.out.print("ID: ");
                    int id = scn.nextInt();

                    Entity entity = Database.get(id);

                    if (entity instanceof Task)
                        TaskService.removeTask(id);
                    else
                        StepService.removeStep(id);

                    scn.nextLine();
                } catch (EntityNotFoundException e) {
                    System.out.println("Cannot delete entity with the given ID.\nError: " + e.getMessage());
                    return;
                } catch (InputMismatchException e) {
                    System.out.println("Wrong input!");
                    scn.nextLine();
                }
            } else if (operation.equals("update")) {
                String object;
                try {
                    object = scn.nextLine();
                } catch (InputMismatchException e) {
                    System.out.println("Wrong input!");
                    scn.nextLine();
                    continue;
                }

                if (object.equals(" task")) {
                    try {
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
                            else if (newValue.equals("InProgress")) TaskService.setAsInProgress(id);
                            else if (newValue.equals("Completed")) TaskService.setAsCompleted(id);
                            else
                                System.out.println("There is no such status");
                        } else if (field.equals("dueDate")) {
                            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                            Date newDueDate = formatter.parse(newValue);
                            TaskService.setDueDate(id, newDueDate);
                        } else
                            System.out.println("This field doesn't exist or is not accessible.");
                    } catch (InputMismatchException e) {
                        System.out.println("Wrong input!");
                        scn.nextLine();
                    } catch (ParseException e) {
                        System.out.println("Invalid date format");
                        return;
                    }
                } else if (object.equals(" step")) {
                    try {
                        System.out.print("ID: ");
                        int id = scn.nextInt();
                        scn.nextLine();

                        System.out.print("Field: ");
                        String field = scn.nextLine();

                        System.out.print("New Value: ");
                        String newValue = scn.nextLine();

                        if (field.equals("title"))
                            StepService.setTitle(id, newValue);
                        else if (field.equals("status")) {
                            if (newValue.equals("NotStarted")) StepService.setAsNotStarted(id);
                            else if (newValue.equals("Completed")) StepService.setAsCompleted(id);
                            else
                                System.out.println("There is no such status");
                        } else {
                            System.out.println("This field doesn't exist or is not accessible.");
                        }
                    } catch (InputMismatchException e) {
                        System.out.println("Wrong input!");
                        scn.nextLine();
                    }
                } else {
                    System.out.println("There is no such object!");
                }
            } else if (operation.equals("get")) {
                String obj;
                try {
                    obj = scn.nextLine();
                } catch (InputMismatchException e) {
                    System.out.println("Wrong input!");
                    scn.nextLine();
                    continue;
                }

                ArrayList<Entity> tasks = Database.getAll(Task.TASK_ENTITY_CODE);

                tasks.sort(Comparator.comparing(entity -> ((Task) entity).dueDate));

                if (obj.equals(" task-by-id")) {
                    try {
                        System.out.print("ID: ");
                        int id = scn.nextInt();
                        scn.nextLine();

                        TaskService.printTask(id);
                    } catch (InputMismatchException e) {
                        System.out.println("Wrong input!");
                        scn.nextLine();
                    }
                } else if (obj.equals(" all-tasks")) {
                    for (Entity task : tasks)
                        TaskService.printTask(task.id);
                } else if (obj.equals("incomplete-tasks")) {
                    for (Entity task : tasks)
                        if (((Task) task).status != Task.Status.Completed)
                            TaskService.printTask(task.id);
                } else {
                    System.out.println("There is no such object!");
                }
            } else if (operation.equals("exit"))
                System.out.println("Successfully logged out");
            else
                System.out.println("There is no such operation!");
        } while (!operation.equals("exit"));
    }
}