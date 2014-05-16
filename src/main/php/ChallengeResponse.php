<?php
class ChallengeResponse{

	public function calculate($key, $challenge, $rand) {
		return sha1($key . $challenge . $rand);
	}

}
?>