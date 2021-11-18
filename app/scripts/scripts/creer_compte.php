<?php
    $host = "mysql:host=localhost;dbname=etudiant";
    $user = "root";
    $pass = "";
    
    $nom = $_POST["nom"];
    $prenom = $_POST["prenom"];
    $email = $_POST["email"];
    $telephone = $_POST["telephone"];
    $username = $_POST["username"];
    $password = $_POST["password"];

    /*$nom = "Coulibaly";
    $prenom = "Bakary";
    $email = "coulibalybaraky864@gmail.com";
    $telephone = "4387220916";
    $username = "booba";
    $password = "booba";*/

    try
    {
        $conn = new PDO($host,$user,$pass);
        $conn->setAttribute(PDO::ATTR_ERRMODE,PDO::ERRMODE_EXCEPTION);

        $query = $conn->prepare("INSERT INTO etudiant (nom, prenom, email, telephone, user, password ) values (:nom, :prenom, :email, :telephone, :user, :password)");
        //$data = [$nom,$prenom,$email,$telephone,$username,$password];
        $query->execute([
            'nom' => $nom,
            'prenom' => $prenom,
            'email' => $email,
            'telephone' => $telephone,
            'user' => $username,
            'password' => $password
        ]);

       
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
    }
    catch(Exception $e)
    {
        $result = $e->getMessage();
    }
?>

