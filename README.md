# Task Tracker CLI

A simple command-line task tracker written in Java. It stores tasks in a local `data.json` file and provides an interactive menu to add, list, filter, update, and delete tasks.

---
Project URL: https://roadmap.sh/projects/task-tracker
## Contents
- Quick summary
- Prerequisites
- Build & run (Windows / CMD)
- Usage (menu options explained)
- Example session
- `data.json` format
- Troubleshooting
- Contributing / Notes

---

## Quick summary
Run the program and follow the interactive menu. Tasks have these fields: `id`, `description`, `status` (`todo`, `in-progress`, `done`), `createdAt`, and `updateAt`.

Source files:
- `src\Main.java` — main CLI program and menu
- `src\Task.java` — Task model
- `src\Status.java` — enum for statuses
- `src\helper\Helper.java` — helper routines for printing and filtering
- `data.json` — storage file (created/updated by the program)

The project depends on Gson (located at `lib\gson-2.10.1.jar`).

---

## Prerequisites
- Java JDK 8 or newer installed and `java`/`javac` available on PATH.
- The Gson JAR file is included in `lib\gson-2.10.1.jar`.

---

## Build & run (Windows — Command Prompt)
Open a command prompt in the project root (the folder that contains `src` and `lib`). Because the path may contain spaces, wrap paths in quotes when necessary.

1) Compile the sources:

```cmd
javac -cp "lib\gson-2.10.1.jar" -d out "src\*.java" "src\helper\*.java"
```

This compiles the Java files and writes class files into the `out` folder.

2) Run the CLI:

```cmd
java -cp "out;lib\gson-2.10.1.jar" Main
```

Note: If you use an IDE (IntelliJ IDEA), simply open the project and run `src\Main.java` — make sure the Gson JAR is added as a library/dependency in the project settings.

---

## How the CLI works (menu options)
When you run the program you'll see a menu with numeric options. Type the option number and press Enter.

1 : Add New Task
- The program will prompt for a description (required) and then for status.
- Status input accepts a number: `1` = `todo`, `2` = `in-progress`, `3` = `done`.
- The task will be appended to `data.json` under the top-level `todo` array.

2 : Show All Task
- Displays every task in the `data.json` file.

3 : Show All Task Of Progress
- Displays only tasks with status `in-progress`.

4 : Show All Task Of done
- Displays only tasks with status `done`.

5 : Show All Task Of not done
- Displays only tasks with status `todo`.

6 : Update Task
- Shows a numbered list of tasks; enter the number to select a task to update.
- Prompts for a new description (required) and a new status (1/2/3 as above).
- Updates the task's `status` and `updateAt` fields in `data.json`.

7 : Delete Task
- Shows a numbered list of tasks; enter the number to delete.
- The selected task is removed from the `data.json` file.

8 : Exit
- Exits the CLI.

---

## Example session
Below is a short example of what a session looks like (user input is prefixed with `>`):

1) Start the app:

```cmd
> java -cp "out;lib\gson-2.10.1.jar" Main
=== Task Tracker CLI ===
1 : Add New Task
2 : Show All Task
3 : Show All Task Of Progress
4 : Show All Task Of done
5 : Show All Task Of not done
6 : Update Task
7 : Delete Task
8 : Exit
Choose an option: > 1
Add new description:
> Buy groceries
Add The Status=>  1 : todo , 2 : in-progress , 3 : done 
> 1
New task added successfully.
```

2) List tasks:

```cmd
Choose an option: > 2
=== All Tasks ===
 description : Buy groceries   status : todo  createdAt : Tue Oct 28 12:34:56 GMT 2025  updateAt :  not update
```

3) Update status to `done`:

```cmd
Choose an option: > 6
=== Update Tasks ===
Select the update item:
1 :  description : Buy groceries status : todo createdAt : Tue Oct 28 12:34:56 GMT 2025
> 1
Enter new description (leave blank to keep current):
> Buy groceries - done
Enter new status (1: todo, 2: in-progress, 3: done) (leave blank to keep current):
> 3
Task updated successfully.
```

---

## `data.json` format
The program expects a top-level JSON object with an array named `todo`. The easiest initial `data.json` contents is:

```json
{
  "todo": []
}
```

When the program creates tasks, each task object has these properties (example):

```json
{
  "id": "36e7cf3a-...",
  "description": "Buy groceries",
  "status": "todo",
  "createdAt": "Tue Oct 28 12:34:56 GMT 2025",
  "updateAt": "Tue Oct 28 12:34:56 GMT 2025"
}
```

Notes:
- The program appends tasks to the `todo` array.
- `createdAt` and `updateAt` use Java's `Date.toString()` representation.
- `updateAt` is set to the same value as `createdAt` when the task is first created (and displayed as `not update` by the helper when those values are equal).

---

## Troubleshooting
- "File not found" or `data.json` missing:
  - Create a file named `data.json` in the project root with the content shown above.

- Malformed `data.json` (invalid JSON):
  - The program uses Gson's parser; fix the JSON syntax (e.g., missing commas or unmatched braces). If the file is badly corrupted you can replace it with the initial empty template and restart.

- Classpath issues while compiling/running:
  - Ensure the `lib\gson-2.10.1.jar` file exists.
  - Use the exact compile/run commands above and make sure to run them from the project root.

- Colors not showing or garbled output:
  - The helper uses simple ANSI escape codes for colored terminal output. Windows 10+ Command Prompt and most terminals support these, but older terminals may show escape sequences instead.

---
