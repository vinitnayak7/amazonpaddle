<?php
$con = mysqli_connect("localhost","gamersso_paddle","paddle","gamersso_paddle");
if (mysqli_connect_errno($con))
{
  echo "Failed to connect to MySQL: " . mysqli_connect_error();
}

if(!$_GET['username'] || !$_GET['password']){
	die("BAD");
}

$result = mysqli_query($con,"SELECT * FROM users WHERE `username` = '".$_GET['username']."' AND `password` = '".$_GET['password']."' LIMIT 1");
if(mysqli_num_rows($result) == 1){
	$info = mysqli_fetch_assoc($result);
	echo $info['id'];

}else
	echo("BAD");

?>