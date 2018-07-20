<?
	include "head.php";
	
	$handle = opendir("Schule");
	for($i = 0; $datei = readdir($handle) ; $i++)
	{
		if(!($datei == "." || $datei == ".." ) && strrpos($datei,".") === false)
		{
			if($i % 2 == 0)
				$color = "dark";
			else
				$color = "bright";
			
				echo '<div class="list '.$color.'"><a class="list '.$color.'" href="/Schule/'.$datei.'">'.$datei.'</a></div> ';
		}
	}
	
	include "foot.php";
?>
