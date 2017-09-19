package com.reactive.simple;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Subscriber;
import rx.functions.Func2;

public class ErrorHandling {
	public static void main(String[] args) throws Exception {
		ErrorHandling errorHandling = new ErrorHandling();
		// errorHandling.onErrorResumeNext();
		// errorHandling.onExceptionResumeNext();
		// errorHandling.onErrorReturn();
		// errorHandling.retryWithLimit();
		//errorHandling.retryWhen();
		//Thread.sleep(6000);
		List<String> a=Arrays.asList("4","5","6");
		Observable.just(1,2).zipWith(a,new Func2<Integer, String, String>() {

			@Override
			public String call(Integer t1, String t2) {
			return ""+t1+"."+t2;
			}
		}).subscribe(t-> System.out.println(t));
	}

	public void onErrorResumeNext() {
		Observable.just("1", "2", "a", "4", "5").map(x -> Integer.parseInt(x))
				.onErrorResumeNext(Observable.<Integer>empty()).subscribe(new Subscriber<Integer>() {

					@Override
					public void onCompleted() {
						System.out.println("complete");
					}

					@Override
					public void onError(Throwable e) {
						System.out.println("error " + e.getMessage());
					}

					@Override
					public void onNext(Integer t) {
						System.out.println(t);
					}
				});
	}

	public void onExceptionResumeNext() {
		Observable.just("1", "2", "a", "4", "5").map(x -> Integer.parseInt(x))
				.onExceptionResumeNext(Observable.just(-1)).subscribe(new Subscriber<Integer>() {

					@Override
					public void onCompleted() {
						System.out.println("complete");
					}

					@Override
					public void onError(Throwable e) {
						System.out.println("error " + e.getMessage());
					}

					@Override
					public void onNext(Integer t) {
						System.out.println(t);
					}
				});
	}

	public void onErrorReturn() {
		Observable.just("1", "2", "a", "4", "5").map(x -> Integer.parseInt(x)).onErrorReturn(t -> -1)
				.subscribe(new Subscriber<Integer>() {

					@Override
					public void onCompleted() {
						System.out.println("complete");
					}

					@Override
					public void onError(Throwable e) {
						System.out.println("error " + e.getMessage());
					}

					@Override
					public void onNext(Integer t) {
						System.out.println(t);
					}
				});
	}

	public void retry() {
		Observable.just("1", "2", "a", "4", "5").map(x -> Integer.parseInt(x)).retry()
				.subscribe(new Subscriber<Integer>() {

					@Override
					public void onCompleted() {
						System.out.println("complete");
					}

					@Override
					public void onError(Throwable e) {
						System.out.println("error " + e.getMessage());
					}

					@Override
					public void onNext(Integer t) {
						System.out.println(t);
					}
				});
	}

	public void retryWithLimit() {
		Observable.just("1", "2", "a", "4", "5").map(x -> Integer.parseInt(x)).retry(1)
				.subscribe(new Subscriber<Integer>() {

					@Override
					public void onCompleted() {
						System.out.println("complete");
					}

					@Override
					public void onError(Throwable e) {
						System.out.println("error " + e.getMessage());
					}

					@Override
					public void onNext(Integer t) {
						System.out.println(t);
					}
				});
	}

	public void retryWhen() {
		Observable.just("1", "2", "a", "4", "5").map(x -> Integer.parseInt(x)).retryWhen(attempts -> {
			return attempts.zipWith(Observable.range(1, 3), (n, i) -> i).flatMap(i -> {
				System.out.println("delay retry by " + i + " second(s)");
				return Observable.timer(2, TimeUnit.SECONDS);
			});
		}).subscribe(new Subscriber<Integer>() {

			@Override
			public void onCompleted() {
				System.out.println("complete");
			}

			@Override
			public void onError(Throwable e) {
				System.out.println("error " + e.getMessage());
			}

			@Override
			public void onNext(Integer t) {
				System.out.println(t);
			}
		});
	}

}
