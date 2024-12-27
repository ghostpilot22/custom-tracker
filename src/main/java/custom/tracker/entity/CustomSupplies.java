package custom.tracker.entity;

import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Entity
@Data
public class CustomSupplies 
{
	@Id 
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer customSuppliesId;
	
	//@EmbeddedId
	@ManyToOne(cascade = CascadeType.PERSIST)
	@JoinColumn(name = "custom_id")
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	private Custom custom;
	
	//@EmbeddedId
	@ManyToOne(cascade = CascadeType.PERSIST)
	@JoinColumn(name = "supply_id")
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	private Supplies supplies;
	
	@Column
	private Integer quantityNeeded;
}
