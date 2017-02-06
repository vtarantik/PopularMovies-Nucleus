package com.vtarantik.popularmovies.di;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.vtarantik.popularmovies.domain.api.ApiDescription;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by vtarantik on 10.1.2017.
 * Service module which provides the retrofit, sets up the http client and every other required
 * network tools
 */
@Module
public class ServiceModule {
    public static final String TAG = ServiceModule.class.getName();

    public static final Gson GSON = new GsonBuilder()
            .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
            .create();

    @Provides
    @Singleton
    public ApiDescription provideApiDescription() {
        return createApiDescription();
    }

    public static ApiDescription createApiDescription() {
        Gson gson = new GsonBuilder()
                .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                .create();
        return new Retrofit.Builder()
                .baseUrl(ApiDescription.ENDPOINT_URL)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(generateClient())
                .build()
                .create(ApiDescription.class);
    }

    private static OkHttpClient generateClient() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        return new OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .build();
    }
}
