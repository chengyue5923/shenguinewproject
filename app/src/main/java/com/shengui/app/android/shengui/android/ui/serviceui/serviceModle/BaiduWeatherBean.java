package com.shengui.app.android.shengui.android.ui.serviceui.serviceModle;

import java.util.List;

/**
 * Created by Administrator on 2017/4/16.
 */

public class BaiduWeatherBean {

    /**
     * error : 0
     * status : success
     * date : 2017-04-16
     * results : [{"currentCity":"佛山市","pm25":"65","index":[{"title":"穿衣","zs":"舒适","tipt":"穿衣指数","des":"建议着长袖T恤、衬衫加单裤等服装。年老体弱者宜着针织长袖衬衫、马甲和长裤。"},{"title":"洗车","zs":"较适宜","tipt":"洗车指数","des":"较适宜洗车，未来一天无雨，风力较小，擦洗一新的汽车至少能保持一天。"},{"title":"感冒","zs":"较易发","tipt":"感冒指数","des":"天气转凉，空气湿度较大，较易发生感冒，体质较弱的朋友请注意适当防护。"},{"title":"运动","zs":"较适宜","tipt":"运动指数","des":"阴天，较适宜进行各种户内外运动。"},{"title":"紫外线强度","zs":"弱","tipt":"紫外线强度指数","des":"紫外线强度较弱，建议出门前涂擦SPF在12-15之间、PA+的防晒护肤品。"}],"weather_data":[{"date":"周日 04月16日 (实时：28℃)","dayPictureUrl":"http://api.map.baidu.com/images/weather/day/yin.png","nightPictureUrl":"http://api.map.baidu.com/images/weather/night/yin.png","weather":"阴","wind":"微风","temperature":"28 ~ 23℃"},{"date":"周一","dayPictureUrl":"http://api.map.baidu.com/images/weather/day/duoyun.png","nightPictureUrl":"http://api.map.baidu.com/images/weather/night/duoyun.png","weather":"多云","wind":"微风","temperature":"30 ~ 23℃"},{"date":"周二","dayPictureUrl":"http://api.map.baidu.com/images/weather/day/duoyun.png","nightPictureUrl":"http://api.map.baidu.com/images/weather/night/duoyun.png","weather":"多云","wind":"微风","temperature":"31 ~ 24℃"},{"date":"周三","dayPictureUrl":"http://api.map.baidu.com/images/weather/day/zhenyu.png","nightPictureUrl":"http://api.map.baidu.com/images/weather/night/zhenyu.png","weather":"阵雨","wind":"微风","temperature":"31 ~ 22℃"}]}]
     */

    private int error;
    private String status;
    private String date;
    private List<ResultsBean> results;

    public int getError() {
        return error;
    }

    public void setError(int error) {
        this.error = error;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public List<ResultsBean> getResults() {
        return results;
    }

    public void setResults(List<ResultsBean> results) {
        this.results = results;
    }

    public static class ResultsBean {
        /**
         * currentCity : 佛山市
         * pm25 : 65
         * index : [{"title":"穿衣","zs":"舒适","tipt":"穿衣指数","des":"建议着长袖T恤、衬衫加单裤等服装。年老体弱者宜着针织长袖衬衫、马甲和长裤。"},{"title":"洗车","zs":"较适宜","tipt":"洗车指数","des":"较适宜洗车，未来一天无雨，风力较小，擦洗一新的汽车至少能保持一天。"},{"title":"感冒","zs":"较易发","tipt":"感冒指数","des":"天气转凉，空气湿度较大，较易发生感冒，体质较弱的朋友请注意适当防护。"},{"title":"运动","zs":"较适宜","tipt":"运动指数","des":"阴天，较适宜进行各种户内外运动。"},{"title":"紫外线强度","zs":"弱","tipt":"紫外线强度指数","des":"紫外线强度较弱，建议出门前涂擦SPF在12-15之间、PA+的防晒护肤品。"}]
         * weather_data : [{"date":"周日 04月16日 (实时：28℃)","dayPictureUrl":"http://api.map.baidu.com/images/weather/day/yin.png","nightPictureUrl":"http://api.map.baidu.com/images/weather/night/yin.png","weather":"阴","wind":"微风","temperature":"28 ~ 23℃"},{"date":"周一","dayPictureUrl":"http://api.map.baidu.com/images/weather/day/duoyun.png","nightPictureUrl":"http://api.map.baidu.com/images/weather/night/duoyun.png","weather":"多云","wind":"微风","temperature":"30 ~ 23℃"},{"date":"周二","dayPictureUrl":"http://api.map.baidu.com/images/weather/day/duoyun.png","nightPictureUrl":"http://api.map.baidu.com/images/weather/night/duoyun.png","weather":"多云","wind":"微风","temperature":"31 ~ 24℃"},{"date":"周三","dayPictureUrl":"http://api.map.baidu.com/images/weather/day/zhenyu.png","nightPictureUrl":"http://api.map.baidu.com/images/weather/night/zhenyu.png","weather":"阵雨","wind":"微风","temperature":"31 ~ 22℃"}]
         */

        private String currentCity;
        private String pm25;
        private List<IndexBean> index;
        private List<WeatherDataBean> weather_data;

        public String getCurrentCity() {
            return currentCity;
        }

        public void setCurrentCity(String currentCity) {
            this.currentCity = currentCity;
        }

        public String getPm25() {
            return pm25;
        }

        public void setPm25(String pm25) {
            this.pm25 = pm25;
        }

        public List<IndexBean> getIndex() {
            return index;
        }

        public void setIndex(List<IndexBean> index) {
            this.index = index;
        }

        public List<WeatherDataBean> getWeather_data() {
            return weather_data;
        }

        public void setWeather_data(List<WeatherDataBean> weather_data) {
            this.weather_data = weather_data;
        }

        public static class IndexBean {
            /**
             * title : 穿衣
             * zs : 舒适
             * tipt : 穿衣指数
             * des : 建议着长袖T恤、衬衫加单裤等服装。年老体弱者宜着针织长袖衬衫、马甲和长裤。
             */

            private String title;
            private String zs;
            private String tipt;
            private String des;

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getZs() {
                return zs;
            }

            public void setZs(String zs) {
                this.zs = zs;
            }

            public String getTipt() {
                return tipt;
            }

            public void setTipt(String tipt) {
                this.tipt = tipt;
            }

            public String getDes() {
                return des;
            }

            public void setDes(String des) {
                this.des = des;
            }
        }

        public static class WeatherDataBean {
            /**
             * date : 周日 04月16日 (实时：28℃)
             * dayPictureUrl : http://api.map.baidu.com/images/weather/day/yin.png
             * nightPictureUrl : http://api.map.baidu.com/images/weather/night/yin.png
             * weather : 阴
             * wind : 微风
             * temperature : 28 ~ 23℃
             */

            private String date;
            private String dayPictureUrl;
            private String nightPictureUrl;
            private String weather;
            private String wind;
            private String temperature;

            public String getDate() {
                return date;
            }

            public void setDate(String date) {
                this.date = date;
            }

            public String getDayPictureUrl() {
                return dayPictureUrl;
            }

            public void setDayPictureUrl(String dayPictureUrl) {
                this.dayPictureUrl = dayPictureUrl;
            }

            public String getNightPictureUrl() {
                return nightPictureUrl;
            }

            public void setNightPictureUrl(String nightPictureUrl) {
                this.nightPictureUrl = nightPictureUrl;
            }

            public String getWeather() {
                return weather;
            }

            public void setWeather(String weather) {
                this.weather = weather;
            }

            public String getWind() {
                return wind;
            }

            public void setWind(String wind) {
                this.wind = wind;
            }

            public String getTemperature() {
                return temperature;
            }

            public void setTemperature(String temperature) {
                this.temperature = temperature;
            }
        }
    }
}
