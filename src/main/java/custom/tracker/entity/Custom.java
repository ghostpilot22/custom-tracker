package custom.tracker.entity;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Entity
@Data
public class Custom 
{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer customId;
	
	@ManyToOne(cascade = CascadeType.PERSIST)
	@JoinColumn(name = "doll_base_id")
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	private DollBase dollBase;
	
	// Foreign key.
	@OneToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "character_id", referencedColumnName = "character_id")
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	private Characters character;
	
	@Column
	private String customName;
	
	/*@ManyToMany(cascade = CascadeType.PERSIST)
	@JoinTable(name = "custom_supplies", joinColumns = @JoinColumn(name = "custom_id"), inverseJoinColumns = @JoinColumn(name = "supply_id"))
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	private Set<Supplies> supplies = new HashSet<>();*/
	
	@OneToMany(mappedBy = "custom", cascade = CascadeType.ALL, orphanRemoval = true)
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	private Set<CustomSupplies> supplies = new HashSet<>();
	
	@OneToMany(mappedBy = "custom", cascade = CascadeType.ALL, orphanRemoval = true)
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	private Set<Step> steps = new HashSet<>();
	
	// Variables for the trackers. 
	// Might end up not needing these.
	// Could just do a count of the step and supply sets for the totals.
	// progDone would be to iterate through the steps and increase count for each one marked as done.
	// prepOwned a bit harder as have to also take the doll base into account.
	// So first check the doll base and check if its owned. Then iterate through each
	// custom_supplies and if quantity_needed <= quantity_owned on the supply object,
	// then increase count. And don't forget to add 1 for the doll base to the totals.
	//private Integer prepTotal; // Total number of supplies linked to the custom.
	//private Integer prepOwned; // Number of those that're marked as owned.
	//private Integer progTotal; // Total number of steps linked to the custom.
	//private Integer progDone; // Number of steps marked as completed.
}
