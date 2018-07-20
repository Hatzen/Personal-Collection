<?
	include "head.php";
	include "connect.php";
	
	$query = "SELECT * FROM downloads ";
	if($_POST)
	{
		if($_POST[searchText] )
		{
			$query = "SELECT * FROM downloads WHERE Name LIKE '%".$_POST[searchText]."%' ";
		}
		if($_POST[name] == "Name")
		{
			$query .= "ORDER BY name ASC";
		}
		if($_POST[datum] == "Datum")
		{
			$query .= "ORDER BY Datum DESC";
		}
	}
	$result = mysql_query($query) or die (mysql_error()."<br><br>".$query);
?>
	<div style="margin-left: -10px; width: 760px; padding: 20px; ">
    	<?
			if($_POST && $_POST[hinzu])
			{
		?>
            <form enctype="multipart/form-data" action="createDownload.php" method="post">
                <input class="abstand" name="name" title="name" value="" type="text" />
                <input style="float:right;" name="file" title="file" type="file" />
                <br>
                <input style="padding: 5px; margin-top:10px;" name="submit" value="Hochladen" title="submit" type="submit" />
            </form>
        <?
			}
			else if(($_POST && $_POST[suchen]) || $_POST[searchText])
			{
		?>
            <form action="downloads.php" method="post">
            	<input class="abstand" value="<? echo $_POST[searchText] ?>" type="text" name="searchText"  />
                <input style="padding: 5px; margin-left:30px;" name="search" value="Suchen.." type="submit" />
            </form>
        <?
				$num_rows = mysql_num_rows($result);
				if($num_rows == 0)
				{
					echo "<p>Die Suche ergab keine Treffer </p>";
				}
				else if($num_rows == 1)
				{
					echo "<p>Die Suche ergab nur einen Treffer </p>";
				}
				else if($_POST[searchText])
				{
					echo "<p>".$num_rows." Ergebnisse wurden gefunden </p>";
				}
			}
		?>
        <div style="float:left; display:block;"><p style="margin-top: 20px; font-size:13px;"> Aktionen: 
            <form method="post" action="downloads.php">
            	<?
				if($_POST && ($_POST[hinzu] || $_POST[suchen]))
               		echo '<input style="padding: 5px; margin-top:10px;" name="zuruck" value="zur Übersicht" type="submit" />';
				if(!$_POST[hinzu])
               		echo '<input style="padding: 5px; margin-top:10px;" name="hinzu" value="Hinzuf&uuml;gen" type="submit" />';
				if(!$_POST[suchen])
               		echo '<input style="padding: 5px; margin-top:10px;" name="suchen" value="Suchen" type="submit" />';
				?>
                <input type="hidden" name="datum" value="<? echo $_POST[datum]; ?>">
                <input type="hidden" name="name" value="<? echo $_POST[name]; ?>">
            </form>
        </p></div>
        <div style="margin-left: 50px; float:left; display:block;"><p style="margin-top: 20px; font-size:13px;"> Sortieren: 
            <form method="post" action="downloads.php">
            	<?
				if(!$_POST[datum])
               		echo '<input style="padding: 5px; margin-top:10px;" name="datum" value="Datum" type="submit" />';
				if(!$_POST[name])
               		echo '<input style="padding: 5px; margin-top:10px;" name="name" value="Name" type="submit" />';
				if($_POST &&( $_POST[datum] || $_POST[name]))
               		echo '<input style="padding: 5px; margin-top:10px;" name="standard" value="Standard" type="submit" />';
				?>
                <input type="hidden" name="searchText" value="<? echo $_POST[searchText]; ?>">
            </form>
        </p></div>
	</div>
    <table id="overview">
    <tr style="border-bottom: 2px solid #666; margin: 4px; ">
    	<td><p><? if($_POST[name] ) echo "<b>"; ?> Name </p><? if($_POST[name] ) echo "</b>"; ?></td>
        <td><p> Datei</p></td>
        <td><? if($_POST[datum] ) echo "<b>"; ?><p> Datum </p><? if($_POST[datum] ) echo "</b>"; ?></td>
        <td><p> Aktion </p></td>
    </tr>
<?
	for( $i = 0; $row = mysql_fetch_object($result) ; $i++)
	{
		if($i % 2 == 0)
			$color = "dark";
		else
			$color = "bright";
		echo '<tr class="list '.$color.'">
			<td><a class="list '.$color.'" href="/downloads/'.$row->Pfad.'">'.$row->Name.'</a></td>
			<td style="font-size: 13px;">'.$row->Pfad.'</td>
			<td style="font-size: 13px;">'.date("d.m.Y",$row->Datum).'</td>
			<td><a style="margin-right: 20px; color: #f00; text-decoration:none; float:right;" href="unlink.php?id='.$row->Nr.'">X</a></td></tr>';
	}
?>
	</table>
<?
	include "foot.php";
?>
