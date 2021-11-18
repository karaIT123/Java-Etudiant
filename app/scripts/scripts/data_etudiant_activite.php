<?php
    $host = "mysql:host=localhost;dbname=etudiant";
    $user = "root";
    $pass = "";

    try
    {
        $conn = new PDO($host,$user,$pass);
        $conn->setAttribute(PDO::ATTR_ERRMODE,PDO::ERRMODE_EXCEPTION);

        //$query = $conn->prepare("SELECT * FROM etudiant");
        $query = $conn->prepare("SELECT 
        etudiant_activite.idactivite ,activite.titre,etudiant.idetudiant,etudiant.prenom,etudiant.nom 
        FROM etudiant_activite,activite,etudiant 
        WHERE etudiant_activite.idactivite = activite.idactivite 
        AND etudiant.idetudiant = etudiant_activite.idetudiant
        AND etudiant.idetudiant = 1;");
        
        $query->execute();

        $result = null;
        $line = null;
        $info = null;

        $count = $query->rowCount();

        while($line = $query->fetchObject())
        {
            $info .= "$line->titre/";
            //$info .= "$line->idetudiant#$line->nom#$line->prenom#$line->email#$line->telephone#$line->user#$line->password/";
        }
        $data1 = "Karamoko Coulibaly/$info";

        ############################################################
        $query = $conn->prepare("SELECT 
        etudiant_activite.idactivite ,activite.titre,etudiant.idetudiant,etudiant.prenom,etudiant.nom 
        FROM etudiant_activite,activite,etudiant 
        WHERE etudiant_activite.idactivite = activite.idactivite 
        AND etudiant.idetudiant = etudiant_activite.idetudiant
        AND etudiant.idetudiant = 2;");
        
        $query->execute();

        $result = null;
        $line = null;
        $info = null;

        $count = $query->rowCount();

        while($line = $query->fetchObject())
        {
            $info .= "$line->titre/";
            //$info .= "$line->idetudiant#$line->nom#$line->prenom#$line->email#$line->telephone#$line->user#$line->password/";
        }
        $data2 = "Bakary Coulibaly/$info";
        ############################################################
        $query = $conn->prepare("SELECT 
        etudiant_activite.idactivite ,activite.titre,etudiant.idetudiant,etudiant.prenom,etudiant.nom 
        FROM etudiant_activite,activite,etudiant 
        WHERE etudiant_activite.idactivite = activite.idactivite 
        AND etudiant.idetudiant = etudiant_activite.idetudiant
        AND etudiant.idetudiant = 3;");
        
        $query->execute();

        $result = null;
        $line = null;
        $info = null;

        $count = $query->rowCount();

        while($line = $query->fetchObject())
        {
            $info .= "$line->titre/";
            //$info .= "$line->idetudiant#$line->nom#$line->prenom#$line->email#$line->telephone#$line->user#$line->password/";
        }
        $data3= "Moh Dembele/$info";

        echo "$data1@$data2@$data3";
    }
    catch(Exception $e)
    {
        $result = $e->getMessage();
    }
?>

