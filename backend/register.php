<?php
$con = mysqli_connect("localhost","gamersso_paddle","paddle","gamersso_paddle");
if (mysqli_connect_errno($con))
{
  echo "Failed to connect to MySQL: " . mysqli_connect_error();
}

if(!$_GET['username'] || !$_GET['password'] || !$_GET['email']){
	die("BAD");
}

$result = mysqli_query($con,"SELECT * FROM users WHERE `username` = '".$_GET['username']."' OR `email` = '".$_GET['email']."' LIMIT 1");
if(mysqli_num_rows($result) == 1){
	die("BAD");
}


$result2 = mysqli_query($con,"INSERT INTO `users` (`username`, `email`, `password`) VALUES ('".$_GET['username']."','".$_GET['email']."','".$_GET['password']."')");
if(mysqli_affected_rows($con) == 1){
	echo(mysqli_insert_id($con));
}else
	echo("BAD");

?>