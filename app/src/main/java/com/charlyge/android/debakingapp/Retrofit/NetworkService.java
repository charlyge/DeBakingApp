package com.charlyge.android.debakingapp.Retrofit;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NetworkService {
    private static NetworkService networkService;
    private final NetworkCallbacks networkCallbacks;

    public synchronized static NetworkService getInstance() {

        if(networkService==null){
            networkService= new NetworkService();
        }
        return networkService;
    }

    private NetworkService(){
        Retrofit retrofit = new Retrofit.Builder().baseUrl("https://d17h27t6h515a5.cloudfront.net/")
                .addConverterFactory(GsonConverterFactory.create()).build();
        networkCallbacks = retrofit.create(NetworkCallbacks.class);

    }

    public NetworkCallbacks getNetworkCallbacks() {
        return networkCallbacks;
    }
}
