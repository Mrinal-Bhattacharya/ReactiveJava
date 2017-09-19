

public class Product {
	Long id;
	String description;
	
	public Product() {
	}
	
	

	public Product(String description) {
		this.description = description;
	}



	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}



	@Override
	public String toString() {
		return "Product [id=" + id + ", description=" + description + "]";
	}


}
