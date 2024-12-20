package com.altafjava.examples.audit;

import java.time.LocalDateTime;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Entity
@Setter
@Getter
@Auditable
public class Product {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String name;
	private int price;

	@CreatedBy
	private String createdBy;

	@LastModifiedBy
	private String lastModifiedBy;

	@CreatedDate
	private LocalDateTime createdDate;

	@LastModifiedDate
	private LocalDateTime lastModifiedDate;

	@Version
	private Integer version;

	@Override
	public String toString() {
		return "Product{" +
				"id=" + id +
				", name='" + name + '\'' +
				", price='" + price + '\'' +
				", createdBy='" + createdBy + '\'' +
				", lastModifiedBy='" + lastModifiedBy + '\'' +
				", createdDate=" + createdDate +
				", lastModifiedDate=" + lastModifiedDate +
				", version=" + version +
				'}';
	}
}
