<?php
$con = mysqli_connect("localhost","gamersso_paddle","paddle","gamersso_paddle");
if (mysqli_connect_errno($con))
{
  echo "Failed to connect to MySQL: " . mysqli_connect_error();
}

if(!$_GET['p1'] || !$_GET['p2']){
	die("BAD");
}

$result = mysqli_query($con,"SELECT `name`,`winner`,`loser`,`winnerScore`,`loserScore` FROM games WHERE (`p1`='".$_GET['p1']."' OR `p2`='".$_GET['p1']."') AND (`p1`='".$_GET['p2']."' OR `p2`='".$_GET['p2']."') AND (`winnerScore` > 0) ORDER BY `modified` DESC");
while($row = mysqli_fetch_assoc($result)){
	echo($row['name'].','.$row['winner'].','.$row['loser']."\n");
}

?>