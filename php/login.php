<?php
   $con=mysqli_connect("211.213.95.132","deu_mobile","8804","mybookstore");

   if(mysqli_connect_errno($con)){
     echo "Failed to connect to MariaDB: " . mysqli_connect_error();
   }
   mysqli_set_charset($con, "utf8");

   // $email = 'yong@naver.com';
   // $password = '1234';
   $email = $_POST['email'];
   $password = $_POST['password'];

   $login_status = 2;

   $sql="SELECT count(email)
         FROM user_info
         WHERE email='$email'";
   $result = mysqli_query($con,$sql);

   $row = mysqli_fetch_array($result);
   // echo "email_result : " . $row[0] . "<br />";

   if($row[0]==0){
     $login_status = 2;
     // echo "Email not found.<br />";
   }
   else{
     $sql="SELECT count(email)
           FROM user_info
           WHERE email='$email' and password='$password'";
     $result = mysqli_query($con,$sql);

     $row = mysqli_fetch_array($result);
     // echo "password_result : " . $row[0] . "<br /><br />";

     if($row[0]==0){
       $login_status = 3;
       // echo "Passwords do not match.<br />";
     }
     else{
       $login_status = 1;
       // echo "Login successful.<br />";
     }
   }

   header('Content-Type: application/json; charset=utf8');
   $json = json_encode(array("login_status"=>$login_status), JSON_PRETTY_PRINT+JSON_UNESCAPED_UNICODE);
   echo $json;
   // echo json_encode(array("login_status" => $login_status));

   mysqli_close($con);
?>
