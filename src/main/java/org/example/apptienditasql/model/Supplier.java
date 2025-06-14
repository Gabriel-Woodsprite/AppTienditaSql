package org.example.apptienditasql.model;

public class Supplier {
	private int supplierId;
	private String name;
	private String address;
	private String email;
	private String tel;
	private String type;
	private String notes;
	private String contactPerson;

	public int getSupplierId() {
		return supplierId;
	}

	public String getName() {
		return name;
	}

	public String getAddress() {
		return address;
	}

	public String getEmail() {
		return email;
	}

	public String getTel() {
		return tel;
	}

	public String getType() {
		return type;
	}

	public String getNotes() {
		return notes;
	}

	public String getContactPerson() {
		return contactPerson;
	}

	public void setSupplierId(int supplierId) {
		this.supplierId = supplierId;
	}

	public void setContactPerson(String contactPerson) {
		this.contactPerson = contactPerson;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public void setType(String type) {
		this.type = type;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	@Override
	public String toString() {
		return "Supplier{" +
				"name='" + name + '\'' +
				", address='" + address + '\'' +
				", email='" + email + '\'' +
				", tel='" + tel + '\'' +
				", type='" + type + '\'' +
				", notes='" + notes + '\'' +
				'}';
	}
}
