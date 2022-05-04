<?php 
	include 'connect.php';
 $bdd = connect();
 
 $first_name=$_GET['first_name'];
 $last_name=$_GET['last_name'];
 $email=$_GET['email'];
 $password=$_GET['password'];
 
 $sql = "INSERT into user (nom, prenom, email, password) VALUES ('$first_name','$last_name', '$email', '$password')";   
 $req = $bdd->exec($sql);
      
		if ($req!='') 
				echo json_encode(array('status' => "OK"));
		else 
				echo json_encode(array('status' => "KO"));
 ?>