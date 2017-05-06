package com.shengui.app.android.shengui.android.ui.utilsview.sortlistview;

import java.io.Serializable;

public class PersonBean implements Serializable{

	private String Name;
	private String PinYin;
	private String FirstPinYin=" ";
	String id;
	String name;
	String name_first;
	String name_py;
	String sort_order;
	String status;
	String create_time;
	String update_time;

	@Override
	public String toString() {
		return "PersonBean{" +
				"Name='" + Name + '\'' +
				", PinYin='" + PinYin + '\'' +
				", FirstPinYin='" + FirstPinYin + '\'' +
				", id='" + id + '\'' +
				", name='" + name + '\'' +
				", name_first='" + name_first + '\'' +
				", name_py='" + name_py + '\'' +
				", sort_order='" + sort_order + '\'' +
				", status='" + status + '\'' +
				", create_time='" + create_time + '\'' +
				", update_time='" + update_time + '\'' +
				'}';
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName_first() {
		return name_first;
	}

	public void setName_first(String name_first) {
		this.name_first = name_first;
	}

	public String getName_py() {
		return name_py;
	}

	public void setName_py(String name_py) {
		this.name_py = name_py;
	}

	public String getSort_order() {
		return sort_order;
	}

	public void setSort_order(String sort_order) {
		this.sort_order = sort_order;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getCreate_time() {
		return create_time;
	}

	public void setCreate_time(String create_time) {
		this.create_time = create_time;
	}

	public String getUpdate_time() {
		return update_time;
	}

	public void setUpdate_time(String update_time) {
		this.update_time = update_time;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPinYin() {
		return PinYin;
	}

	public void setPinYin(String pinYin) {
		PinYin = pinYin;
	}

	public String getFirstPinYin() {
		return FirstPinYin;
	}

	public void setFirstPinYin(String firstPinYin) {
		FirstPinYin = firstPinYin;
	}


}
