package by.ontravelsolutions.controller;

import org.apache.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import by.ontravelsolutions.exceptions.VotingRestException;

@Controller
public class ExceptionHandlerController {
	private static final Logger LOGGER = Logger.getLogger(VotingServiceImpl.class);

	@ExceptionHandler(VotingRestException.class)
	public @ResponseBody @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR) ResponseEntity<String> handleException(
			VotingRestException e) {
		LOGGER.error("Error : " + e.getMessage(), e);
		return new ResponseEntity<>("Error : " + e.getMessage(), HttpStatus.BAD_REQUEST);
	}

}
