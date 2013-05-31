<?php
$con = mysqli_connect("localhost","gamersso_paddle","paddle","gamersso_paddle");
if (mysqli_connect_errno($con))
{
  echo "Failed to connect to MySQL: " . mysqli_connect_error();
}

$userID = $_REQUEST['id'];

$query = 'SELECT * FROM games WHERE (p2='.$userID.' OR p1='.$userID.') AND winner=0 ORDER BY modified';
$response = mysqli_query($con, $query);

if ($response) {
	$totalRows = mysqli_num_rows($response);
	$count = 1;
	while ($row = mysqli_fetch_assoc($response)) {
		$ch=curl_init('http://brandonnalls.com/paddle/getNameFromID.php?id='.$row['p1']);
	    curl_setopt($ch, CURLOPT_RETURNTRANSFER, true);
	    $result1=curl_exec($ch);
	    curl_close($ch);
	    $ch=curl_init('http://brandonnalls.com/paddle/getNameFromID.php?id='.$row['p2']);
	    curl_setopt($ch, CURLOPT_RETURNTRANSFER, true);
	    $result2=curl_exec($ch);
	    curl_close($ch);
	    print($row['id'].','.$result1.','.$result2.',');
	    if ($row['p2'] == $userID && $row['accepted'] == 0) {
			print('1'."\n");
		} else {
			print('0'."\n");
		}
	}
} else {
	print('failure');
}
?>