package model;

public class Schedule {
	private int id;
	private int user_id;
	private java.sql.Date scheduleDate;
	private java.sql.Time startTime;
	private java.sql.Time endTime;
	private String scheduleTitle;
	private String scheduleMemo;

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getUser_id() {
		return user_id;
	}
	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}
	public java.sql.Date getScheduleDate() {
		return scheduleDate;
	}
	public void setScheduleDate(java.sql.Date scheduleDate) {
		this.scheduleDate = scheduleDate;
	}
	public java.sql.Time getStartTime() {
		return startTime;
	}
	public void setStartTime(java.sql.Time startTime) {
		this.startTime = startTime;
	}
	public java.sql.Time getEndTime() {
		return endTime;
	}
	public void setEndTime(java.sql.Time endTime) {
		this.endTime = endTime;
	}
	public String getScheduleTitle() {
		return scheduleTitle;
	}
	public void setScheduleTitle(String scheduleTitle) {
		this.scheduleTitle = scheduleTitle;
	}
	public String getScheduleMemo() {
		return scheduleMemo;
	}
	public void setScheduleMemo(String scheduleMemo) {
		this.scheduleMemo = scheduleMemo;
	}

}
