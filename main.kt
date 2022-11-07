import java.io.IOException
import java.sql.*;
import java.util.Scanner

private const val url = "jdbc:mysql://localhost:3306/kotlin" 

// Execute this query before executing this code
/*  
    create database kotlin;
    use kotlin;
    create table user(id int auto_increment primary key, name varchar(40) not null, email varchar(40) unique not null, password varchar(40) not null);
*/

fun connection () : Connection = DriverManager.getConnection(url, "root", "root")

// insert
fun insert(con: Connection){
    try {
        print("Enter the Name: ")
        var name: String = readLine()!!
        print("Enter the Email: ")
        var email: String = readLine()!!
        print("Enter the Password: ")
        var password: String = readLine()!!

        var query: String = "insert into user(name,email,password) values (?,?,?);"
        var stmt : PreparedStatement = con.prepareStatement(query)
        stmt.setString(1, name)
        stmt.setString(2, email)
        stmt.setString(3, password)
        stmt.execute()
        println("record inserted successfully...")
    } catch (e: Exception){
        println("record not inserted!!")
        println(e.message)
    }
}

// read
fun read(con: Connection){
    try {
        println(con.isValid(0))
        val query = con.prepareStatement("select * from user")
        val rs = query.executeQuery()
        while(rs.next()) {
            println("User Id: ${rs.getInt("id")} User Name: ${rs.getString("name")} User Email: ${rs.getString("email")} User Password: ${rs.getString("password")}")
        }
    } catch (e: Exception){
        println(e.message)
    }
}

// update
fun update(con: Connection){
    try {
        print("Enter the id: ")
        var id = readLine()!!
        var uid: Int = id.toInt()
        print("Enter the Name : ")
        var name: String = readLine()!!
        print("Enter the Email: ")
        var email: String = readLine()!!
        print("Enter the Password: ")
        var password: String = readLine()!!

        var query: String = "update user set name=?, email=?, password=? where id=?;"
        var stmt : PreparedStatement = con.prepareStatement(query)
        stmt.setString(1, name)
        stmt.setString(2, email)
        stmt.setString(3, password)
        stmt.setInt(4, uid)
        stmt.execute()
        println("record updated successfully...")
    } catch (e: Exception){
        println("record not updated!!")
        println(e.message)
    }
}

// delete
fun delete(con: Connection){
    try {
        print("Enter the Id: ")
        val id: String = readLine()!!
        val uid: Int = id.toInt()
        var query: String = "delete from user where id=?;"
        var stmt: PreparedStatement = con.prepareStatement(query)
        stmt.setInt(1, uid)
        stmt.execute()
        println("record deleted successfully...")
    } catch (e: Exception){
        println("record not deleted!!")
        println(e.message)
    }
}

fun main() {
    // connection
    var con : Connection? = null
    try {
        con  = connection()
    } catch (e: Exception){
        println(e.message)
    }

    println("Welcome to Kotlin MySQL CRUD Operation")

    var flag: Boolean = true
    while (flag) {
        print("Enter Your Choice\n 1. Insert new record\n 2. View all records\n 3. Update a record\n 4. Delete a record\n 5. Exit\n")
        println("Enter the choice: ")
        var choice: String? = readLine()
        when(choice){
            "1" -> con?.let { insert(con) }
            "2" -> con?.let { read(con) }
            "3" -> con?.let { update(con) }
            "4" -> con?.let { delete(con) }
            "5" -> flag = false
            else -> println("Your Entered Wrong")
        }
    }
}
