<?php

// Establish a connection to the database.
$con = mysqli_connect("localhost", "id11579080_ar_sweep", "www.hallo.com", "id11579080_arsweep");

// Get the username and password that the client entered.
$username = $_POST["username"];
$password = $_POST["password"];

// Run the SQL statement to get the username and password.
$statement = mysqli_prepare($con, "SELECT * FROM user WHERE username = ? AND password = ?");
mysqli_stmt_bind_param($statement, "ss", $username, $password);
mysqli_stmt_execute($statement);

// Store the results from the SQL statement so we can get the results later on.
mysqli_stmt_store_result($statement);
mysqli_stmt_bind_result($statement, $id, $username, $password);

// Create an array with fail to be passed to the client (will change to success if we got the user).
$response = array();
$response["success"] = false;

// Loop through the SQL statement result and if we got a match, return success while storing the username and password.
while (mysqli_stmt_fetch($statement)) {
    $response["success"] = true;
    $response["username"] = $username;
    $response["password"] = $password;
}

// Close the statement and connection
$statement->close();
$con->close();

// Pass the success array to the client along with the username and password to inform them that it was a success.
echo json_encode($response);
?>