<?
    $sql_host = "localhost";
	$sql_user = "user_db";
	$sql_pwd =  "user_pwd";
	$sql_db= "my_db" ;
	
	$verbindung = mysql_connect ($sql_host,$sql_user, $sql_pwd)
		or die ("keine Verbindung möglich.Benutzername oder Passwort sind falsch");
	$select = mysql_select_db($sql_db,$verbindung);
?>
