package com.example.dhernandez.vidvintage.di.module;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.AssetManager;

import com.example.dhernandez.vidvintage.BuildConfig;
import com.example.dhernandez.vidvintage.application.MyApplication;
import com.example.dhernandez.vidvintage.presenter.PresenterFactory;

import java.net.HttpURLConnection;
import java.security.cert.CertificateException;
import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import dagger.Module;
import dagger.Provides;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by dhernandez on 30/08/2018.
 */

@Module
public class ApplicationModule {

    private final MyApplication myApplication;
    private static final String SHARED_PREFERENCES = "sp";
    private String urlBase;


    public ApplicationModule(MyApplication myApplication, String urlBase) {
        this.myApplication = myApplication;
        this.urlBase = urlBase;

    }

    @Provides
    @Singleton
    MyApplication getMyApplication(){
        return myApplication;
    }

    @Provides
    @Singleton
    Application getApplication(){
        return myApplication;
    }

    @Provides
    @Singleton
    public PresenterFactory getPresenterFactory(){
        return new PresenterFactory(this.myApplication);
    }

    @Provides
    @Singleton
    SharedPreferences getSharedPreferences(Application application){
        return application.getSharedPreferences(SHARED_PREFERENCES, Context.MODE_PRIVATE);
    }

    @Provides
    @Singleton
    AssetManager getAssetManager(){
        return myApplication.getAssets();
    }

    @Provides
    @Singleton
    Realm provideRealm() {
        Realm.init(myApplication);
        RealmConfiguration config = new RealmConfiguration.Builder()
                .modules(Realm.getDefaultModule())
                .deleteRealmIfMigrationNeeded()
                .build();
        Realm.setDefaultConfiguration(config);
        return Realm.getDefaultInstance();
    }

    @Provides
    @Singleton
    Retrofit getRetrofitService(OkHttpClient okHttpClient) {
        return new Retrofit.Builder()
                .baseUrl(urlBase)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }


    @Provides
    @Singleton
    OkHttpClient provideUnsafeOkhttpClient() {
        try {
            // Create a trust manager that does not validate certificate chains
            final TrustManager[] trustAllCerts = new TrustManager[] {
                    new X509TrustManager() {
                        @Override
                        public void checkClientTrusted(java.security.cert.X509Certificate[] chain, String authType) throws CertificateException {
                        }

                        @Override
                        public void checkServerTrusted(java.security.cert.X509Certificate[] chain, String authType) throws CertificateException {
                        }

                        @Override
                        public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                            return new java.security.cert.X509Certificate[]{};
                        }
                    }
            };

            // Install the all-trusting trust manager
            final SSLContext sslContext = SSLContext.getInstance("SSL");
            sslContext.init(null, trustAllCerts, new java.security.SecureRandom());
            // Create an ssl socket factory with our all-trusting manager
            final SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();

            OkHttpClient.Builder builder = new OkHttpClient.Builder();
            builder.sslSocketFactory(sslSocketFactory, (X509TrustManager)trustAllCerts[0]);
            builder.hostnameVerifier(new HostnameVerifier() {
                @Override
                public boolean verify(String hostname, SSLSession session) {
                    return true;
                }
            });

            if (BuildConfig.BUILD_TYPE.equalsIgnoreCase("debug")) {
                HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
                logging.setLevel(HttpLoggingInterceptor.Level.HEADERS);
                logging.setLevel(HttpLoggingInterceptor.Level.BODY);
                builder.addInterceptor(logging);
            }

            builder.connectTimeout(15, TimeUnit.SECONDS);
            builder.readTimeout(15, TimeUnit.SECONDS);
            builder.writeTimeout(15, TimeUnit.SECONDS);

            return  builder.build();

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
