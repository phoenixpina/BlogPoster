fun main() {
    print("Enter your comment: ")
    val comment = readLine() // Read user input
    if (!comment.isNullOrBlank()) {
        println("You posted: $comment")
    } else {
        println("No comment entered.")
    }
}
