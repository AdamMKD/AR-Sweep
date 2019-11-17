<?php

// Establish a connection to the database.
$con = mysqli_connect("localhost", "id11579080_ar_sweep", "www.hallo.com", "id11579080_arsweep");

// Get the username and password that the client entered.
$name;
$score;

// Run the SQL statement to get the scores.
$statement = mysqli_prepare($con, "SELECT * FROM score");
mysqli_stmt_execute($statement);

// Store the results from the SQL statement so we can get the results later on.
mysqli_stmt_store_result($statement);
mysqli_stmt_bind_result($statement, $id, $name, $score);

// Create 3 arrays to store the information, 1 for name, 1 for score, and 1 that holds them both.
$status = array("success" => false);
$nameArray = array();
$scoreArray = array();
$response = array();

// Loop through the SQL statement result and if we got a match, return success while storing the username and password.
while (mysqli_stmt_fetch($statement)) {
    $status["success"] = true;
    $nameArray[] = $name;
    $scoreArray[] = $score;
}
$size = array("size" => count($nameArray));
$response = array_merge($status, $size, $nameArray, $scoreArray);

// Close the statement and connection
$statement->close();
$con->close();

// Pass the success array to the client along with the username and password to inform them that it was a success.
echo json_encode($response);
?>