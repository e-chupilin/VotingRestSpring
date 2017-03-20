package by.ontravelsolutions.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import org.apache.log4j.Logger;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import by.ontravelsolutions.enums.Choice;
import by.ontravelsolutions.exceptions.VotingRestException;
import by.ontravelsolutions.interfaces.VotingService;
import by.ontravelsolutions.model.Vote;
import static by.ontravelsolutions.constants.Constants.*;

/**
 * REST controller for managing vote and vote`s themes
 * 
 */
@RestController
public class VotingServiceImpl extends ExceptionHandlerController implements VotingService {

	private List<String> votesThemes = new ArrayList<>();
	private Map<Integer, Vote> votes = new HashMap<>();
	private Integer id = 0;
	private static final Logger LOGGER = Logger.getLogger(VotingServiceImpl.class);

	/**
	 * Creates a theme for voting
	 * 
	 * @param name
	 *            The name of theme
	 * @return OK
	 */
	@RequestMapping(method = RequestMethod.POST, value = "/theme", consumes = "application/json")
	public ResponseEntity<String> createTheme(@RequestBody String name) throws VotingRestException {

		if (votesThemes.contains(name))
			throw new VotingRestException(ERROR_CREATE_THEME + name);

		votesThemes.add(name);
		LOGGER.info("Create vote theme : " + name);
		return new ResponseEntity<String>(OK_CREATE_THEME + name, HttpStatus.OK);
	}

	/**
	 * Returns all themes for voting
	 * 
	 * @return List Array of themes
	 */
	@RequestMapping(method = RequestMethod.GET, value = "/themes")
	public List<String> getAllTheme() {
		return votesThemes;
	}

	/**
	 * Creates and starts voting
	 * 
	 * @param name
	 *            The name of vote
	 * @return Ok, in header vote id, in body vote url.
	 */
	@RequestMapping(method = RequestMethod.POST, value = "/vote")
	public ResponseEntity<String> createVote(@RequestBody String name, RequestEntity<String> requestEntity)
			throws VotingRestException {
		votes.put(++id, new Vote(name));
		LOGGER.info("Create vote : " + id + DASH + votes.get(id));
		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.set(HEADER_ID, id.toString());
		return new ResponseEntity<>(requestEntity.getUrl().toString(), responseHeaders, HttpStatus.OK);
	}

	/**
	 * Stop a vote
	 * 
	 * @param id
	 *            The vote id to stop
	 * @return OK
	 */
	@RequestMapping(method = RequestMethod.DELETE, value = "/vote")
	public String stopVote(@RequestParam(value = "id", required = true) int id) throws VotingRestException {
		Vote vote = checkContainVote(id);
		vote.stop();
		LOGGER.info("Stop vote: " + id + DASH + vote);
		return OK_STOP_VOTE + id;
	}

	/**
	 * Get vote by id
	 * 
	 * @param id
	 *            The vote id to get
	 * @return Vote
	 */
	@RequestMapping(method = RequestMethod.GET, value = "/vote", consumes = "application/json")
	public Vote getVote(@RequestParam(value = "id", required = true) int id) throws VotingRestException {
		Vote vote = checkContainVote(id);
		LOGGER.info("Get vote: " + id + DASH + vote);
		return vote;
	}

	/**
	 * Set new choice in vote.
	 * 
	 * @param id
	 *            Vote id
	 * @param name
	 *            User who make choice
	 * @param choice
	 *            User choice
	 * @return OK
	 */
	@RequestMapping(method = RequestMethod.PUT, value = "/vote")
	public ResponseEntity<String> setChoice(@RequestParam(value = "id", required = true) int id,
			@RequestParam(value = "name", required = true) String name,
			@RequestParam(value = "choice", required = true) String choice) throws VotingRestException {
		Vote vote = checkContainVote(id);

		try {
			vote.setChoice(name, Choice.valueOf(choice.trim().toUpperCase(Locale.ENGLISH)));
		} catch (IllegalArgumentException e) {
			throw new VotingRestException(ERROR_VOTE_NOT_FOUND + id);
		}

		LOGGER.info("Set Choice : " + name + DASH + choice);
		return new ResponseEntity<>(OK_SET_CHOICE, HttpStatus.CREATED);
	}

	/**
	 * Get statistic of choice in vote
	 * 
	 * @param id
	 *            Vote id
	 * @param choice
	 *            Choice for calculation
	 * @return OK
	 */

	@RequestMapping(method = RequestMethod.GET, value = "/voteStatistic")
	public int getVoteStatic(@RequestParam(value = "id", required = true) int id,
			@RequestParam(value = "choice", required = true) String choiceString) throws VotingRestException {
		int count = 0;
		Vote vote = checkContainVote(id);
		Map<String, Choice> choices = vote.getChoices();
		Choice choice = Choice.valueOf(choiceString.trim().toUpperCase(Locale.ENGLISH));

		for (Choice c : choices.values()) {
			if (choice.equals(c))
				count++;
		}

		LOGGER.info("Quantity choice :" + count);
		return count;
	}

	private Vote checkContainVote(int id) throws VotingRestException {
		if (!votes.containsKey(id))
			throw new VotingRestException(ERROR_VOTE_NOT_FOUND + id);
		return votes.get(id);
	}

}
