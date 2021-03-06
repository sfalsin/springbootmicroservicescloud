package com.example.demo.service;

import java.util.Arrays;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.example.demo.domain.Rating;

import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Controller
@RequestMapping("/ratings")
public class TestController {

	@ApiResponses(value = { 
			@ApiResponse(code = 200, message = "Avaliações Listadas com sucesso"),
			@ApiResponse(code = 401, message = "You are not authorized to view the resource"),
		    @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
		    @ApiResponse(code = 404, message = "The resource you were trying to reach is not found") })
	@GetMapping("/")
	public List<Rating> findAllRatings() {
		return Arrays.asList(new Rating[] {new Rating(),new Rating(),new Rating(),new Rating()});
	}
	
	@PostMapping("/")
	@ResponseStatus(HttpStatus.CREATED)
	public Rating insertRatings(@RequestBody Rating rating) {
		return new Rating();
	}
	
	@DeleteMapping("/{ratingId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deleteRating(@PathVariable("ratingId") Long ratingId) {
		//
	}
 
	@GetMapping("/search/findByBookId")
	public List<Rating> findByBookId(@RequestParam("bookId") Long bookId) {
		return Arrays.asList(new Rating[] {new Rating(),new Rating(),new Rating(),new Rating()});
	}
	
	
}
