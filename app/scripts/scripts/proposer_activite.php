<?php
    $host = "mysql:host=localhost;dbname=etudiant";
    $user = "root";
    $pass = "";
    
    $titre = $_POST["titre"];
    $description = $_POST["description"];
    $datedebut = $_POST["datedebut"];
    $datefin = $_POST["datefin"];
    $prix = $_POST["prix"];
    
    /*
    $titre = "Peche";
    $description = "Aller au lac";
    $datedebut = "2020/01/01";
    $datefin = "2020/03/03";
    $prix = "500";
*/
    try
    {
        $conn = new PDO($host,$user,$pass);
        $conn->setAttribute(PDO::ATTR_ERRMODE,PDO::ERRMODE_EXCEPTION);

        $query = $conn->prepare("INSERT INTO activite (titre, description, datedebut, datefin, prix) values (:titre, :description, :datedebut, :datefin, :prix)");
        //$data = [$nom,$prenom,$email,$telephone,$username,$password];
        $query->execute([
            'titre' => $titre,
            'description' => $description,
            'datedebut' => $datedebut,
            'datefin' => $datefin,
            'prix' => $prix
        ]);

       
        $query = $conn->prepare("SELECT * FROM activite WHERE titre = ?");
        $data = [$titre];
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

        while($line = $query->fetchObject())
        {
            $resultat = "$line->titre"; 
        }
        if($resultat != null){
            echo $result."/".$resultat;
        }else{
            echo $result;
        }
    }
    catch(Exception $e)
    {
        $result = $e->getMessage();
    }
?>

