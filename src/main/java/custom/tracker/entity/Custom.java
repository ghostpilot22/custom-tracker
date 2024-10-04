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
	
	@OneToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "character_id", referencedColumnName = "character_id")
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	private Character character;
	
	@Column
	private String customName;
	
	// Note to self: Ask Michael about how to set up a proper many-to-many.
	@ManyToMany(cascade = CascadeType.PERSIST)
	@JoinTable(name = "custom_supplies", joinColumns = @JoinColumn(name = "custom_id"), inverseJoinColumns = @JoinColumn(name = "supply_id"))
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	private Set<Supplies> supplies = new HashSet<>();
	
	@OneToMany(mappedBy = "custom", cascade = CascadeType.ALL, orphanRemoval = true)
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	private Set<Step> steps = new HashSet<>();
	
	// Variables for the trackers. 
	// Might end up not needing these.
	private Integer prepTotal; // Total number of supplies linked to the custom.
	private Integer prepOwned; // Number of those that're marked as owned.
	private Integer progTotal; // Total number of steps linked to the custom.
	private Integer progDone; // Number of steps marked as completed.
}
