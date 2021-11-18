<?php
    $host = "mysql:host=localhost;dbname=etudiant";
    $user = "root";
    $pass = "";

    $id = $_POST["id"];
    try
    {
        $conn = new PDO($host,$user,$pass);
        $conn->setAttribute(PDO::ATTR_ERRMODE,PDO::ERRMODE_EXCEPTION);

        #$query = $conn->prepare("SELECT * FROM activite WHERE idetudiant = $id");
        $query = $conn->prepare("select etudiant_activite.idactivite, etudiant_activite.note 
        ,activite.titre,etudiant.idetudiant,etudiant.prenom,etudiant.nom 
        from etudiant_activite,activite,etudiant where etudiant_activite.idactivite = 
        activite.idactivite and etudiant.idetudiant = etudiant_activite.idetudiant and etudiant.idetudiant = $id");
        $query->execute();

        $result = null;
        $line = null;
        $info = null;

        $count = $query->rowCount();

        while($line = $query->fetchObject())
        {
            $info .= "$line->titre@$line->idactivite@$line->note/";
        }
        echo($info);
    }
    catch(Exception $e)
    {
        $result = $e->getMessage();
    }
?>

