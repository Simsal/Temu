package model;

public class ConfigurationInformation {

	String measurementValueStart;
	String measurementValueStartUnity;
	String measurementValueEnd;
	String measurementValueEndUnity;
	String failureBehavior;
	
	public ConfigurationInformation() {
	}
	
	public ConfigurationInformation(String measurementValueStart, String measurementValueStartUnity,
			String measurementValueEnd, String measurementValueEndUnity, String failureBehavior,
			String dampingActualValue) {
		this.measurementValueStart = measurementValueStart;
		this.measurementValueStartUnity = measurementValueStartUnity;
		this.measurementValueEnd = measurementValueEnd;
		this.measurementValueEndUnity = measurementValueEndUnity;
		this.failureBehavior = failureBehavior;
	}

	public String getMeasurementValueStart() {
		return measurementValueStart;
	}

	public void setMeasurementValueStart(String measurementValueStart) {
		this.measurementValueStart = measurementValueStart;
	}

	public String getMeasurementValueStartUnity() {
		return measurementValueStartUnity;
	}

	public void setMeasurementValueStartUnity(String measurementValueStartUnity) {
		this.measurementValueStartUnity = measurementValueStartUnity;
	}

	public String getMeasurementValueEnd() {
		return measurementValueEnd;
	}

	public void setMeasurementValueEnd(String measurementValueEnd) {
		this.measurementValueEnd = measurementValueEnd;
	}

	public String getMeasurementValueEndUnity() {
		return measurementValueEndUnity;
	}

	public void setMeasurementValueEndUnity(String measurementValueEndUnity) {
		this.measurementValueEndUnity = measurementValueEndUnity;
	}

	public String getFailureBehavior() {
		return failureBehavior;
	}

	public void setFailureBehavior(String failureBehavior) {
		this.failureBehavior = failureBehavior;
	}


	@Override
	public String toString() {
		return "ConfigurationInformation [measurementValueStart=" + measurementValueStart
				+ ", measurementValueStartUnity=" + measurementValueStartUnity + ", measurementValueEnd="
				+ measurementValueEnd + ", measurementValueEndUnity=" + measurementValueEndUnity + ", failureBehavior="
				+ failureBehavior + "]";
	}
	
	
	
	
}
