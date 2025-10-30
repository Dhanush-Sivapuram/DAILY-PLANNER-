import java.time.*;
import java.time.format.*;
import java.util.*;

public class DailyPlanner {
    private static final List<Task> tasks = new ArrayList<>();
    private static final Scanner sc = new Scanner(System.in);
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");

    public static void main(String[] args) {
        while (true) {
            System.out.println("\n===== DAILY PLANNER & REMINDER APP =====");
            System.out.println("1. Add Task");
            System.out.println("2. View All Tasks");
            System.out.println("3. View Today's Tasks");
            System.out.println("4. Check Reminders");
            System.out.println("5. Exit");
            System.out.print("Enter choice: ");

            int choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1 -> addTask();
                case 2 -> viewTasks();
                case 3 -> viewTodayTasks();
                case 4 -> checkReminders();
                case 5 -> {
                    System.out.println("👋 Goodbye! Have a productive day!");
                    System.exit(0);
                }
                default -> System.out.println("❌ Invalid choice!");
            }
        }
    }

    private static void addTask() {
        try {
            System.out.print("Enter Task Title: ");
            String title = sc.nextLine();
            System.out.print("Enter Description: ");
            String desc = sc.nextLine();
            System.out.print("Enter Date & Time (dd-MM-yyyy HH:mm): ");
            String dt = sc.nextLine();
            LocalDateTime dateTime = LocalDateTime.parse(dt, formatter);
            tasks.add(new Task(title, desc, dateTime));
            System.out.println("✅ Task added successfully!");
        } catch (DateTimeParseException e) {
            System.out.println("⚠️ Invalid date format! Use dd-MM-yyyy HH:mm");
        }
    }

    private static void viewTasks() {
        if (tasks.isEmpty()) {
            System.out.println("🕳️ No tasks added yet!");
            return;
        }
        System.out.println("\n--- ALL TASKS ---");
        tasks.forEach(System.out::println);
    }

    private static void viewTodayTasks() {
        LocalDate today = LocalDate.now();
        System.out.println("\n--- TODAY'S TASKS (" + today + ") ---");
        tasks.stream()
             .filter(t -> t.getDateTime().toLocalDate().equals(today))
             .forEach(System.out::println);
    }

    private static void checkReminders() {
        LocalDateTime now = LocalDateTime.now();
        System.out.println("\n--- REMINDERS ---");
        boolean found = false;
        for (Task t : tasks) {
            long minutes = Duration.between(now, t.getDateTime()).toMinutes();
            if (minutes >= 0 && minutes <= 30) {
                System.out.println("⏰ Reminder: " + t.getTitle() + " is due in " + minutes + " minutes!");
                found = true;
            }
        }
        if (!found) System.out.println("✅ No upcoming tasks within 30 minutes.");
    }
}
