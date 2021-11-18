<?php
    $host = "mysql:host=localhost;dbname=etudiant";
    $user = "root";
    $pass = "";
    
    $idetudiant = $_POST["idetudiant"];
    $idactivite = $_POST["idactivite"];
    /*
    $idetudiant = $_POST["idetudiant"];
    $idactivite = $_POST["idactivite"];
    */
    try
    {
        $conn = new PDO($host,$user,$pass);
        $conn->setAttribute(PDO::ATTR_ERRMODE,PDO::ERRMODE_EXCEPTION);

        $query = $conn->prepare("INSERT INTO etudiant_activite (idetudiant, idactivite) values (:idetudiant, :idactivite)");
        //$data = [$nom,$prenom,$email,$telephone,$username,$password];
        $query->execute([
            'idetudiant' => $idetudiant,
            'idactivite' => $idactivite
        ]);

       
        $query = $conn->prepare("SELECT * FROM etudiant_activite WHERE idetudiant = ? AND idactivite = ?");
        $data = [$idetudiant,$idactivite];
        $query->execute($data);
        

        $result = null;
        $line = null;

        $count = $query->rowCount();

        if($count > 0){
            $result = "success";
        }
        else
        {
            $result = "error";
        }

        /*while($line = $query->fetchObject())
        {
            $resultat = "$line->titre"; 
        }
        if($resultat != null){
            echo $result."/".$resultat;
        }else{
            echo $result;
        }*/
        echo $result;
    }
    catch(Exception $e)
    {
        $result = $e->getMessage();
    }
?>

