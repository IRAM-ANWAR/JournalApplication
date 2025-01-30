package com.journal.entity;

import java.util.ArrayList;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

//import com.fasterxml.jackson.databind.ObjectMapper; // version 2.11.1
//import com.fasterxml.jackson.annotation.JsonProperty; // version 2.11.1
/* ObjectMapper om = new ObjectMapper();
Root root = om.readValue(myJsonString, Root.class); */
@Getter
@Setter
public class WeatherResponse {
	@Getter
	@Setter
	public class Current {
		@JsonProperty("observation_time")
		public String observationTime;
		public int temperature;
		public int weather_code;
		public ArrayList<String> weather_icons;
		public ArrayList<String> weather_descriptions;
		public int wind_speed;
		public int wind_degree;
		public String wind_dir;
		public int pressure;
		public int precip;
		public int humidity;
		public int cloudcover;
		public int feelslike;
		public int uv_index;
		public int visibility;
	}

	private Current current;
}
