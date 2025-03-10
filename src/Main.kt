import java.io.File
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

fun main() {
    val file = File("comments.txt")

    //Main menu
    while (true) {
        println("\nA Blog Poster in 2025!:")
        println("<Ignore the fact that blogs do still exist as personal social media pages>")
        println("\nChoose an option: ")
        println("1. Enter username to log in, or create a new account")
        println("2. View comments and posts of a user")
        println("3. Exit")

        when (readLine()?.trim()) {
            "1" -> loginAndComment(file)
            "2" -> {
                print("Enter the username to view comments: ")
                val username = readLine()?.trim()
                if (!username.isNullOrBlank()) {
                    displayUserComments(file, username)
                } else {
                    println("Invalid username.")
                }
            }
            "3" -> {
                println("Goodbye!")
                return
            }
            else -> println("Invalid option.")
        }
    }
}

// Login
fun loginAndComment(file: File) {
    print("Enter your username: ")
    val username = readLine()?.trim()

    if (username.isNullOrBlank()) {
        println("Please enter a username.")
        return
    }

    // Checks if a username has saved comments associated with it- if not, the account does not exist.
    val hasPreviousComments = file.exists() && file.readLines().any { it.contains(" $username: ") }

    if (!hasPreviousComments) {
        print("This username does not exist. Create a new account? (yes/no): ")
        val response = readLine()?.trim()?.lowercase()
        if (response != "yes") {
            println("Account creation canceled. Returning to main menu.")
            return
        }
        println("Account created successfully!")
    }

    println("Welcome, $username! Create a post (or type 'exit' to return to the menu).")

    while (true) {
        print("Say something interesting: ")
        val comment = readLine()?.trim()

        if (comment.equals("exit", ignoreCase = true)) {
            println("Logging out. Returning to main menu.")
            break
        }

        // Associates the current time and date with the comment
        if (!comment.isNullOrBlank()) {
            val timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
            val logEntry = "[$timestamp] $username: $comment"

            // Saves comment to text file
            file.appendText("$logEntry\n")

            println(logEntry)
        } else {
            println("Don't be shy. Say something.")
        }
    }
}

// Displays all previous comments of a specific username
fun displayUserComments(file: File, username: String) {
    if (!file.exists()) {
        println("No posts found.")
        return
    }

    val userComments = file.readLines().filter { it.contains(" $username: ") }

    if (userComments.isEmpty()) {
        println("No posts found for $username.")
    } else {
        println("\nPosts from $username:")
        userComments.forEach { println(it) }
    }
}