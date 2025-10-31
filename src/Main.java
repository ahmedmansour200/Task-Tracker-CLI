import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonArray;
import helper.Helper;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;


import java.io.*;
import java.util.Date;
import java.util.Scanner;
public class Main {
    public static void main(String[] args) {
        String FILE_PATH = "data.json";
        Scanner sc = new Scanner(System.in);
        boolean running = true;
        do {
            System.out.println("\n=== Task Tracker CLI ===");
            System.out.println("1 : Add New Task");
            System.out.println("2 : Show All Task");
            System.out.println("3 : Show All Task Of Progress");
            System.out.println("4 : Show All Task Of done");
            System.out.println("5 : Show All Task Of not done");
            System.out.println("6 : Update Task");
            System.out.println("7 : Delete Task");
            System.out.println("8 : Exit");
            System.out.print("Choose an option: ");
            String action = sc.nextLine();
            switch (action) {
                case "1":
                    System.out.println("Add new description:");
                    String description = sc.nextLine();
                    while (description.isEmpty()){
                        System.out.println("Description cannot be empty. Please enter a valid description:");
                        description = sc.nextLine();
                    }
                    System.out.println("Add The Status=>  1 : todo , 2 : in-progress , 3 : done ");
                    String statusInput = sc.nextLine();
                    while (statusInput.isEmpty()){
                        System.out.println("Status cannot be empty. Please enter a valid status:");
                        statusInput = sc.nextLine();
                    }while (statusInput.charAt(0) < '1' || statusInput.charAt(0) > '3' || statusInput.length() > 1){
                        System.out.println("Invalid status. Please enter 1, 2, or 3.");
                        statusInput = sc.nextLine();
                    }
                    Status status = statusInput.equals("1") ? Status.TODO : statusInput.equals("2") ? Status.IN_PROGRESS : Status.DONE;
                    Task myTask = new Task(description, status, new Date(), new Date());
                    try {
                        BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH));
                        System.out.println(reader.lines());
                        JsonObject jsonObject = JsonParser.parseReader(reader).getAsJsonObject();
                        JsonArray todoArray = jsonObject.getAsJsonArray("todo");
                        todoArray.add(new Gson().toJsonTree(myTask));
                        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH))) {
                            Gson gson = new Gson();
                            gson.toJson(jsonObject, writer);
                        }
                    reader.close();
                        System.out.println("New task added successfully.");
                    } catch (IOException e) {
                        e.getMessage();
                    }
                    break;

                case "2":
                    System.out.println("\n=== All Tasks ===");
                    try {
                        BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH));
                        JsonObject jsonObject = JsonParser.parseReader(reader).getAsJsonObject();
                        JsonArray todoArray = jsonObject.getAsJsonArray("todo");
                        for (int i = 0; i < todoArray.size(); i++) {
                            JsonObject taskObj = todoArray.get(i).getAsJsonObject();
                            String descriptionStr = taskObj.get("description").getAsString();
                            String statusStr = taskObj.get("status").getAsString();
                            String createdAtStr = taskObj.get("createdAt").getAsString();
                            String updateAtStr = taskObj.get("updateAt").getAsString().equals(taskObj.get("createdAt").getAsString()) ? " not update" : taskObj.get("updateAt").getAsString();
                            Helper.printTask(descriptionStr, statusStr, createdAtStr, updateAtStr);
                            }
                        reader.close();
                    } catch (IOException e) {
                        e.getMessage();
                        }
                        break;
                        case "3":
                            System.out.println("\n=== Tasks In Progress ===");
                            Helper.readerAndPrintStatus("in-progress" , FILE_PATH);
                            break;

                        case "4":
                            System.out.println("\n=== Done Tasks ===");
                            Helper.readerAndPrintStatus("done" , FILE_PATH);
                            break;
                        case "5":
                            System.out.println("\n=== Not Done Tasks ===");
                            Helper.readerAndPrintStatus("todo" , FILE_PATH);
                            break;
                        case "6":
                            System.out.println("\n=== Update Tasks ===");
                            try {
                                BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH));
                                JsonObject jsonObject = JsonParser.parseReader(reader).getAsJsonObject();
                                JsonArray todoArray = jsonObject.getAsJsonArray("todo");
                                System.out.println("Select the update item:");
                                for (int i = 0; i < todoArray.size(); i++) {
                                    JsonObject taskObj = todoArray.get(i).getAsJsonObject();
                                    System.out.println((i + 1) + " : " +
                                            " description : " + taskObj.get("description").getAsString() +
                                            " status : " + taskObj.get("status").getAsString() +
                                            " createdAt : " + taskObj.get("createdAt").getAsString());
                                }
                                int updateIndex = Integer.parseInt(sc.nextLine());
                                if (updateIndex - 1 >= 0 && updateIndex - 1< todoArray.size()) {
                                    JsonObject taskToUpdate = todoArray.get(updateIndex - 1).getAsJsonObject();
                                    System.out.println("Enter new description (leave blank to keep current):");
                                    String newDescription = sc.nextLine();
                                    while (newDescription.isEmpty()){
                                        System.out.println("Description cannot be empty. Please enter a valid description:");
                                        newDescription = sc.nextLine();
                                    }
                                    System.out.println("Enter new status (1: todo, 2: in-progress, 3: done) (leave blank to keep current):");
                                    String statusInputStr = sc.nextLine();
                                    while (statusInputStr.isEmpty()){
                                        System.out.println("Status cannot be empty. Please enter a valid status:");
                                        statusInputStr = sc.nextLine();
                                    }
                                        while (statusInputStr.charAt(0) < '1' || statusInputStr.charAt(0) > '3' || statusInputStr.length() > 1){
                                            System.out.println("Invalid status. Please enter 1, 2, or 3.");
                                            statusInputStr = sc.nextLine();
                                        }
                                        String newStatus = statusInputStr.equals("1") ? "todo" : statusInputStr.equals("2") ? "in-progress" : "done";
                                        taskToUpdate.addProperty("status", newStatus);

                                    taskToUpdate.addProperty("updateAt", new Date().toString());
                                    try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH))) {
                                        Gson gson = new Gson();
                                        gson.toJson(jsonObject, writer);
                                    }
                                    System.out.println("Task updated successfully.");
                                } else {
                                    System.out.println("Invalid index. No task updated.");
                                }
                            }catch (IOException e){
                                System.out.println(e.getMessage());
                            }
                            break;
                        case "7":
                            System.out.println("\n=== Delete ===");
                            try {
                                BufferedReader reade = new BufferedReader(new FileReader(FILE_PATH));
                                JsonObject jsonObject = JsonParser.parseReader(reade).getAsJsonObject();
                                JsonArray todoArray = jsonObject.getAsJsonArray("todo");
                                System.out.println("Select the delete item:");
                                for (int i = 0; i < todoArray.size(); i++) {
                                    JsonObject taskObj = todoArray.get(i).getAsJsonObject();
                                    System.out.println((i + 1) + " : " +
                                            " description : " + taskObj.get("description").getAsString() +
                                            " status : " + taskObj.get("status").getAsString() +
                                            " createdAt : " + taskObj.get("createdAt").getAsString());
                                }
                                int deleteIndex = Integer.parseInt(sc.nextLine());
                                if (deleteIndex - 1 >= 0 && deleteIndex - 1< todoArray.size()) {
                                    todoArray.remove(deleteIndex - 1);
                                    try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH))) {
                                        Gson gson = new Gson();
                                        gson.toJson(jsonObject, writer);
                                    }
                                    System.out.println("Task deleted successfully.");
                                } else {
                                    System.out.println("Invalid index. No task deleted.");
                                }
                            } catch (IOException e) {
                                e.getMessage();
                            }
                            break;

                        case "8":
                            System.out.println("Exiting Task Tracker CLI. Goodbye!");
                            running = false;
                            break;

                        default:
                            System.out.println("Invalid input");
                            break;
                    }

        }while (running) ;
            sc.close();
        }
    }


