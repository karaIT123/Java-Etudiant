<?php
    $host = "mysql:host=localhost;dbname=etudiant";
    $user = "root";
    $pass = "";

    $note = $_POST["note"];
    $idetudiant = $_POST["idetudiant"];
    $idactivite = $_POST["idactivite"];

    /*$note = "25";
    $idetudiant = "1";
    $idactivite = "1";*/


    try
    {
        $conn = new PDO($host,$user,$pass);
        $conn->setAttribute(PDO::ATTR_ERRMODE,PDO::ERRMODE_EXCEPTION);

        #$query = $conn->prepare("SELECT * FROM activite WHERE idetudiant = $id");
        /*$query = $conn->prepare("select etudiant_activite.idactivite 
        ,activite.titre,etudiant.idetudiant,etudiant.prenom,etudiant.nom 
        from etudiant_activite,activite,etudiant where etudiant_activite.idactivite = 
        activite.idactivite and etudiant.idetudiant = etudiant_activite.idetudiant and etudiant.idetudiant = $id");*/
        $query = $conn->prepare("UPDATE etudiant_activite SET note = $note 
            WHERE idetudiant = $idetudiant and idactivite = $idactivite");
        $query->execute();

        $result = null;
        $line = null;
        $info = null;

        /*$count = $query->rowCount();

        while($line = $query->fetchObject())
        {
            $info .= "$line->titre@$line->idactivite/";
        }*/
        echo($note."/".$idactivite."/".$idetudiant);
    }
    catch(Exception $e)
    {
        $result = $e->getMessage();
    }
?>

