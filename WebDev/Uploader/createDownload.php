<?
	include "check.php";
	include "connect.php";
	
	$name = $_POST["name"];
	$datei = basename($_FILES['file']['name']);
	move_uploaded_file($_FILES['file']['tmp_name'], "downloads/".$datei);
	$time = time();
	
	//$query = "INSERT INTO downloads (Name,Pfad,Datum) VALUES ('$name','$datei','$time')";
	//mysql_query($query) or die (mysql_error());
	
	//header("Location: downloads.php");
?>