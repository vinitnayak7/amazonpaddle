<?php
$con = mysqli_connect("localhost","gamersso_paddle","paddle","gamersso_paddle");
if (mysqli_connect_errno($con))
{
  echo "Failed to connect to MySQL: " . mysqli_connect_error();
}

$id = $_REQUEST['id'];

$query = "SELECT username FROM users WHERE id=".$id;
$response = mysqli_query($con, $query);

if ($response) {
	$row = mysqli_fetch_assoc($response);
	if ($row['username']) {
		print($row['username']);
	} else {
		print("BAD");
	}
} else {
	print('BAD ID');
}
?>