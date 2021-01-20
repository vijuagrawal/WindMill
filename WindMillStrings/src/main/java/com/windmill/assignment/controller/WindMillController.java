package com.windmill.assignment.controller;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WindMillController {

	@PostMapping(value = "/windmill/api")
	public ResponseEntity<String> getlngestString(@RequestBody  ArrayList<String> listStr) throws Exception {
		String longestStr = new String();
		if (listStr != null) {
			System.out.println(listStr);
			longestStr = listStr.stream().max(Comparator.comparingInt(String::length)).get();
		}

		return new ResponseEntity<String>(longestStr, HttpStatus.OK);

	}

	@DeleteMapping(value = "/windmill/api")
	public ResponseEntity<List<String>> deleteString(@RequestBody  ArrayList<String> listStr) throws Exception {
		List<String> resultList = new ArrayList<String>();
		if (listStr != null) {
			resultList = listStr.stream().filter(x -> x.length() <= 10).collect(Collectors.toList());
			System.out.println(resultList);
		}

		return new ResponseEntity<List<String>>(resultList, HttpStatus.OK);

	}
}
