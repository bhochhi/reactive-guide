package com.codewithe.bhochhi;

import rx.Observable;
import rx.functions.Action1;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        sayHello("Xing","Sameeri","Me");
    }
    
    
    
    public static void sayHello(String... names) {
    	Observable.from(names).subscribe(new Action1<String>() {
			public void call(String name) {
				System.out.println("Hello "+name+"!");
				
			}
		
    	});
    	
    	Observable<String> Obsers = Observable.from(names);
    	Obsers.subscribe(System.out::println);
    }
}
