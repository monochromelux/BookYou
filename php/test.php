<?php
   $con=mysqli_connect("211.213.95.132","deu_mobile","8804","mybookstore");

   if(mysqli_connect_errno($con)){
     echo "Failed to connect to MariaDB: " . mysqli_connect_error();
   }
   mysqli_set_charset($con, "utf8");

   $res = mysqli_query($con, "select * from user_info");
   $user = array();

   while($row = mysqli_fetch_array($res)){
     array_push($user,
        array('id' => $row[0], 'password' => $row[1],
              'email' => $row[2], 'name' => $row[3], 'created' => $row[4]
        )
     );
   }


   header('Content-Type: application/json; charset=utf8');
   $json = json_encode(array("user"=>$user), JSON_PRETTY_PRINT+JSON_UNESCAPED_UNICODE);
   echo $json;

   // echo json_encode(array("user" => $user));
   // echo "<pre>";
   // print_r($user);
   // echo '</pre>';

   mysqli_close($con);

   /***********************************************/

   // $sql="INSERT INTO user_info (email, password, name) VALUES ('kim@gmail.com', '1111','kimminsu')";
   // if (mysqli_query($con,$sql)) {
   //    echo "Values have been inserted successfully";
   // }
   // else {
   //   echo "fail...";
   // }

   /***********************************************/

   // if (mysqli_connect_errno($con)) {
   //    echo "Failed to connect to MySQL: " . mysqli_connect_error();
   // }
   //
   // $username = $_GET['username'];
   // $password = $_GET['password'];
   // $result = mysqli_query($con,"SELECT Role FROM table1 where Username='$username'
   //    and Password='$password'");
   // $row = mysqli_fetch_array($result);
   // $data = $row[0];
   //
   // if($data){
   //    echo $data;
   // }
   // mysqli_close($con);

   /***********************************************/

   // if (mysqli_connect_errno($con)) {
   //    echo "Failed to connect to MySQL: " . mysqli_connect_error();
   // }
   //
   // $username = $_POST['username'];
   // $password = $_POST['password'];
   // $result = mysqli_query($con,"SELECT Role FROM table1 where
   // Username='$username' and Password='$password'");
   // $row = mysqli_fetch_array($result);
   // $data = $row[0];
   //
   // if($data){
   //    echo $data;
   // }
   //
   // mysqli_close($con);


?>
