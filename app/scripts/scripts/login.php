<?php
    $host = "mysql:host=localhost;dbname=etudiant";
    $user = "root";
    $pass = "";
    
    $username = $_POST["username"];
    $password = $_POST["pass"];

    try
    {
        $conn = new PDO($host,$user,$pass);
        $conn->setAttribute(PDO::ATTR_ERRMODE,PDO::ERRMODE_EXCEPTION);

        $query = $conn->prepare("SELECT * FROM etudiant WHERE user = ? and password = ?");
        #$query->execute();
        $data = [$username,$password];
        $query->execute($data);

        $result = null;
        $line = null;
        $info = null;

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
            //$resultat = "$line->idetudiant"; 
            $info = "$line->idetudiant $line->nom $line->prenom $line->email $line->telephone $line->user $line->password";

        }
        if($info != null){
            echo $result."/".$info;
        }else{
            echo $result;
        }
    }
    catch(Exception $e)
    {
        $result = $e->getMessage();
    }

    //echo $result;
?>

