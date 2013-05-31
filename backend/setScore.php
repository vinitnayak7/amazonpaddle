<?php
$con = mysqli_connect("localhost","gamersso_paddle","paddle","gamersso_paddle");
if (mysqli_connect_errno($con))
{
  echo "Failed to connect to MySQL: " . mysqli_connect_error();
}

if(!$_GET['gameID'] || !$_GET['winner'] || !$_GET['loser'] || !$_GET['winnerScore'] || !$_GET['loserScore'])
	die("BAD");


$gameID = $_REQUEST['gameID'];

$query = "UPDATE games SET winner=".$_GET['winner'].", loser=".$_GET['loser'].", winnerScore=".$_GET['winnerScore'].", loserScore=".$_GET['loserScore']." WHERE id=".$_GET['gameID'];
$response = mysqli_query($con, $query);
if (mysqli_affected_rows($con)) {
	print('success');
} else {
	print('failure');
}

?>