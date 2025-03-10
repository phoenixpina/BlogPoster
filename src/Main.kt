import java.io.File
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

fun main() {
    // File to store comments
    val file = File("comments.txt")

    // User login
    print("Enter your username: ")
    val username = readLine()?.trim()

    if (username.isNullOrBlank()) {
        println("Username cannot be empty. Exiting program.")
        return
    }

    println("\nWelcome, $username!")

    // Display previous comments from this user
    displayUserComments(file, username)

    println("Type your comments below (or type 'exit' to quit).")

    while (true) {
        print("Say something: ")
        val comment = readLine()?.trim()

        if (comment.equals("exit", ignoreCase = true)) {
            println("Goodbye, $username!")
            break
        }

        if (!comment.isNullOrBlank()) {
            val timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
            val logEntry = "[$timestamp] $username: $comment"

            // Save comment to file
            file.appendText("$logEntry\n")

            println(logEntry)
        } else {
            println("Comment cannot be empty.")
        }
    }
}

// Function to display previous comments made by the user
fun displayUserComments(file: File, username: String) {
    if (!file.exists()) {
        println("No previous comments found.")
        return
    }

    val userComments = file.readLines().filter { it.contains(" $username: ") }

    if (userComments.isEmpty()) {
        println("No previous comments found for $username.")
    } else {
        println("\nYour past comments:")
        userComments.forEach { println(it) }
    }
}