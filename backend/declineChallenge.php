<?php
$con = mysqli_connect("localhost","gamersso_paddle","paddle","gamersso_paddle");
if (mysqli_connect_errno($con))
{
  echo "Failed to connect to MySQL: " . mysqli_connect_error();
}

$gameID = $_REQUEST['gameID'];

$query = 'DELETE FROM games WHERE id='.$gameID;
$response = mysqli_query($con, $query);

if (mysqli_affected_rows($con)) {
	print('success');
} else {
	print('failure');
}

?>