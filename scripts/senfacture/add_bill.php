<?php 
	include 'connect.php';
 $bdd = connect();
 
 $intitule=$_GET['intitule'];
 $entreprise=$_GET['entreprise'];
 $montant=$_GET['montant'];
 $numero=$_GET['numero'];
 $date=$_GET['date'];
 $id=$_GET['id'];
 
 $sql = "INSERT into facture (intitule, entreprise, montant, numero, date_echeance, is_paid, id_user) VALUES ('$intitule','$entreprise','$montant', '$numero', '$date', 0, '$id')";   
 $req = $bdd->exec($sql);
      
		if ($req!='') 
				echo json_encode(array('status' => "OK"));
		else 
				echo json_encode(array('status' => "KO"));
 ?>