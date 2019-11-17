<?php

// Establish a connection to the database.
$con = mysqli_connect("localhost", "id11579080_ar_sweep", "www.hallo.com", "id11579080_arsweep");

// Get the name and score that the client entered.
$name = $_POST["name"];
$score = $_POST["score"];

// Run the SQL statement to add the new score into the database.
$statement = mysqli_prepare($con, "INSERT INTO score (name, score) VALUES (?, ?)");
mysqli_stmt_bind_param($statement, "ss", $name, $score);
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