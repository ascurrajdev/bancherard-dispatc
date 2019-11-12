package py.com.sodep.bancard.api.generator;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class Status {

	@JsonIgnore
	private boolean forcedRun;

	@JsonIgnore
	private String todayDate;

	public String lastRunDate;

	public String getLastRunDate() {
		return lastRunDate;
	}

	public void setLastRunDate(String lastRunDate) {
		this.lastRunDate = lastRunDate;
	}

	public String getTodayDate() {
		return todayDate;
	}

	public void setTodayDate(String todayDate) {
		this.todayDate = todayDate;
	}

	public void setForcedRun(boolean forcedRun) {
		this.forcedRun = forcedRun;
	}

	public boolean isForcedRun() {
		return this.forcedRun;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Status [forcedRun=" + forcedRun + ", todayDate=" + todayDate
				+ ", lastRunDate=" + lastRunDate + "]";
	}

	
}
