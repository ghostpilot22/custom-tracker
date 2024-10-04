package custom.tracker.entity;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Entity
@Data
public class Supplies 
{
	@Id 
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer supplyId;
	
	@Column
	private String supplyName;
	
	@Column
	private Integer quantityOwned;
	
	@Column
	private Float price;
	
	@ManyToMany(mappedBy = "supplies", cascade = CascadeType.PERSIST)
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	private Set<Custom> customs = new HashSet<>();
}
