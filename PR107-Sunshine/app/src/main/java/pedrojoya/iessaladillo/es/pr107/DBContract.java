/*
 * Copyright (C) 2014 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package pedrojoya.iessaladillo.es.pr107;

import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DBContract {

    // Nombre completo del proveedor.
    public static final String CONTENT_AUTHORITY = "pedrojoya.iessaladillo.es.pr107.app";

    // URI base.
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    // Paths correctos.
    public static final String PATH_METEO = "meteo";
    public static final String PATH_LOCALIDAD = "localidad";

    // Formato de almacenamiento de las fechas en la BD.
    public static final String FORMATO_FECHA = "yyyyMMdd";

    // Retorna la cadena correspondiente a la fecha en el formato correcto.
    public static String dateToString(Date date){
        SimpleDateFormat sdf = new SimpleDateFormat(FORMATO_FECHA);
        return sdf.format(date);
    }

    // Retorna el objeto Date correspondiente a una cadena de fecha en el formato correcto.
    public static Date stringToDate(String dateText) {
        SimpleDateFormat dbDateFormat = new SimpleDateFormat(FORMATO_FECHA);
        try {
            return dbDateFormat.parse(dateText);
        } catch ( ParseException e ) {
            e.printStackTrace();
            return null;
        }
    }

    // Tabla Localidad.
    public static final class Localidad implements BaseColumns {

        public static final String NOMBRE_TABLA = "localidad";

        public static final String TEXTO_CONSULTA = "texto_consulta";
        public static final String NOMBRE_CIUDAD = "nombre_ciudad";
        public static final String COORD_LATITUD = "coord_latitud";
        public static final String COORD_LONGITUD = "coord_longitud";


        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_LOCALIDAD).build();
        public static final String CONTENT_TYPE =
                "vnd.android.cursor.dir/" + CONTENT_AUTHORITY + "/" + PATH_LOCALIDAD;
        public static final String CONTENT_ITEM_TYPE =
                "vnd.android.cursor.item/" + CONTENT_AUTHORITY + "/" + PATH_LOCALIDAD;

        public static Uri getLocalidadUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }

    }

    // Tabla Meteo.
    public static final class Meteo implements BaseColumns {

        public static final String NOMBRE_TABLA = "meteo";

        // Column with the foreign key into the location table.
        public static final String LOCALIDAD_ID = "localidad_id";
        // Date, stored as Text with format yyyy-MM-dd
        public static final String FECHA = "fecha";
        // Weather id as returned by API, to identify the icon to be used
        public static final String METEO_ID = "meteo_id";

        // Short description and long description of the weather, as provided by API.
        // e.g "clear" vs "sky is clear".
        public static final String DESCRIPCION_CORTA = "descripcion_corta";

        // Min and max temperatures for the day (stored as floats)
        public static final String TEMPERATURA_MINIMA = "temp_min";
        public static final String TEMPERATURA_MAXIMA = "temp_max";

        // Humidity is stored as a float representing percentage
        public static final String HUMEDAD = "humedad";

        // Humidity is stored as a float representing percentage
        public static final String PRESION = "presion";

        // Windspeed is stored as a float representing windspeed  mph
        public static final String VELOCIDAD_VIENTO = "velocidad_viento";

        // Degrees are meteorological degrees (e.g, 0 is north, 180 is south).  Stored as floats.
        public static final String GRADOS = "grados";

        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_METEO).build();

        public static final String CONTENT_TYPE =
                "vnd.android.cursor.dir/" + CONTENT_AUTHORITY + "/" + PATH_METEO;
        public static final String CONTENT_ITEM_TYPE =
                "vnd.android.cursor.item/" + CONTENT_AUTHORITY + "/" + PATH_METEO;

        public static Uri getMeteoUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }

        public static Uri getMeteoLocalidadUri(String localidad) {
            return CONTENT_URI.buildUpon().appendPath(localidad).build();
        }

        public static Uri getMeteoLocalidadConFechaInicioUri(
                String localidad, String fechaInicio) {
            return CONTENT_URI.buildUpon().appendPath(localidad)
                    .appendQueryParameter(FECHA, fechaInicio).build();
        }

        public static Uri getMeteoLocalidadConFecha(String locationSetting, String date) {
            return CONTENT_URI.buildUpon().appendPath(locationSetting).appendPath(date).build();
        }

        public static String getLocalidadDesdeUri(Uri uri) {
            return uri.getPathSegments().get(1);
        }

        public static String getFechaDesdeUri(Uri uri) {
            return uri.getPathSegments().get(2);
        }

        public static String getFechaInicioDesdeUri(Uri uri) {
            return uri.getQueryParameter(FECHA);
        }
    }
}
