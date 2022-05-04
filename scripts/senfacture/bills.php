<?php 

 $base = mysqli_connect("localhost", "root", "", "senfacture") or die("error !");

	  $id=$_GET['id'];
	  $chaine='';
      $sql = 'SELECT intitule,entreprise,montant,numero,date_echeance,is_paid FROM facture WHERE id_user="'.$id.'"';   	    
	  $req = mysqli_query($base,$sql);
      $emparray = array();
	 
	  while ($row = mysqli_fetch_assoc($req)) {
		$emparray[] = $row;
	  }		
	echo json_encode(array('bills' => $emparray));
 ?> 
 
 
 
 