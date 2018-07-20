<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<link rel="stylesheet" type="text/css" href="login.css"/>
    <title>Login</title>
</head>
<body>
<?php
	if ($_SERVER['REQUEST_METHOD'] == 'POST') 
	{
		session_start();
	
		$benutzername = $_POST['user'];
		$passwort = $_POST['pass'];
		if($_POST[cookie] == "cookie")
		{
			setcookie("user",$benutzername,time()+(3600*24*5));
			setcookie("pass",$passwort,time()+(3600*24*5));
		}
		else
		{
			setcookie("user","",time() - 3600);
			setcookie("pass","",time() - 3600);
		}
		
		if ($benutzername == 'kai' && $passwort == 'auto') 
		{
			$_SESSION['logged'] = true;
	
			if ($_SERVER['SERVER_PROTOCOL'] == 'HTTP/1.1') 
			{
				if (php_sapi_name() == 'cgi') 
				{
					header('Status: 303 See Other');
				}
			}
			header('Location: index.php');
			exit;
		}
	}
?>
<div id="login">
  <h1> Login </h1>
  <form action="login.php" method="post">
      <table id="table">
            <tr>
            <td>
                <p>Name:</p>
            </td>
            <td>
                <input value="<? echo $_COOKIE["user"]; ?>" type="text" name="user" />
            </td>
            </tr>
            <tr>
            <td>
                <p>Passwort:</p>
            </td>
            <td>
                <input value="<? echo $_COOKIE["pass"]; ?>" type="password" name="pass" />
            </td>
            </tr>
            <tr>
            <td>
            	<p><input <? if($_COOKIE["user"]) echo "checked='checked'";  ?> type="checkbox" value="cookie" name="cookie" /> Cookie ablegen</p>
            </td>
            </tr>
        </table>
        <input value="Login" id="button" type="submit" />
  </form>
  <?
  if ($_POST && !($benutzername == 'kai' && $passwort == 'auto')) 
  {
	  echo '<p style="color:#990000;">Anmeldung erfolglos, versuch es noch einmal!</p>';
  }
  ?>
</div>
</body>
</html>
