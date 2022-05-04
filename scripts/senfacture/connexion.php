<?php 
   
	include 'connect.php';
    $bdd = connect();
 	$email=$_GET['email'];
 	$password =$_GET['password'];

 	$sql = 'SELECT * FROM user WHERE email = "'.$email.'" AND password="'.$password.'"';  
	$req = $bdd->query($sql);
	  
    if ($data = $req->fetch())  
		echo json_encode(array('status' => "OK"));
	else    
		echo json_encode(array('status' => "KO"));
		
		
 ?>