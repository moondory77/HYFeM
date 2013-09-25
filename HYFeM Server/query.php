<? header("content-type:text/html; charset=utf-8");?>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<?php
	//login.php에 host, user, passwd, name 정보가 들어있음
	require("login.php");
	$conn = mysqli_connect($db_host,$db_user,$db_passwd,$db_name);
	if(!$conn){
		die('Connect Error : '.mysqli_connect_errno());
	}
	//mysql 입출력 인코딩 지정
	mysqli_query($conn, "set session character_set_connection=utf8;");
	mysqli_query($conn, "set session character_set_results=utf8;");
	mysqli_query($conn, "set session character_set_client=utf8;");
	
	//쿼리를 받는 부분
	$qid = $_GET['id'];
	$qname = $_GET['name'];
	$qmajor = $_GET['major'];
	$qlocation = $_GET['location'];
	$qsdate = $_GET['start_date'];
	$qedate = $_GET['end_date'];
	
	//쿼리를 한 문장으로 합침
	$q = " WHERE";
	$and = false;
	if($qid){ 
		$q = $q." id = '".$qid."'"; 
		$and = true;
	}
	if($qname){
		if($and){ $q = $q." AND"; }
		$q = $q." name = '".$qname."'"; 
		$and = true;
	}
	if($qmajor){
		if($and){ $q = $q." AND"; }
		$q = $q." major = '".$qmajor."'"; 
		$and = true;
	}
	if($qlocation){
		if($and){ $q = $q." AND"; }
		$q = $q." location = '".$qlocation."'"; 
		$and = true;
	}
	/* 시간정보를 포함한 쿼리의 처리는 작업 중
	if($qsdate){
		if($and){ $q." AND"; }
		$q = $q." (start_date < '".$qsdate."')";
		$and = true;
	}
	if($qedate){
		if($and){ $q." AND"; }
		$q = $q." (end_date < '".$qedate."')";
	}
	*/
	
	if($and){
		$q = "SELECT * FROM hyfem".$q;
	}else{
		$q = "SELECT * FROM hyfem";
	}
	
	echo "Query : ".$q."<br>";
	
	$json = array();
	//DB에 쿼리를 전송
	$result = mysqli_query($conn, $q);
	if(!$result){
		die('Invalid query : '.mysqli_connect_errno());
	}

	$num = 0;
	while($row = mysqli_fetch_assoc($result)){
		//한글 깨짐 방지를 위해 urlencode
		$json[$num]['id'] = urlencode($row['id']);
		$json[$num]['name'] = urlencode($row['name']);
		$json[$num]['major'] = urlencode($row['major']);
		$json[$num]['location'] = urlencode($row['location']);
		$json[$num]['start_date'] = urlencode($row['start_date']);
		$json[$num]['end_date'] = urlencode($row['end_date']);
		$json[$num]['content'] = urlencode($row['content']);
		$num++;
	}
	echo urldecode(json_encode($json)); //인코딩 했다면 당연히 디코딩
	//메모리 해제
	mysqli_free_result($result);
	mysqli_close($conn);
?>