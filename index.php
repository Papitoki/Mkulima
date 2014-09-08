<?php

$host="192.34.59.123";
$user="sycko";
$password="N0'R3lation";
$port=3306;
$database="MKULIMA";

function check_ph_suitable_crops($current_PH){
	$query="SELECT * from MKULIMA.crops_Data where max_PH>=$current_PH";
	$result=mysqli_query($GLOBALS['conn'] ,$query);
	$price=Array();
	if($result){
		while($row=mysqli_fetch_assoc($result)){					
			$price=computePrice($row,$price);			
			$row=Array();
			}
		echo json_encode($price);
		}
      else{
           echo "No such item found";
       }
	}


function computePrice($row,$price){
	$maizex=153;
	$beanx=$milletx=$potatoesx=$sorghum=141;
	
	if($row['crop_Name']=='maize'){
		
		$price['Nairobi_maize']=($row['slope_nai']*$maizex)+$row['y_intercept_nai'];
		$price['Eldoret_maize']=($row['slope_eld']*$maizex)+$row['y_intercept_eld'];
		$price['Kisumu_maize']=($row['slope_kis']*$maizex)+$row['y_intercept_kis'];
		$price['Mombasa_maize']=($row['slope_mom']*$maizex)+$row['y_intercept_mom'];
		return $price;
	}

	else if($row['crop_Name']=='beans'){
		$price['Nairobi_beans']=($row['slope_nai']*$beanx)+$row['y_intercept_nai'];
		$price['Eldoret_beans']=($row['slope_eld']*$beanx)+$row['y_intercept_eld'];
		$price['Kisumu_beans']=($row['slope_kis']*$beanx)+$row['y_intercept_kis'];
		$price['Mombasa_beans']=($row['slope_mom']*$beanx)+$row['y_intercept_mom'];
		return $price;
	}

	else if($row['crop_Name']=='potatoes'){
		$price['Nairobi_potatoe']=($row['slope_nai']*$potatoesx)+$row['y_intercept_nai'];
		$price['Eldoret_potatoe']=($row['slope_eld']*$potatoesx)+$row['y_intercept_eld'];
		$price['Kisumu_potatoe']=($row['slope_kis']*$potatoesx)+$row['y_intercept_kis'];
		$price['Mombasa_potatoe']=($row['slope_mom']*$potatoesx)+$row['y_intercept_mom'];
		return $price;
	}
	
	else if($row['crop_Name']=='sorghum'){
		$price['Nairobi_sorghum']=($row['slope_nai']*$sorghumx)+$row['y_intercept_nai'];
		$price['Eldoret_sorghum']=($row['slope_eld']*$sorghumx)+$row['y_intercept_eld'];
		$price['Kisumu_sorghum']=($row['slope_kis']*$sorghumx)+$row['y_intercept_kis'];
		$price['Mombasa_sorghum']=($row['slope_mom']*$sorghumx)+$row['y_intercept_mom'];
		return $price;
	}

	else if($row['crop_Name']=='millet'){
		$price['Nairobi_millet']=($row['slope_nai']*$milletx)+$row['y_intercept_nai'];
		$price['Eldoret_millet']=($row['slope_eld']*$milletx)+$row['y_intercept_eld'];
		$price['Kisumu_millet']=($row['slope_kis']*$milletx)+$row['y_intercept_kis'];
		$price['Mombasa_millet']=($row['slope_mom']*$milletx)+$row['y_intercept_mom'];
		return $price;
	}
}





//main body

$conn=mysqli_connect($host, $user, $password, $database, $port);
if(!$conn)
{
	die("Could not connect to database. Please try again later");
}
	if(!empty ($_POST)){
		$last_Planted=$_POST["recentCrop"];
		$ph_Before=$_POST["soilph"];
		$query="Select effect_On_PH from MKULIMA.crops_Data where crop_Name='$last_Planted'";
		$result=mysqli_query($conn, $query);
   if($result){
		while($row=mysqli_fetch_array($result)){
			//print_r($row['effect_On_PH']);
			check_ph_suitable_crops($ph_Before+$row['effect_On_PH']);
			}
      }
      else{
           echo "No such item found";}
		}
			
	