<?php
   $con=mysqli_connect("211.213.95.132","deu_mobile","8804","mybookstore");

   if(mysqli_connect_errno($con)){
     echo "Failed to connect to MariaDB: " . mysqli_connect_error();
   }
   mysqli_set_charset($con, "utf8");

   // $email = 'minyoung@daum.com';
   // $password = '0212';
   // $name = "minyoung";
   $email = $_POST['email'];
   $password = $_POST['password'];
   $name = $_POST['name'];

   $join_status = 2;

   $sql="SELECT count(email)
         FROM user_info
         WHERE email='$email'";
   $result = mysqli_query($con,$sql);

   $row = mysqli_fetch_array($result);
   if($row[0]==1){
     $join_status = 2;
     // echo "This email already exists.<br />";
   }
   else{
     $sql="INSERT INTO user_info (email, password, name)
           VALUES ('$email', '$password','$name')";
     $result = mysqli_query($con,$sql);

     $row = mysqli_fetch_array($result);

     $join_status = 1;
     // echo "Join successful.<br />";
   }

////////////////////////////////////////////////

   header('Content-Type: application/json; charset=utf8');
   $json = json_encode(array("join_status"=>$join_status), JSON_PRETTY_PRINT+JSON_UNESCAPED_UNICODE);
   echo $json;

   mysqli_close($con);
?>
