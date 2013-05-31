<?php
$con = mysqli_connect("localhost","gamersso_paddle","paddle","gamersso_paddle");
if (mysqli_connect_errno($con))
{
  echo "Failed to connect to MySQL: " . mysqli_connect_error();
}

$userID = $_REQUEST['id'];

$query = 'SELECT * FROM games WHERE (p2='.$userID.' OR p1='.$userID.') AND winner > 0 ORDER BY modified';
$response = mysqli_query($con, $query);

if ($response) {
	$totalRows = mysqli_num_rows($response);
	$count = 1;
	while ($row = mysqli_fetch_assoc($response)) {
	    print($row['name'].',');
	    $ch=curl_init('http://brandonnalls.com/paddle/getNameFromID.php?id='.$row['p2']);
	    curl_setopt($ch, CURLOPT_RETURNTRANSFER, true);
	    $opponent2=curl_exec($ch);
	    curl_close($ch);
	    $ch=curl_init('http://brandonnalls.com/paddle/getNameFromID.php?id='.$row['p1']);
	    curl_setopt($ch, CURLOPT_RETURNTRANSFER, true);
	    $opponent1=curl_exec($ch);
	    curl_close($ch);
	    if ($row['p1'] == $userID) {
	    	print($opponent2.',');
	    } else {
	    	print($opponent1.',');
	    }
	    if ($row['winner'] == $row['p1']) {
	    	print($opponent2.',');
	    } else {
	    	print($opponent1.',');
	    }
	    print($row['winnerScore'].','.$row['loserScore']."\n");
		
	}
} else {
	print('failure');
}
?>