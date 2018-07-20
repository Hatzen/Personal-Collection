<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>Unbenanntes Dokument</title>
</head>
<?
  include "../../connect.php";
  if($_POST[add])
  {
	  $id = mysql_query("select max(id) from mFragen");
	  echo $id;
	  $query = "INSERT INTO mFragen (id,frage,schwere) VALUES (".$id.",'".$_POST[frage]."',".$_POST[schwere].")";
  }
  
?>
<form action="frage.php" method="post">
<h1> Frage </h1>
<input name="frage" type="text" />
<h2> Schwierigkeit: 
<input name="schwere" type="text" /></h2>
<h2> Richtige Antwort: 
<input name="richtig" type="text" /></h2>
<h2> Falsche Antworten: </h2>
<?
  for($i = 0 ; $i < $_POST["mehr"] || $i < 3; $i++)
  {
	  echo '<input name="falsch'.$i.'" type="text" /><br>';
  }
?>
<h3> Mehr Falsche Antworten: 
<input name="anzahl" type="text" /><input value="mehr" name="mehr" type="submit" /></h3>
<input name="add" value="Hinzuf&uuml;gen" type="submit" />
</form>

<body>
</body>
</html>
