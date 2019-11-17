<?php

// Establish a connection to the database.
$con = mysqli_connect("localhost", "id11579080_ar_sweep", "www.hallo.com", "id11579080_arsweep");

// Get the username and password that the client entered.
$username = $_POST["username"];
$password = $_POST["password"];

// Run the SQL statement to add the new user into the database.
$statement = mysqli_prepare($con, "INSERT INTO user (username, password) VALUES (?, ?)");
mysqli_stmt_bind_param($statement, "ss", $username, $password);
mysqli_stmt_execute($statement);

// Create an array with success to be passed to the client.
$response = array();
$response["success"] = true;

// Close the statement and connection
$statement->close();
$con->close();

// Pass the success array to the client to inform them that it was a success.
echo json_encode($response);
?>