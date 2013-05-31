<?php
$con = mysqli_connect("localhost","gamersso_paddle","paddle","gamersso_paddle");
if (mysqli_connect_errno($con))
{
  echo "Failed to connect to MySQL: " . mysqli_connect_error();
}

$id = $_REQUEST['id'];

$query = 'SELECT * FROM users WHERE id <>'.$id.' ORDER BY username';
$response = mysqli_query($con, $query);

if ($response) {
	while ($row = mysqli_fetch_assoc($response)) {
		print($row['username'].','.$row['email'].','.$row['id']."\n");
	}
} else {
	print('failure');
}

?>