<?php
class ChallengeResponse{

	public function calculate($key, $challenge, $rand) {
		return sha1($key . $challenge . $rand);
	}

}

$eaahash = $_POST["EAAHash"];
$eaachallenge = $_POST["EAAChallenge"];
//$eaachallenge = "459967532";
if($eaahash != "" AND $eaachallenge != ""){
	// Generate Random number
	$rand = mt_rand();
	//$rand = "459967532";

	// Lookup Key
	$key = "14c70b93-b986-44eb-862c-38dccd088a76";

	$c = new ChallengeResponse();
	$result = $c->calculate($key, $eaachallenge, $rand)
	echo $rand."|".$result."|".$eaachallenge;
	header('EAARAND:' + $rand);
	header('EAAResult:' + $result);
	setcookie('EAAResult', $result);
}
else{
	if($_POST["EAAResult"]){
		echo $_POST["email"];
	}
	setcookie('EAAResult', "");
}
?>
