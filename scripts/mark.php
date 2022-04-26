<?php 

 $base = mysqli_connect("localhost", "root", "", "db_school") or die("error !");

	  $login=$_GET['login'];
	  $chaine='';
      $sql = 'SELECT course,grade FROM mark WHERE login="'.$login.'"';   	    
	  $req = mysqli_query($base,$sql);
      $emparray = array();
	 
	  while ($row = mysqli_fetch_assoc($req)) {
		$emparray[] = $row;
	  }		
	echo json_encode(array('marks' => $emparray));
 ?> 
 
 
 
 