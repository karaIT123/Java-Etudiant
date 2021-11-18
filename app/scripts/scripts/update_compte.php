<?php
    $host = "mysql:host=localhost;dbname=etudiant";
    $user = "root";
    $pass = "";
    
    $idetudiant = $_POST["idetudiant"];
    $nom = $_POST["nom"];
    $prenom = $_POST["prenom"];
    $email = $_POST["email"];
    $telephone = $_POST["telephone"];
    $username = $_POST["username"];
    $password = $_POST["password"];

    /*
    $idetudiant = 2;
    $nom = "Coulibaly";
    $prenom = "Bakary";
    $email = "coulibalybaraky864@gmail.com";
    $telephone = "4387220916";
    $username = "booba";
    $password = "booba";*/

    try
    {
        $conn = new PDO($host,$user,$pass);
        $conn->setAttribute(PDO::ATTR_ERRMODE,PDO::ERRMODE_EXCEPTION);

        $query = $conn->prepare("UPDATE etudiant SET nom = '$nom', 
                                                  prenom = '$prenom', 
                                                   email = '$email', 
                                               telephone = '$telephone', 
                                                    user = '$username', 
                                                password = '$password'
                                        WHERE etudiant.idetudiant = '$idetudiant'");
        $query->execute();
        echo "success";

        
       /*
        $query = $conn->prepare("SELECT * FROM etudiant WHERE user = ? and password = ?");
        $data = [$username,$password];
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

            $resultat = "$line->nom"." "."$line->prenom"; 
        }
        if($resultat != null){
            echo $result."/".$resultat;
        }else{
            echo $result;
        }
        */
    }
    catch(Exception $e)
    {
        $result = $e->getMessage();
    }
?>

