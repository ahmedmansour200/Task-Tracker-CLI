package helper;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

    public class Helper {
        public static void readerAndPrintStatus(String status, String filePath) {
            try {
                BufferedReader reader = new BufferedReader(new FileReader(filePath));
                JsonObject jsonObject = JsonParser.parseReader(reader).getAsJsonObject();
                JsonArray todoArray = jsonObject.getAsJsonArray("todo");
                for (int i = 0; i < todoArray.size(); i++) {
                    JsonObject taskObj = todoArray.get(i).getAsJsonObject();
                    String descriptionStr = taskObj.get("description").getAsString();
                    String statusStr = taskObj.get("status").getAsString();
                    String createdAtStr = taskObj.get("createdAt").getAsString();
                    String updateAtStr = taskObj.get("updateAt").getAsString().equals(taskObj.get("createdAt")
                            .getAsString()) ? " not update" : taskObj.get("updateAt").getAsString();
                    if (taskObj.get("status").getAsString().equals(status)) {
                        printTask(descriptionStr, statusStr, createdAtStr, updateAtStr);
                    }
                }
                reader.close();
            } catch (IOException e) {
                e.getMessage();
            }
        }

        public static void printTask(String description, String status, String createdAt, String updateAt) {
            String RESET = "\u001B[0m";
            String RED = "\u001B[31m";
            String GREEN = "\u001B[32m";
            String BOLD = "\u001B[1m";
            String ITALIC = "\u001B[3m";

            String statusColor = (status.equalsIgnoreCase("done") || status.equalsIgnoreCase("completed")) ? GREEN : RED;
            System.out.println(BOLD + " description : " + RESET + description +
                    "  " + BOLD + "status : " + statusColor + status + RESET +
                    "  " + ITALIC + "createdAt : " + RESET + createdAt +
                    "  " + ITALIC + "updateAt : " + RESET + updateAt);
        }

    }
