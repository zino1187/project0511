package db.join;

public class SubCategory {
	private int subcategory_id;
	private TopCategory topCategory;//Has a
	private String name;
	
	public int getSubcategory_id() {
		return subcategory_id;
	}
	public void setSubcategory_id(int subcategory_id) {
		this.subcategory_id = subcategory_id;
	}
	public TopCategory getTopCategory() {
		return topCategory;
	}
	public void setTopCategory(TopCategory topCategory) {
		this.topCategory = topCategory;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	
	
	
}
