package com.example.rxjava;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.core.SingleObserver;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.functions.Action;
import io.reactivex.rxjava3.functions.BiFunction;

public class MainActivity extends AppCompatActivity {
    public Button justButton,multipleSingles;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        justButton = findViewById(R.id.justButton);
        multipleSingles=findViewById(R.id.multipleSingles);

        justButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this,"Just",Toast.LENGTH_SHORT).show();
                Observable.just("a","b","c","d","e","f","g","h","i","j")
                        .subscribe(new Observer<String>() {
                            @Override
                            public void onSubscribe(@NonNull Disposable d) {
                                Log.d("just", "onSubscribe: ");
                            }

                            @Override
                            public void onNext(@NonNull String s) {
                                Log.d("just", "onNext: " + s);
                            }

                            @Override
                            public void onError(@NonNull Throwable e) {
                                Log.d("just", "onError: " + e.toString());
                            }

                            @Override
                            public void onComplete() {
                                Log.d("just", "onComplete: ");
                            }
                        });
            }
        });

        multipleSingles.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Observable<Object> obs1 = Single.create(emitter -> {
                    Log.d("single", "o1");
                    emitter.onSuccess("o1");
                }).toObservable();
                Observable<Object> obs2 = Single.create(emitter -> {
                    Log.d("single", "o2");
                    emitter.onSuccess("o2");
                }).toObservable();
                Observable<Object> obs3 = Single.create(emitter -> {
                    Log.d("single", "o3");
                    emitter.onSuccess("o3");
                }).toObservable();
                Observable<Object> obs4 = Single.create(emitter -> {
                    Log.d("single", "o4");
                    emitter.onSuccess("o4");
                }).toObservable();
                Observable<Object> obs5 = Single.create(emitter -> {
                    Log.d("single", "o5");
                    emitter.onSuccess("o5");
                }).toObservable();

                ArrayList<Observable> obsList = new ArrayList<>();
                obsList.add(obs1);
                obsList.add(obs2);
                obsList.add(obs3);
                obsList.add(obs4);
                obsList.add(obs5);

                Observable.merge(Arrays.asList(obs1, obs2, obs3,obs4,obs5 ))
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Observer<Object>() {
                            @Override
                            public void onSubscribe(@NonNull Disposable d) {
                                Log.d("single", "onSubscribe observables: ");
                            }

                            @Override
                            public void onNext(@NonNull Object o) {
                                Log.d("single", "onNext: " +  o);
                            }

                            @Override
                            public void onError(@NonNull Throwable e) {
                            }

                            @Override
                            public void onComplete() {
                                Log.d("single", "finished: ");
                            }
                        });


                Single<Object> sin1 = Single.create(emitter -> {
                    Log.d("single", "s1");
                    emitter.onSuccess("s1");
                });
                Single<Object> sin2 = Single.create(emitter -> {
                    Log.d("single", "s2");
                    emitter.onSuccess("s2");
                });
                ArrayList<Single> sinList = new ArrayList<>();
                sinList.add(sin1);
                sinList.add(sin2);

                Observable.merge(sin1.toObservable(),sin2.toObservable())
                        .subscribe(new Observer<Object>() {
                            @Override
                            public void onSubscribe(@NonNull Disposable d) {
                                Log.d("single", "onSubscribe: singles" );
                            }

                            @Override
                            public void onNext(Object o) {
                                Log.d("single", "onNext: " +  o);
                            }

                            @Override
                            public void onError(@NonNull Throwable e) {
                            }

                            @Override
                            public void onComplete() {
                            }
                        });
            }
        });


    }
}
