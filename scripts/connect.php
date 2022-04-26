<?php 
    
function connect() {
	try
	{
$bdd = new PDO('mysql:host=127.0.0.1; dbname=senfacture', 'root', '');
return $bdd;
	}
	catch(Exception $e)
	{
		die('Erreur : '.$e->getMessage());
	}
}
 ?>