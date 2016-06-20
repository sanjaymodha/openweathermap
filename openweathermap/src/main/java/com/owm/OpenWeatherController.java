package com.owm;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.TimeZone;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.aksingh.owmjapis.CurrentWeather;
import net.aksingh.owmjapis.OpenWeatherMap;
import net.aksingh.owmjapis.OpenWeatherMap.Units;

/**
 * This class provides current weather data from OpenWeatherMap.org
 * based on a city chosen by the user
 */
public class OpenWeatherController extends HttpServlet {
  private static final long serialVersionUID = 1L;

  /**
   * @see HttpServlet#HttpServlet()
   */
  public OpenWeatherController() {
    super();
  }

  /**
   * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
   */
  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    PrintWriter out = response.getWriter();
    String selectedLocation = request.getParameter("location");
    // Declaring object of "OpenWeatherMap" class and the parameter passed is the
    // APPID key that is obtained from OpenWatherMap.org
    OpenWeatherMap weatherinFahrenheit = new OpenWeatherMap(Units.IMPERIAL, "f827eebaca489e71b659fecfb551dc9c");
    OpenWeatherMap weatherInCelsius = new OpenWeatherMap(Units.METRIC, "f827eebaca489e71b659fecfb551dc9c");
    // getting current weather data for the selected city

    CurrentWeather cwdinF = weatherinFahrenheit.currentWeatherByCityName(selectedLocation);
    CurrentWeather cwdinC = weatherInCelsius.currentWeatherByCityName(selectedLocation);

    getCurrentDate(out, selectedLocation, cwdinF);

    // checking data retrieval was successful or not
    if (cwdinF.isValid()) {
      // checking if city name is available
      if (cwdinF.hasCityName()) {
        // printing city name from the retrieved data
        getCityName(out, cwdinF);
        getOverallWeather(out, cwdinF);
      }
      // checking if temperature is available
      if (cwdinF.getMainInstance().hasTemperature()) {
        getTemperature(out, cwdinF, cwdinC);
        getSunRiseSunSet(out, cwdinF);
      }
    }
  }

  protected void getSunRiseSunSet(PrintWriter out, CurrentWeather cwdinF) {
    SimpleDateFormat smf = new SimpleDateFormat("hh:mm:ss a");
    out.println("<br /> Sunrise: " + smf.format(cwdinF.getSysInstance().getSunriseTime()));
    out.println("<br /> Sunset: " + smf.format(cwdinF.getSysInstance().getSunsetTime()));
  }

  protected void getTemperature(PrintWriter out, CurrentWeather cwdinF, CurrentWeather cwdinC) {
    // printing the temperature
    out.println("<br /> Temperature in Fahrenheit: " + cwdinF.getMainInstance().getTemperature());
    out.println("<br /> Temperature in Celsius: " + (cwdinC.getMainInstance().getTemperature()));
  }

  protected void getOverallWeather(PrintWriter out, CurrentWeather cwdinF) {
    out.println("<br /> Overall Weather: " + cwdinF.getWeatherInstance(0).getWeatherDescription());
  }

  protected void getCityName(PrintWriter out, CurrentWeather cwdinF) {
    out.println("<br /> City: " + cwdinF.getCityName());
  }

  protected void getCurrentDate(PrintWriter out, String selectedLocation, CurrentWeather cwdinF) {
    // displaying current date based on selected location
    DateFormat df = new SimpleDateFormat("dd/MMM/yy hh:mm:ss z");
    if (selectedLocation.equalsIgnoreCase("London")) {
      df.setTimeZone(TimeZone.getTimeZone("Europe/London"));
    }
    else if (selectedLocation.equalsIgnoreCase("Hong Kong")) {
      df.setTimeZone(TimeZone.getTimeZone("Asia/Hong_Kong"));
    }
    out.println("<br />Current Date: " + df.format(cwdinF.getDateTime()));
  }

}
