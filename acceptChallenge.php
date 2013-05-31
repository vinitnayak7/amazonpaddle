<?php
$con = mysqli_connect("localhost","gamersso_paddle","paddle","gamersso_paddle");
if (mysqli_connect_errno($con))
{
  echo "Failed to connect to MySQL: " . mysqli_connect_error();
}

$gameID = $_REQUEST['gameID'];

$query = 'UPDATE games SET accepted=1 WHERE id='.$gameID;
$response = mysqli_query($con, $query);

if ($response) {
	print('success');
} else {
	print('failure');
}

?>