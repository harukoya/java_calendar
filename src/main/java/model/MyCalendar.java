package model;

public class MyCalendar {
	//	年
	private int year;
	//	月
	private int month;
	//	日（配列で保持）
	private String[][] data;

	public int getYear() {
		return year;
	}
	public void setYear(int year) {
		this.year = year;
	}
	public int getMonth() {
		return month;
	}
	public void setMonth(int month) {
		this.month = month;
	}
	public String[][] getData() {
		return data;
	}
	public void setData(String[][] data) {
		this.data = data;
	}
}
