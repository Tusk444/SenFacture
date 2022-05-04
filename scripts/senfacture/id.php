<?php 
   
	include 'connect.php';
    $bdd = connect();
 	$email=$_GET['email'];

 	$sql = 'SELECT id FROM user WHERE email = "'.$email.'"';  
	$req = $bdd->query($sql);
	  
    if ($data = $req->fetch())  
		echo json_encode(array('status' => $data[0]));
	else    
		echo json_encode(array('status' => "0"));
		
		
 ?>