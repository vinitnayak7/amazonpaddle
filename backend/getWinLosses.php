<?php
$con = mysqli_connect("localhost","gamersso_paddle","paddle","gamersso_paddle");
if (mysqli_connect_errno($con))
{
  echo "Failed to connect to MySQL: " . mysqli_connect_error();
}

if(!$_GET['id']){
	die("BAD");
}

$result = mysqli_query($con,"SELECT `id` FROM games WHERE `winner` = '".$_GET['id']."'");
echo(mysqli_num_rows($result).":");
$result = mysqli_query($con,"SELECT `id` FROM games WHERE `loser` = '".$_GET['id']."'");
echo(mysqli_num_rows($result));



?>