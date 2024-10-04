package custom.tracker.entity;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Entity
@Data
public class DollBase 
{
	@Id 
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer dollBaseId;
	
	@OneToMany(mappedBy = "doll_base", cascade = CascadeType.PERSIST, orphanRemoval = false)
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	private Set<Custom> customs = new HashSet<>();
	
	@Column 
	private String dollName;
	
	@Column
	private String dollSet;
	
	@Column
	private String brand;
	
	@Column 
	private String size;
	
	@Column
	private String jointType;
	
	@Column
	private String eyeType;
	
	@Column
	private String features;
	
	@Column
	private Integer quantityOwned;
}
