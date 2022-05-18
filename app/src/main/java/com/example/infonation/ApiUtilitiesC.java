package com.example.infonation;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiUtilitiesC {




        public static Retrofit retrofit=null;

        public static ApiInterfaceC getAPIInterface()
        {
            if (retrofit==null)
            {
                retrofit=new Retrofit.Builder().baseUrl(ApiInterfaceC.BASE_URL_C).addConverterFactory(GsonConverterFactory.create()).build();
            }
            return retrofit.create(ApiInterfaceC.class);

        }


    }

