package custom.tracker.entity;

import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
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
public class Step 
{
	@Id 
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer stepId;
	
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "custom_id")
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	private Custom custom;
	
	@Column
	private Integer stepNumber;
	
	@Column
	private String stepText;
	
	@Column
	private Boolean completed;
}
