<?php 
	include 'connect.php';
 $bdd = connect();
 
 $first_name=$_GET['first_name'];
 $last_name=$_GET['last_name'];
 $formation=$_GET['formation'];
 $degrees=$_GET['degrees'];
 
 $sql = "INSERT into inscription (first_name,last_name, formation, degrees) VALUES ('$first_name','$last_name', '$formation', '$degrees')";   
 $req = $bdd->exec($sql);
      
		if ($req!='') 
				echo json_encode(array('status' => "OK"));
		else 
				echo json_encode(array('status' => "KO"));
 ?>