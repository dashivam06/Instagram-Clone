package com.shivam.instagram;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class InstagramApplication 
{	

	public static void main(String[] args) {
		SpringApplication.run(InstagramApplication.class, args);

		// long milliSec = Instant.now().toEpochMilli();

        // System.out.println(milliSec);
        // System.out.println(milliSec + 1000 * 60 * 30);


		// System.out.println(new Date());
		// System.out.println(System.currentTimeMillis());
		// System.out.println(new Date(System.currentTimeMillis()));
		// System.out.println(new Date(System.currentTimeMillis()+60* 10000));

		// Time time = new Time();
		// System.out.println(Instant.now());
		// System.out.println(time.getGmtDateInMilliSec() );
		// System.out.println(new Date(time.getGmtDateInMilliSec()) );
		// System.out.println(new Date(time.getGmtDateInMilliSec() + 1000 * 60 * 30) );




	}

}
