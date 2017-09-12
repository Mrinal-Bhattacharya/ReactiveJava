package com.reactive.simple;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Observable.OnSubscribe;
import rx.Subscriber;
import rx.functions.Func0;

public class FilterRex {

	public static void main(String[] args) throws Exception {
		FilterRex filterRex = new FilterRex();
		/*
		 * filterRex.observaleInterval(); filterRex.observaleTimer();
		 * Thread.sleep(5000);// So that interval can print elements
		 * filterRex.observaleCreateOnNext();
		 * filterRex.observaleCreateOnNextAndComplete();
		 * filterRex.observableDeferIssue(); filterRex.observableDefer();
		 * filterRex.map();
		 */
		filterRex.flatMap();
		filterRex.concatMap();
		filterRex.zip();
		filterRex.concat();
		filterRex.distinct();
	}

	public void filterLambdaWithError() {
		List<Object> input = Arrays.asList(1, 2, 3, 4, "sadsa");
		Observable.from(input).filter(x -> (Integer) x % 2 == 0).subscribe(new Subscriber<Object>() {

			@Override
			public void onCompleted() {
				System.out.println("Completed");
			}

			@Override
			public void onError(Throwable e) {
				System.out.println("OnError " + e.getMessage());
			}

			@Override
			public void onNext(Object t) {
				System.out.println("OnNext " + t);
			}
		});
	}

	public void filterLambdaWithInteger() {
		List<Integer> input = Arrays.asList(1, 2, 3, 4, 5);
		Observable.from(input).filter(x -> x % 2 == 0).subscribe(integerSubscribe());
	}

	public void observaleJustWithList() {
		List<Integer> input = Arrays.asList(1, 2, 3, 4, 5);
		Observable.just(input).subscribe(new Subscriber<List<Integer>>() {

			@Override
			public void onCompleted() {
				System.out.println("Completed");
			}

			@Override
			public void onError(Throwable e) {
				System.out.println("OnError " + e.getMessage());
			}

			@Override
			public void onNext(List<Integer> t) {
				System.out.println("OnNext " + t);
			}
		});
	}

	public void observaleJust() {
		// just limitastion is just 10 object max after that give u compile time
		// error
		Observable.just(1, 2, 3, 4, 5, 6, 7, 8, 9, 10).subscribe(integerSubscribe());
	}

	public void observaleRange() {
		Observable.range(1, 5).subscribe(integerSubscribe());
	}

	public void observaleInterval() {
		Observable.interval(1, TimeUnit.SECONDS).subscribe(longSubscribe());
	}

	public void observaleTimer() {
		Observable.interval(1, TimeUnit.SECONDS).subscribe(longSubscribe());
	}

	public void observaleCreateOnNext() {
		Observable.create(t -> t.onNext("hello"));
	}

	public void observaleCreateOnNextAndComplete() {
		Observable.create(new OnSubscribe<String>() {

			@Override
			public void call(Subscriber<? super String> t) {
				t.onNext("Hello");
				t.onCompleted();
			}
		});
	}

	public Observable<Object> getDataEmpty(Object data) {
		if (data == null) {
			return Observable.empty();
		}
		return Observable.just(data);
	}

	public Observable<Object> getDataError(Object data) {
		if (data == null) {
			return Observable.error(new Exception("no data !!!!"));
		}
		return Observable.just(data);
	}

	public void observableDeferIssue() {
		Person person = new Person();
		Observable<String> nameOfPerson = Observable.just(person.getName());
		Observable<Integer> ageOfPerson = Observable.just(person.getAge());

		person.setAge(35);
		person.setName("Bob");

		subscribe(nameOfPerson, ageOfPerson);

	}

	// The Defer operator waits until an observer subscribes to it, and then it
	// generates an Observable
	public void observableDefer() {
		Person person = new Person();
		Observable<String> nameOfPerson = Observable.defer(() -> Observable.just(person.getName()));
		Observable<Integer> ageOfPerson = Observable.defer(new Func0<Observable<Integer>>() {

			@Override
			public Observable<Integer> call() {
				return Observable.just(person.getAge());
			}
		});

		person.setAge(35);
		person.setName("Bob");

		subscribe(nameOfPerson, ageOfPerson);

	}

	private void subscribe(Observable<String> nameOfPerson, Observable<Integer> ageOfPerson) {
		ageOfPerson.subscribe(integerSubscribe());

		nameOfPerson.subscribe(new Subscriber<String>() {

			@Override
			public void onCompleted() {
				// TODO Auto-generated method stub

			}

			@Override
			public void onError(Throwable e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onNext(String t) {
				System.out.println(t);
			}
		});
	}

	public void map() {
		Observable.just(1, 2, 3).map(x -> x * 10).subscribe(integerSubscribe());
	}

	// The FlatMap operator transforms an Observable by applying a function that
	// you specify to each item emitted by the source Observable.
	// It will first process list1 then process list2 same time means any order.
	public void flatMap() {
		List<Integer> list = Arrays.asList(1, 2, 3, 7, 8, 9, 10);
		List<Integer> list2 = Arrays.asList(4, 5, 6);
		Observable.just(list, list2).flatMap(x -> Observable.from(x)).map(x -> x * 10).subscribe(integerSubscribe());
	}

	// It will first process list1 then process list2. list1 will process in any
	// order. but list1 will always five first result then it will go for list2.
	//https://fernandocejas.com/2015/01/11/rxjava-observable-tranformation-concatmap-vs-flatmap/
	public void concatMap() {
		List<Integer> list1 = Arrays.asList(1, 2, 3, 7, 8, 9, 10);
		List<Integer> list2 = Arrays.asList(4, 5, 6);
		Observable.just(list1, list2).concatMap(x -> Observable.from(x)).map(x -> x * 10).subscribe(integerSubscribe());
	}

	public void zip(){
		Observable<Integer> majorVersion = Observable.range(1, 3);
		Observable<Integer> minorVersion = Observable.range(5, 10);
		/*Observable.zip(majorVersion, minorVersion, new Func2<Integer, Integer, String>() {

			@Override
			public String call(Integer t1, Integer t2) {
				// TODO Auto-generated method stub
				return majorVersion+"."+minorVersion;
			}
		});*/
		Observable.zip(majorVersion, minorVersion, (t1, t2) -> t1+"."+t2).subscribe(t -> System.out.println("Version is "+t));
		
	}
	
	public void concat(){
		Observable<String> first = Observable.just("one", "two","three");
		Observable<String> second = Observable.just("four","five");
		Observable.concat(first,second).subscribe(t -> System.out.println("Value "+t));
	}
	
	public void distinct(){
		List<Integer> range=Arrays.asList(1,2,3,4,1,3,5);
		Observable<Integer> distinctNumber = Observable.from(range);
		distinctNumber.distinct().subscribe(t -> System.out.println("Value "+t));
	}
	private Subscriber<Long> longSubscribe() {
		return new Subscriber<Long>() {

			@Override
			public void onCompleted() {
				System.out.println("Completed");
			}

			@Override
			public void onError(Throwable e) {
				System.out.println("OnError " + e.getMessage());
			}

			@Override
			public void onNext(Long t) {
				System.out.println("OnNext " + t);
			}
		};
	}

	private Subscriber<Integer> integerSubscribe() {
		return new Subscriber<Integer>() {

			@Override
			public void onCompleted() {
				System.out.println("Completed");
			}

			@Override
			public void onError(Throwable e) {
				System.out.println("OnError " + e.getMessage());
			}

			@Override
			public void onNext(Integer t) {
				System.out.println("OnNext " + t);
			}
		};
	}
}

class Person {

	private Integer age;
	private String name;

	public Person(String name, Integer age) {
		this.name = name;
		this.age = age;
	}

	public Person() {
		// TODO Auto-generated constructor stub
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
