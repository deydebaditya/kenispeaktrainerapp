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
      $date = $HEAD['date'];
      $school_name = $HEAD['school_name'];
      $holiday_reason = $HEAD['reason'];

      $checkQuery = "select school_code from schools where school_name = '".$school_name."'";
      $result = $conn->query($checkQuery);
      if($result->num_rows>0){
        $response['schoolFound'] = "VALID_SCHOOL";
        while($row = $result->fetch_assoc()){
          $school_id = $row['school_code'];
        }

        $query = "insert into activity (activity_num,pri_mob_num,_date,school_id,_leave,leave_reason,holiday,holiday_reason,sessions,num_of_sessions) values('".RndString()."','".$pri_mob_num."','".$date."','".$school_id."',0,'',1,'".$holiday_reason."',0,0)";

        $result = $conn->query($query);

        if($result){
          $response['insert'] = "IN_SUCC";
        }
        else{
          $response['insert'] = "IN_FAIL";
        }
      }
      else{
        $response['schoolFound'] = "INVALID_SCHOOL";
      }
      
    }
    print(json_encode($response));
  function RndString()
  {
    $characters = '0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ';
    $randstring = '';
    for ($i = 0; $i < 10; $i++) {
        $randstring = $randstring . $characters[rand(0, strlen($characters))];
    }
    return $randstring;
  }
?>