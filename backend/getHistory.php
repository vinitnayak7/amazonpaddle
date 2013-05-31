<?php
$con = mysqli_connect("localhost","gamersso_paddle","paddle","gamersso_paddle");
if (mysqli_connect_errno($con))
{
  echo "Failed to connect to MySQL: " . mysqli_connect_error();
}

$userID = $_REQUEST['id'];

$query = 'SELECT * FROM games WHERE p2='.$userID.' AND winner>0 ORDER BY modified';
$response = mysqli_query($con, $query);

if ($response) {
	$totalRows = mysqli_num_rows($response);
	$count = 1;
	while ($row = mysqli_fetch_assoc($response)) {
		print($row['name'].','.$row['p1'].','.$row['id']."\n");
	}
} else {
	print('failure');
}
?>