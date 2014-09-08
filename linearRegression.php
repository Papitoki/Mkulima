<html>
<pre>
<?php


include 'PolynomialRegression.php';

//Database Details
$databasehost = "192.34.59.123";
$databasename = "MKULIMA";

//Authentication
$databaseusername ="sycko";
$databasepassword = "N0'R3lation";

//Database Tables
$dbtablemaize = "maize_Trend";
$dbtablebeans = "beans_Trend";
$dbtablemillet = "millet_Trend";
$dbtablepotatoes = "potatoes_Trend";
$dbtablesorghum = "sorghum_Trend";

function queryDB($place, $tablename)
{
  $query = "SELECT Month, Year, ". $place ." FROM ". $tablename ."";

  return $query;
}

function dbData($query, $place)
{
  $x = 0;
  $result = mysql_query($query);
  $storeArray = Array();
  $array = Array();

  if(empty($result))
  {
    echo "No entries found in the databases";
  }
  else
  {
    while ($row = mysql_fetch_array($result, MYSQL_ASSOC)) 
    {
      $array[0]= $x++;
      $array[1]=$row[$place];
    
      $storeArray[] =  $array;  
    }
    echo "The final value is: ". $x ."\n";
    return $storeArray;
  }
  return $storeArray;
}

function linearRegression($data, $place, $crop)
{
  // Precision digits in BC math.
  bcscale( 10 );
  // Start a regression class of order 2--linear regression.
  $PolynomialRegression = new PolynomialRegression( 2 );

  // Add all the data to the regression analysis.
  foreach ( $data as $dataPoint )
    {
      $PolynomialRegression->addData( $dataPoint[0], $dataPoint[1] );
}
  // Get coefficients for the polynomial.
  $coefficients = $PolynomialRegression->getCoefficients();
   print_r($coefficients)."<\n>";

  // Print slope and intercept of linear regression.
  echo "Slope : " . round( $coefficients[ 1 ], 2 ) . "<br />";
  echo "Y-intercept : " . round( $coefficients[ 0 ], 2 ) . "<br />";

  if($place == "Nairobi")
  {
    $prefix = "nai";
    insertIntoDB(round( $coefficients[ 1 ], 2 ),round( $coefficients[ 0 ], 2 ), $prefix, $crop);
  }
  else  if($place == "Kisumu")
  {
    $prefix = "kis";
    insertIntoDB(round( $coefficients[ 1 ], 2 ),round( $coefficients[ 0 ], 2 ), $prefix, $crop );
  }
  else if($place == "Eldoret")
  {
    $prefix = "eld";
    insertIntoDB(round( $coefficients[ 1 ], 2 ),round( $coefficients[ 0 ], 2 ), $prefix, $crop );
  }
  else  if($place == "Mombasa")
  {
    $prefix = "mom";
    insertIntoDB(round( $coefficients[ 1 ], 2 ),round( $coefficients[ 0 ], 2 ), $prefix, $crop);
  }
  else
  {
    $prefix = "kit";
    insertIntoDB(round( $coefficients[ 1 ], 2 ),round( $coefficients[ 0 ], 2 ), $prefix, $crop );
  }

}

function insertIntoDB($slope, $yintercept, $prefix, $crop)
{
  $column_slope = "slope_".$prefix;
  $column_gradient = "y_intercept_".$prefix;

  $queried = "UPDATE crops_Data SET $column_slope = $slope, $column_gradient = $yintercept WHERE crop_Name = '$crop'";

  mysql_query($queried);
}

function usingTableName($tablenames, $crop)
{
  $places = Array("Nairobi","Eldoret","Kisumu","Kitui", "Mombasa");
  foreach ($places as $key => $value) 
  {
    linearRegression(dbData(queryDB($value, $tablenames), $value),$value, $crop);
  }
}

//Connection to the database
$con = @mysql_connect($databasehost, $databaseusername, $databasepassword) or die(mysql_error());
@mysql_select_db($databasename) or die(mysql_error());

$maize = 'maize';
$beans = 'beans';
$potatoes =  'potatoes';
$sorghum = 'sorghum';
$millet = 'millet';

usingTableName($dbtablemaize,$maize);
usingTableName($dbtablebeans, $beans);
usingTableName($dbtablesorghum, $sorghum);
usingTableName($dbtablemillet, $millet);
usingTableName($dbtablepotatoes, $potatoes);

@mysql_close($con);


?>
</pre>
</html>