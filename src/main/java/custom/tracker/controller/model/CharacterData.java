package custom.tracker.controller.model;

import custom.tracker.entity.Characters;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @NoArgsConstructor
public class CharacterData
{
	public CharacterData(Characters charactr)
	{
		characterId = charactr.getCharacterId();
		name = charactr.getName();
		hairColor = charactr.getHairColor();
		eyeColor = charactr.getEyeColor();
		gender = charactr.getGender();
		traits = charactr.getTraits();
		personality = charactr.getPersonality();
	}
	private Integer characterId;
	private String name;
	private String hairColor;
	private String eyeColor;
	private String gender;
	private String traits;
	private String personality;
}
