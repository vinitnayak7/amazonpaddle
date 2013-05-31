<?php
$con = mysqli_connect("localhost","gamersso_paddle","paddle","gamersso_paddle");
if (mysqli_connect_errno($con))
{
  echo "Failed to connect to MySQL: " . mysqli_connect_error();
}

$gameName = $_REQUEST['gameName'];
$playerOne = $_REQUEST['playerOne'];
$playerTwo = $_REQUEST['playerTwo'];
$comments = $_REQUEST['comments'];

$query = 'INSERT INTO games (name, p1, p2, accepted, comments) VALUES ("'.$gameName.'","'.$playerOne.'","'.$playerTwo.'",0,"'.$comments.'")';
$response = mysqli_query($con, $query);

if ($response) {
	print(mysqli_insert_id($con));
} else {
	print('failed');
}
?>
