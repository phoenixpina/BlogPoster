import java.io.File
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

fun main() {
    val commentsFile = File("comments.txt")
    val usersFile = File("users.txt")

    //Main Menu
    while (true) {
        println("\nA Blog Posting site in 2025!")
        println("<Ignore the fact that they actually still exist, we just don't use the word 'blog' anymore.>")
        print("Choose an option: ")
        println("1. Enter your username to log in, or create a new username")
        println("2. View posts of a user")
        println("3. Exit")

        //Switchcase option logic
        when (readLine()?.trim()) {
            "1" -> loginAndComment(commentsFile, usersFile)
            "2" -> {
                print("Enter the username to view posts: ")
                val username = readLine()?.trim()
                if (!username.isNullOrBlank()) {
                    displayUserComments(commentsFile, username)
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

//Function for reading user input
fun loginAndComment(commentsFile: File, usersFile: File) {
    print("Enter your username: ")
    val username = readLine()?.trim()

    if (username.isNullOrBlank()) {
        println("I said enter your username.")
        return
    }

    val userPassword = getUserPassword(usersFile, username)

    if (userPassword == null) {
        //If no comments are saved to a username, then it doesn't exist
        print("This username does not exist. Create a new account? (yes/no): ")
        val response = readLine()?.trim()?.lowercase()
        if (response != "yes") {
            println("Account creation canceled. Returning to main menu.")
            return
        }

        //Stores passwords to users.txt
        val newPassword = createNewPassword()
        usersFile.appendText("$username:$newPassword\n")

        println("Account created successfully!")
    } else {
        //If username already exists with comments in comments.txt
        print("Enter your password: ")
        val inputPassword = readLine()?.trim()

        if (inputPassword != userPassword) {
            println("Incorrect password. Returning to main menu. Get crunked by annoying security measures, loser.")
            return
        }
    }

    println("Welcome, $username! Create a post (or type 'exit' to return to the menu).")

    while (true) {
        print("Say something interesting: ")
        val comment = readLine()?.trim()

        if (comment.equals("exit", ignoreCase = true)) {
            println("Returning to main menu.")
            break
        }
        //Associates current time and date with comments made
        if (!comment.isNullOrBlank()) {
            val timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
            val logEntry = "[$timestamp] $username: $comment"

            commentsFile.appendText("$logEntry\n")

            println(logEntry)
        } else {
            println("Don't be shy. Say something.")
        }
    }
}

//Function for displaying all comments associated with a specific username
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

//Reads and retrieves passwords saved to users.txt
fun getUserPassword(usersFile: File, username: String): String? {
    if (!usersFile.exists()) return null
    return usersFile.readLines()
        .find { it.startsWith("$username:") }
        ?.split(":")
        ?.getOrNull(1)
}

fun createNewPassword(): String {
    while (true) {
        print("Create a password: ")
        val password = readLine()?.trim()
        print("Confirm password: ")
        val confirmPassword = readLine()?.trim()

        if (!password.isNullOrBlank() && password == confirmPassword) {
            return password
        }
        println("Passwords do not match. Try again. Security is very important, you know.")
    }
}
