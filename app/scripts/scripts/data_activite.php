<?php
    $host = "mysql:host=localhost;dbname=etudiant";
    $user = "root";
    $pass = "";

    try
    {
        $conn = new PDO($host,$user,$pass);
        $conn->setAttribute(PDO::ATTR_ERRMODE,PDO::ERRMODE_EXCEPTION);

        $query = $conn->prepare("SELECT * FROM activite");
        $query->execute();

        $result = null;
        $line = null;
        $info = null;

        $count = $query->rowCount();

        while($line = $query->fetchObject())
        {
            $info .= "$line->titre@$line->description@$line->datedebut@$line->datefin@$line->prix@$line->idactivite/";
        }
        echo($info);
    }
    catch(Exception $e)
    {
        $result = $e->getMessage();
    }
?>

