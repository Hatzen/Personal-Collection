<?
	session_start();
	if( empty($_SESSION['logged']) || !$_SESSION['logged'])
	{
		header("Location: login.php");
		exit;
	}
	/*if (!isset($_SERVER['HTTPS']))
	{
		header("Location: https://" . $_SERVER['HTTP_HOST'] . $_SERVER['REQUEST_URI'] . $_SERVER['QUERY_STRING']);
	}*/
?>
