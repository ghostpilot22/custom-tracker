package custom.tracker.entity;

import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Entity
@Data
public class Character 
{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer characterId;
	
	@OneToOne(mappedBy = "character_id")
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	private Custom custom;
	
	@Column
	private String name;

	@Column
	private String hairColor;

	@Column
	private String eyeColor;

	@Column
	private String gender;

	@Column
	private String traits; // This refers to physical traits that will be
	// useful to keep in mind when planning a doll project.

	@Column
	private String personality; // This is where info on personality traits goes.
	
	// Might add another column here for lore but I'm not sure.
	// It'd end up taking up a lot of space and I already have a problem
	// with keeping lore info synced over the various places I store it.
}
