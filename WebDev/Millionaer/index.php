<html>
<head>
<style type="text/css">
body {
 color:#ffffff; 
 }
</style>
</head>
<body>
<center><div style="border:2px solid #000099; background-image:url(http://www.frankundtina.de/assets/images/Titelbild4.jpg); background-position: bottom; background-repeat: none; padding: 20px; maring: 50px auto; width: 700px; height: 600px; ">
<?
  include "connect.php";
  if(($_POST[richtig] == "eins" && $_POST[eins]) ||
     ($_POST[richtig] == "zwei" && $_POST[zwei]) ||
	 ($_POST[richtig] == "drei" && $_POST[drei]) ||
	 ($_POST[richtig] == "vier" && $_POST[vier]))
    echo "<div style='border: solid #009900 1px; background: #00dd00; padding: 10px; width: 100%-30;'> Richtig<br> </div>";
  else if($_POST[eins] || $_POST[zwei] ||$_POST[drei] ||$_POST[vier])
  {
    echo "<div style='border: solid #dd0000 1px; background: #dd0000; padding: 10px; width: 100%-30;'>Falsch<br><div>";
    unset($_POST[position]);
  }
  if(isset($_POST[position]))
  	$runde = $_POST[position]+1;
  else
	$runde = 0;
	
  $schwer = 1;
  if($runde > 5 && $runde < 11)
    $schwer = 2;
  else if($runde > 10)
    $schwer = 3;
    
  if($runde == 15)
  {
  	echo "Sie sind Millionär!";
  	echo "<form method='post' action='index.php'> ";
  	echo "<input style='padding: 5px; margin: 10px; width:150px;' type='submit' >";
  	echo '<input type="hidden" name="position" value="-1">';
  	echo "</form>";
  	die();
  }
  
	
  $ergebnis = mysql_query("SELECT * FROM mFragen WHERE schwere = '$schwer' ORDER BY rand()") or die(mysql_error());
  $row = mysql_fetch_array($ergebnis);
  echo $row[1];
  $abfrage = "SELECT  * FROM mAntworten WHERE id = ".$row[0]." AND richtig != 1 ORDER BY RAND()";
  $finde = mysql_query($abfrage) or die(mysql_error());
  
  $antworten[0] = mysql_fetch_array($finde);
  $antworten[1] = mysql_fetch_array($finde);
  $antworten[2] = mysql_fetch_array($finde);
  $finde = mysql_query("SELECT  * FROM mAntworten WHERE id = '$row[0]' AND richtig = 1") or die(mysql_error());
  $antworten[3] = mysql_fetch_array($finde);
  shuffle($antworten)
?>
<form method="post" action="index.php">
<table>
<?
	$antwort = $antworten[0];
	if($antwort[2] == 1) 
	  $richtig = "eins";
    echo "<tr><td><input name='eins' style='padding: 5px; margin: 10px; width:150px;' type='submit' value='".$antwort[1]."'></td>";
	$antwort = $antworten[1];
	if($antwort[2] == 1)
	  $richtig = "zwei";
    echo "<td><input name='zwei' style='padding: 5px; margin: 10px; width:150px;'  type='submit' value='".$antwort[1]."'></td></tr>";
	$antwort = $antworten[2];
	if($antwort[2] == 1)
	  $richtig = "drei";
    echo "<tr><td><input name='drei' style='padding: 5px; margin: 10px; width:150px;'  type='submit' value='".$antwort[1]."'></td>";
	$antwort = $antworten[3];
	if($antwort[2] == 1)
	  $richtig = "vier";
    echo "<td><input name='vier' style='padding: 5px; margin: 10px; width:150px;'  type='submit' value='".$antwort[1]."'></td></tr>";
	
    echo '<input type="hidden" name="richtig" value="'.$richtig.'">';
    
  echo '<input type="hidden" name="position" value="'.$runde.'">';
?>
</table>
</form>
<table style="float: right; position: relative; top: -125px;">
  <?
  $preis[0] = "50";
  $preis[1] = "100";
  $preis[2] = "200";
  $preis[3] = "300";
  $preis[4] = "500";
  $preis[5] = "1000";
  $preis[6] = "2000";
  $preis[7] = "4000";
  $preis[8] = "8000";
  $preis[9] = "16000";
  $preis[10] = "32000";
  $preis[11] = "64000";
  $preis[12] = "125000";
  $preis[13] = "500000";
  $preis[14] = "1000000";
  	for($i = 0 ; $i < count($preis); $i++)
	{
		?>
        <tr>
        <td>
         <?
		 if($i == $runde)
		 {
			 echo "<font color='#0066CC'>".$preis[$i]."</font>";
		 }
		 else
		 {
			echo $preis[$i]; 
		 }
		 ?>
        </td>
        </tr>
        <?
	}
  ?>
</table>
</div></center>
</body>
</html>
