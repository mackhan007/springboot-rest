package com.example.demo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;
import java.util.logging.Logger;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class DemoApplication {

	private static final String TEMPLATE = "Hello sir ji, %s!";
	private final AtomicLong counter = new AtomicLong();

	private final HashMap<String, Long> dataSet = new HashMap<>();
	private final TrieNode trie = new TrieNode();

	Logger logger = Logger.getLogger(getClass().getName());

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

	@GetMapping("/hello")
	public String hello(@RequestParam(value = "name", defaultValue = "World") String name) {
		return String.format("Hello %s!", name);
	}

	@GetMapping("/greeting")
	public Greeting greeting(@RequestParam(value = "name", defaultValue = "World") String name) {
		String nameLower = name.toLowerCase();
		Long id = dataSet.get(name);

		if (id == null) {
			id = counter.incrementAndGet();
			dataSet.put(nameLower, id);
			trie.addName(nameLower);
		}

		return new Greeting(id, String.format(TEMPLATE, name));
	}

	@GetMapping("/get-all-users")
	public Map<String, Long> getAllUsers() {
		return dataSet;
	}

	@GetMapping("/search")
	public Map<String, Long> search(@RequestParam(value = "name", defaultValue = "World") String name) {
		List<String> names = trie.getNames(name.toLowerCase(), trie);
		HashMap<String, Long> returnValue = new HashMap<>();

		for (String nameArg : names) {
			returnValue.put(nameArg, dataSet.get(nameArg));
		}

		return returnValue;
	}

}
