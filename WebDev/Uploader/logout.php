<?
	session_start();
	session_unregister("logged");
    header('Location: login.php');
?>
