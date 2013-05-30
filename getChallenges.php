<?php
$con = mysqli_connect("localhost","gamersso_paddle","paddle","gamersso_paddle");
if (mysqli_connect_errno($con)) {
  echo "Failed to connect to MySQL: " . mysqli_connect_error();
}

$userID = $_REQUEST['id'];

$query = 'SELECT * FROM games WHERE p2='.$userID;
$response = mysql_query($query);

if ($response) {
	$totalRows = mysql_num_rows($response);
	$count = 1;
	// print('{"result":"failure","challenges":[');
	while ($row = mysql_fetch_assoc($response)) {
		// if ($count < $totalRows) {
		// 	print(json_encode($row).',');
		// } else {
  //           print(json_encode($row));
  //       }
  //       print(']}');
		print($row['name'].','.$row['p1'].','.$row['id'].'\n');
	}
} else {
	print('failure');
}
?>