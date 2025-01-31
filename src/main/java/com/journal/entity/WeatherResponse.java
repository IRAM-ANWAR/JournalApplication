package com.journal.entity;

import java.util.ArrayList;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

/*import com.fasterxml.jackson.databind.ObjectMapper; // version 2.11.1
//import com.fasterxml.jackson.annotation.JsonProperty; // version 2.11.1
//ObjectMapper instance Root root = om.readValue(myJsonString, Root.class) */
@Getter
@Setter
public class WeatherResponse {
	@Getter
	@Setter
	public class Current {
		@JsonProperty("observation_time")
		private String observationTime;
		private int temperature;
		private int weatherCode;
		private ArrayList<String> weatherIcons;
		private ArrayList<String> weatherDescriptions;
		private int windSpeed;
		private int windDegree;
		private String windDir;
		private int pressure;
		private int precip;
		private int humidity;
		private int cloudcover;
		private int feelslike;
		private int uvIndex;
		private int visibility;
	}

	private Current current;
}
