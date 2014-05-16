package eu.eurofel.util;


public class ChallengeResponseImpl implements ChallengeResponse {

	public String calculate(String key, String challenge, String rand) {
		StringBuffer sb = new StringBuffer();
		sb.append(key);
		sb.append(challenge);
		sb.append(rand);
		return EAAHash.getSHA1Hash(sb.toString());
	}

	
	public String generateChallenge() {
		return new Long(Math.round(Math.random() * 1000000)).toString();
	}

	
	public String handle(String hash, String challenge){
		String rand = generateChallenge();
		String key = "14c70b93-b986-44eb-862c-38dccd088a76";
		return rand + "|" + calculate(key, challenge, rand);
	}

}
