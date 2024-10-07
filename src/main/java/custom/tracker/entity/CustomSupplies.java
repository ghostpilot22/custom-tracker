package custom.tracker.entity;

import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Entity
@Data
public class CustomSupplies 
{
	@EmbeddedId
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "custom_id")
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	private Custom custom;
	
	@EmbeddedId
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "supply_id")
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	private Supplies supplies;
	
	@Column
	private Integer quantityNeeded;
}
