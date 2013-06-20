Android Yahoo Weather API
======================
Yahoo WeatherAPIから天気を取得します。

## 使い方(How to use?) ##
### YahooWeatherAPIからのデータ取得 ###
    /*
     * My information about Yahoo Weather API
     */
    String key      = "dj0zaiZpPW1lSUozbFZabTd1RSZkPVlXazlVVlpZTkVFeE5XRW1jR285TUEtLSZzPWNvbnN1bWVyc2VjcmV0Jng9OWY-";
    String position = "140.22036,39.611813";

    /*
     * Get weather information from Yahoo API
     */
    YahooWeatherAPI yahooapi = new YahooWeatherAPI(key, position);
    Weathers weathers = yahooapi.getWeathers();

    /*
     * Weather information
     */
    for (Weather weather:weathers) {
        System.out.println(weather.toString());
    }


関連情報
--------
特になし

ライセンス
----------
Copyright &copy; 2013 Sawada Naoya
Licensed under the [Apache License, Version 2.0][Apache]
Distributed under the [MIT License][mit].
Dual licensed under the [MIT license][MIT] and [GPL license][GPL].

[Apache]: http://www.apache.org/licenses/LICENSE-2.0
[MIT]: http://www.opensource.org/licenses/mit-license.php
[GPL]: http://www.gnu.org/licenses/gpl.html
