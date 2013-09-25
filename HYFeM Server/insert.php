<? header("content-type:text/html; charset=utf-8");?>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<?php
	//엑셀 읽기 파트
	require_once 'Comn/reader.php';

	$data = new Spreadsheet_Excel_Reader();
	$data->setOutputEncoding('utf8');
	$data->read('sample_excel.xls');
	
	error_reporting(E_ALL ^ E_NOTICE);

	//DB 연결 파트
	require("login.php");
	$conn = mysqli_connect($db_host,$db_user,$db_passwd,$db_name);
	if(!$conn){
		die('Connect Error : '.mysqli_connect_errno());
	}
	//mysql 입출력 인코딩 지정
	mysqli_query($conn, "set session character_set_connection=utf8;");
	mysqli_query($conn, "set session character_set_results=utf8;");
	mysqli_query($conn, "set session character_set_client=utf8;");

	$qhead = "Insert into $dbname (id, name, major, location, start_date, end_date, content) VALUES (NULL";
	for ($i = 2; $i <= $data->sheets[0]['numRows']; $i++) {
		for ($j = 1; $j <= $data->sheets[0]['numCols']; $j++) {
			$q = $q.", '".$data->sheets[0]['cells'][$i][$j]."'";
		}
		$q = $qhead.$q.")";
		echo $q."<br>";
		mysqli_query($conn, $q);
		$q = "";
	}
	mysqli_close($conn);
?>
