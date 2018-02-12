<?php
	$username="root";
  	$dbname="keni";
  	$url="localhost";
  	$password="MH31eh@2964";
  	mysqli_report(MYSQLI_REPORT_STRICT);
  	$fail=false;
  	$response['conn']="FL_CONN";
  	try{
    	$conn=new mysqli($url,$username,$password,$dbname);
  	}
  	catch(Exception $e){
    	//echo $e->message;
    	$fail=true;
  	}
  	if($fail){
    	$response['conn']="CONN_FAIL";
  	}
  	else{
  		//session_start();
  		$response['conn']="CONN_SUCCESS";
  		$HEAD = getAllHeaders();
  		$pri_mob_num = $HEAD['pri_mob_num'];
  		$password = $HEAD['password'];
      $response['pri_mob_num'] = $pri_mob_num;
      $response['password'] = $password;

  		$query_pass = "select name, password from trainer where pri_mob_num = '".$pri_mob_num."'";
  		$result = $conn->query($query_pass);
  		if($result->num_rows > 0){
  			while($row = $result->fetch_assoc()){
  				$password_database = $row['password'];
          $name = $row['name'];
          $response['pass_db'] = $password_database;
  			}
  			if($password == $password_database){
  				if($_SESSION[$pri_mob_num] == 1){
  					$response['loginStatus'] = "LOGGED_IN";
            $response['name'] = $name;
            $response['pri_mob_num'] = $pri_mob_num;
  				}
  				else{
  					$response['loginStatus'] = "LOGIN_SUCCESS";
            $response['name'] = $name;
            $response['pri_mob_num'] = $pri_mob_num;
  					//$_SESSION[$pri_mob_num] = 1;
  				}
  			}
  		}
  		else{
  			$response['loginStatus'] = "LOGIN_FAIL";
  		}
  	}
  	print(json_encode($response));
?>
