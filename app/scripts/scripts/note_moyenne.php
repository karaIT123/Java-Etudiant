<?php
    $host = "mysql:host=localhost;dbname=etudiant";
    $user = "root";
    $pass = "";

    try
    {
        $conn = new PDO($host,$user,$pass);
        $conn->setAttribute(PDO::ATTR_ERRMODE,PDO::ERRMODE_EXCEPTION);

        $query = $conn->prepare("SELECT avg(note) AS moyenne,
        etudiant_activite.idactivite,titre FROM etudiant_activite,activite 
        WHERE etudiant_activite.idactivite = activite.idactivite GROUP BY etudiant_activite.idactivite");
        $query->execute();

        $result = null;
        $line = null;
        $info = null;

        $count = $query->rowCount();

        while($line = $query->fetchObject())
        {
            $x = $line->moyenne;
            $y = round($x,2,PHP_ROUND_HALF_UP);
            $info .= "$line->titre@$y@$line->idactivite/";
        }
        echo($info);
    }
    catch(Exception $e)
    {
        $result = $e->getMessage();
    }
?>

