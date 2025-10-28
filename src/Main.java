import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonArray;

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
//        try {
//            // Read the JSON file using a BufferedReader
//            BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH));
//            JsonObject jsonObject = JsonParser.parseReader(reader).getAsJsonObject();
//            reader.close();
//
//            // Get the "todo" array and add the new item
//            JsonArray todoArray = jsonObject.getAsJsonArray("todo");
//
//
//
//            System.out.println("New task added successfully.");
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

        Scanner sc = new Scanner(System.in);
        boolean running = true;
        do {
            System.out.println("\n=== Task Tracker CLI ===");
            System.out.println("1 : Add Todo");
            System.out.println("2 : Show All Todo");
            System.out.println("3 : Show All Todo Of Progress");
            System.out.println("4 : Show All Todo Of done");
            System.out.println("5 : Show All Todo Of not done");
            System.out.println("6 : Update Todo");
            System.out.println("7 : Delete Todo");
            System.out.println("8 : Exit");
            System.out.print("Choose an option: ");
            String action = sc.nextLine();
            switch (action) {
                case "1":
                    System.out.println("the description:");
                    String description = sc.nextLine();
                    System.out.println("The Status=>  1 : todo , 2 : in-progress , 3 : done ");
                    int statusInput = Integer.parseInt(sc.nextLine());
                    String status = statusInput == 1 ? "todo" : statusInput == 2 ? "in-progress" : "done";
                    Task myTask = new Task(description, status, new Date(), new Date());
                    try {
                        BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH));
                        System.out.println(reader.lines());
                        JsonObject jsonObject = JsonParser.parseReader(reader).getAsJsonObject();
                        JsonArray todoArray = jsonObject.getAsJsonArray("todo");
                        todoArray.add(new Gson().toJsonTree(myTask));
                        System.out.println(todoArray);
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
                            printTask(descriptionStr, statusStr, createdAtStr, updateAtStr);
                            }
                        reader.close();
                    } catch (IOException e) {
                        e.getMessage();
                        }
                        break;
                        case "3":
                            System.out.println("\n=== Tasks In Progress ===");
                            readerAndPrintStatus("in-progress" , FILE_PATH);
                            break;

                        case "4":
                            System.out.println("\n=== Done Tasks ===");
                            readerAndPrintStatus("done" , FILE_PATH);
                            break;
                        case "5":
                            System.out.println("\n=== Not Done Tasks ===");
                            readerAndPrintStatus("todo" , FILE_PATH);
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
                                    System.out.println("Enter new status (1: todo, 2: in-progress, 3: done) (leave blank to keep current):");
                                    String statusInputStr = sc.nextLine();
                                    if (!newDescription.isEmpty()) {
                                        taskToUpdate.addProperty("description", newDescription);
                                    }
                                    if (!statusInputStr.isEmpty()) {
                                        int statusInputInt = Integer.parseInt(statusInputStr);
                                        String newStatus = statusInputInt == 1 ? "todo" : statusInputInt == 2 ? "in-progress" : "done";
                                        taskToUpdate.addProperty("status", newStatus);
                                    }
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
    public static void readerAndPrintStatus(String status , String filePath) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(filePath));
            JsonObject jsonObject = JsonParser.parseReader(reader).getAsJsonObject();
            JsonArray todoArray = jsonObject.getAsJsonArray("todo");
            for (int i = 0; i < todoArray.size(); i++) {
                JsonObject taskObj = todoArray.get(i).getAsJsonObject();
                    String descriptionStr = taskObj.get("description").getAsString();
                    String statusStr = taskObj.get("status").getAsString();
                    String createdAtStr = taskObj.get("createdAt").getAsString();
                    String updateAtStr = taskObj.get("updateAt").getAsString().equals(taskObj.get("createdAt").getAsString()) ? " not update" : taskObj.get("updateAt").getAsString();
                if (taskObj.get("status").getAsString().equals(status)) {
                    printTask(descriptionStr, statusStr, createdAtStr, updateAtStr);
                }
            }
            reader.close();
        } catch (IOException e) {
            e.getMessage();
        }
    }
    public static void printTask (String description, String status, String createdAt , String updateAt) {
        System.out.println( " description : " + description + " status : " +  status +" createdAt : " + createdAt +
                        " updateAt : " + updateAt );
    }
    }


