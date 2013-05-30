<?php
$con = mysqli_connect("localhost","gamersso_paddle","paddle","gamersso_paddle");
if (mysqli_connect_errno($con)) {
  echo "Failed to connect to MySQL: " . mysqli_connect_error();
}

$gameName = $_REQUEST['gameName'];
$playerOne = $_REQUEST['playerOne'];
$playerTwo = $_REQUEST['playerTwo'];
$comments = $_REQUEST['comments'];

$query = 'INSERT INTO games (p1, p2, name, accepted, comments) VALUES ('.$gameName.','.$playerOne.','.$playerTwo.',0'.$comments.')';
$response = mysql_query($query);

if ($response) {
	print('{"result":"success"}');
} else {
	print('{"result":"failed"}');
}
?>
