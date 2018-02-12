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
  		$response['conn']="CONN_SUCCESS";
  		$HEAD=getAllHeaders();
  		$pri_mob_num = $HEAD['pri_mob_num'];
  		$password = $HEAD['password'];
  		$name = $HEAD['name'];
      $email = $HEAD['email'];
  		$alt_mob_num = $HEAD['alt_mob_num'];
  		$landline = $HEAD['landline'];
  		$add_line1 = $HEAD['add_line1'];
  		$add_line2 = $HEAD['add_line2'];
  		$add_line3 = $HEAD['add_line3'];
  		$city = $HEAD['city'];
  		$state = $HEAD['state'];
      $pincode = $HEAD['pincode'];

      $response['name']=$name;
      $response['email']=$email;
      $response['password']=$password;
      $response['pri_mob_num']=$pri_mob_num;
      $response['alt_mob_nium']=$alt_mob_num;
      $response['add_line1']=$add_line1;
      $response['add_line2']=$add_line2;
      $response['add_line3']=$add_line3;
      $response['city']=$city;
      $response['state']=$state;
      $response['pincode']=$pincode;

  		$checkQuery = "select name from trainer where pri_mob_num = '".$pri_mob_num."'";
  		$result = $conn->query($checkQuery);
  		if($result->num_rows>0){
  			$response['duplicateEntry'] = "1";
  			$response['queryInsert'] = "IN_FAIL";
  		}
  		else{
        $response['duplicateEntry'] = "0";
  			$query = "insert into trainer (name,email,password,pri_mob_num,alt_mob_num,landline,add_line1,add_line2,add_line3,city,state,pincode) values('".$name."','".$email."','".$password."','".$pri_mob_num."','".$alt_mob_num."','".$landline."','".$add_line1."','".$add_line2."','".$add_line3."','".$city."','".$state."','".$pincode."')";

  			$result = $conn->query($query);
  			if($result){
  				$response['queryInsert'] = "IN_SUCC";
  			}
  			else{
  				$response['queryInsert'] = "IN_FAIL";
  			}
  		}
  	}
  	print(json_encode($response));
?>