package custom.tracker.controller.model;

import custom.tracker.entity.Supplies;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @NoArgsConstructor
public class SuppliesData 
{
	public SuppliesData(Supplies supply)
	{
		supplyId = supply.getSupplyId();
		supplyName = supply.getSupplyName();
		quantityOwned = supply.getQuantityOwned();
		price = supply.getPrice();
	}
	private Integer supplyId;
	private String supplyName;
	private Integer quantityOwned;
	private Float price;
	
}
