package esa.titan.soap.weatherservice;

import esa.titan.soap.weatherservice.city.City;
import java.net.URL;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.xml.namespace.QName;
import javax.xml.ws.Service;

import esa.titan.soap.weatherservice.generated.GlobalWeatherSoap;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@ManagedBean
@SessionScoped
public class GlobalWeatherService {

    private Weather weather;
    private GlobalWeatherSoap soap;
    private String country = "Germany", cityName;
    private City city = new City("Select A Country");
    private List<City> cities = new ArrayList<City>();
    
    final int widgetSize = 150;

    public GlobalWeatherService() {
        String url = "http://www.webserviceX.NET";

        try {
            final Service service = Service.create(
                    new URL("http://www.webservicex.com/globalweather.asmx?wsdl"),
                    new QName(url, "GlobalWeather"));

            soap = service.getPort(GlobalWeatherSoap.class);

        } catch (Exception e) {
            Logger.getLogger(GlobalWeatherService.class.getName()).log(Level.SEVERE, "Error in constructor GlobalWeatherService(): " + e.getMessage());
        }
    }

    public void updateWeather() {
        try {

            weather = null;

            String cityName = getCityName();
            cityName = cityName.substring(0, 1).toUpperCase() + cityName.substring(1, cityName.length());

            Logger.getLogger(GlobalWeatherService.class.getName()).log(Level.INFO, "Loading Weather for " + cityName.toString() + " in Country " + getCountry());

            Weather newWeather = new Weather();

            String xml = soap.getWeather(cityName, country);
            String value = null;
            String[] parts;

            /*
             * date and time
             */
            value = getValueFromXMLTag(xml, "Time");
            parts = value.split(" ");

            String[] dateParts = parts[parts.length - 3].split("\\.");
            newWeather.setYear(Integer.parseInt(dateParts[0]));
            newWeather.setMonth(Integer.parseInt(dateParts[1]));
            newWeather.setDay(Integer.parseInt(dateParts[2]));

            newWeather.setHour(Integer.parseInt(parts[parts.length - 2]) / 100);
            newWeather.setMinute(Integer.parseInt(parts[parts.length - 2]) % 100);

            /*
             * temperature
             */
            value = getValueFromXMLTag(xml, "Temperature");
            newWeather.setTemperature(Integer.parseInt(value.substring(value.indexOf("(") + 1, value.indexOf(" C)"))));

            /*
             * wind direction and speed
             */
            value = getValueFromXMLTag(xml, "Wind");
            newWeather.setWindDirection(Integer.parseInt(value.substring(value.indexOf("(") + 1, value.indexOf(" degrees)"))));

            newWeather.setWindspeed(round(Double.parseDouble(value.substring(value.indexOf("at ") + 3, value.indexOf(" MPH"))) / 0.62, 2));

            /*
             * sky conditions
             */
            value = getValueFromXMLTag(xml, "SkyConditions");
            newWeather.setCondition(value.trim());

            weather = newWeather;
        } catch (Exception e) {
            weather = null;
            Logger.getLogger(GlobalWeatherService.class.getName()).log(Level.SEVERE, "Error in updateWeather(): " + e.getMessage());
        }

    }

    private static double round(double value, int digits) {
        return (double) ((int) value + (Math.round(Math.pow(10, digits) * (value - (int) value))) / (Math.pow(10, digits)));
    }

    private static String getValueFromXMLTag(String line, String tag) {
        String startTag = "<" + tag + ">", endTag = "</" + tag + ">";
        String value = null;

        if (!line.contains(startTag) || !line.contains(endTag)) {
            return null;
        }

        int start = line.indexOf(startTag) + startTag.length();
        int end = line.indexOf(endTag);

        if (start >= end) {
            return null;
        }

        value = line.substring(start, end);

        return value;
    }

    public int getWidgetSize() {
        return widgetSize;
    }

    public int getTemperature() {
        try {
            return weather.getTemperature();
        } catch (Exception e) {
            return 0;
        }
    }

    public int getWindDirection() {
        try {
            return weather.getWindDirection();
        } catch (Exception e) {
            return 0;
        }
    }

    public int getWindspeed() {
        try {
            return (int) weather.getWindspeed();
        } catch (Exception e) {
            return 0;
        }
    }

    public String getCondition() {
        try {
            return weather.getCondition();
        } catch (Exception e) {
            return "";
        }
    }
    
    public String getGmtTime(){
        try {
            return weather.getYear() + "/"
                    + getFormattedTimeValue(weather.getMonth()) + "/"
                    + getFormattedTimeValue(weather.getDay()) + " "
                    + getFormattedTimeValue(weather.getHour()) + ":"
                    + getFormattedTimeValue(weather.getMinute());
        } catch (NullPointerException e) {
            return "N/A";
        }
        
    }

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
        Logger.getLogger(GlobalWeatherService.class.getName()).log(Level.INFO, "City has been set: " + this.city);
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCountry() throws NullPointerException {
        return country;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getCityName() {
        return cityName;
    }

    public int getNumberOfCities() {
        try {
            return cities.size();
        } catch (Exception e) {
            Logger.getLogger(GlobalWeatherService.class.getName()).log(Level.SEVERE, "Error in getNumberOfCities(): " + e.getMessage());
            return 0;
        }
    }

    public List<City> getCities() {
        return cities;
    }

    public boolean getUpdateWeatherButtonDisabled() {
        return cities.size() <= 0;
    }

    public boolean getLoadCityButtonDisabled() {
        return country.length() <= 0;
    }

    public String getWeatherPanelVisibility() {
        if (weather == null) {
            return "none";
        }
        return "anything";
    }

    public void loadCities() {
        try {
            Logger.getLogger(GlobalWeatherService.class.getName()).log(Level.INFO, "Starting to load cities for country " + country);

            String[] entries = soap.getCitiesByCountry(country).split("<Table>");
            String startTag = "<City>", endTag = "</City>";
            cities.clear();

            for (int i = 0; i < entries.length; i++) {
                String city = entries[i];
                if (city.contains(startTag) && city.contains(endTag)) {
                    entries[i] = city.substring(city.indexOf(startTag) + startTag.length(), city.indexOf(endTag));
                } else {
                    entries[i] = "";
                }
            }

            /*
             * sort the cities in alphabetical order
             */
            boolean sorted = false;
            do {
                sorted = true;
                for (int i = 0; i < entries.length - 1; i++) {
                    if (entries[i].compareTo(entries[i + 1]) > 0) {
                        sorted = false;

                        String tmp = entries[i];
                        entries[i] = entries[i + 1];
                        entries[i + 1] = tmp;
                    }
                }
            } while (!sorted);

            for (String city : entries) {
                if (city.length() > 0) {
                    cities.add(new City(city));
                }
            }

            Logger.getLogger(GlobalWeatherService.class.getName()).log(Level.INFO, getNumberOfCities() + " cities loaded");
        } catch (Exception e) {
            Logger.getLogger(GlobalWeatherService.class.getName()).log(Level.SEVERE, "Error in loadCities(): " + e.getMessage());
        }
    }

    private String getFormattedTimeValue(int value) {
        if(value<10)
            return "0"+value;
        return ""+value;
    }
}
