package by.ontravelsolutions.model;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import by.ontravelsolutions.enums.Choice;

public class Vote {

	private final LocalDateTime createTime = LocalDateTime.now();
	private String name;
	private boolean isStarted = true;
	private Map<String, Choice> Choices = new HashMap<String, Choice>();

	public Vote() {
		super();
	}

	public Vote(String name) {
		super();
		this.name = name;
	}

	public Vote(String name, boolean isStarted) {
		super();
		this.name = name;
		this.isStarted = isStarted;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void stop() {
		this.isStarted = false;
	}

	public Map<String, Choice> getChoices() {
		return Choices;
	}

	public void setChoice(String votersName, Choice choice) {
		Choices.put(votersName, choice);
	}

	@Override
	public String toString() {
		return "Vote [createTime=" + createTime + ", name=" + name + ", isStarted=" + isStarted + ", Choices size="
				+ Choices.size() + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((createTime == null) ? 0 : createTime.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Vote other = (Vote) obj;
		if (createTime == null) {
			if (other.createTime != null)
				return false;
		} else if (!createTime.equals(other.createTime))
			return false;
		return true;
	}

}