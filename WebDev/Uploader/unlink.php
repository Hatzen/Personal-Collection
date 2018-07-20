<?
	include "check.php";
	include "connect.php";
	
	$id = $_GET['id'];
	$name = mysql_query("SELECT * FROM downloads where NR = ".$id);
	$pfad = mysql_fetch_object($name);
	unlink("downloads/".$pfad->Pfad);
	mysql_query("DELETE FROM downloads where NR = ".$id);
	
	header("Location: downloads.php");
?>
